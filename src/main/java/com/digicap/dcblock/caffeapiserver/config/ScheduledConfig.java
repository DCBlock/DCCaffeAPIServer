package com.digicap.dcblock.caffeapiserver.config;

import com.digicap.dcblock.caffeapiserver.store.PurchaseMapper;
import com.digicap.dcblock.caffeapiserver.store.ReceiptIdsMapper;
import java.sql.Timestamp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Schedule이 필요한 기능 구현
 */
@Component
@Slf4j
public class ScheduledConfig {

    private PurchaseMapper purchaseMapper;

    private ReceiptIdsMapper receiptIdsMapper;

    @Autowired
    public ScheduledConfig(PurchaseMapper mapper, ReceiptIdsMapper receiptIdsMapper) {
        this.purchaseMapper = mapper;

        this.receiptIdsMapper = receiptIdsMapper;
    }

    /**
     * 매일 0시 0분에 동작.
     */
    @Scheduled(cron="0 0 * * * *")
//    @Scheduled(cron="*/10 * * * * *")
    public void initializeReceiptIdSequence() {
        log.info("initialize ReceiptId Sequence");

        try {
            // receipt_id를 0으로 초기화한다.
            purchaseMapper.updateReceiptId();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        try {
            // 어제 발급만 하고 사용하지 않은 영수증 테이블 삭제.
            receiptIdsMapper.deleteAll(new Timestamp(System.currentTimeMillis()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
