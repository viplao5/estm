import request from '@/config/axios'

export interface TechnicalStaffVO {
  id?: number
  name: string
  post?: string
  school?: string
  major?: string
  eduDegree?: string
  title?: string
  entryDate?: Date
  isActive?: boolean
  isCertified?: boolean
  hasNonCompete?: boolean
  hasDispute?: boolean
  achievements?: string
  background?: string
  incentiveInfo?: string
  hasConfidentialAgreement?: boolean
  confidentialFile?: string
  hasInventionPledge?: boolean
  inventionPledgeFile?: string
  hasEquityPlan?: boolean
  equityPlanFile?: string
  createTime?: Date
}


// 查询核心技术人员分页
export const getStaffPage = (params: PageParam) => {
  return request.get({ url: '/bus/technical-staff/page', params })
}

// 查询核心技术人员详情
export const getStaff = (id: number) => {
  return request.get({ url: '/bus/technical-staff/get?id=' + id })
}

// 新增核心技术人员
export const createStaff = (data: TechnicalStaffVO) => {
  return request.post({ url: '/bus/technical-staff/create', data })
}

// 修改核心技术人员
export const updateStaff = (data: TechnicalStaffVO) => {
  return request.put({ url: '/bus/technical-staff/update', data })
}

// 删除核心技术人员
export const deleteStaff = (id: number) => {
  return request.delete({ url: '/bus/technical-staff/delete?id=' + id })
}

// 批量删除核心技术人员
export const deleteStaffList = (ids: number[]) => {
  return request.delete({ url: '/bus/technical-staff/delete-list', params: { ids: ids.join(',') } })
}

// 获取核心技术人员精简列表
export const getStaffSimpleList = (): Promise<TechnicalStaffVO[]> => {
  return request.get({ url: '/bus/technical-staff/simple-list' })
}
