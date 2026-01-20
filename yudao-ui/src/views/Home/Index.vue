<template>
  <div class="dashboard-container">
    <!-- 欢迎 Banner -->
    <div class="welcome-banner mb-20px p-20px rounded-8px shadow-sm flex items-center justify-between">
      <div class="flex items-center">
        <el-avatar :src="avatar" :size="70" class="mr-20px border-4 border-white shadow-md">
          <img src="@/assets/imgs/avatar.gif" alt="" />
        </el-avatar>
        <div>
          <div class="text-24px font-bold text-gray-800">
            {{ t('workplace.welcome') }} {{ username }}，{{ t('workplace.happyDay') }}
          </div>
          <div class="mt-8px text-14px text-gray-500">
            {{ t('workplace.toady') }}，20℃ - 32℃！ | 每一天都是新的开始
          </div>
        </div>
      </div>
      <div class="flex items-center space-x-32px hidden md:flex">
        <div class="text-right px-20px">
          <div class="text-12px text-gray-400 uppercase tracking-wider mb-4px">总成果数</div>
          <div class="text-30px font-extrabold text-blue-600">
            <CountTo :start-val="0" :end-val="totalAchievementCount" :duration="2000" />
          </div>
        </div>
      </div>
    </div>

    <!-- 业务模块工作台 (Grid Layout for 10 items) -->
    <div v-if="packageId !== 10001">
      <div class="mb-20px">
        <div class="flex items-center mb-16px px-4px border-l-4 border-blue-500 pl-12px">
          <h3 class="text-18px font-bold text-gray-800 m-0">业务工作台</h3>
        </div>
        <div class="module-grid">
          <div
            v-for="(item, index) in moduleCards"
            :key="`module-${index}`"
            class="module-card bg-white p-20px rounded-8px shadow-sm hover:shadow-lg transition-all cursor-pointer group border border-gray-100 flex flex-col items-center justify-center"
            @click="handleModuleClick(item.path)"
          >
            <div 
              class="w-56px h-56px rounded-full flex items-center justify-center mb-12px group-hover:scale-110 transition-transform duration-300"
              :style="{ backgroundColor: item.bg }"
            >
               <Icon :icon="item.icon" :size="28" :color="item.color" />
            </div>
            <div class="text-14px font-medium text-gray-600 mb-4px">{{ item.name }}</div>
            <div class="text-24px font-bold text-gray-800">
              <CountTo :start-val="0" :end-val="item.count" :duration="2000" />
            </div>
          </div>
        </div>
      </div>

      <!-- 通知与提醒 (Split View) -->
      <el-row :gutter="20">
        <!-- 失效提醒 (Task Reminders) -->
        <el-col :span="12" :xs="24" class="mb-20px">
          <el-card shadow="never" class="h-full border-0 shadow-sm rounded-8px" :body-style="{ padding: '0' }">
            <template #header>
              <div class="flex justify-between items-center px-8px py-4px">
                <span class="text-16px font-bold flex items-center text-gray-800">
                  <Icon icon="ep:bell-filled" class="mr-8px text-red-500" />
                  失效提醒
                </span>
                <el-tag type="danger" effect="plain" round v-if="reminderList.length > 0">
                  {{ reminderList.length }} 待处理
                </el-tag>
              </div>
            </template>
            <div class="p-16px bg-white min-h-300px">
              <el-scrollbar max-height="300px">
                <div v-if="reminderList.length > 0">
                  <div v-for="(item, index) in reminderList" :key="index" 
                       class="py-12px px-8px border-b last:border-0 border-gray-50 hover:bg-red-50 transition-colors rounded-sm cursor-pointer group">
                    <div class="flex justify-between items-start">
                      <div class="flex-1 mr-16px">
                        <div class="text-14px text-gray-800 group-hover:text-red-600 font-medium mb-4px line-clamp-1">
                          {{ item.title }}
                        </div>
                      </div>
                      <div class="text-12px text-gray-400 whitespace-nowrap">{{ formatTime(item.createTime, 'yyyy-MM-dd') }}</div>
                    </div>
                  </div>
                </div>
                <el-empty v-else description="暂无需要处理的提醒" :image-size="80" />
              </el-scrollbar>
            </div>
          </el-card>
        </el-col>

        <!-- 系统通知 (System Notifications) -->
        <el-col :span="12" :xs="24" class="mb-20px">
          <el-card shadow="never" class="h-full border-0 shadow-sm rounded-8px" :body-style="{ padding: '0' }">
            <template #header>
              <div class="flex justify-between items-center px-8px py-4px">
                <span class="text-16px font-bold flex items-center text-gray-800">
                  <Icon icon="ep:notification" class="mr-8px text-blue-500" />
                  系统通知
                </span>
                <el-link type="primary" :underline="false">查看更多</el-link>
              </div>
            </template>
             <div class="p-16px bg-white min-h-300px">
               <el-scrollbar max-height="300px">
                <div v-if="systemNoticeList.length > 0">
                  <div v-for="(item, index) in systemNoticeList" :key="index" 
                       class="py-12px px-8px border-b last:border-0 border-gray-50 hover:bg-blue-50 transition-colors rounded-sm cursor-pointer group">
                    <div class="flex justify-between items-start">
                      <div class="flex-1 mr-16px">
                         <div class="text-14px text-gray-800 group-hover:text-blue-600 font-medium mb-4px line-clamp-1">
                          {{ item.title }}
                        </div>
                      </div>
                      <div class="text-12px text-gray-400 whitespace-nowrap">{{ formatTime(item.createTime, 'yyyy-MM-dd') }}</div>
                    </div>
                  </div>
                </div>
                <el-empty v-else description="暂无系统通知" :image-size="80" />
               </el-scrollbar>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 待审核提示 (Only shown for packageId 10001) -->
    <div v-else class="pending-review-container">
      <el-card class="review-card" shadow="hover">
        <template #header>
          <div class="flex items-center">
            <Icon icon="ep:info-filled" class="mr-10px text-warning" :size="24" />
            <span class="text-18px font-bold">账号审核中</span>
          </div>
        </template>
        <div class="p-20px">
          <p class="text-16px text-gray-700 mb-20px">
            您的企业账号已成功注册，目前处于<b>待审核</b>状态。请完成费用缴纳后联系管理员进行激活。
          </p>
          
          <div class="bank-info bg-gray-50 p-20px rounded-8px border border-gray-100 mb-20px">
            <h4 class="text-16px font-bold mb-16px border-b pb-8px">汇款账号信息</h4>
            <el-descriptions :column="1" border>
                <el-descriptions-item label="开户名称">某某科技有限公司</el-descriptions-item>
                <el-descriptions-item label="开户银行">中国银行上海市分行</el-descriptions-item>
                <el-descriptions-item label="银行账号">6222 0000 1111 2222 333</el-descriptions-item>
                <el-descriptions-item label="汇款备注">企业注册手机号 + {{ username }}</el-descriptions-item>
            </el-descriptions>
          </div>

          <el-alert
            title="温馨提示"
            type="warning"
            description="汇款完成后，通常在1-2个工作日内完成审核。如有疑问请咨询客服热线：400-123-4567。"
            show-icon
            :closable="false"
          />
        </div>
      </el-card>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { formatTime } from '@/utils'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/modules/user'
