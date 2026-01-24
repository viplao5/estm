<template>
  <el-drawer v-model="dialogVisible" title="论文与著作详情" size="1200px">
    <div v-loading="loading" class="p-20px">
      <!-- 基本信息 (始终显示在顶部) -->
      <el-descriptions :column="2" border title="基本信息" class="mb-20px">
        <el-descriptions-item label="论文ID">{{ detailData.id }}</el-descriptions-item>
        <el-descriptions-item label="论文标题">{{ detailData.title }}</el-descriptions-item>
        <el-descriptions-item label="期刊/会议名称">{{ detailData.publication }}</el-descriptions-item>
        <el-descriptions-item label="发表年份">{{ detailData.pubYear }}</el-descriptions-item>
        <el-descriptions-item label="DOI">{{ detailData.doi }}</el-descriptions-item>
        <el-descriptions-item label="收录情况">
           <dict-tag :type="DICT_TYPE.BUS_PAPER_INDEXING" :value="detailData.indexing || ''" />
        </el-descriptions-item>
        <el-descriptions-item label="外部作者" :span="2">{{ detailData.externalAuthors || '无' }}</el-descriptions-item>
        <el-descriptions-item label="创建时间" :span="2">{{ detailData.createTime ? formatDate(detailData.createTime) : '' }}</el-descriptions-item>
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
import * as PaperApi from '@/api/bus/paper'
import * as ProjectApi from '@/api/bus/project'
import * as StaffApi from '@/api/bus/staff'
import { formatDate } from '@/utils/formatTime'

defineOptions({ name: 'PaperDetail' })

const dialogVisible = ref(false)
const loading = ref(false)
const activeTab = ref('project')
const detailData = ref<PaperApi.PaperWorkVO>({} as any)

const projectList = ref<ProjectApi.ResearchProjectVO[]>([])
const staffList = ref<StaffApi.TechnicalStaffVO[]>([])

const open = async (id: number) => {
  dialogVisible.value = true
  activeTab.value = 'project'
  loading.value = true
  try {
    // 加载基本信息
    detailData.value = await PaperApi.getPaper(id)

    // 加载关联项目
    if (detailData.value.projectIds?.length) {
      const allProjects = await ProjectApi.getProjectSimpleList()
      projectList.value = allProjects.filter(p => detailData.value.projectIds?.includes(p.id!))
    } else {
      projectList.value = []
    }

    // 加载核心人员 (作者)
    if (detailData.value.authorIds?.length) {
      const allStaff = await StaffApi.getStaffSimpleList()
      staffList.value = allStaff.filter(s => detailData.value.authorIds?.includes(s.id!))
    } else {
      staffList.value = []
    }

  } finally {
    loading.value = false
  }
}

defineExpose({ open })
</script>
