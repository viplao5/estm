<template>
  <ContentWrap>
    <el-form class="-mb-15px" :model="queryParams" ref="queryFormRef" :inline="true" label-width="80px">
      <el-form-item label="名称" prop="name"><el-input v-model="queryParams.name" placeholder="请输入名称" clearable @keyup.enter="handleQuery" class="!w-200px" /></el-form-item>
      <el-form-item label="类型" prop="category"><el-input v-model="queryParams.category" placeholder="请输入类型" clearable class="!w-200px" /></el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button type="primary" plain @click="openForm('create')" v-hasPermi="['bus:intellectual-property:create']"><Icon icon="ep:plus" class="mr-5px" /> 新增</el-button>
        <el-button type="danger" plain :disabled="checkedIds.length === 0" @click="handleDeleteBatch" v-hasPermi="['bus:intellectual-property:delete']"><Icon icon="ep:delete" class="mr-5px" /> 批量删除</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <ContentWrap>
    <el-table v-loading="loading" :data="list" @selection-change="handleRowCheckboxChange">
      <el-table-column type="selection" width="55" />
      <el-table-column label="名称" prop="name" min-width="200" />
      <el-table-column label="类型" prop="category" min-width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.BUS_IP_CATEGORY" :value="scope.row.category" />
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="status" min-width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.BUS_IP_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="申请号/登记号" prop="appNumber" min-width="150" />
      <el-table-column label="权利人" prop="owner" min-width="120" />
      <el-table-column label="申请日期" prop="appDate" width="120" :formatter="dateFormatter2" />
      <el-table-column label="授权日期" prop="grantDate" width="120" :formatter="dateFormatter2" />
      <el-table-column label="创建时间" prop="createTime" width="180" :formatter="dateFormatter" />
      <el-table-column label="操作" align="center" width="150" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="openForm('update', scope.row.id)" v-hasPermi="['bus:intellectual-property:update']">修改</el-button>
          <el-button link type="danger" @click="handleDelete(scope.row.id)" v-hasPermi="['bus:intellectual-property:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />
  </ContentWrap>
  <IPForm ref="formRef" @success="getList" />
</template>
<script lang="ts" setup>
import { DICT_TYPE } from '@/utils/dict'
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import * as IPApi from '@/api/bus/ip'
import IPForm from './IPForm.vue'
defineOptions({ name: 'BusIntellectualProperty' })
const message = useMessage()
const { t } = useI18n()
const loading = ref(true)
const list = ref([])
const total = ref(0)
const queryParams = reactive({ pageNo: 1, pageSize: 10, name: undefined, category: undefined })
const queryFormRef = ref()
const getList = async () => { loading.value = true; try { const data = await IPApi.getIPPage(queryParams); list.value = data.list; total.value = data.total } finally { loading.value = false } }
const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryFormRef.value?.resetFields(); handleQuery() }
const formRef = ref()
const openForm = (type: string, id?: number) => { formRef.value.open(type, id) }
const handleDelete = async (id: number) => { try { await message.delConfirm(); await IPApi.deleteIP(id); message.success(t('common.delSuccess')); await getList() } catch {} }
const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (rows: IPApi.IntellectualPropertyVO[]) => { checkedIds.value = rows.map((row) => row.id!) }
const handleDeleteBatch = async () => { try { await message.delConfirm(); await IPApi.deleteIPList(checkedIds.value); checkedIds.value = []; message.success(t('common.delSuccess')); await getList() } catch {} }
onMounted(() => { getList() })
</script>
