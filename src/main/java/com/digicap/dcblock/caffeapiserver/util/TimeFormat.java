package com.digicap.dcblock.caffeapiserver.util;

import static java.util.TimeZone.getTimeZone;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeFormat {

    /**
     * 현재 시간을 2018-11-12로 리턴
     * 
     * @return 2018-11-01
     */
    public String getCurrent() {
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        simpleDateFormat.setTimeZone(getTimeZone("Asia/Seoul"));

        return simpleDateFormat.format(new Date());
    }

    public String timestampToString(Timestamp timestamp) {
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        simpleDateFormat.setTimeZone(getTimeZone("Asia/Seoul"));

        return simpleDateFormat.format(timestamp);
    }
    
    /**
     * 현재 시간 + minute
     * 
     * @param minute add minute
     * @return
     */
    public Date getAddMinute(int minute) {
       Calendar calendar = Calendar.getInstance(Locale.KOREA); 
       calendar.setTime(new Date());
       calendar.add(Calendar.MINUTE, minute);
       
       return calendar.getTime();
    }
}
