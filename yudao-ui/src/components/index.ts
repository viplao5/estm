import type { App } from 'vue'
import { Icon } from './Icon'
import { MetricsBar } from './MetricsBar'

export const setupGlobCom = (app: App<Element>): void => {
  app.component('Icon', Icon)
}

export {
  MetricsBar
}
