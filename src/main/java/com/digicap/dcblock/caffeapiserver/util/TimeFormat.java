package com.digicap.dcblock.caffeapiserver.util;

import static java.util.TimeZone.getTimeZone;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormat {

    /**
     * 현재 시간을 2018-11-12로 리턴
     * @return 2018-11-01
     */
    public String getCurrent() {
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        simpleDateFormat.setTimeZone(getTimeZone("Asia/Seoul"));

        return simpleDateFormat.format(new Date());
    }
}
