<template>
  <el-drawer v-model="dialogVisible" title="技术人员详情" size="1200px">
    <div v-loading="loading" class="p-20px">
      <!-- 基本信息 (始终显示在顶部) -->
      <el-descriptions :column="2" border title="基本信息" class="mb-20px">
        <el-descriptions-item label="人员ID">{{ detailData.id }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ detailData.name }}</el-descriptions-item>
        <el-descriptions-item label="岗位">{{ detailData.post }}</el-descriptions-item>
        <el-descriptions-item label="职称">
          <dict-tag :type="DICT_TYPE.BUS_TITLE" :value="detailData.title || ''" />
        </el-descriptions-item>
        <el-descriptions-item label="毕业院校">{{ detailData.school }}</el-descriptions-item>
        <el-descriptions-item label="专业">{{ detailData.major }}</el-descriptions-item>
        <el-descriptions-item label="学历">
          <dict-tag :type="DICT_TYPE.BUS_EDU_DEGREE" :value="detailData.eduDegree || ''" />
        </el-descriptions-item>
        <el-descriptions-item label="入职日期">{{ detailData.entryDate ? formatDate(detailData.entryDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
        <el-descriptions-item label="是否在职">
           <el-tag :type="detailData.isActive ? 'success' : 'danger'">{{ detailData.isActive ? '在职' : '离职' }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="核心技术人员认定">
           <el-tag :type="detailData.isCertified ? 'primary' : 'info'">{{ detailData.isCertified ? '是' : '否' }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="与前单位竞业禁止">
           <el-tag :type="detailData.hasNonCompete ? 'primary' : 'info'">{{ detailData.hasNonCompete ? '有' : '无' }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="与前单位潜在纠纷">
           <el-tag :type="detailData.hasDispute ? 'danger' : 'success'">{{ detailData.hasDispute ? '有' : '无' }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="保密与知识产权协议">
           <el-tag :type="detailData.hasConfidentialAgreement ? 'primary' : 'info'">{{ detailData.hasConfidentialAgreement ? '已签署' : '未签署' }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="协议扫描件">
           <el-image 
             v-if="detailData.confidentialFile" 
             style="width: 100px; height: 100px" 
             :src="detailData.confidentialFile" 
             :preview-src-list="[detailData.confidentialFile]" 
             fit="cover" 
             preview-teleported
           />
           <span v-else>无</span>
        </el-descriptions-item>
        <el-descriptions-item label="职务发明承诺书">
           <el-tag :type="detailData.hasInventionPledge ? 'primary' : 'info'">{{ detailData.hasInventionPledge ? '已签署' : '未签署' }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="承诺书扫描件">
           <el-image 
             v-if="detailData.inventionPledgeFile" 
             style="width: 100px; height: 100px" 
             :src="detailData.inventionPledgeFile" 
             :preview-src-list="[detailData.inventionPledgeFile]" 
             fit="cover" 
             preview-teleported
           />
           <span v-else>无</span>
        </el-descriptions-item>
        <el-descriptions-item label="股权激励计划">
           <el-tag :type="detailData.hasEquityPlan ? 'primary' : 'info'">{{ detailData.hasEquityPlan ? '已参与' : '未参与' }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="股权协议扫描件">
           <el-image 
             v-if="detailData.equityPlanFile" 
             style="width: 100px; height: 100px" 
             :src="detailData.equityPlanFile" 
             :preview-src-list="[detailData.equityPlanFile]" 
             fit="cover" 
             preview-teleported
           />
           <span v-else>无</span>
        </el-descriptions-item>
        <el-descriptions-item label="突出成就" :span="2">{{ detailData.achievements }}</el-descriptions-item>
        <el-descriptions-item label="背景描述" :span="2">{{ detailData.background }}</el-descriptions-item>
        <el-descriptions-item label="其他激励措施" :span="2">{{ detailData.incentiveInfo }}</el-descriptions-item>
        <el-descriptions-item label="创建时间" :span="2">{{ detailData.createTime ? formatDate(detailData.createTime) : '' }}</el-descriptions-item>
      </el-descriptions>

      <!-- 关联信息 (底部页签) -->
      <el-tabs v-model="activeTab">
        <!-- 研发项目 -->
        <el-tab-pane label="研发项目" name="project" v-loading="loadingProject">
           <el-table :data="projectList" border stripe style="width: 100%">
             <el-table-column prop="id" label="ID" width="80" />
             <el-table-column prop="name" label="项目名称" />
             <el-table-column prop="category" label="项目类别">
               <template #default="scope">
                 <dict-tag :type="DICT_TYPE.BUS_PROJECT_CATEGORY" :value="scope.row.category" />
               </template>
             </el-table-column>
             <el-table-column prop="status" label="项目状态">
               <template #default="scope">
                 <dict-tag :type="DICT_TYPE.BUS_PROJECT_STATUS" :value="scope.row.status" />
               </template>
             </el-table-column>
             <el-table-column prop="startDate" label="开始时间" :formatter="dateFormatter2" />
             <el-table-column prop="endDate" label="结束时间" :formatter="dateFormatter2" />
           </el-table>
        </el-tab-pane>

        <!-- 知识产权 -->
        <el-tab-pane label="知识产权" name="ip" v-loading="loadingIp">
          <el-table :data="ipList" border stripe style="width: 100%">
             <el-table-column prop="id" label="ID" width="80" />
             <el-table-column prop="name" label="资产名称" />
             <el-table-column prop="appNumber" label="申请号/登记号" />
             <el-table-column prop="category" label="资产类别">
               <template #default="scope">
                 <dict-tag :type="DICT_TYPE.BUS_IP_CATEGORY" :value="scope.row.category" />
               </template>
             </el-table-column>
             <el-table-column prop="status" label="法律状态">
               <template #default="scope">
                 <dict-tag :type="DICT_TYPE.BUS_IP_STATUS" :value="scope.row.status" />
               </template>
             </el-table-column>
             <el-table-column prop="appDate" label="申请日" :formatter="dateFormatter2" />
           </el-table>
        </el-tab-pane>

        <!-- 技术秘密 -->
        <el-tab-pane label="技术秘密" name="secret" v-loading="loadingSecret">
          <el-table :data="secretList" border stripe style="width: 100%">
             <el-table-column prop="id" label="ID" width="80" />
             <el-table-column prop="name" label="成果名称" />
             <el-table-column prop="type" label="成果类型">
               <template #default="scope">
                 <dict-tag :type="DICT_TYPE.BUS_SECRET_TYPE" :value="scope.row.type" />
               </template>
             </el-table-column>
             <el-table-column prop="secretLevel" label="保密级别">
               <template #default="scope">
                 <dict-tag :type="DICT_TYPE.BUS_SECRET_LEVEL" :value="scope.row.secretLevel" />
               </template>
             </el-table-column>
           </el-table>
        </el-tab-pane>

        <!-- 论文著作 -->
        <el-tab-pane label="论文著作" name="paper" v-loading="loadingPaper">
          <el-table :data="paperList" border stripe style="width: 100%">
             <el-table-column prop="id" label="ID" width="80" />
             <el-table-column prop="title" label="论文/专著标题" />
             <el-table-column prop="publication" label="期刊/会议名称" />
             <el-table-column prop="indexing" label="收录情况">
               <template #default="scope">
                 <dict-tag :type="DICT_TYPE.BUS_PAPER_INDEXING" :value="scope.row.indexing" />
               </template>
             </el-table-column>
             <el-table-column prop="pubYear" label="发表年份" />
           </el-table>
        </el-tab-pane>

        <!-- 标准信息 -->
        <el-tab-pane label="标准信息" name="standard" v-loading="loadingStandard">
          <el-table :data="standardList" border stripe style="width: 100%">
             <el-table-column prop="id" label="ID" width="80" />
             <el-table-column prop="name" label="标准名称" />
             <el-table-column prop="standardNumber" label="标准编号" />
             <el-table-column prop="type" label="标准类型">
               <template #default="scope">
                 <dict-tag :type="DICT_TYPE.BUS_STANDARD_TYPE" :value="scope.row.type" />
               </template>
             </el-table-column>
             <el-table-column prop="status" label="标准状态">
               <template #default="scope">
                 <dict-tag :type="DICT_TYPE.BUS_STANDARD_STATUS" :value="scope.row.status" />
               </template>
             </el-table-column>
             <el-table-column prop="pubDate" label="发布日期" :formatter="dateFormatter2" />
           </el-table>
        </el-tab-pane>

        <!-- 科技奖励 -->
        <el-tab-pane label="科技奖励" name="award" v-loading="loadingAward">
          <el-table :data="awardList" border stripe style="width: 100%">
             <el-table-column prop="id" label="ID" width="80" />
             <el-table-column prop="name" label="科技奖励名称" />
             <el-table-column prop="level" label="奖励级别">
               <template #default="scope">
                 <dict-tag :type="DICT_TYPE.BUS_AWARD_LEVEL" :value="scope.row.level" />
               </template>
             </el-table-column>
             <el-table-column prop="grade" label="奖励等级">
               <template #default="scope">
                 <dict-tag :type="DICT_TYPE.BUS_AWARD_GRADE" :value="scope.row.grade" />
               </template>
             </el-table-column>
             <el-table-column prop="awardDate" label="获奖日期" :formatter="dateFormatter2" />
           </el-table>
        </el-tab-pane>
        
        <!-- 科研平台 -->
        <el-tab-pane label="科研平台" name="platform" v-loading="loadingPlatform">
          <el-table :data="platformList" border stripe style="width: 100%">
             <el-table-column prop="id" label="ID" width="80" />
             <el-table-column prop="name" label="平台名称" />
             <el-table-column prop="level" label="平台级别">
               <template #default="scope">
                 <dict-tag :type="DICT_TYPE.BUS_PLATFORM_LEVEL" :value="scope.row.level" />
               </template>
             </el-table-column>
             <el-table-column prop="certUnit" label="认定单位" />
             <el-table-column prop="certDate" label="认定日期" :formatter="dateFormatter2" />
           </el-table>
        </el-tab-pane>

        <!-- 产品与服务 -->
        <el-tab-pane label="产品与服务" name="product" v-loading="loadingProduct">
          <el-table :data="productList" border stripe style="width: 100%">
             <el-table-column prop="id" label="ID" width="80" />
             <el-table-column prop="name" label="产品名称" />
             <el-table-column prop="category" label="产品类别">
               <template #default="scope">
                 <dict-tag :type="DICT_TYPE.BUS_PRODUCT_CATEGORY" :value="scope.row.category" />
               </template>
             </el-table-column>
             <el-table-column prop="status" label="产品状态">
               <template #default="scope">
                 <dict-tag :type="DICT_TYPE.BUS_PRODUCT_STATUS" :value="scope.row.status" />
               </template>
             </el-table-column>
             <el-table-column prop="launchDate" label="上市日期" :formatter="dateFormatter2" />
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
import * as StaffApi from '@/api/bus/staff'
import * as ProjectApi from '@/api/bus/project'
import * as IPApi from '@/api/bus/ip'
import * as SecretApi from '@/api/bus/secret'
import * as PaperApi from '@/api/bus/paper'
import * as StandardApi from '@/api/bus/standard'
import * as AwardApi from '@/api/bus/award'
import * as PlatformApi from '@/api/bus/platform'
import * as ProductApi from '@/api/bus/product'
import { dateFormatter2, formatDate } from '@/utils/formatTime'

defineOptions({ name: 'StaffDetail' })

const dialogVisible = ref(false)
const loading = ref(false)
const activeTab = ref('project')
const detailData = ref<StaffApi.TechnicalStaffVO>({} as any)

// Tab Data
const loadingProject = ref(false); const projectList = ref([])
const loadingIp = ref(false); const ipList = ref([])
const loadingSecret = ref(false); const secretList = ref([])
const loadingPaper = ref(false); const paperList = ref([])
const loadingStandard = ref(false); const standardList = ref([])
const loadingAward = ref(false); const awardList = ref([])
const loadingPlatform = ref(false); const platformList = ref([])
const loadingProduct = ref(false); const productList = ref([])

const open = async (id: number) => {
  dialogVisible.value = true
  activeTab.value = 'project'
  loading.value = true
  
  // Reset lists
  projectList.value = []
  ipList.value = []
  secretList.value = []
  paperList.value = []
  standardList.value = []
  awardList.value = []
  platformList.value = []
  productList.value = []

  try {
    detailData.value = await StaffApi.getStaff(id)
  } finally {
    loading.value = false
  }
}

// Function to load tab data
const loadTabData = async (val: string) => {
  if (!detailData.value.id) return
  const id = detailData.value.id
  if (val === 'project' && projectList.value.length === 0) {
    loadingProject.value = true
    try { const res = await ProjectApi.getProjectPage({ staffId: id, pageSize: 100 } as any); projectList.value = res.list || [] } finally { loadingProject.value = false }
  } else if (val === 'ip' && ipList.value.length === 0) {
    loadingIp.value = true
    try { const res = await IPApi.getIPPage({ staffId: id, pageSize: 100 } as any); ipList.value = res.list || [] } finally { loadingIp.value = false }
  } else if (val === 'secret' && secretList.value.length === 0) {
    loadingSecret.value = true
    try { const res = await SecretApi.getSecretPage({ staffId: id, pageSize: 100 } as any); secretList.value = res.list || [] } finally { loadingSecret.value = false }
  } else if (val === 'paper' && paperList.value.length === 0) {
    loadingPaper.value = true
    try { const res = await PaperApi.getPaperPage({ staffId: id, pageSize: 100 } as any); paperList.value = res.list || [] } finally { loadingPaper.value = false }
  } else if (val === 'standard' && standardList.value.length === 0) {
    loadingStandard.value = true
    try { const res = await StandardApi.getStandardPage({ staffId: id, pageSize: 100 } as any); standardList.value = res.list || [] } finally { loadingStandard.value = false }
  } else if (val === 'award' && awardList.value.length === 0) {
    loadingAward.value = true
    try { const res = await AwardApi.getAwardPage({ staffId: id, pageSize: 100 } as any); awardList.value = res.list || [] } finally { loadingAward.value = false }
  } else if (val === 'platform' && platformList.value.length === 0) {
    loadingPlatform.value = true
    try { const res = await PlatformApi.getPlatformPage({ staffId: id, pageSize: 100 } as any); platformList.value = res.list || [] } finally { loadingPlatform.value = false }
  } else if (val === 'product' && productList.value.length === 0) {
    loadingProduct.value = true
    try { const res = await ProductApi.getProductPage({ staffId: id, pageSize: 100 } as any); productList.value = res.list || [] } finally { loadingProduct.value = false }
  }
}

// Watch both tab and detailData ID to ensure initial load works accurately
watch([activeTab, () => detailData.value.id], ([tab, id]) => {
  if (id) {
    loadTabData(tab)
  }
}, { immediate: true })

defineExpose({ open })
</script>
