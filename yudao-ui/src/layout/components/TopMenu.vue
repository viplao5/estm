<script lang="tsx">
import { defineComponent, computed, unref, watch, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from '@/hooks/web/useI18n'
import { ElMenu, ElMenuItem } from 'element-plus'
import { usePermissionStore } from '@/store/modules/permission'
import { Icon } from '@/components/Icon'
import { cloneDeep } from 'lodash-es'
import { pathResolve } from '@/utils/routerHelper'
import { useDesign } from '@/hooks/web/useDesign'

const { getPrefixCls } = useDesign()
const prefixCls = getPrefixCls('top-menu')

export default defineComponent({
  name: 'TopMenu',
  setup() {
    const { push, currentRoute } = useRouter()
    const { t } = useI18n()
    const permissionStore = usePermissionStore()

    const routers = computed(() => permissionStore.getRouters)
    // 过滤出真正的模块 (一级菜单)
    const topRouters = computed(() => {
      const allRouters = unref(routers)
      // 如果一级菜单是 Layout，则取其 children
      const root = allRouters.find(v => v.path === '/')
      let topNodes: AppRouteRecordRaw[] = []
      if (root && root.children && root.meta?.hidden) {
        topNodes = root.children.filter(v => !v.meta?.hidden)
      } else {
        topNodes = allRouters.filter((v) => !v?.meta?.hidden)
      }
      
      // 必须确保 path 是完整的 (以 / 开头)
      return topNodes.map(v => {
        return {
          ...v,
          path: v.path.startsWith('/') ? v.path : pathResolve('/', v.path)
        }
      })
    })

    const activeTop = ref('')

    const setMenuTabRouters = (path: string) => {
      const allRows = unref(topRouters)
      let route = allRows.find((v) => v.path === path)
      
      // Also search in all routers if not found in topRouters (for deep matching)
      if (!route) {
        route = unref(routers).find(v => v.path === path || (v.path === '/' && path === '/index'))
      }
      
      // Debug logging
      console.log('[TopMenu] setMenuTabRouters called with path:', path)
      console.log('[TopMenu] Found route:', route)
      console.log('[TopMenu] Route children:', route?.children)
      
      if (route && route.children && route.children.length > 0) {
        const filteredChildren = route.children.filter(v => !v.meta?.hidden)
        console.log('[TopMenu] Filtered children (non-hidden):', filteredChildren)
        
        const menuItems = cloneDeep(filteredChildren).map((v) => {
          v.path = pathResolve(route!.path, v.path)
          return v
        })
        console.log('[TopMenu] Setting menuTabRouters:', menuItems)
        permissionStore.setMenuTabRouters(menuItems)
      } else {
        console.log('[TopMenu] No children found, setting empty menuTabRouters')
        permissionStore.setMenuTabRouters([])
      }
    }

    const refreshActive = () => {
      const matched = unref(currentRoute).matched
      const allTop = unref(topRouters)
      
      if (matched.length > 0) {
        // Find top-level module
        const topMatched = matched[0]
        const found = allTop.find(v => v.path === topMatched.path || v.name === topMatched.name)
        if (found) {
          activeTop.value = found.path
          setMenuTabRouters(found.path)
          return
        }
      }

      // Fallback: match by path segment
      const topPath = `/${unref(currentRoute).path.split('/')[1]}`
      const fallback = allTop.find(v => v.path === topPath || (v.path === '/' && topPath === '/index'))
      if (fallback) {
        activeTop.value = fallback.path
        setMenuTabRouters(fallback.path)
      }
    }

    watch(
      () => routers.value,
      () => {
        refreshActive()
      },
      { immediate: true, deep: true }
    )

    watch(
      () => currentRoute.value.path,
      () => {
        refreshActive()
      }
    )

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

    const onSelect = (path: string) => {
      if (path === unref(activeTop)) return
      activeTop.value = path
      setMenuTabRouters(path)
      
      const route = unref(topRouters).find((v) => v.path === path)
      if (route) {
        // 使用递归获取第一个叶子节点路径
        const firstLeafPath = getFirstLeafPath(route)
        if (firstLeafPath && firstLeafPath !== path) {
          push(firstLeafPath)
        } else {
          push(path)
        }
      }
    }

    return () => (
      <ElMenu
        mode="horizontal"
        defaultActive={unref(activeTop)}
        onSelect={onSelect}
        class={[
          prefixCls,
          'flex-1 border-none bg-transparent h-[var(--top-tool-height)] px-10px'
        ]}
        style="--el-menu-hover-bg-color: transparent;"
      >
        {unref(topRouters).map((v) => (
          <ElMenuItem index={v.path} key={v.path} class="!px-15px">
            {{
              default: () => (
                <div class="flex items-center">
                  {v.meta?.icon ? <Icon icon={v.meta.icon as string} class="mr-6px" /> : null}
                  <span class="text-15px font-600">{t(v.meta?.title as string)}</span>
                </div>
              )
            }}
          </ElMenuItem>
        ))}
      </ElMenu>
    )
  }
})
</script>

<style lang="scss" scoped>
.#{$namespace}-top-menu {
  :deep(.el-menu-item) {
    height: var(--top-tool-height);
    line-height: var(--top-tool-height);
    font-size: 15px;
    font-weight: 500;
    transition: all 0.3s;
    
    &.is-active {
      border-bottom: 3px solid #9d76ce !important;
      color: #9d76ce !important;
      background-color: transparent !important;
    }
    
    &:hover {
      background-color: rgba(124, 77, 255, 0.05) !important;
      color: #9d76ce !important;
    }

    .el-icon {
      font-size: 18px;
    }
  }
}
</style>