import * as BusDashboardApi from '@/api/bus/dashboard'
import * as NotifyMessageApi from '@/api/system/notify/message'

defineOptions({ name: 'Index' })

const { t } = useI18n()
const router = useRouter()
const userStore = useUserStore()
const loading = ref(true)

const avatar = computed(() => userStore.getUser.avatar || '@/assets/imgs/avatar.gif')
const username = computed(() => userStore.getUser.nickname)
const packageId = computed(() => userStore.getUser.packageId)

// 模块卡片定义 - Added background colors for icon containers
const moduleCards = reactive([
  { name: '研发项目', icon: 'ep:folder', color: '#409EFF', bg: '#ecf5ff', count: 0, path: '/bus/project' },
  { name: '知识产权', icon: 'ep:reading', color: '#67C23A', bg: '#f0f9eb', count: 0, path: '/bus/ip' },
  { name: '技术秘密', icon: 'ep:lock', color: '#E6A23C', bg: '#fdf6ec', count: 0, path: '/bus/secret' },
  { name: '论文著作', icon: 'ep:document', color: '#F56C6C', bg: '#fef0f0', count: 0, path: '/bus/paper' },
  { name: '标准管理', icon: 'ep:guide', color: '#909399', bg: '#f4f4f5', count: 0, path: '/bus/standard' },
  { name: '科技奖励', icon: 'ep:medal', color: '#E6A23C', bg: '#fdf6ec', count: 0, path: '/bus/award' },
  { name: '产品与服务', icon: 'ep:goods', color: '#409EFF', bg: '#ecf5ff', count: 0, path: '/bus/product' },
  { name: '科研平台', icon: 'ep:office-building', color: '#67C23A', bg: '#f0f9eb', count: 0, path: '/bus/platform' },
  { name: '技术人员', icon: 'ep:user', color: '#F56C6C', bg: '#fef0f0', count: 0, path: '/bus/staff' },
  { name: '资质管理', icon: 'ep:postcard', color: '#909399', bg: '#f4f4f5', count: 0, path: '/bus/qualification' },
])

