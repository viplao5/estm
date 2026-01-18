import request from '@/config/axios'

export interface ResearchProjectVO {
    id?: number
    name: string
    budget?: number
    leaderUserId?: number
    leaderId?: number
    memberIds?: number[]
    platformIds?: number[]
    category?: string
    status?: string
    startDate?: Date
    endDate?: Date
    intro?: string
    createTime?: Date
}



export const getProjectPage = (params: PageParam) => {
    return request.get({ url: '/bus/research-project/page', params })
}

export const getProject = (id: number) => {
    return request.get({ url: '/bus/research-project/get?id=' + id })
}

export const createProject = (data: ResearchProjectVO) => {
    return request.post({ url: '/bus/research-project/create', data })
}

export const updateProject = (data: ResearchProjectVO) => {
    return request.put({ url: '/bus/research-project/update', data })
}

export const deleteProject = (id: number) => {
    return request.delete({ url: '/bus/research-project/delete?id=' + id })
}

export const deleteProjectList = (ids: number[]) => {
    return request.delete({ url: '/bus/research-project/delete-list', params: { ids: ids.join(',') } })
}

export const getProjectSimpleList = (): Promise<ResearchProjectVO[]> => {
    return request.get({ url: '/bus/research-project/simple-list' })
}
