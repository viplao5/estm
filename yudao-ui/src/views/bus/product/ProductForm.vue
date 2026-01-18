<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle" width="800px">
    <el-form ref="formRef" v-loading="formLoading" :model="formData" :rules="formRules" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="产品名称" prop="name">
            <el-input v-model="formData.name" placeholder="请输入产品名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="负责人" prop="leader">
            <el-input v-model="formData.leader" placeholder="请输入负责人" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="产品类别" prop="category">
            <el-select v-model="formData.category" placeholder="请选择产品类别" clearable style="width: 100%">
              <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_PRODUCT_CATEGORY)" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="产品状态" prop="status">
            <el-select v-model="formData.status" placeholder="请选择产品状态" clearable style="width: 100%">
              <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_PRODUCT_STATUS)" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="销售收入(万)" prop="revenue">
            <el-input v-model="formData.revenue" placeholder="请输入最近一年销售收入" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="销售毛利(万)" prop="profit">
            <el-input v-model="formData.profit" placeholder="请输入最近一年销售毛利" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="上市日期" prop="launchDate">
            <el-date-picker v-model="formData.launchDate" type="date" value-format="x" placeholder="选择上市日期" style="width: 100%" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="关联知识产权" prop="ipIds">
        <el-select v-model="formData.ipIds" multiple filterable placeholder="请选择关联知识产权" style="width: 100%">
          <el-option v-for="item in ipList" :key="item.id" :label="item.name" :value="item.id!" />
        </el-select>
      </el-form-item>
      <el-form-item label="关联技术秘密" prop="secretIds">
        <el-select v-model="formData.secretIds" multiple filterable placeholder="请选择关联技术秘密" style="width: 100%">
          <el-option v-for="item in secretList" :key="item.id" :label="item.name" :value="item.id!" />
        </el-select>
      </el-form-item>
      <el-form-item label="核心技术人员" prop="staffIds">
        <el-select v-model="formData.staffIds" multiple filterable placeholder="请选择人员（可删除标签形式）" style="width: 100%">
          <el-option v-for="item in staffList" :key="item.id" :label="item.name" :value="item.id!">
             <span>{{ item.name }}</span>
             <span v-if="item.post" style="color: #999; margin-left: 8px;">{{ item.post }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="产品简介" prop="intro">
        <el-input v-model="formData.intro" type="textarea" :rows="3" placeholder="请输入产品简介" />
      </el-form-item>
      <el-form-item label="重要客户" prop="keyCustomers">
        <el-input v-model="formData.keyCustomers" type="textarea" :rows="3" placeholder="请输入重要客户名称" />
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
import * as ProductApi from '@/api/bus/product'
import * as IpApi from '@/api/bus/ip'
import * as SecretApi from '@/api/bus/secret'
import * as StaffApi from '@/api/bus/staff'

defineOptions({ name: 'BusProductForm' })

const { t } = useI18n()
const message = useMessage()
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')
const formData = ref({
  id: undefined,
  name: '',
  leader: '',
  revenue: undefined,
  profit: undefined,
  category: '',
  status: '',
  launchDate: undefined,
  intro: '',
  keyCustomers: '',
  ipIds: [] as number[],
  secretIds: [] as number[],
  staffIds: [] as number[]
})
const formRules = reactive({
  name: [{ required: true, message: '产品名称不能为空', trigger: 'blur' }]
})
const formRef = ref()

// 下拉列表数据
const ipList = ref<IpApi.IntellectualPropertyVO[]>([])
const secretList = ref<SecretApi.TechnicalSecretVO[]>([])
const staffList = ref<StaffApi.TechnicalStaffVO[]>([])

/** 获取下拉数据 */
const getSelectData = async () => {
  try {
    ipList.value = await IpApi.getIPSimpleList()
    secretList.value = await SecretApi.getSecretSimpleList()
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
      formData.value = await ProductApi.getProduct(id)
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
    if (formType.value === 'create') {
      await ProductApi.createProduct(formData.value as any)
      message.success(t('common.createSuccess'))
    } else {
      await ProductApi.updateProduct(formData.value as any)
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
    leader: '',
    revenue: undefined,
    profit: undefined,
    category: '',
    status: '',
    launchDate: undefined,
    intro: '',
    keyCustomers: '',
    ipIds: [],
    secretIds: [],
    staffIds: []
  }
  formRef.value?.resetFields()
}
</script>