const totalAchievementCount = computed(() => moduleCards.reduce((acc, cur) => acc + cur.count, 0))

// 提醒和通知列表
const reminderList = ref<any[]>([])
const systemNoticeList = ref<any[]>([])

const getDashboardData = async () => {
  try {
    loading.value = true
    
    // 1. 获取统计数据
    const stats = await BusDashboardApi.getWorkplaceStats()
    moduleCards[0].count = stats.projectCount
    moduleCards[1].count = stats.ipCount
    moduleCards[2].count = stats.secretCount
    moduleCards[3].count = stats.paperCount
    moduleCards[4].count = stats.standardCount
    moduleCards[5].count = stats.awardCount
    moduleCards[6].count = stats.productCount
    moduleCards[7].count = stats.platformCount
    moduleCards[8].count = stats.staffCount
    moduleCards[9].count = stats.qualificationCount

    // 2. 获取通知消息
    const notices = await NotifyMessageApi.getUnreadNotifyMessageList()
    reminderList.value = []
    systemNoticeList.value = []
    
    notices.forEach(item => {
      const data = {
        title: item.templateContent,
        createTime: item.createTime
      }
      if (item.templateCode === 'ACHIEVEMENT_EXPIRATION') {
        reminderList.value.push(data)
      } else {
        systemNoticeList.value.push(data)
      }
    })

  } catch (e) {
    console.error('加载首页数据失败', e)
  } finally {
    loading.value = false
  }
}

const handleModuleClick = (path: string) => {
  router.push(path)
}

onMounted(() => {
  getDashboardData()
})
</script>

<style scoped>
.dashboard-container {
  padding: 10px;
  background-color: #f5f7f9;
  min-height: calc(100vh - 84px);
}
.welcome-banner {
  background: linear-gradient(135deg, #ffffff 0%, #f0f7ff 100%);
  border-left: 5px solid #409EFF;
}
.module-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 16px;
}
@media (max-width: 1200px) {
  .module-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}
@media (max-width: 992px) {
  .module-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}
@media (max-width: 768px) {
  .module-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
@media (max-width: 480px) {
  .module-grid {
    grid-template-columns: 1fr;
  }
}
.module-card {
  transition: all 0.3s;
}
.module-card:hover {
  transform: translateY(-5px);
}
</style>

