import request from '@/config/axios'

export interface ProductServiceVO {
    id?: number
    name: string
    leader?: string
    revenue?: number
    profit?: number
    category?: string
    status?: string
    launchDate?: Date
    intro?: string
    keyCustomers?: string
    ipIds?: number[]
    secretIds?: number[]
    staffIds?: number[]
    createTime?: Date
}

export const getProductPage = (params: PageParam) => {
    return request.get({ url: '/bus/product-service/page', params })
}

export const getProduct = (id: number) => {
    return request.get({ url: '/bus/product-service/get?id=' + id })
}

export const createProduct = (data: ProductServiceVO) => {
    return request.post({ url: '/bus/product-service/create', data })
}

export const updateProduct = (data: ProductServiceVO) => {
    return request.put({ url: '/bus/product-service/update', data })
}

export const deleteProduct = (id: number) => {
    return request.delete({ url: '/bus/product-service/delete?id=' + id })
}

export const deleteProductList = (ids: number[]) => {
    return request.delete({ url: '/bus/product-service/delete-list', params: { ids: ids.join(',') } })
}

export const getProductSimpleList = (): Promise<ProductServiceVO[]> => {
    return request.get({ url: '/bus/product-service/simple-list' })
}
