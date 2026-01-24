import { ElSubMenu, ElMenuItem } from 'element-plus'
import { hasOneShowingChild } from '../helper'
import { isUrl } from '@/utils/is'
import { useRenderMenuTitle } from './useRenderMenuTitle'
import { pathResolve } from '@/utils/routerHelper'
import { useRouter } from 'vue-router'

const { renderMenuTitle } = useRenderMenuTitle()

export const useRenderMenuItem = () => {
  const { push } = useRouter()

  const getFirstLeafPath = (item: AppRouteRecordRaw, parentPath: string): string => {
    const fullPath = isUrl(item.path) ? item.path : pathResolve(parentPath, item.path)
    const children = item.children?.filter((v) => v && v.meta && !v.meta.hidden)
    if (children && children.length > 0) {
      return getFirstLeafPath(children[0], fullPath)
    }
    return fullPath
  }

  const renderMenuItem = (routers: AppRouteRecordRaw[], parentPath = '/') => {
    return routers
      .filter((v) => !v.meta?.hidden)
      .map((v) => {
        const meta = v.meta ?? {}
        const { oneShowingChild, onlyOneChild } = hasOneShowingChild(v.children, v)
        const fullPath = isUrl(v.path) ? v.path : pathResolve(parentPath, v.path)

        if (
          oneShowingChild &&
          (!onlyOneChild?.children || onlyOneChild?.noShowingChildren) &&
          !meta?.alwaysShow
        ) {
          return (
            <ElMenuItem
              index={onlyOneChild ? pathResolve(fullPath, onlyOneChild.path) : fullPath}
            >
              {{
                default: () => renderMenuTitle(onlyOneChild ? onlyOneChild?.meta : meta)
              }}
            </ElMenuItem>
          )
        } else {
          return (
            <ElSubMenu index={fullPath}>
              {{
                title: () => (
                  <div
                    class="w-full h-full"
                    onClick={() => {
                      const firstLeafPath = getFirstLeafPath(v, parentPath)
                      if (firstLeafPath && firstLeafPath !== fullPath) {
                        push(firstLeafPath)
                      }
                    }}
                  >
                    {renderMenuTitle(meta)}
                  </div>
                ),
                default: () => renderMenuItem(v.children!, fullPath)
              }}
            </ElSubMenu>
          )
        }
      })
  }

  return {
    renderMenuItem
  }
}
