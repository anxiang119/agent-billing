package com.billing.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataArchiveTask {

    @Scheduled(cron = "0 0 2 * * ?")
    public void archiveOldData() {
        // 每天凌晨2点归档历史数据
    }
}
