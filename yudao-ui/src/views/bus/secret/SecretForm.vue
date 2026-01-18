<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle" width="650px">
    <el-form ref="formRef" v-loading="formLoading" :model="formData" :rules="formRules" label-width="100px">
      <el-form-item label="成果名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入成果名称" />
      </el-form-item>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="成果类型" prop="type">
            <el-select v-model="formData.type" placeholder="请选择成果类型" clearable style="width: 100%">
              <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_SECRET_TYPE)" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="保密级别" prop="secretLevel">
            <el-select v-model="formData.secretLevel" placeholder="请选择保密级别" clearable style="width: 100%">
              <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_SECRET_LEVEL)" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        
        <el-col :span="12">
          <el-form-item label="完成日期" prop="finishDate">
            <el-date-picker v-model="formData.finishDate" type="date" value-format="x" placeholder="选择完成日期" style="width: 100%" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="关联项目" prop="projectIds">
        <el-select v-model="formData.projectIds" placeholder="请选择关联项目" multiple clearable filterable style="width: 100%">
          <el-option v-for="item in projectList" :key="item.id" :label="item.name" :value="item.id!" />
        </el-select>
      </el-form-item>
      <el-form-item label="相关人员" prop="staffIds">
        <el-select v-model="formData.staffIds" placeholder="请选择相关技术人员" multiple filterable style="width: 100%">
          <el-option v-for="item in staffList" :key="item.id" :label="item.name" :value="item.id!" />
        </el-select>
      </el-form-item>
      <el-form-item label="所属技术领域" prop="techField">
        <el-input v-model="formData.techField" type="textarea" :rows="3" placeholder="请输入技术领域" />
      </el-form-item>
      <el-form-item label="成果描述" prop="description">
        <el-input v-model="formData.description" type="textarea" :rows="3" placeholder="请输入成果描述" />
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
import * as SecretApi from '@/api/bus/secret'
import * as ProjectApi from '@/api/bus/project'
import * as StaffApi from '@/api/bus/staff'

defineOptions({ name: 'BusSecretForm' })

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
  type: '',
  secretLevel: '',
  finishDate: undefined,
  description: '',
  techField: '',
  staffIds: [] as number[]
})
const formRules = reactive({
  name: [{ required: true, message: '成果名称不能为空', trigger: 'blur' }]
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
      formData.value = await SecretApi.getSecret(id)
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
    const data = formData.value as SecretApi.TechnicalSecretVO
    if (formType.value === 'create') {
      await SecretApi.createSecret(data)
      message.success(t('common.createSuccess'))
    } else {
      await SecretApi.updateSecret(data)
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
    type: '',
    secretLevel: '',
    finishDate: undefined,
    description: '',
    techField: '',
    staffIds: []
  }
  formRef.value?.resetFields()
}
</script>
