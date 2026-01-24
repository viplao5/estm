<template>
  <el-drawer v-model="dialogVisible" title="资质详情" size="1200px">
    <div v-loading="loading" class="p-20px">
      <el-descriptions :column="2" border title="基本信息">
        <el-descriptions-item label="资质ID">{{ detailData.id }}</el-descriptions-item>
        <el-descriptions-item label="资质名称">{{ detailData.name }}</el-descriptions-item>
        <el-descriptions-item label="认证单位">{{ detailData.certUnit }}</el-descriptions-item>
        <el-descriptions-item label="证书编号">{{ detailData.certNumber }}</el-descriptions-item>
        <el-descriptions-item label="生效日期">{{ detailData.startDate ? formatDate(detailData.startDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
        <el-descriptions-item label="到期日期">{{ detailData.endDate ? formatDate(detailData.endDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
        <el-descriptions-item label="证明材料" :span="2">
          <div v-if="detailData.fileUrl">
            <el-image 
              style="width: 100px; height: 100px" 
              :src="detailData.fileUrl" 
              :preview-src-list="[detailData.fileUrl]" 
              fit="cover" 
              preview-teleported
            />
          </div>
          <div v-else>无</div>
        </el-descriptions-item>
        <el-descriptions-item label="备注说明" :span="2">{{ detailData.remark }}</el-descriptions-item>
        <el-descriptions-item label="创建时间" :span="2">{{ detailData.createTime ? formatDate(detailData.createTime) : '' }}</el-descriptions-item>
      </el-descriptions>
    </div>
    <template #footer>
      <div style="flex: auto">
        <el-button @click="dialogVisible = false">关 闭</el-button>
      </div>
    </template>
  </el-drawer>
</template>
<script lang="ts" setup>
import * as QualificationApi from '@/api/bus/qualification'
import { formatDate } from '@/utils/formatTime'

defineOptions({ name: 'QualificationDetail' })

const dialogVisible = ref(false)
const loading = ref(false)
const detailData = ref<QualificationApi.QualificationVO>({} as any)

const open = async (id: number) => {
  dialogVisible.value = true
  loading.value = true
  try {
    detailData.value = await QualificationApi.getQualification(id)
  } finally {
    loading.value = false
  }
}

defineExpose({ open })
</script>
