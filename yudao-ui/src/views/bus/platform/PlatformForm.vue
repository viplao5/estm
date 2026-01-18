<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
    <el-form ref="formRef" v-loading="formLoading" :model="formData" :rules="formRules" label-width="100px">
      <el-form-item label="平台名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入平台名称" />
      </el-form-item>
      <el-form-item label="平台级别" prop="level">
        <el-select v-model="formData.level" placeholder="请选择平台级别" clearable style="width: 100%">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.BUS_PLATFORM_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="认定单位" prop="certUnit">
        <el-input v-model="formData.certUnit" placeholder="请输入认定单位" />
      </el-form-item>
      <el-form-item label="认定日期" prop="certDate">
        <el-date-picker v-model="formData.certDate" type="date" value-format="x" placeholder="选择认定日期" style="width: 100%" />
      </el-form-item>
      <el-form-item label="有效期开始" prop="startDate">
        <el-date-picker v-model="formData.startDate" type="date" value-format="x" placeholder="选择开始日期" style="width: 100%" />
      </el-form-item>
      <el-form-item label="有效期结束" prop="endDate">
        <el-date-picker v-model="formData.endDate" type="date" value-format="x" placeholder="选择结束日期" style="width: 100%" />
      </el-form-item>
      <el-form-item label="核心技术人员" prop="staffIds">
        <el-select v-model="formData.staffIds" multiple filterable placeholder="请选择人员（可删除标签形式）" style="width: 100%">
          <el-option v-for="item in staffList" :key="item.id" :label="item.name" :value="item.id!">
             <span>{{ item.name }}</span>
             <span v-if="item.post" style="color: #999; margin-left: 8px;">{{ item.post }}</span>
          </el-option>
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
import * as PlatformApi from '@/api/bus/platform'
import * as StaffApi from '@/api/bus/staff'

defineOptions({ name: 'BusPlatformForm' })

const { t } = useI18n()
const message = useMessage()
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')
const formData = ref({ id: undefined, name: '', level: '', certUnit: '', certDate: undefined, startDate: undefined, endDate: undefined, staffIds: [] })
const formRules = reactive({ name: [{ required: true, message: '平台名称不能为空', trigger: 'blur' }] })
const formRef = ref()
const staffList = ref<StaffApi.TechnicalStaffVO[]>([])

/** 获取下拉数据 */
const getSelectData = async () => {
  try {
    staffList.value = await StaffApi.getStaffSimpleList()
  } catch (e) {
    console.error('获取人员列表失败', e)
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
    try { formData.value = await PlatformApi.getPlatform(id) } finally { formLoading.value = false }
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
    const data = formData.value as PlatformApi.ResearchPlatformVO
    if (formType.value === 'create') { await PlatformApi.createPlatform(data); message.success(t('common.createSuccess')) }
    else { await PlatformApi.updatePlatform(data); message.success(t('common.updateSuccess')) }
    dialogVisible.value = false
    emit('success')
  } finally { formLoading.value = false }
}

const resetForm = () => {
  formData.value = { id: undefined, name: '', level: '', certUnit: '', certDate: undefined, startDate: undefined, endDate: undefined, staffIds: [] }
  formRef.value?.resetFields()
}
</script>
