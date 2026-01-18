import request from '@/config/axios'

export interface QualificationVO {
    id?: number
    name: string
    certUnit?: string
    certNumber?: string
    startDate?: Date
    endDate?: Date
    fileUrl?: string
    remark?: string
    createTime?: Date
}

export const getQualificationPage = (params: PageParam) => {
    return request.get({ url: '/bus/qualification/page', params })
}

export const getQualification = (id: number) => {
    return request.get({ url: '/bus/qualification/get?id=' + id })
}

export const createQualification = (data: QualificationVO) => {
    return request.post({ url: '/bus/qualification/create', data })
}

export const createQualificationBatch = (data: QualificationVO[]) => {
    return request.post({ url: '/bus/qualification/create-batch', data })
}

export const updateQualification = (data: QualificationVO) => {
    return request.put({ url: '/bus/qualification/update', data })
}

export const deleteQualification = (id: number) => {
    return request.delete({ url: '/bus/qualification/delete?id=' + id })
}

export const deleteQualificationList = (ids: number[]) => {
    return request.delete({ url: '/bus/qualification/delete-list', params: { ids: ids.join(',') } })
}

export const getQualificationSimpleList = (): Promise<QualificationVO[]> => {
    return request.get({ url: '/bus/qualification/simple-list' })
}
