<template>
  <ContentWrap>
    <el-form class="-mb-15px" :model="queryParams" ref="queryFormRef" :inline="true" label-width="80px">
      <el-form-item label="资质名称" prop="name"><el-input v-model="queryParams.name" placeholder="请输入资质名称" clearable @keyup.enter="handleQuery" class="!w-200px" /></el-form-item>
      <el-form-item label="认证单位" prop="certUnit"><el-input v-model="queryParams.certUnit" placeholder="请输入认证单位" clearable class="!w-200px" /></el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button type="primary" plain @click="openForm('create')" v-hasPermi="['bus:qualification:create']"><Icon icon="ep:plus" class="mr-5px" /> 新增</el-button>
        <el-button type="danger" plain :disabled="checkedIds.length === 0" @click="handleDeleteBatch" v-hasPermi="['bus:qualification:delete']"><Icon icon="ep:delete" class="mr-5px" /> 批量删除</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <ContentWrap>
    <el-table v-loading="loading" :data="list" @selection-change="handleRowCheckboxChange">
      <el-table-column type="selection" width="55" />
      <el-table-column label="资质名称" prop="name" min-width="200" />
      <el-table-column label="认证单位" prop="certUnit" min-width="150" />
      <el-table-column label="证书编号" prop="certNumber" min-width="150" />
      <el-table-column label="生效日期" prop="startDate" width="120" :formatter="dateFormatter2" />
      <el-table-column label="到期日期" prop="endDate" width="120" :formatter="dateFormatter2" />
      <el-table-column label="创建时间" prop="createTime" width="180" :formatter="dateFormatter" />
      <el-table-column label="操作" align="center" width="150" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="openForm('update', scope.row.id)" v-hasPermi="['bus:qualification:update']">修改</el-button>
          <el-button link type="danger" @click="handleDelete(scope.row.id)" v-hasPermi="['bus:qualification:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />
  </ContentWrap>
  <QualificationForm ref="formRef" @success="getList" />
</template>
<script lang="ts" setup>
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import * as QualificationApi from '@/api/bus/qualification'
import QualificationForm from './QualificationForm.vue'
defineOptions({ name: 'BusQualification' })
const message = useMessage()
const { t } = useI18n()
const loading = ref(true)
const list = ref([])
const total = ref(0)
const queryParams = reactive({ pageNo: 1, pageSize: 10, name: undefined, certUnit: undefined })
const queryFormRef = ref()
const getList = async () => { loading.value = true; try { const data = await QualificationApi.getQualificationPage(queryParams); list.value = data.list; total.value = data.total } finally { loading.value = false } }
const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryFormRef.value?.resetFields(); handleQuery() }
const formRef = ref()
const openForm = (type: string, id?: number) => { formRef.value.open(type, id) }
const handleDelete = async (id: number) => { try { await message.delConfirm(); await QualificationApi.deleteQualification(id); message.success(t('common.delSuccess')); await getList() } catch {} }
const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (rows: QualificationApi.QualificationVO[]) => { checkedIds.value = rows.map((row) => row.id!) }
const handleDeleteBatch = async () => { try { await message.delConfirm(); await QualificationApi.deleteQualificationList(checkedIds.value); checkedIds.value = []; message.success(t('common.delSuccess')); await getList() } catch {} }
onMounted(() => { getList() })
</script>
