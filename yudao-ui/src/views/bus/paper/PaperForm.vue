<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle" width="700px">
    <el-form ref="formRef" v-loading="formLoading" :model="formData" :rules="formRules" label-width="120px">
      <el-form-item label="标题" prop="title">
        <el-input v-model="formData.title" placeholder="请输入论文/专著标题" />
      </el-form-item>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="期刊/会议" prop="publication">
            <el-input v-model="formData.publication" placeholder="请输入期刊或会议名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="收录情况" prop="indexing">
            <el-select v-model="formData.indexing" placeholder="请选择收录情况" clearable style="width: 100%">
              <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_PAPER_INDEXING)" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="发表年份" prop="pubYear">
            <el-date-picker v-model="formData.pubYear" type="year" value-format="YYYY" placeholder="选择发表年份" style="width: 100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="DOI" prop="doi">
            <el-input v-model="formData.doi" placeholder="请输入DOI号" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="关联项目" prop="projectIds">
        <el-select v-model="formData.projectIds" placeholder="请选择关联项目" multiple clearable filterable style="width: 100%">
          <el-option v-for="item in projectList" :key="item.id" :label="item.name" :value="item.id!" />
        </el-select>
      </el-form-item>
      <el-form-item label="作者" prop="authorIds">
        <el-select
          v-model="formData.authorIds"
          placeholder="请选择作者（公司内部人员将关联为核心技术人员）"
          multiple
          filterable
          style="width: 100%"
        >
          <el-option v-for="item in staffList" :key="item.id" :label="item.name" :value="item.id!">
            <span>{{ item.name }}</span>
            <span v-if="item.post" style="color: #999; margin-left: 8px;">{{ item.post }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="外部作者" prop="externalAuthors">
        <el-select
          ref="externalAuthorSelect"
          v-model="formData.externalAuthors"
          multiple
          filterable
          allow-create
          default-first-option
          no-data-text="输入姓名后按回车添加"
          placeholder="请输入外部作者姓名"
          style="width: 100%"
          @change="handleExternalAuthorChange"
        >
          <el-option v-for="item in formData.externalAuthors" :key="item" :label="item" :value="item" />
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
import * as PaperApi from '@/api/bus/paper'
import * as ProjectApi from '@/api/bus/project'
import * as StaffApi from '@/api/bus/staff'

defineOptions({ name: 'BusPaperForm' })

const { t } = useI18n()
const message = useMessage()
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')
const formData = ref({
  id: undefined,
  title: '',
  publication: '',
  doi: '',
  indexing: '',
  pubYear: undefined as string | undefined,
  projectIds: [] as number[],
  authorIds: [] as number[],
  externalAuthors: [] as string[]
})
const formRules = reactive({
  title: [{ required: true, message: '标题不能为空', trigger: 'blur' }]
})
const formRef = ref()
const externalAuthorSelect = ref()

/** 清除外部作者选择框的查询词 */
const handleExternalAuthorChange = () => {
  nextTick(() => {
    if (externalAuthorSelect.value) {
      externalAuthorSelect.value.query = ''
      if (externalAuthorSelect.value.states) {
        externalAuthorSelect.value.states.query = ''
        externalAuthorSelect.value.states.inputValue = ''
        externalAuthorSelect.value.states.selectedLabel = ''
      }
    }
  })
}

// 项目列表
const projectList = ref<ProjectApi.ResearchProjectVO[]>([])
// 技术人员列表
const staffList = ref<StaffApi.TechnicalStaffVO[]>([])

/** 获取下拉数据 */
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
      const data = await PaperApi.getPaper(id)
      formData.value = {
        ...data,
        pubYear: data.pubYear ? data.pubYear.toString() : undefined,
        externalAuthors: data.externalAuthors ? data.externalAuthors.split(',') : []
      }
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
    const data = {
      ...formData.value,
      externalAuthors: formData.value.externalAuthors.join(',')
    } as any
    if (formType.value === 'create') {
      await PaperApi.createPaper(data)
      message.success(t('common.createSuccess'))
    } else {
      await PaperApi.updatePaper(data)
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
    title: '',
    publication: '',
    doi: '',
    indexing: '',
    pubYear: undefined,
    projectIds: [],
    authorIds: [],
    externalAuthors: []
  }
  formRef.value?.resetFields()
}
</script>
