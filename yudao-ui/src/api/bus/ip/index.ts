import request from '@/config/axios'

export interface IntellectualPropertyVO {
    id?: number
    projectIds?: number[]
    name: string
    appNumber?: string
    owner?: string
    agency?: string
    category?: string
    status?: string
    source?: string
    appDate?: Date
    grantDate?: Date
    nextFeeDate?: Date
    fileUrl?: string
    inventorIds?: number[]
    createTime?: Date
}


export const getIPPage = (params: PageParam) => {
    return request.get({ url: '/bus/intellectual-property/page', params })
}

export const getIP = (id: number) => {
    return request.get({ url: '/bus/intellectual-property/get?id=' + id })
}

export const createIP = (data: IntellectualPropertyVO) => {
    return request.post({ url: '/bus/intellectual-property/create', data })
}

export const updateIP = (data: IntellectualPropertyVO) => {
    return request.put({ url: '/bus/intellectual-property/update', data })
}

export const deleteIP = (id: number) => {
    return request.delete({ url: '/bus/intellectual-property/delete?id=' + id })
}

export const deleteIPList = (ids: number[]) => {
    return request.delete({ url: '/bus/intellectual-property/delete-list', params: { ids: ids.join(',') } })
}

export const getIPSimpleList = (): Promise<IntellectualPropertyVO[]> => {
    return request.get({ url: '/bus/intellectual-property/simple-list' })
}
