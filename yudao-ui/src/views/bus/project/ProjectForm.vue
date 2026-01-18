<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle" width="700px">
    <el-form ref="formRef" v-loading="formLoading" :model="formData" :rules="formRules" label-width="100px">
      <el-form-item label="项目名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入项目名称" />
      </el-form-item>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="项目类别" prop="category">
            <el-select v-model="formData.category" placeholder="请选择项目类别" clearable style="width: 100%">
              <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_PROJECT_CATEGORY)" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目状态" prop="status">
            <el-select v-model="formData.status" placeholder="请选择项目状态" clearable style="width: 100%">
              <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_PROJECT_STATUS)" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="预算(万)" prop="budget">
            <el-input-number v-model="formData.budget" :min="0" :precision="2" style="width: 100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目负责人" prop="leaderUserId">
            <el-select v-model="formData.leaderUserId" placeholder="请选择项目负责人" clearable filterable style="width: 100%">
              <el-option v-for="item in staffList" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="开始日期" prop="startDate">
            <el-date-picker v-model="formData.startDate" type="date" value-format="x" placeholder="选择开始日期" style="width: 100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="结束日期" prop="endDate">
            <el-date-picker v-model="formData.endDate" type="date" value-format="x" placeholder="选择结束日期" style="width: 100%" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="关联平台" prop="platformIds">
        <el-select 
          v-model="formData.platformIds" 
          placeholder="请选择关联的科研平台" 
          multiple 
          filterable 
          style="width: 100%"
        >
          <el-option v-for="item in platformList" :key="item.id" :label="item.name" :value="item.id">
            <span>{{ item.name }}</span>
            <span v-if="item.level" style="color: #999; margin-left: 8px;">({{ item.level }})</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="项目成员" prop="memberIds">
        <el-select v-model="formData.memberIds" placeholder="请选择项目成员" multiple filterable style="width: 100%">
          <el-option v-for="item in staffList" :key="item.id" :label="item.name" :value="item.id">
            <span>{{ item.name }}</span>
            <span v-if="item.post" style="color: #999; margin-left: 8px;">{{ item.post }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="项目简介" prop="intro">
        <el-input v-model="formData.intro" type="textarea" :rows="3" placeholder="请输入项目简介" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" setup>
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import * as ProjectApi from '@/api/bus/project'
import * as StaffApi from '@/api/bus/staff'
import * as PlatformApi from '@/api/bus/platform'

defineOptions({ name: 'BusProjectForm' })

const { t } = useI18n()
const message = useMessage()
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')
const formData = ref({
  id: undefined,
  name: '',
  category: '',
  status: '',
  budget: undefined,
  leaderUserId: undefined,
  startDate: undefined,
  endDate: undefined,
  intro: '',
  platformIds: [] as number[],
  memberIds: [] as number[]
})
const formRules = reactive({
  name: [{ required: true, message: '项目名称不能为空', trigger: 'blur' }]
})
const formRef = ref()

const staffList = ref<StaffApi.TechnicalStaffVO[]>([])
const platformList = ref<PlatformApi.ResearchPlatformVO[]>([])

const getSelectData = async () => {
  try {
    staffList.value = await StaffApi.getStaffSimpleList()
    platformList.value = await PlatformApi.getPlatformSimpleList()
  } catch (e) {
    console.error('获取下拉数据失败', e)
  }
}

const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  await getSelectData()
  if (id) {
    formLoading.value = true
    try {
      formData.value = await ProjectApi.getProject(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open })

const emit = defineEmits(['success'])
const submitForm = async () => {
  if (!formRef.value) return
  const valid = await formRef.value.validate()
  if (!valid) return
  formLoading.value = true
  try {
    const data = formData.value as ProjectApi.ResearchProjectVO
    if (formType.value === 'create') {
      await ProjectApi.createProject(data)
      message.success(t('common.createSuccess'))
    } else {
      await ProjectApi.updateProject(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    emit('success')
  } finally {
    formLoading.value = false
  }
}

const resetForm = () => {
  formData.value = {
    id: undefined,
    name: '',
    category: '',
    status: '',
    budget: undefined,
    leaderUserId: undefined,
    startDate: undefined,
    endDate: undefined,
    intro: '',
    platformIds: [],
    memberIds: []
  }
  formRef.value?.resetFields()
}
</script>
