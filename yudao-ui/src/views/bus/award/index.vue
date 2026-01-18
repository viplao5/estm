<template>
  <ContentWrap>
    <el-form class="-mb-15px" :model="queryParams" ref="queryFormRef" :inline="true" label-width="80px">
      <el-form-item label="奖励名称" prop="name"><el-input v-model="queryParams.name" placeholder="请输入名称" clearable @keyup.enter="handleQuery" class="!w-200px" /></el-form-item>
      <el-form-item label="奖励级别" prop="level">
        <el-select v-model="queryParams.level" placeholder="请选择级别" clearable class="!w-200px">
          <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_AWARD_LEVEL)" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button type="primary" plain @click="openForm('create')" v-hasPermi="['bus:award:create']"><Icon icon="ep:plus" class="mr-5px" /> 新增</el-button>
        <el-button type="danger" plain :disabled="checkedIds.length === 0" @click="handleDeleteBatch" v-hasPermi="['bus:award:delete']"><Icon icon="ep:delete" class="mr-5px" /> 批量删除</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <ContentWrap>
    <el-table v-loading="loading" :data="list" @selection-change="handleRowCheckboxChange">
      <el-table-column type="selection" width="55" />
      <el-table-column label="奖励名称" prop="name" min-width="200" />
      <el-table-column label="颁奖单位" prop="grantUnit" min-width="150" />
      <el-table-column label="奖励级别" prop="level" min-width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.BUS_AWARD_LEVEL" :value="scope.row.level" />
        </template>
      </el-table-column>
      <el-table-column label="奖励等级" prop="grade" min-width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.BUS_AWARD_GRADE" :value="scope.row.grade" />
        </template>
      </el-table-column>
      <el-table-column label="获奖日期" prop="awardDate" width="120" :formatter="dateFormatter2" />
      <el-table-column label="创建时间" prop="createTime" width="180" :formatter="dateFormatter" />
      <el-table-column label="操作" align="center" width="200" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="openDetail(scope.row.id)" v-hasPermi="['bus:award:query']">查看</el-button>
          <el-button link type="primary" @click="openForm('update', scope.row.id)" v-hasPermi="['bus:award:update']">修改</el-button>
          <el-button link type="danger" @click="handleDelete(scope.row.id)" v-hasPermi="['bus:award:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />
  </ContentWrap>
  <AwardForm ref="formRef" @success="getList" />
  <AwardDetail ref="detailRef" />
</template>
<script lang="ts" setup>
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import * as AwardApi from '@/api/bus/award'
import AwardForm from './AwardForm.vue'
import AwardDetail from './AwardDetail.vue'

defineOptions({ name: 'BusAward' })
const message = useMessage()
const { t } = useI18n()
const loading = ref(true)
const list = ref([])
const total = ref(0)
const queryParams = reactive({ pageNo: 1, pageSize: 10, name: undefined, level: undefined })
const queryFormRef = ref()
const getList = async () => { loading.value = true; try { const data = await AwardApi.getAwardPage(queryParams); list.value = data.list; total.value = data.total } finally { loading.value = false } }
const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryFormRef.value?.resetFields(); handleQuery() }
const formRef = ref()
const openForm = (type: string, id?: number) => { formRef.value.open(type, id) }
const detailRef = ref()
const openDetail = (id: number) => { detailRef.value.open(id) }
const handleDelete = async (id: number) => { try { await message.delConfirm(); await AwardApi.deleteAward(id); message.success(t('common.delSuccess')); await getList() } catch {} }
const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (rows: AwardApi.AwardVO[]) => { checkedIds.value = rows.map((row) => row.id!) }
const handleDeleteBatch = async () => { try { await message.delConfirm(); await AwardApi.deleteAwardList(checkedIds.value); checkedIds.value = []; message.success(t('common.delSuccess')); await getList() } catch {} }
onMounted(() => { getList() })
</script>
