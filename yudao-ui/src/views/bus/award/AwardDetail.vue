<template>
  <el-drawer v-model="dialogVisible" title="科技奖励详情" size="1200px">
    <div v-loading="loading" class="p-20px">
      <!-- 基本信息 (始终显示在顶部) -->
      <el-descriptions :column="2" border title="基本信息" class="mb-20px">
        <el-descriptions-item label="奖励ID">{{ detailData.id }}</el-descriptions-item>
        <el-descriptions-item label="奖励名称">{{ detailData.name }}</el-descriptions-item>
        <el-descriptions-item label="奖励级别">
           <dict-tag :type="DICT_TYPE.BUS_AWARD_LEVEL" :value="detailData.level || ''" />
        </el-descriptions-item>
        <el-descriptions-item label="奖励等级">
           <dict-tag :type="DICT_TYPE.BUS_AWARD_GRADE" :value="detailData.grade || ''" />
        </el-descriptions-item>
        <el-descriptions-item label="颁奖单位" :span="2">{{ detailData.grantUnit }}</el-descriptions-item>
        <el-descriptions-item label="获奖日期">{{ detailData.awardDate ? formatDate(detailData.awardDate, 'YYYY-MM-DD') : '' }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ detailData.createTime ? formatDate(detailData.createTime) : '' }}</el-descriptions-item>
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
import * as AwardApi from '@/api/bus/award'
import * as ProjectApi from '@/api/bus/project'
import * as StaffApi from '@/api/bus/staff'
import { formatDate } from '@/utils/formatTime'

defineOptions({ name: 'AwardDetail' })

const dialogVisible = ref(false)
const loading = ref(false)
const activeTab = ref('project')
const detailData = ref<AwardApi.AwardVO>({} as any)

const projectList = ref<ProjectApi.ResearchProjectVO[]>([])
const staffList = ref<StaffApi.TechnicalStaffVO[]>([])

const open = async (id: number) => {
  dialogVisible.value = true
  activeTab.value = 'project'
  loading.value = true
  try {
    // 加载基本信息
    detailData.value = await AwardApi.getAward(id)

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

  } finally {
    loading.value = false
  }
}

defineExpose({ open })
</script>
