<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle" width="700px">
    <el-form ref="formRef" v-loading="formLoading" :model="formData" :rules="formRules" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="名称" prop="name">
            <el-input v-model="formData.name" placeholder="请输入名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="类型" prop="category">
            <el-select v-model="formData.category" placeholder="请选择类型" clearable style="width: 100%">
              <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_IP_CATEGORY)" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="申请号/登记号" prop="appNumber">
            <el-input v-model="formData.appNumber" placeholder="请输入申请号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-select v-model="formData.status" placeholder="请选择状态" clearable style="width: 100%">
              <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_IP_STATUS)" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="权利人" prop="owner">
            <el-input v-model="formData.owner" placeholder="请输入权利人" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="代理机构" prop="agency">
            <el-input v-model="formData.agency" placeholder="请输入代理机构" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="来源" prop="source">
            <el-select v-model="formData.source" placeholder="请选择来源" clearable style="width: 100%">
              <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_IP_SOURCE)" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="关联项目" prop="projectIds">
            <el-select v-model="formData.projectIds" placeholder="请选择关联项目" multiple clearable filterable style="width: 100%">
              <el-option v-for="item in projectList" :key="item.id" :label="item.name" :value="item.id!" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="申请日期" prop="appDate">
            <el-date-picker v-model="formData.appDate" type="date" value-format="x" placeholder="选择申请日期" style="width: 100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="授权日期" prop="grantDate">
            <el-date-picker v-model="formData.grantDate" type="date" value-format="x" placeholder="选择授权日期" style="width: 100%" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="下次缴费日期" prop="nextFeeDate">
            <el-date-picker v-model="formData.nextFeeDate" type="date" value-format="x" placeholder="选择下次缴费日期" style="width: 100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发明人/作者" prop="inventorIds">
            <el-select v-model="formData.inventorIds" placeholder="请选择发明人" multiple filterable style="width: 100%">
              <el-option v-for="item in staffList" :key="item.id" :label="item.name" :value="item.id!" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="官文扫描件" prop="fileUrl">
            <UploadImg v-model="formData.fileUrl" :limit="1" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" setup>
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import * as IPApi from '@/api/bus/ip'
import * as ProjectApi from '@/api/bus/project'
import * as StaffApi from '@/api/bus/staff'
import { UploadImg } from '@/components/UploadFile'

defineOptions({ name: 'BusIPForm' })

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
  appNumber: '',
  owner: '',
  agency: '',
  category: '',
  status: '',
  source: '',
  appDate: undefined,
  grantDate: undefined,
  nextFeeDate: undefined,
  fileUrl: '',
  inventorIds: [] as number[]
})
const formRules = reactive({
  name: [{ required: true, message: '名称不能为空', trigger: 'blur' }]
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
      formData.value = await IPApi.getIP(id)
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
    const data = formData.value as IPApi.IntellectualPropertyVO
    if (formType.value === 'create') {
      await IPApi.createIP(data)
      message.success(t('common.createSuccess'))
    } else {
      await IPApi.updateIP(data)
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
    appNumber: '',
    owner: '',
    agency: '',
    category: '',
    status: '',
    source: '',
    appDate: undefined,
    grantDate: undefined,
    nextFeeDate: undefined,
    fileUrl: '',
    inventorIds: []
  }
  formRef.value?.resetFields()
}
</script>
