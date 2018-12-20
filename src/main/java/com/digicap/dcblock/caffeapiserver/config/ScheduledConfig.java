package com.digicap.dcblock.caffeapiserver.config;

import com.digicap.dcblock.caffeapiserver.dao.ReceiptIdDao;
import com.digicap.dcblock.caffeapiserver.store.PurchaseMapper;
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

    private ReceiptIdDao receiptIdDao;

    @Autowired
    public ScheduledConfig(PurchaseMapper mapper, ReceiptIdDao receiptIdDao) {
        this.purchaseMapper = mapper;

        this.receiptIdDao = receiptIdDao;
    }

    /**
     * 매일 0시 0분에 동작.
     */
    @Scheduled(cron="0 0 0 * * *")
//    @Scheduled(cron="*/10 * * * * *") // for test
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
            receiptIdDao.deleteByRegdate(new Timestamp(System.currentTimeMillis()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
