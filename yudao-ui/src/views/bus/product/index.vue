<template>
  <ContentWrap>
    <el-form class="-mb-15px" :model="queryParams" ref="queryFormRef" :inline="true" label-width="80px">
      <el-form-item label="产品名称" prop="name"><el-input v-model="queryParams.name" placeholder="请输入产品名称" clearable @keyup.enter="handleQuery" class="!w-200px" /></el-form-item>
      <el-form-item label="产品类别" prop="category">
        <el-select v-model="queryParams.category" placeholder="请选择类别" clearable class="!w-200px">
          <el-option v-for="dict in getStrDictOptions(DICT_TYPE.BUS_PRODUCT_CATEGORY)" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button type="primary" plain @click="openForm('create')" v-hasPermi="['bus:product-service:create']"><Icon icon="ep:plus" class="mr-5px" /> 新增</el-button>
        <el-button type="danger" plain :disabled="checkedIds.length === 0" @click="handleDeleteBatch" v-hasPermi="['bus:product-service:delete']"><Icon icon="ep:delete" class="mr-5px" /> 批量删除</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <ContentWrap>
    <el-table v-loading="loading" :data="list" @selection-change="handleRowCheckboxChange">
      <el-table-column type="selection" width="55" />
      <el-table-column label="产品名称" prop="name" min-width="200" />
      <el-table-column label="产品类别" prop="category" min-width="120">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.BUS_PRODUCT_CATEGORY" :value="scope.row.category" />
        </template>
      </el-table-column>
      <el-table-column label="产品状态" prop="status" min-width="120">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.BUS_PRODUCT_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="负责人" prop="leader" min-width="100" />
      <el-table-column label="销售收入(万)" prop="revenue" min-width="120" />
      <el-table-column label="销售毛利(万)" prop="profit" min-width="120" />
      <el-table-column label="上市日期" prop="launchDate" width="120" :formatter="dateFormatter2" />
      <el-table-column label="创建时间" prop="createTime" width="180" :formatter="dateFormatter" />
      <el-table-column label="操作" align="center" width="200" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="openDetail(scope.row.id)" v-hasPermi="['bus:product-service:query']">查看</el-button>
          <el-button link type="primary" @click="openForm('update', scope.row.id)" v-hasPermi="['bus:product-service:update']">修改</el-button>
          <el-button link type="danger" @click="handleDelete(scope.row.id)" v-hasPermi="['bus:product-service:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />
  </ContentWrap>
  <ProductForm ref="formRef" @success="getList" />
  <ProductDetail ref="detailRef" />
</template>
<script lang="ts" setup>
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'
import { dateFormatter, dateFormatter2 } from '@/utils/formatTime'
import * as ProductApi from '@/api/bus/product'
import ProductForm from './ProductForm.vue'
import ProductDetail from './ProductDetail.vue'

defineOptions({ name: 'BusProductService' })
const message = useMessage()
const { t } = useI18n()
const loading = ref(true)
const list = ref([])
const total = ref(0)
const queryParams = reactive({ pageNo: 1, pageSize: 10, name: undefined, category: undefined })
const queryFormRef = ref()
const getList = async () => { loading.value = true; try { const data = await ProductApi.getProductPage(queryParams); list.value = data.list; total.value = data.total } finally { loading.value = false } }
const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryFormRef.value?.resetFields(); handleQuery() }
const formRef = ref()
const openForm = (type: string, id?: number) => { formRef.value.open(type, id) }
const detailRef = ref()
const openDetail = (id: number) => { detailRef.value.open(id) }
const handleDelete = async (id: number) => { try { await message.delConfirm(); await ProductApi.deleteProduct(id); message.success(t('common.delSuccess')); await getList() } catch {} }
const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (rows: ProductApi.ProductServiceVO[]) => { checkedIds.value = rows.map((row) => row.id!) }
const handleDeleteBatch = async () => { try { await message.delConfirm(); await ProductApi.deleteProductList(checkedIds.value); checkedIds.value = []; message.success(t('common.delSuccess')); await getList() } catch {} }
onMounted(() => { getList() })
</script>
