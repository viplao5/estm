import request from '@/config/axios'

export interface ResearchPlatformVO {
    id?: number
    name: string
    level?: string
    certUnit?: string
    certDate?: Date
    startDate?: Date
    endDate?: Date
    staffIds?: number[]
    createTime?: Date
}

export const getPlatformPage = (params: PageParam) => {
    return request.get({ url: '/bus/research-platform/page', params })
}

export const getPlatform = (id: number) => {
    return request.get({ url: '/bus/research-platform/get?id=' + id })
}

export const createPlatform = (data: ResearchPlatformVO) => {
    return request.post({ url: '/bus/research-platform/create', data })
}

export const updatePlatform = (data: ResearchPlatformVO) => {
    return request.put({ url: '/bus/research-platform/update', data })
}

export const deletePlatform = (id: number) => {
    return request.delete({ url: '/bus/research-platform/delete?id=' + id })
}

export const deletePlatformList = (ids: number[]) => {
    return request.delete({ url: '/bus/research-platform/delete-list', params: { ids: ids.join(',') } })
}

export const getPlatformSimpleList = (): Promise<ResearchPlatformVO[]> => {
    return request.get({ url: '/bus/research-platform/simple-list' })
}
