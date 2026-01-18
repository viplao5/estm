<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle" width="800px">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="150px"
    >
      <!-- 基础信息 -->
      <el-divider content-position="left">基础信息</el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="formData.name" placeholder="请输入姓名" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="岗位" prop="post">
            <el-input v-model="formData.post" placeholder="请输入岗位" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="毕业院校" prop="school">
            <el-input v-model="formData.school" placeholder="请输入毕业院校" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="专业" prop="major">
            <el-input v-model="formData.major" placeholder="请输入专业" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="学历" prop="eduDegree">
            <el-select v-model="formData.eduDegree" placeholder="请选择学历" clearable style="width: 100%">
              <el-option
                v-for="dict in getStrDictOptions(DICT_TYPE.BUS_EDU_DEGREE)"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="职称" prop="title">
            <el-select v-model="formData.title" placeholder="请选择职称" clearable style="width: 100%">
              <el-option
                v-for="dict in getStrDictOptions(DICT_TYPE.BUS_TITLE)"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="入职日期" prop="entryDate">
            <el-date-picker
              v-model="formData.entryDate"
              type="date"
              placeholder="选择入职日期"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否在职" prop="isActive">
            <el-radio-group v-model="formData.isActive">
              <el-radio :value="true">在职</el-radio>
              <el-radio :value="false">离职</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 认定与合规 -->
      <el-divider content-position="left">认定与合规</el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="核心技术人员认定" prop="isCertified">
            <el-radio-group v-model="formData.isCertified">
              <el-radio :value="true">是</el-radio>
              <el-radio :value="false">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="与前单位竞业禁止" prop="hasNonCompete">
            <el-radio-group v-model="formData.hasNonCompete">
              <el-radio :value="true">有</el-radio>
              <el-radio :value="false">无</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="与前单位潜在纠纷" prop="hasDispute">
            <el-radio-group v-model="formData.hasDispute">
              <el-radio :value="true">有</el-radio>
              <el-radio :value="false">无</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 协议签署 -->
      <el-divider content-position="left">协议签署</el-divider>
      
      <!-- 保密与知识产权协议 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="保密与知识产权协议" prop="hasConfidentialAgreement">
            <el-radio-group v-model="formData.hasConfidentialAgreement">
              <el-radio :value="true">已签署</el-radio>
              <el-radio :value="false">未签署</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item v-if="formData.hasConfidentialAgreement" label="协议扫描件" prop="confidentialFile">
            <UploadImg v-model="formData.confidentialFile" height="100px" width="100px" />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 职务发明承诺书 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="职务发明承诺书" prop="hasInventionPledge">
            <el-radio-group v-model="formData.hasInventionPledge">
              <el-radio :value="true">已签署</el-radio>
              <el-radio :value="false">未签署</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item v-if="formData.hasInventionPledge" label="承诺书扫描件" prop="inventionPledgeFile">
            <UploadImg v-model="formData.inventionPledgeFile" height="100px" width="100px" />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 股权激励计划 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="股权激励计划" prop="hasEquityPlan">
            <el-radio-group v-model="formData.hasEquityPlan">
              <el-radio :value="true">已参与</el-radio>
              <el-radio :value="false">未参与</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item v-if="formData.hasEquityPlan" label="股权协议扫描件" prop="equityPlanFile">
            <UploadImg v-model="formData.equityPlanFile" height="100px" width="100px" />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 其他信息 -->
      <el-divider content-position="left">其他信息</el-divider>
      <el-form-item label="突出成就" prop="achievements">
        <el-input v-model="formData.achievements" type="textarea" :rows="3" placeholder="请输入突出成就" />
      </el-form-item>
      <el-form-item label="背景描述" prop="background">
        <el-input v-model="formData.background" type="textarea" :rows="3" placeholder="请输入背景描述" />
      </el-form-item>
      <el-form-item label="其他激励措施" prop="incentiveInfo">
        <el-input v-model="formData.incentiveInfo" type="textarea" :rows="2" placeholder="请输入其他激励措施" />
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
import { UploadImg } from '@/components/UploadFile'
import * as StaffApi from '@/api/bus/staff'

defineOptions({ name: 'BusStaffForm' })

const { t } = useI18n()
const message = useMessage()

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')
const formData = ref({
  id: undefined,
  name: '',
  post: '',
  school: '',
  major: '',
  eduDegree: '',
  title: '',
  entryDate: undefined,
  isActive: true,
  isCertified: false,
  hasNonCompete: false,
  hasDispute: false,
  hasConfidentialAgreement: false,
  confidentialFile: '',
  hasInventionPledge: false,
  inventionPledgeFile: '',
  hasEquityPlan: false,
  equityPlanFile: '',
  achievements: '',
  background: '',
  incentiveInfo: ''
})
const formRules = reactive({
  name: [{ required: true, message: '姓名不能为空', trigger: 'blur' }]
})
const formRef = ref()

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  if (id) {
    formLoading.value = true
    try {
      formData.value = await StaffApi.getStaff(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open })

/** 提交表单 */
const emit = defineEmits(['success'])
const submitForm = async () => {
  if (!formRef.value) return
  const valid = await formRef.value.validate()
  if (!valid) return
  formLoading.value = true
  try {
    const data = formData.value as StaffApi.TechnicalStaffVO
    if (formType.value === 'create') {
      await StaffApi.createStaff(data)
      message.success(t('common.createSuccess'))
    } else {
      await StaffApi.updateStaff(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    name: '',
    post: '',
    school: '',
    major: '',
    eduDegree: '',
    title: '',
    entryDate: undefined,
    isActive: true,
    isCertified: false,
    hasNonCompete: false,
    hasDispute: false,
    hasConfidentialAgreement: false,
    confidentialFile: '',
    hasInventionPledge: false,
    inventionPledgeFile: '',
    hasEquityPlan: false,
    equityPlanFile: '',
    achievements: '',
    background: '',
    incentiveInfo: ''
  }
  formRef.value?.resetFields()
}
</script>
