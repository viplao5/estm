<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle" width="650px">
    <el-form ref="formRef" v-loading="formLoading" :model="formData" :rules="formRules" label-width="100px">
      <el-form-item label="奖励名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入奖励名称" />
      </el-form-item>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="颁奖单位" prop="grantUnit">
            <el-input v-model="formData.grantUnit" placeholder="请输入颁奖单位" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="获奖日期" prop="awardDate">
            <el-date-picker v-model="formData.awardDate" type="date" value-format="x" placeholder="选择获奖日期" style="width: 100%" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="奖励级别" prop="level">
            <el-select v-model="formData.level" placeholder="请选择奖励级别" clearable style="width: 100%">
              <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_AWARD_LEVEL)" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="奖励等级" prop="grade">
            <el-select v-model="formData.grade" placeholder="请选择奖励等级" clearable style="width: 100%">
              <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_AWARD_GRADE)" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="关联项目" prop="projectIds">
        <el-select v-model="formData.projectIds" placeholder="请选择关联项目" multiple clearable filterable style="width: 100%">
          <el-option v-for="item in projectList" :key="item.id" :label="item.name" :value="item.id!" />
        </el-select>
      </el-form-item>
      <el-form-item label="获奖人员" prop="staffIds">
        <el-select v-model="formData.staffIds" placeholder="请选择获奖人员" multiple filterable style="width: 100%">
          <el-option v-for="item in staffList" :key="item.id" :label="item.name" :value="item.id!" />
        </el-select>
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
import * as AwardApi from '@/api/bus/award'
import * as ProjectApi from '@/api/bus/project'
import * as StaffApi from '@/api/bus/staff'

defineOptions({ name: 'BusAwardForm' })

const { t } = useI18n()
const message = useMessage()
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')
const formData = ref({
  id: undefined,
  projectIds: [] as number[],
  name: '',
  grantUnit: '',
  level: '',
  grade: '',
  awardDate: undefined,
  staffIds: [] as number[]
})
const formRules = reactive({
  name: [{ required: true, message: '奖励名称不能为空', trigger: 'blur' }]
})
const formRef = ref()

const projectList = ref<ProjectApi.ResearchProjectVO[]>([])
const staffList = ref<StaffApi.TechnicalStaffVO[]>([])

const getSelectData = async () => {
  try {
    projectList.value = await ProjectApi.getProjectSimpleList()
    staffList.value = await StaffApi.getStaffSimpleList()
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
      formData.value = await AwardApi.getAward(id)
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
    const data = formData.value as AwardApi.AwardVO
    if (formType.value === 'create') {
      await AwardApi.createAward(data)
      message.success(t('common.createSuccess'))
    } else {
      await AwardApi.updateAward(data)
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
    projectIds: [],
    name: '',
    grantUnit: '',
    level: '',
    grade: '',
    awardDate: undefined,
    staffIds: []
  }
  formRef.value?.resetFields()
}
</script>
