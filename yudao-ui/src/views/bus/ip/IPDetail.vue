<template>
  <el-drawer v-model="dialogVisible" title="知识产权详情" size="1200px">
    <div v-loading="loading" class="p-20px">
      <!-- 基本信息 (始终显示在顶部) -->
      <el-descriptions :column="2" border title="基本信息" class="mb-20px">
        <el-descriptions-item label="资产ID">{{ detailData.id }}</el-descriptions-item>
        <el-descriptions-item label="名称">{{ detailData.name }}</el-descriptions-item>
        <el-descriptions-item label="类型">
          <dict-tag :type="DICT_TYPE.BUS_IP_CATEGORY" :value="detailData.category || ''" />
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <dict-tag :type="DICT_TYPE.BUS_IP_STATUS" :value="detailData.status || ''" />
        </el-descriptions-item>
        <el-descriptions-item label="申请号/登记号">{{ detailData.appNumber }}</el-descriptions-item>
        <el-descriptions-item label="权利人">{{ detailData.owner }}</el-descriptions-item>
        <el-descriptions-item label="代理机构">{{ detailData.agency }}</el-descriptions-item>
        <el-descriptions-item label="来源">
          <dict-tag :type="DICT_TYPE.BUS_IP_SOURCE" :value="detailData.source || ''" />
        </el-descriptions-item>
        <el-descriptions-item label="申请日期">{{ detailData.appDate ? formatDate(detailData.appDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
        <el-descriptions-item label="授权日期">{{ detailData.grantDate ? formatDate(detailData.grantDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
        <el-descriptions-item label="下次缴费日期">{{ detailData.nextFeeDate ? formatDate(detailData.nextFeeDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ detailData.createTime ? formatDate(detailData.createTime) : '' }}</el-descriptions-item>
        <el-descriptions-item label="官文扫描件" :span="2">
          <el-image 
            v-if="detailData.fileUrl" 
            style="width: 100px; height: 100px" 
            :src="detailData.fileUrl" 
            :preview-src-list="[detailData.fileUrl]" 
            fit="cover" 
            preview-teleported
          />
          <span v-else>无</span>
        </el-descriptions-item>
      </el-descriptions>

      <!-- 关联信息 (底部页签) -->
      <el-tabs v-model="activeTab">
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
    </div>
    <template #footer>
      <div style="flex: auto">
        <el-button @click="dialogVisible = false">关 闭</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<script lang="ts" setup>
import { DICT_TYPE } from '@/utils/dict'
import * as IPApi from '@/api/bus/ip'
import * as ProjectApi from '@/api/bus/project'
import * as ProductApi from '@/api/bus/product'
import * as StaffApi from '@/api/bus/staff'
import { formatDate } from '@/utils/formatTime'

defineOptions({ name: 'IPDetail' })

const dialogVisible = ref(false)
const loading = ref(false)
const activeTab = ref('project')
const detailData = ref<IPApi.IntellectualPropertyVO>({} as any)

const projectList = ref<ProjectApi.ResearchProjectVO[]>([])
const productList = ref<ProductApi.ProductServiceVO[]>([])
const staffList = ref<StaffApi.TechnicalStaffVO[]>([])

const open = async (id: number) => {
  dialogVisible.value = true
  activeTab.value = 'project'
  loading.value = true
  try {
    // 加载基本信息
    detailData.value = await IPApi.getIP(id)

    // 加载关联项目
    if (detailData.value.projectIds?.length) {
      const allProjects = await ProjectApi.getProjectSimpleList()
      projectList.value = allProjects.filter(p => detailData.value.projectIds?.includes(p.id!))
    } else {
      projectList.value = []
    }

    // 加载核心人员 (发明人)
    if (detailData.value.inventorIds?.length) {
      const allStaff = await StaffApi.getStaffSimpleList()
      staffList.value = allStaff.filter(s => detailData.value.inventorIds?.includes(s.id!))
    } else {
      staffList.value = []
    }

    // 加载关联产品
    const productRes = await ProductApi.getProductPage({ pageNo: 1, pageSize: 100, ipId: id } as any)
    productList.value = productRes.list || []

  } finally {
    loading.value = false
  }
}

defineExpose({ open })
</script>
