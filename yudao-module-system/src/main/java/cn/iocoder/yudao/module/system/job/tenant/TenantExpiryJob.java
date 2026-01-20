package cn.iocoder.yudao.module.system.job.tenant;

import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.system.dal.dataobject.tenant.TenantDO;
import cn.iocoder.yudao.module.system.dal.mysql.tenant.TenantMapper;
import cn.iocoder.yudao.module.system.service.sms.SmsSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 租户过期通知 Job
 *
 * @author 芋道源码
 */
@Slf4j
@Component
public class TenantExpiryJob implements JobHandler {

    @Resource
    private TenantMapper tenantMapper;

    @Resource
    private SmsSendService smsSendService;

    @Override
    @TenantIgnore
    public String execute(String param) {
        // 1. 定义需要检查的天数
        int[] daysArr = { 30, 14, 7, 1 };
        int totalSent = 0;

        for (int days : daysArr) {
            LocalDateTime startOfDay = LocalDateTime.now().plusDays(days).with(LocalTime.MIN);
            LocalDateTime endOfDay = LocalDateTime.now().plusDays(days).with(LocalTime.MAX);

            // 2. 查询该天过期的租户
            List<TenantDO> tenants = tenantMapper.selectListByExpireTimeBetween(startOfDay, endOfDay);
            if (tenants.isEmpty()) {
                continue;
            }

            // 3. 发送通知
            for (TenantDO tenant : tenants) {
                try {
                    sendExpirySms(tenant, days);
                    totalSent++;
                } catch (Exception e) {
                    log.error("[execute][租户({}) 发送 {} 天过期通知失败]", tenant.getId(), days, e);
                }
            }
        }

        return String.format("已发送过期通知数量：%d", totalSent);
    }

    private void sendExpirySms(TenantDO tenant, int days) {
        if (tenant.getContactMobile() == null) {
            return;
        }
        Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("tenantName", tenant.getName());
        templateParams.put("days", days);
        // 使用具体的模板 code
        smsSendService.sendSingleSms(tenant.getContactMobile(), null, UserTypeEnum.ADMIN.getValue(),
                "tenant-expiry-reminder", templateParams);
    }

}
