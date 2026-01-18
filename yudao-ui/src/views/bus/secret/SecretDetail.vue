<template>
  <Dialog v-model="dialogVisible" title="技术秘密详情" width="900px">
    <el-tabs v-model="activeTab" v-loading="loading">
      <!-- 基本信息 -->
      <el-tab-pane label="基本信息" name="basic">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="成果ID">{{ detailData.id }}</el-descriptions-item>
          <el-descriptions-item label="成果名称">{{ detailData.name }}</el-descriptions-item>
          <el-descriptions-item label="成果类型">
            <dict-tag :type="DICT_TYPE.BUS_SECRET_TYPE" :value="detailData.type || ''" />
          </el-descriptions-item>
          <el-descriptions-item label="保密级别">
            <dict-tag :type="DICT_TYPE.BUS_SECRET_LEVEL" :value="detailData.secretLevel || ''" />
          </el-descriptions-item>
          <el-descriptions-item label="技术领域" :span="2">{{ detailData.techField }}</el-descriptions-item>
          <el-descriptions-item label="完成日期">{{ detailData.finishDate ? formatDate(detailData.finishDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ detailData.createTime ? formatDate(detailData.createTime) : '' }}</el-descriptions-item>
          <el-descriptions-item label="技术秘密简介" :span="2">{{ detailData.description }}</el-descriptions-item>
        </el-descriptions>
      </el-tab-pane>

      <!-- 关联研发项目 -->
      <el-tab-pane label="关联研发项目" name="project" lazy>
        <el-table :data="projectList" border stripe>
          <el-table-column label="项目ID" prop="id" width="80" />
          <el-table-column label="项目名称" prop="name" min-width="180" />
          <el-table-column label="项目类别" prop="category" width="120">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.BUS_PROJECT_CATEGORY" :value="scope.row.category" />
            </template>
          </el-table-column>
          <el-table-column label="状态" prop="status" width="100">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.BUS_PROJECT_STATUS" :value="scope.row.status" />
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <!-- 关联产品与服务 -->
      <el-tab-pane label="关联产品与服务" name="product" lazy>
        <el-table :data="productList" border stripe>
          <el-table-column label="产品ID" prop="id" width="80" />
          <el-table-column label="产品名称" prop="name" min-width="180" />
          <el-table-column label="产品类别" prop="category" width="120">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.BUS_PRODUCT_CATEGORY" :value="scope.row.category" />
            </template>
          </el-table-column>
          <el-table-column label="状态" prop="status" width="100">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.BUS_PRODUCT_STATUS" :value="scope.row.status" />
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
import * as SecretApi from '@/api/bus/secret'
import * as ProjectApi from '@/api/bus/project'
import * as ProductApi from '@/api/bus/product'
import * as StaffApi from '@/api/bus/staff'
import { formatDate } from '@/utils/formatTime'

defineOptions({ name: 'SecretDetail' })

const dialogVisible = ref(false)
const loading = ref(false)
const activeTab = ref('basic')
const detailData = ref<SecretApi.TechnicalSecretVO>({} as any)

const projectList = ref<ProjectApi.ResearchProjectVO[]>([])
const productList = ref<ProductApi.ProductServiceVO[]>([])
const staffList = ref<StaffApi.TechnicalStaffVO[]>([])

const open = async (id: number) => {
  dialogVisible.value = true
  activeTab.value = 'basic'
  loading.value = true
  try {
    // 加载基本信息
    detailData.value = await SecretApi.getSecret(id)

    // 加载关联项目
    if (detailData.value.projectIds?.length) {
      const allProjects = await ProjectApi.getProjectSimpleList()
      projectList.value = allProjects.filter(p => detailData.value.projectIds?.includes(p.id!))
    } else {
      projectList.value = []
    }

    // 加载核心人员
    if (detailData.value.staffIds?.length) {
      const allStaff = await StaffApi.getStaffSimpleList()
      staffList.value = allStaff.filter(s => detailData.value.staffIds?.includes(s.id!))
    } else {
      staffList.value = []
    }

    // 加载关联产品
    const productRes = await ProductApi.getProductPage({ pageNo: 1, pageSize: 100, secretId: id } as any)
    productList.value = productRes.list || []

  } finally {
    loading.value = false
  }
}

defineExpose({ open })
</script>
