<template>
  <Dialog v-model="dialogVisible" title="产品与服务详情" width="900px">
    <el-tabs v-model="activeTab" v-loading="loading">
      <!-- 基本信息 -->
      <el-tab-pane label="基本信息" name="basic">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="产品ID">{{ detailData.id }}</el-descriptions-item>
          <el-descriptions-item label="产品名称">{{ detailData.name }}</el-descriptions-item>
          <el-descriptions-item label="产品类别">
            <dict-tag :type="DICT_TYPE.BUS_PRODUCT_CATEGORY" :value="detailData.category || ''" />
          </el-descriptions-item>
          <el-descriptions-item label="产品状态">
            <dict-tag :type="DICT_TYPE.BUS_PRODUCT_STATUS" :value="detailData.status || ''" />
          </el-descriptions-item>
          <el-descriptions-item label="产品负责人">{{ detailData.leader }}</el-descriptions-item>
          <el-descriptions-item label="上市日期">{{ detailData.launchDate ? formatDate(detailData.launchDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
          <el-descriptions-item label="营收(万)">{{ detailData.revenue }}</el-descriptions-item>
          <el-descriptions-item label="利润(万)">{{ detailData.profit }}</el-descriptions-item>
          <el-descriptions-item label="重点客户" :span="2">{{ detailData.keyCustomers }}</el-descriptions-item>
          <el-descriptions-item label="产品简介" :span="2">{{ detailData.intro }}</el-descriptions-item>
          <el-descriptions-item label="创建时间" :span="2">{{ detailData.createTime ? formatDate(detailData.createTime) : '' }}</el-descriptions-item>
        </el-descriptions>
      </el-tab-pane>

      <!-- 关联知识产权 -->
      <el-tab-pane label="关联知识产权" name="ip" lazy>
        <el-table :data="ipList" border stripe>
          <el-table-column label="资产ID" prop="id" width="80" />
          <el-table-column label="资产名称" prop="name" min-width="180" />
          <el-table-column label="资产类别" prop="category" width="120">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.BUS_IP_CATEGORY" :value="scope.row.category" />
            </template>
          </el-table-column>
          <el-table-column label="法律状态" prop="status" width="100">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.BUS_IP_STATUS" :value="scope.row.status" />
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <!-- 关联技术秘密 -->
      <el-tab-pane label="关联技术秘密" name="secret" lazy>
        <el-table :data="secretList" border stripe>
          <el-table-column label="成果ID" prop="id" width="80" />
          <el-table-column label="成果名称" prop="name" min-width="180" />
          <el-table-column label="保密级别" prop="secretLevel" width="120">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.BUS_SECRET_LEVEL" :value="scope.row.secretLevel" />
            </template>
          </el-table-column>
        </el-table>
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
import * as ProductApi from '@/api/bus/product'
import * as IPApi from '@/api/bus/ip'
import * as SecretApi from '@/api/bus/secret'
import * as StaffApi from '@/api/bus/staff'
import { formatDate } from '@/utils/formatTime'

defineOptions({ name: 'ProductDetail' })

const dialogVisible = ref(false)
const loading = ref(false)
const activeTab = ref('basic')
const detailData = ref<ProductApi.ProductServiceVO>({} as any)

const ipList = ref<IPApi.IntellectualPropertyVO[]>([])
const secretList = ref<SecretApi.TechnicalSecretVO[]>([])
const staffList = ref<StaffApi.TechnicalStaffVO[]>([])

const open = async (id: number) => {
  dialogVisible.value = true
  activeTab.value = 'basic'
  loading.value = true
  try {
    // 加载基本信息
    detailData.value = await ProductApi.getProduct(id)

    // 加载关联IP
    if (detailData.value.ipIds?.length) {
      const ipRes = await IPApi.getIPPage({ pageNo: 1, pageSize: 100, ids: detailData.value.ipIds } as any)
      ipList.value = ipRes.list || []
    } else {
      ipList.value = []
    }

    // 加载关联技术秘密
    if (detailData.value.secretIds?.length) {
      const secretRes = await SecretApi.getSecretPage({ pageNo: 1, pageSize: 100, ids: detailData.value.secretIds } as any)
      secretList.value = secretRes.list || []
    } else {
      secretList.value = []
    }

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
