<template>
  <ContentWrap>
    <div class="flex justify-between items-start">
      <el-form class="-mb-15px flex-1" :model="queryParams" ref="queryFormRef" :inline="true" label-width="80px">
        <el-form-item label="标准名称" prop="name"><el-input v-model="queryParams.name" placeholder="请输入标准名称" clearable @keyup.enter="handleQuery" class="!w-200px" /></el-form-item>
        <el-form-item label="标准类型" prop="type">
          <el-select v-model="queryParams.type" placeholder="请选择类型" clearable class="!w-200px">
            <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_STANDARD_TYPE)" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
          <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        </el-form-item>
      </el-form>
      <div class="flex-shrink-0">
        <el-button type="primary" @click="openForm('create')" v-hasPermi="['bus:standard:create']"><Icon icon="ep:plus" class="mr-5px" /> 新增</el-button>
        <el-button type="danger" plain :disabled="checkedIds.length === 0" @click="handleDeleteBatch" v-hasPermi="['bus:standard:delete']"><Icon icon="ep:delete" class="mr-5px" /> 批量删除</el-button>
      </div>
    </div>
  </ContentWrap>
  <ContentWrap>
    <el-table v-loading="loading" :data="list" @selection-change="handleRowCheckboxChange" stripe class="refined-table">
      <el-table-column type="selection" width="55" />
      <el-table-column label="标准名称" prop="name" min-width="250">
        <template #default="scope">
          <el-button link type="primary" @click="openDetail(scope.row.id)">{{ scope.row.name }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="标准类型" prop="type" min-width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.BUS_STANDARD_TYPE" :value="scope.row.type" />
        </template>
      </el-table-column>
      <el-table-column label="发表状态" prop="status" min-width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.BUS_STANDARD_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="公司角色" prop="companyRole" min-width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.BUS_STANDARD_ROLE" :value="scope.row.companyRole" />
        </template>
      </el-table-column>
      <el-table-column label="发布日期" prop="pubDate" width="120" :formatter="dateFormatter2" />
      <el-table-column label="创建时间" prop="createTime" width="180" :formatter="dateFormatter" />
      <el-table-column label="操作" align="center" width="160" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="openForm('update', scope.row.id)" v-hasPermi="['bus:standard:update']">修改</el-button>
          <el-button link type="danger" @click="handleDelete(scope.row.id)" v-hasPermi="['bus:standard:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />
  </ContentWrap>

  <StandardForm ref="formRef" @success="getList" />
  <StandardDetail ref="detailRef" />
</template>
<script lang="ts" setup>
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import * as StandardApi from '@/api/bus/standard'
import StandardForm from './StandardForm.vue'
import StandardDetail from './StandardDetail.vue'

defineOptions({ name: 'BusStandard' })
const message = useMessage()
const { t } = useI18n()
const loading = ref(true)
const list = ref([])
const total = ref(0)
const queryParams = reactive({ pageNo: 1, pageSize: 10, name: undefined, type: undefined })
const queryFormRef = ref()
const getList = async () => { loading.value = true; try { const data = await StandardApi.getStandardPage(queryParams); list.value = data.list; total.value = data.total } finally { loading.value = false } }
const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryFormRef.value?.resetFields(); handleQuery() }
const formRef = ref()
const openForm = (type: string, id?: number) => { formRef.value.open(type, id) }
const detailRef = ref()
const openDetail = (id: number) => { detailRef.value.open(id) }
const handleDelete = async (id: number) => { try { await message.delConfirm(); await StandardApi.deleteStandard(id); message.success(t('common.delSuccess')); await getList() } catch {} }
const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (rows: StandardApi.StandardVO[]) => { checkedIds.value = rows.map((row) => row.id!) }
const handleDeleteBatch = async () => { try { await message.delConfirm(); await StandardApi.deleteStandardList(checkedIds.value); checkedIds.value = []; message.success(t('common.delSuccess')); await getList() } catch {} }
onMounted(() => { getList() })
</script>
