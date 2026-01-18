import request from '@/config/axios'

export interface StandardVO {
    id?: number
    projectIds?: number[]
    name: string
    type?: string
    status?: string
    companyRole?: string
    pubDate?: Date
    staffIds?: number[]
    createTime?: Date
}


export const getStandardPage = (params: PageParam) => {
    return request.get({ url: '/bus/standard/page', params })
}

export const getStandard = (id: number) => {
    return request.get({ url: '/bus/standard/get?id=' + id })
}

export const createStandard = (data: StandardVO) => {
    return request.post({ url: '/bus/standard/create', data })
}

export const updateStandard = (data: StandardVO) => {
    return request.put({ url: '/bus/standard/update', data })
}

export const deleteStandard = (id: number) => {
    return request.delete({ url: '/bus/standard/delete?id=' + id })
}

export const deleteStandardList = (ids: number[]) => {
    return request.delete({ url: '/bus/standard/delete-list', params: { ids: ids.join(',') } })
}

export const getStandardSimpleList = (): Promise<StandardVO[]> => {
    return request.get({ url: '/bus/standard/simple-list' })
}
