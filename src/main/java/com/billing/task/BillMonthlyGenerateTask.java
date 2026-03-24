package com.billing.task;

import com.billing.service.bill.BillService;
import com.billing.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BillMonthlyGenerateTask {

    private final BillService billService;
    private final TenantService tenantService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void generateMonthlyBills() {
        // 每月1日自动生成账单
    }
}
