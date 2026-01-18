<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle" width="800px">
    <el-tabs
      v-model="activeTab"
      type="card"
      :closable="formType === 'create' && qualificationList.length > 1"
      @tab-remove="removeTab"
    >
      <el-tab-pane
        v-for="(item, index) in qualificationList"
        :key="index"
        :label="item.name || '新资质'"
        :name="index.toString()"
      >
        <el-form
          :ref="(el) => (formRefs[index] = el)"
          v-loading="formLoading"
          :model="item"
          :rules="formRules"
          label-width="120px"
        >
          <el-form-item label="资质名称" prop="name">
            <el-input v-model="item.name" placeholder="请输入资质名称" />
          </el-form-item>
          <el-form-item label="认证单位" prop="certUnit">
            <el-input v-model="item.certUnit" placeholder="请输入认证单位" />
          </el-form-item>
          <el-form-item label="证书编号" prop="certNumber">
            <el-input v-model="item.certNumber" placeholder="请输入证书编号" />
          </el-form-item>
          <el-form-item label="生效日期" prop="startDate">
            <el-date-picker
              v-model="item.startDate"
              type="date"
              value-format="x"
              placeholder="选择生效日期"
              style="width: 100%"
            />
          </el-form-item>
          <el-form-item label="到期日期" prop="endDate">
            <el-date-picker
              v-model="item.endDate"
              type="date"
              value-format="x"
              placeholder="选择到期日期"
              style="width: 100%"
            />
          </el-form-item>
          <el-form-item label="证明材料" prop="fileUrl">
            <UploadImg v-model="item.fileUrl" />
          </el-form-item>
          <el-form-item label="备注说明" prop="remark">
            <el-input v-model="item.remark" type="textarea" :rows="3" placeholder="请输入备注说明" />
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
    <div v-if="formType === 'create'" style="margin-top: 10px">
      <el-button type="primary" link @click="addTab">
        <Icon icon="ep:plus" class="mr-5px" /> 新增资质
      </el-button>
    </div>
    <template #footer>
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" setup>
import * as QualificationApi from '@/api/bus/qualification'

defineOptions({ name: 'BusQualificationForm' })

const { t } = useI18n()
const message = useMessage()

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')
const activeTab = ref('0')
const qualificationList = ref<QualificationApi.QualificationVO[]>([])
const formRefs = ref<any[]>([])

const formRules = reactive({
  name: [{ required: true, message: '资质名称不能为空', trigger: 'blur' }]
})

const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  if (id) {
    formLoading.value = true
    try {
      const data = await QualificationApi.getQualification(id)
      qualificationList.value = [data]
    } finally {
      formLoading.value = false
    }
  } else {
    addTab()
  }
}
defineExpose({ open })

const addTab = () => {
  qualificationList.value.push({
    id: undefined,
    name: '',
    certUnit: '',
    certNumber: '',
    startDate: undefined,
    endDate: undefined,
    fileUrl: '',
    remark: ''
  })
  activeTab.value = (qualificationList.value.length - 1).toString()
}

const removeTab = (targetName: string) => {
  const tabs = qualificationList.value
  let activeName = activeTab.value
  if (activeName === targetName) {
    tabs.forEach((_, index) => {
      if (index.toString() === targetName) {
        const nextTab = tabs[index + 1] || tabs[index - 1]
        if (nextTab) {
          activeName = tabs.indexOf(nextTab).toString()
        }
      }
    })
  }

  activeTab.value = activeName
  qualificationList.value = tabs.filter((_, index) => index.toString() !== targetName)
  formRefs.value.splice(parseInt(targetName), 1)
}

const emit = defineEmits(['success'])
const submitForm = async () => {
  // 校验所有表单
  for (let i = 0; i < formRefs.value.length; i++) {
    const formRef = formRefs.value[i]
    if (formRef) {
      const valid = await formRef.validate()
      if (!valid) {
        activeTab.value = i.toString()
        return
      }
    }
  }

  formLoading.value = true
  try {
    if (formType.value === 'create') {
      await QualificationApi.createQualificationBatch(qualificationList.value)
      message.success(t('common.createSuccess'))
    } else {
      await QualificationApi.updateQualification(qualificationList.value[0])
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    emit('success')
  } finally {
    formLoading.value = false
  }
}

const resetForm = () => {
  qualificationList.value = []
  formRefs.value = []
  activeTab.value = '0'
}
</script>
