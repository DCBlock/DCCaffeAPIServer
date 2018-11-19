package com.digicap.dcblock.caffeapiserver.config;

import com.digicap.dcblock.caffeapiserver.store.PurchaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 매일 0시0분0초에 DB Sequence를 1로 초기화한다.
 */
@Component
@Slf4j
public class ScheduledConfig {

    PurchaseMapper purchaseMapper;

    @Autowired
    public ScheduledConfig(PurchaseMapper mapper) {
        this.purchaseMapper = mapper;
    }

    @Scheduled(cron="0 0 * * * *")
    public void initalizeReceiptIdSequence() {
        log.info("initalize ReceiptId Sequence");

        try {
            purchaseMapper.initReceptId();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
