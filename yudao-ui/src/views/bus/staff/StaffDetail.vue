<template>
  <Dialog v-model="dialogVisible" title="技术人员详情" width="900px">
    <el-tabs v-model="activeTab" v-loading="loading">
      <!-- 基本信息 -->
      <el-tab-pane label="基本信息" name="basic">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="人员ID">{{ detailData.id }}</el-descriptions-item>
          <el-descriptions-item label="姓名">{{ detailData.name }}</el-descriptions-item>
          <el-descriptions-item label="岗位">{{ detailData.post }}</el-descriptions-item>
          <el-descriptions-item label="职称">
            <dict-tag :type="DICT_TYPE.BUS_TITLE" :value="detailData.title || ''" />
          </el-descriptions-item>
          <el-descriptions-item label="毕业院校">{{ detailData.school }}</el-descriptions-item>
          <el-descriptions-item label="所学专业">{{ detailData.major }}</el-descriptions-item>
          <el-descriptions-item label="学历">
            <dict-tag :type="DICT_TYPE.BUS_EDU_DEGREE" :value="detailData.eduDegree || ''" />
          </el-descriptions-item>
          <el-descriptions-item label="入职日期">{{ detailData.entryDate ? formatDate(detailData.entryDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
          <el-descriptions-item label="是否在职">
             <el-tag :type="detailData.isActive ? 'success' : 'danger'">{{ detailData.isActive ? '在职' : '离职' }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="核心认定">
             <el-tag :type="detailData.isCertified ? 'primary' : 'info'">{{ detailData.isCertified ? '是' : '否' }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="竞业限制协议">
             <el-tag :type="detailData.hasNonCompete ? 'primary' : 'info'">{{ detailData.hasNonCompete ? '有' : '无' }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="及劳动合同争议">
             <el-tag :type="detailData.hasDispute ? 'danger' : 'success'">{{ detailData.hasDispute ? '有' : '无' }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="保密协议">
             <el-tag :type="detailData.hasConfidentialAgreement ? 'primary' : 'info'">{{ detailData.hasConfidentialAgreement ? '有' : '无' }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="发明创造承诺">
             <el-tag :type="detailData.hasInventionPledge ? 'primary' : 'info'">{{ detailData.hasInventionPledge ? '有' : '无' }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="股权激励计划">
             <el-tag :type="detailData.hasEquityPlan ? 'primary' : 'info'">{{ detailData.hasEquityPlan ? '有' : '无' }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="科研成果及贡献" :span="2">{{ detailData.achievements }}</el-descriptions-item>
          <el-descriptions-item label="技术背景" :span="2">{{ detailData.background }}</el-descriptions-item>
          <el-descriptions-item label="激励情况" :span="2">{{ detailData.incentiveInfo }}</el-descriptions-item>
          <el-descriptions-item label="创建时间" :span="2">{{ detailData.createTime ? formatDate(detailData.createTime) : '' }}</el-descriptions-item>
        </el-descriptions>
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <el-button @click="dialogVisible = false">关 闭</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" setup>
import { DICT_TYPE } from '@/utils/dict'
import * as StaffApi from '@/api/bus/staff'
import { formatDate } from '@/utils/formatTime'

defineOptions({ name: 'StaffDetail' })

const dialogVisible = ref(false)
const loading = ref(false)
const activeTab = ref('basic')
const detailData = ref<StaffApi.TechnicalStaffVO>({} as any)

const open = async (id: number) => {
  dialogVisible.value = true
  activeTab.value = 'basic'
  loading.value = true
  try {
    detailData.value = await StaffApi.getStaff(id)
  } finally {
    loading.value = false
  }
}

defineExpose({ open })
</script>
