import request from '@/config/axios'

export interface AwardVO {
    id?: number
    projectIds?: number[]
    name: string
    grantUnit?: string
    level?: string
    grade?: string
    awardDate?: Date
    staffIds?: number[]
    createTime?: Date
}


export const getAwardPage = (params: PageParam) => {
    return request.get({ url: '/bus/award/page', params })
}

export const getAward = (id: number) => {
    return request.get({ url: '/bus/award/get?id=' + id })
}

export const createAward = (data: AwardVO) => {
    return request.post({ url: '/bus/award/create', data })
}

export const updateAward = (data: AwardVO) => {
    return request.put({ url: '/bus/award/update', data })
}

export const deleteAward = (id: number) => {
    return request.delete({ url: '/bus/award/delete?id=' + id })
}

export const deleteAwardList = (ids: number[]) => {
    return request.delete({ url: '/bus/award/delete-list', params: { ids: ids.join(',') } })
}

export const getAwardSimpleList = (): Promise<AwardVO[]> => {
    return request.get({ url: '/bus/award/simple-list' })
}
