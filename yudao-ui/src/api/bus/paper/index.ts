import request from '@/config/axios'

export interface PaperWorkVO {
    id?: number
    projectIds?: number[]
    title: string
    publication?: string
    doi?: string
    indexing?: string
    pubYear?: number
    authorIds?: number[]
    externalAuthors?: string
    createTime?: Date
}


export const getPaperPage = (params: PageParam) => {
    return request.get({ url: '/bus/paper-work/page', params })
}

export const getPaper = (id: number) => {
    return request.get({ url: '/bus/paper-work/get?id=' + id })
}

export const createPaper = (data: PaperWorkVO) => {
    return request.post({ url: '/bus/paper-work/create', data })
}

export const updatePaper = (data: PaperWorkVO) => {
    return request.put({ url: '/bus/paper-work/update', data })
}

export const deletePaper = (id: number) => {
    return request.delete({ url: '/bus/paper-work/delete?id=' + id })
}

export const deletePaperList = (ids: number[]) => {
    return request.delete({ url: '/bus/paper-work/delete-list', params: { ids: ids.join(',') } })
}

export const getPaperSimpleList = (): Promise<PaperWorkVO[]> => {
    return request.get({ url: '/bus/paper-work/simple-list' })
}
