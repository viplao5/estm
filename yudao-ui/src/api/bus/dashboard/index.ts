import request from '@/config/axios'

export interface BusWorkplaceStatsRespVO {
    projectCount: number
    ipCount: number
    secretCount: number
    paperCount: number
    standardCount: number
    awardCount: number
    productCount: number
    platformCount: number
    staffCount: number
    qualificationCount: number
}

// 获得工作台统计信息
export const getWorkplaceStats = async () => {
    return await request.get({ url: '/bus/dashboard/workplace-stats' })
}
