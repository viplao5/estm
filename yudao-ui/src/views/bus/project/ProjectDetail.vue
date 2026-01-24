<template>
  <el-drawer v-model="dialogVisible" title="研发项目详情" size="1200px">
    <div v-loading="loading" class="p-20px">
      <!-- 基本信息 (始终显示在顶部) -->
      <el-descriptions :column="2" border title="基本信息" class="mb-20px">
        <el-descriptions-item label="项目ID">{{ detailData.id }}</el-descriptions-item>
        <el-descriptions-item label="项目名称">{{ detailData.name }}</el-descriptions-item>
        <el-descriptions-item label="项目类别">
          <dict-tag :type="DICT_TYPE.BUS_PROJECT_CATEGORY" :value="detailData.category || ''" />
        </el-descriptions-item>
        <el-descriptions-item label="项目状态">
          <dict-tag :type="DICT_TYPE.BUS_PROJECT_STATUS" :value="detailData.status || ''" />
        </el-descriptions-item>
        <el-descriptions-item label="预算(万)">{{ detailData.budget }}</el-descriptions-item>
        <el-descriptions-item label="项目负责人">{{ leaderName }}</el-descriptions-item>
        <el-descriptions-item label="开始日期">{{ detailData.startDate ? formatDate(detailData.startDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
        <el-descriptions-item label="结束日期">{{ detailData.endDate ? formatDate(detailData.endDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
        <el-descriptions-item label="项目简介" :span="2">{{ detailData.intro }}</el-descriptions-item>
        <el-descriptions-item label="创建时间" :span="2">{{ detailData.createTime ? formatDate(detailData.createTime) : '' }}</el-descriptions-item>
      </el-descriptions>

      <!-- 关联信息 (底部页签) -->
      <el-tabs v-model="activeTab">
        <!-- 关联平台 -->
        <el-tab-pane label="关联平台" name="platform" lazy>
          <el-table :data="platformList" border stripe>
            <el-table-column label="平台ID" prop="id" width="80" />
            <el-table-column label="平台名称" prop="name" min-width="180" />
            <el-table-column label="平台级别" prop="level" width="120">
              <template #default="scope">
                <dict-tag :type="DICT_TYPE.BUS_PLATFORM_LEVEL" :value="scope.row.level" />
              </template>
            </el-table-column>
            <el-table-column label="认定单位" prop="certUnit" min-width="150" />
          </el-table>
        </el-tab-pane>

        <!-- 知识产权 -->
        <el-tab-pane label="知识产权" name="ip" lazy>
          <el-table :data="ipList" border stripe>
            <el-table-column label="资产ID" prop="id" width="80" />
            <el-table-column label="资产名称" prop="name" min-width="180" />
            <el-table-column label="申请号" prop="appNumber" width="150" />
            <el-table-column label="资产类别" prop="category" width="100">
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

        <!-- 技术秘密 -->
        <el-tab-pane label="技术秘密" name="secret" lazy>
          <el-table :data="secretList" border stripe>
            <el-table-column label="成果ID" prop="id" width="80" />
            <el-table-column label="成果名称" prop="name" min-width="180" />
            <el-table-column label="成果类型" prop="type" width="120">
              <template #default="scope">
                <dict-tag :type="DICT_TYPE.BUS_SECRET_TYPE" :value="scope.row.type" />
              </template>
            </el-table-column>
            <el-table-column label="保密级别" prop="secretLevel" width="100">
              <template #default="scope">
                <dict-tag :type="DICT_TYPE.BUS_SECRET_LEVEL" :value="scope.row.secretLevel" />
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <!-- 论文著作 -->
        <el-tab-pane label="论文著作" name="paper" lazy>
          <el-table :data="paperList" border stripe>
            <el-table-column label="论文ID" prop="id" width="80" />
            <el-table-column label="论文标题" prop="title" min-width="200" />
            <el-table-column label="期刊/会议" prop="publication" min-width="150" />
            <el-table-column label="发表年份" prop="pubYear" width="100" />
            <el-table-column label="收录情况" prop="indexing" width="100">
              <template #default="scope">
                <dict-tag :type="DICT_TYPE.BUS_PAPER_INDEXING" :value="scope.row.indexing" />
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <!-- 标准 -->
        <el-tab-pane label="标准" name="standard" lazy>
          <el-table :data="standardList" border stripe>
            <el-table-column label="标准ID" prop="id" width="80" />
            <el-table-column label="标准名称" prop="name" min-width="200" />
            <el-table-column label="标准类型" prop="type" width="120">
              <template #default="scope">
                <dict-tag :type="DICT_TYPE.BUS_STANDARD_TYPE" :value="scope.row.type" />
              </template>
            </el-table-column>
            <el-table-column label="发布状态" prop="status" width="100">
              <template #default="scope">
                <dict-tag :type="DICT_TYPE.BUS_STANDARD_STATUS" :value="scope.row.status" />
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <!-- 科技奖励 -->
        <el-tab-pane label="科技奖励" name="award" lazy>
          <el-table :data="awardList" border stripe>
            <el-table-column label="奖励ID" prop="id" width="80" />
            <el-table-column label="奖励名称" prop="name" min-width="200" />
            <el-table-column label="奖励级别" prop="level" width="100">
              <template #default="scope">
                <dict-tag :type="DICT_TYPE.BUS_AWARD_LEVEL" :value="scope.row.level" />
              </template>
            </el-table-column>
            <el-table-column label="奖励等级" prop="grade" width="100">
              <template #default="scope">
                <dict-tag :type="DICT_TYPE.BUS_AWARD_GRADE" :value="scope.row.grade" />
              </template>
            </el-table-column>
            <el-table-column label="颁奖单位" prop="grantUnit" min-width="150" />
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
import * as ProjectApi from '@/api/bus/project'
import * as PlatformApi from '@/api/bus/platform'
import * as IPApi from '@/api/bus/ip'
import * as SecretApi from '@/api/bus/secret'
import * as PaperApi from '@/api/bus/paper'
import * as StandardApi from '@/api/bus/standard'
import * as AwardApi from '@/api/bus/award'
import * as StaffApi from '@/api/bus/staff'
import { formatDate } from '@/utils/formatTime'

defineOptions({ name: 'ProjectDetail' })

const dialogVisible = ref(false)
const loading = ref(false)
const activeTab = ref('platform')
const detailData = ref<ProjectApi.ResearchProjectVO>({} as any)

const platformList = ref<PlatformApi.ResearchPlatformVO[]>([])
const ipList = ref<IPApi.IntellectualPropertyVO[]>([])
const secretList = ref<SecretApi.TechnicalSecretVO[]>([])
const paperList = ref<PaperApi.PaperWorkVO[]>([])
const standardList = ref<StandardApi.StandardVO[]>([])
const awardList = ref<AwardApi.AwardVO[]>([])
const staffList = ref<StaffApi.TechnicalStaffVO[]>([])

const leaderName = ref('')

const open = async (id: number) => {
  dialogVisible.value = true
  activeTab.value = 'platform'
  loading.value = true
  try {
    // 加载基本信息
    detailData.value = await ProjectApi.getProject(id)
    
    // 加载负责人名称
    if (detailData.value.leaderUserId) {
      const staffSimpleList = await StaffApi.getStaffSimpleList()
      const leader = staffSimpleList.find(s => s.id === detailData.value.leaderUserId)
      leaderName.value = leader?.name || ''
    }

    // 加载关联平台
    if (detailData.value.platformIds?.length) {
      const allPlatforms = await PlatformApi.getPlatformSimpleList()
      platformList.value = allPlatforms.filter(p => detailData.value.platformIds?.includes(p.id!))
    } else {
      platformList.value = []
    }

    // 加载核心人员
    if (detailData.value.memberIds?.length) {
      const allStaff = await StaffApi.getStaffSimpleList()
      staffList.value = allStaff.filter(s => detailData.value.memberIds?.includes(s.id!))
    } else {
      staffList.value = []
    }

    // 加载关联成果
    const [ipRes, secretRes, paperRes, standardRes, awardRes] = await Promise.all([
      IPApi.getIPPage({ pageNo: 1, pageSize: 100, projectId: id } as any),
      SecretApi.getSecretPage({ pageNo: 1, pageSize: 100, projectId: id } as any),
      PaperApi.getPaperPage({ pageNo: 1, pageSize: 100, projectId: id } as any),
      StandardApi.getStandardPage({ pageNo: 1, pageSize: 100, projectId: id } as any),
      AwardApi.getAwardPage({ pageNo: 1, pageSize: 100, projectId: id } as any)
    ])
    ipList.value = ipRes.list || []
    secretList.value = secretRes.list || []
    paperList.value = paperRes.list || []
    standardList.value = standardRes.list || []
    awardList.value = awardRes.list || []
  } finally {
    loading.value = false
  }
}

defineExpose({ open })
</script>
