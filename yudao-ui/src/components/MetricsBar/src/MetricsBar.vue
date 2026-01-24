<script lang="ts" setup>
import { PropType } from 'vue'

defineOptions({ name: 'MetricsBar' })

defineProps({
  metrics: {
    type: Array as PropType<{ title: string; value: string | number; icon: string; color?: string }[]>,
    default: () => []
  }
})
</script>

<template>
  <div class="metrics-bar flex items-center bg-white px-20px py-16px mb-16px border-b border-b-solid border-b-[#f0f0f0]">
    <div v-for="(item, index) in metrics" :key="index" class="flex items-center metric-item mr-40px last:mr-0">
      <div 
        class="w-40px h-40px rounded-8px flex items-center justify-center mr-12px"
        :style="{ backgroundColor: item.color || 'rgba(124, 77, 255, 0.08)' }"
      >
        <Icon :icon="item.icon" :size="20" :color="item.color ? '#fff' : '#9d76ce'" />
      </div>
      <div class="flex flex-col">
        <span class="text-12px text-[#999] mb-4px">{{ item.title }}</span>
        <span class="text-20px font-700 text-[#333] leading-tight">{{ item.value }}</span>
      </div>
      <div v-if="index < metrics.length - 1" class="ml-40px h-24px w-1px bg-[#f0f0f0]"></div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.metrics-bar {
  transition: all 0.3s;
}
.metric-item {
  &:last-child {
    .h-24px {
      display: none;
    }
  }
}
</style>
