<template>
  <ContentWrap>
    <el-form class="-mb-15px" :model="queryParams" ref="queryFormRef" :inline="true" label-width="80px">
      <el-form-item label="标题" prop="title"><el-input v-model="queryParams.title" placeholder="请输入标题" clearable @keyup.enter="handleQuery" class="!w-200px" /></el-form-item>
      <el-form-item label="收录情况" prop="indexing">
        <el-select v-model="queryParams.indexing" placeholder="请选择收录情况" clearable class="!w-200px">
          <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_PAPER_INDEXING)" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button type="primary" plain @click="openForm('create')" v-hasPermi="['bus:paper-work:create']"><Icon icon="ep:plus" class="mr-5px" /> 新增</el-button>
        <el-button type="danger" plain :disabled="checkedIds.length === 0" @click="handleDeleteBatch" v-hasPermi="['bus:paper-work:delete']"><Icon icon="ep:delete" class="mr-5px" /> 批量删除</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <ContentWrap>
    <el-table v-loading="loading" :data="list" @selection-change="handleRowCheckboxChange">
      <el-table-column type="selection" width="55" />
      <el-table-column label="标题" prop="title" min-width="250" />
      <el-table-column label="期刊/会议" prop="publication" min-width="150" />
      <el-table-column label="收录情况" prop="indexing" min-width="120">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.BUS_PAPER_INDEXING" :value="scope.row.indexing" />
        </template>
      </el-table-column>
      <el-table-column label="发表年份" prop="pubYear" min-width="100" />
      <el-table-column label="DOI" prop="doi" min-width="150" />
      <el-table-column label="创建时间" prop="createTime" width="180" :formatter="dateFormatter" />
      <el-table-column label="操作" align="center" width="150" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="openForm('update', scope.row.id)" v-hasPermi="['bus:paper-work:update']">修改</el-button>
          <el-button link type="danger" @click="handleDelete(scope.row.id)" v-hasPermi="['bus:paper-work:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />
  </ContentWrap>
  <PaperForm ref="formRef" @success="getList" />
</template>
<script lang="ts" setup>
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import * as PaperApi from '@/api/bus/paper'
import PaperForm from './PaperForm.vue'
defineOptions({ name: 'BusPaperWork' })
const message = useMessage()
const { t } = useI18n()
const loading = ref(true)
const list = ref([])
const total = ref(0)
const queryParams = reactive({ pageNo: 1, pageSize: 10, title: undefined, indexing: undefined })
const queryFormRef = ref()
const getList = async () => { loading.value = true; try { const data = await PaperApi.getPaperPage(queryParams); list.value = data.list; total.value = data.total } finally { loading.value = false } }
const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryFormRef.value?.resetFields(); handleQuery() }
const formRef = ref()
const openForm = (type: string, id?: number) => { formRef.value.open(type, id) }
const handleDelete = async (id: number) => { try { await message.delConfirm(); await PaperApi.deletePaper(id); message.success(t('common.delSuccess')); await getList() } catch {} }
const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (rows: PaperApi.PaperWorkVO[]) => { checkedIds.value = rows.map((row) => row.id!) }
const handleDeleteBatch = async () => { try { await message.delConfirm(); await PaperApi.deletePaperList(checkedIds.value); checkedIds.value = []; message.success(t('common.delSuccess')); await getList() } catch {} }
onMounted(() => { getList() })
</script>
