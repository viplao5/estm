<template>
  <ContentWrap>
    <el-form class="-mb-15px" :model="queryParams" ref="queryFormRef" :inline="true" label-width="80px">
      <el-form-item label="成果名称" prop="name"><el-input v-model="queryParams.name" placeholder="请输入名称" clearable @keyup.enter="handleQuery" class="!w-200px" /></el-form-item>
      <el-form-item label="保密级别" prop="secretLevel"><el-input v-model="queryParams.secretLevel" placeholder="请输入保密级别" clearable class="!w-200px" /></el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button type="primary" plain @click="openForm('create')" v-hasPermi="['bus:technical-secret:create']"><Icon icon="ep:plus" class="mr-5px" /> 新增</el-button>
        <el-button type="danger" plain :disabled="checkedIds.length === 0" @click="handleDeleteBatch" v-hasPermi="['bus:technical-secret:delete']"><Icon icon="ep:delete" class="mr-5px" /> 批量删除</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <ContentWrap>
    <el-table v-loading="loading" :data="list" @selection-change="handleRowCheckboxChange">
      <el-table-column type="selection" width="55" />
      <el-table-column label="成果名称" prop="name" min-width="200" />
      <el-table-column label="成果类型" prop="type" min-width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.BUS_SECRET_TYPE" :value="scope.row.type" />
        </template>
      </el-table-column>
      <el-table-column label="保密级别" prop="secretLevel" min-width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.BUS_SECRET_LEVEL" :value="scope.row.secretLevel" />
        </template>
      </el-table-column>
      <el-table-column label="技术领域" prop="techField" min-width="120" />
      <el-table-column label="完成日期" prop="finishDate" width="120" :formatter="dateFormatter2" />
      <el-table-column label="创建时间" prop="createTime" width="180" :formatter="dateFormatter" />
      <el-table-column label="操作" align="center" width="150" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="openForm('update', scope.row.id)" v-hasPermi="['bus:technical-secret:update']">修改</el-button>
          <el-button link type="danger" @click="handleDelete(scope.row.id)" v-hasPermi="['bus:technical-secret:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />
  </ContentWrap>
  <SecretForm ref="formRef" @success="getList" />
</template>
<script lang="ts" setup>
import { DICT_TYPE } from '@/utils/dict'
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import * as SecretApi from '@/api/bus/secret'
import SecretForm from './SecretForm.vue'
defineOptions({ name: 'BusTechnicalSecret' })
const message = useMessage()
const { t } = useI18n()
const loading = ref(true)
const list = ref([])
const total = ref(0)
const queryParams = reactive({ pageNo: 1, pageSize: 10, name: undefined, secretLevel: undefined })
const queryFormRef = ref()
const getList = async () => { loading.value = true; try { const data = await SecretApi.getSecretPage(queryParams); list.value = data.list; total.value = data.total } finally { loading.value = false } }
const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryFormRef.value?.resetFields(); handleQuery() }
const formRef = ref()
const openForm = (type: string, id?: number) => { formRef.value.open(type, id) }
const handleDelete = async (id: number) => { try { await message.delConfirm(); await SecretApi.deleteSecret(id); message.success(t('common.delSuccess')); await getList() } catch {} }
const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (rows: SecretApi.TechnicalSecretVO[]) => { checkedIds.value = rows.map((row) => row.id!) }
const handleDeleteBatch = async () => { try { await message.delConfirm(); await SecretApi.deleteSecretList(checkedIds.value); checkedIds.value = []; message.success(t('common.delSuccess')); await getList() } catch {} }
onMounted(() => { getList() })
</script>
