<template>
  <Dialog v-model="dialogVisible" title="科研平台详情" width="900px">
    <el-tabs v-model="activeTab" v-loading="loading">
      <!-- 基本信息 -->
      <el-tab-pane label="基本信息" name="basic">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="平台ID">{{ detailData.id }}</el-descriptions-item>
          <el-descriptions-item label="平台名称">{{ detailData.name }}</el-descriptions-item>
          <el-descriptions-item label="平台级别">
             <dict-tag :type="DICT_TYPE.BUS_PLATFORM_LEVEL" :value="detailData.level || ''" />
          </el-descriptions-item>
          <el-descriptions-item label="认定单位">{{ detailData.certUnit }}</el-descriptions-item>
          <el-descriptions-item label="认定日期">{{ detailData.certDate ? formatDate(detailData.certDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
          <el-descriptions-item label="有效期开始">{{ detailData.startDate ? formatDate(detailData.startDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
          <el-descriptions-item label="有效期结束">{{ detailData.endDate ? formatDate(detailData.endDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ detailData.createTime ? formatDate(detailData.createTime) : '' }}</el-descriptions-item>
        </el-descriptions>
      </el-tab-pane>

      <!-- 核心人员 -->
      <el-tab-pane label="核心人员" name="staff" lazy>
        <el-table :data="staffList" border stripe>
          <el-table-column label="人员ID" prop="id" width="80" />
          <el-table-column label="姓名" prop="name" width="120" />
          <el-table-column label="岗位" prop="post" min-width="150" />
          <el-table-column label="学历" prop="eduDegree" width="80">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.BUS_EDU_DEGREE" :value="scope.row.eduDegree" />
            </template>
          </el-table-column>
          <el-table-column label="职称" prop="title" width="120">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.BUS_TITLE" :value="scope.row.title" />
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <el-button @click="dialogVisible = false">关 闭</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" setup>
import { DICT_TYPE } from '@/utils/dict'
import * as PlatformApi from '@/api/bus/platform'
import * as StaffApi from '@/api/bus/staff'
import { formatDate } from '@/utils/formatTime'

defineOptions({ name: 'PlatformDetail' })

const dialogVisible = ref(false)
const loading = ref(false)
const activeTab = ref('basic')
const detailData = ref<PlatformApi.ResearchPlatformVO>({} as any)

const staffList = ref<StaffApi.TechnicalStaffVO[]>([])

const open = async (id: number) => {
  dialogVisible.value = true
  activeTab.value = 'basic'
  loading.value = true
  try {
    // 加载基本信息
    detailData.value = await PlatformApi.getPlatform(id)

    // 加载核心人员
    if (detailData.value.staffIds?.length) {
      const allStaff = await StaffApi.getStaffSimpleList()
      staffList.value = allStaff.filter(s => detailData.value.staffIds?.includes(s.id!))
    } else {
      staffList.value = []
    }

  } finally {
    loading.value = false
  }
}

defineExpose({ open })
</script>
