import request from '@/config/axios'

export interface TechnicalSecretVO {
    id?: number
    projectIds?: number[]
    name: string
    type?: string
    secretLevel?: string
    finishDate?: Date
    description?: string
    techField?: string
    staffIds?: number[]
    createTime?: Date
}


export const getSecretPage = (params: PageParam) => {
    return request.get({ url: '/bus/technical-secret/page', params })
}

export const getSecret = (id: number) => {
    return request.get({ url: '/bus/technical-secret/get?id=' + id })
}

export const createSecret = (data: TechnicalSecretVO) => {
    return request.post({ url: '/bus/technical-secret/create', data })
}

export const updateSecret = (data: TechnicalSecretVO) => {
    return request.put({ url: '/bus/technical-secret/update', data })
}

export const deleteSecret = (id: number) => {
    return request.delete({ url: '/bus/technical-secret/delete?id=' + id })
}

export const deleteSecretList = (ids: number[]) => {
    return request.delete({ url: '/bus/technical-secret/delete-list', params: { ids: ids.join(',') } })
}

export const getSecretSimpleList = (): Promise<TechnicalSecretVO[]> => {
    return request.get({ url: '/bus/technical-secret/simple-list' })
}
