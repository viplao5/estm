<script lang="tsx">
import { usePermissionStore } from '@/store/modules/permission'
import { useAppStore } from '@/store/modules/app'

import { ElScrollbar } from 'element-plus'
import { Icon } from '@/components/Icon'
import { Menu } from '@/layout/components/Menu'
import { pathResolve } from '@/utils/routerHelper'
import { cloneDeep } from 'lodash-es'
import { filterMenusPath, initTabMap, tabPathMap } from './helper'
import { useDesign } from '@/hooks/web/useDesign'
import { isUrl } from '@/utils/is'

export default defineComponent({
  name: 'TabMenu',
  setup() {
    const { getPrefixCls, variables } = useDesign()
    const prefixCls = getPrefixCls('tab-menu')

    const { push, currentRoute } = useRouter()
    const { t } = useI18n()
    const appStore = useAppStore()
    const permissionStore = usePermissionStore()

    const collapse = computed(() => appStore.getCollapse)
    const fixedMenu = computed(() => appStore.getFixedMenu)

    const routers = computed(() =>
      appStore.getLayout === 'mixed' ? permissionStore.getMenuTabRouters : permissionStore.getRouters
    )

    const tabRouters = computed(() => unref(routers).filter((v) => !v?.meta?.hidden))

    // --- 状态定义移到前面以避免初始化错误 ---
    // tab高亮
    const tabActive = ref('')
    // 二级菜单数据 (从一级分类中提取的子菜单)
    const secondaryMenuRouters = ref<AppRouteRecordRaw[]>([])
    // 是否显示二级菜单 (受业务逻辑和用户手动折叠共同影响)
    const showMenu = ref(unref(fixedMenu) ? true : false)
    // 用户手动折叠状态
    const userCollapsed = ref(false)

    // 获取第一个叶子节点路径
    const getFirstLeafPath = (item: AppRouteRecordRaw): string => {
      const children = item.children?.filter((v) => v && v.meta && !v.meta.hidden)
      if (children && children.length > 0) {
        return getFirstLeafPath({
          ...children[0],
          path: pathResolve(item.path, children[0].path)
        })
      }
      return item.path
    }

    // 更新二级菜单数据
    const updateSecondaryMenu = () => {
      try {
        const path = unref(currentRoute).path
        const routers = unref(tabRouters)
        if (!routers || routers.length === 0) return

        // 查找当前路径属于哪个一级(Tab)菜单
        const activeTabRoot = routers.find((v) => {
          if (!v || !v.path) return false
          const isActiveTab = v.path === path
          // 检查 pathResolve(v.path, '...') 是否包含当前路径
          const isInTabPathMap = tabPathMap[v.path] && Array.isArray(tabPathMap[v.path]) && tabPathMap[v.path].includes(path)
          return isActiveTab || isInTabPathMap
        })

        if (activeTabRoot) {
          tabActive.value = activeTabRoot.path || ''
          const children = activeTabRoot.children?.filter((v) => v && v.meta && !v.meta.hidden)
          if (children && children.length > 0) {
            secondaryMenuRouters.value = cloneDeep(children).map((v) => {
              v.path = pathResolve(activeTabRoot.path, v.path)
              return v
            })
            // 只有当有子菜单时且用户未手动收起时才显示
            if (!userCollapsed.value) {
              showMenu.value = true
            }
          } else {
            secondaryMenuRouters.value = []
            showMenu.value = false
          }
        } else {
          secondaryMenuRouters.value = []
          showMenu.value = false
        }
      } catch (e) {
        console.error('[TabMenu] updateSecondaryMenu error:', e)
      }
    }

    // 监听 tabRouters 变化
    watch(
      () => tabRouters.value,
      () => {
        if (unref(tabRouters).length > 0 && (unref(fixedMenu) || appStore.getLayout === 'mixed')) {
          updateSecondaryMenu()
        }
      },
      { immediate: true }
    )

    // 监听路由变化
    watch(
      () => currentRoute.value.path,
      () => {
        updateSecondaryMenu()
      }
    )

    onMounted(() => {
      if (unref(fixedMenu) || appStore.getLayout === 'mixed') {
        updateSecondaryMenu()
      }
    })

    watch(
      () => routers.value,
      (routers: AppRouteRecordRaw[]) => {
        if (routers && Array.isArray(routers)) {
          initTabMap(routers)
          filterMenusPath(routers, routers)
        }
      },
      {
        immediate: true,
        deep: true
      }
    )

    const showTitle = ref(true)

    watch(
      () => collapse.value,
      (collapse: boolean) => {
        if (!collapse) {
          setTimeout(() => {
            showTitle.value = !collapse
          }, 200)
        } else {
          showTitle.value = !collapse
        }
      }
    )

    // tab点击事件
    const tabClick = (item: AppRouteRecordRaw) => {
      if (!item) return
      if (isUrl(item.path)) {
        window.open(item.path)
        return
      }
      
      const newPath = item.path || ''
      tabActive.value = newPath
      
      const children = item.children?.filter(v => v && v.meta && !v.meta.hidden)
      if (children && children.length > 0) {
        secondaryMenuRouters.value = cloneDeep(children).map((v) => {
          v.path = pathResolve(newPath, v.path)
          return v
        })
        userCollapsed.value = false // 点击一级菜单时自动展开二级菜单
        showMenu.value = true
        
        // 递归跳转到第一个叶子节点
        const firstLeafPath = getFirstLeafPath(item)
        if (firstLeafPath && firstLeafPath !== newPath) {
          push(firstLeafPath)
        } else {
          push(newPath)
        }
      } else {
        secondaryMenuRouters.value = []
        showMenu.value = false
        if (newPath) push(newPath)
      }
    }

    // 设置高亮
    const isActive = (currentPath: string) => {
      const { path } = unref(currentRoute)
      // 如果是一级菜单路径匹配，或者当前路径在这一级菜单的映射路径列表中
      if (currentPath === tabActive.value) return true
      if (currentPath && tabPathMap[currentPath] && Array.isArray(tabPathMap[currentPath])) {
        return tabPathMap[currentPath].includes(path)
      }
      return false
    }

    const mouseleave = () => {
      if (appStore.getLayout === 'mixed') return
      if (!unref(showMenu) || unref(fixedMenu)) return
      showMenu.value = false
    }

    const toggleSubMenu = () => {
      userCollapsed.value = !unref(userCollapsed)
      showMenu.value = !unref(userCollapsed)
    }

    return () => (
      <div
        id={`${variables.namespace || 'v'}-tab-menu`}
        key="tab-menu"
        class={[
          prefixCls,
          'relative bg-[#2D303B] layout-border__right flex h-full flex-shrink-0',
          {
            'w-[var(--tab-menu-max-width)]': !unref(collapse) && !unref(showMenu),
            'w-[var(--tab-menu-min-width)]': unref(collapse) && !unref(showMenu),
            'w-[calc(var(--tab-menu-max-width)+var(--left-menu-max-width))]': !unref(collapse) && unref(showMenu),
            'w-[calc(var(--tab-menu-min-width)+var(--left-menu-max-width))]': unref(collapse) && unref(showMenu)
          }
        ]}
        onMouseleave={mouseleave}
      >
        <div 
          class="flex-none h-full relative flex-shrink-0" 
          style={{ width: unref(collapse) ? 'var(--tab-menu-min-width)' : 'var(--tab-menu-max-width)' }}
        >
          <ElScrollbar class="!h-[calc(100%-40px)]">
            <div class="py-10px">
              {() => {
                return unref(tabRouters).map((v) => {
                  if (!v) return null
                  return (
                    <div
                      key={v.path || ''}
                      class={[
                        `${prefixCls}__item`,
                        'text-center text-15px font-medium relative py-10px mb-8px! cursor-pointer flex flex-col items-center justify-center transition-all',
                        {
                          'is-active': isActive(v.path || '')
                        }
                      ]}
                      onClick={() => {
                        tabClick(v)
                      }}
                    >
                      <div class="mb-4px">
                        <Icon icon={(v.meta?.icon || '') as string} size={20}></Icon>
                      </div>
                      {!unref(showTitle) ? undefined : (
                        <p class="break-words px-4px text-14px">{t((v.meta?.title || '') as string)}</p>
                      )}
                    </div>
                  )
                })
              }}
            </div>
          </ElScrollbar>
        </div>
        <div 
          class="h-full bg-[var(--left-menu-bg-light-color)] relative flex-shrink-0"
          style={{ 
            width: unref(showMenu) ? 'var(--left-menu-max-width)' : '0',
            transition: 'width var(--transition-time-02)',
            overflow: 'hidden'
          }}
        >
          <Menu
            routers={unref(secondaryMenuRouters)}
            class="h-full"
            style="width: var(--left-menu-max-width)"
          ></Menu>
        </div>

        {/* 统一的二级菜单收起/展开按钮 - 靠近底部放置 */}
        {unref(secondaryMenuRouters).length > 0 && appStore.getLayout !== 'top' ? (
          <div 
            class="absolute bottom-100px -right-23px z-3000 cursor-pointer group"
            onClick={toggleSubMenu}
          >
            <div class="flex flex-col items-center bg-[#40444F] hover:bg-[#525763] px-4px py-12px rounded-r-md shadow-lg transition-all border border-l-0 border-[#525763]">
              <Icon 
                icon={unref(showMenu) ? 'ep:arrow-left' : 'ep:arrow-right'} 
                size={12} 
                class="mb-4px text-white"
              />
              <span class="text-11px text-white [writing-mode:vertical-lr] tracking-2px select-none">
                {unref(showMenu) ? '收起' : '展开'}
              </span>
            </div>
          </div>
        ) : null}
      </div>

    )
  }
})
</script>

<style lang="scss" scoped>
$prefix-cls: #{$namespace}-tab-menu;

.#{$prefix-cls} {
  transition: all var(--transition-time-02);

  &__item {
    color: rgba(255, 255, 255, 0.7);
    transition: all var(--transition-time-02);

    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, 'Noto Sans', sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji', 'FontAwesome' !important;

    margin: 4px 8px;

    &:hover {
      color: #fff;
    }

    &.is-active {
      color: #fff;
      background-color: #9d76ce; // 使用较深的紫色
      border-radius: 8px;
    }
  }

  &--collapse {
    color: rgba(255, 255, 255, 0.7);
    background-color: rgba(0, 0, 0, 0.2);
  }
}
</style>
