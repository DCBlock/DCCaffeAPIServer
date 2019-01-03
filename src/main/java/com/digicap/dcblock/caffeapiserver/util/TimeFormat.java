package com.digicap.dcblock.caffeapiserver.util;

import static java.util.TimeZone.getTimeZone;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    s.setTimeZone(getTimeZone("Asia/Seoul"));
    return s.format(new Date());
  }

  public String timestampToString(Timestamp timestamp) {
    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    s.setTimeZone(getTimeZone("Asia/Seoul"));
    return s.format(timestamp);
  }

  /**
   * timestamp + minute
   *
   * @param minute add minute
   * @return
   */
  public Date getAddMinute(long timestamp, int minute) {
    Calendar c = Calendar.getInstance(Locale.KOREA);
    c.setTime(new Date(timestamp));
    c.add(Calendar.MINUTE, minute);
    return c.getTime();
  }

  public String fromLong(long timestamp) {
    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    s.setTimeZone(getTimeZone("Asia/Seoul"));
    return s.format(timestamp);
  }

  /**
   * 시/분/초를 제거하고 년/월/일 timestamp
   *
   * @param time
   * @return
   */
  public Timestamp toTimeStampExcludeTime(long time) {
    Date d = new java.sql.Date(time);
    long t = java.sql.Date.valueOf(d.toString()).getTime();
    return new Timestamp(t);
  }

  /**
   * java.sql.Date에서 시/분/초를 제거하고 내일을 Get.
   *
   * @param time
   * @return
   */
  public Timestamp toTomorrow(Timestamp time) {
    Timestamp t = toTimeStampExcludeTime(time.getTime());
    LocalDate l = t.toLocalDateTime().toLocalDate();
    LocalDate tomorrow = l.plus(1, ChronoUnit.DAYS);
    return Timestamp.valueOf(tomorrow.atStartOfDay());
  }

  /**
   * 시/분/초를 제거하고 어제 timestamp Get.
   *
   * @param time
   * @return
   */
  public Timestamp toYesterday(Timestamp time) {
    Timestamp t = toTimeStampExcludeTime(time.getTime());
    LocalDate l = t.toLocalDateTime().toLocalDate();
    LocalDate y = l.minus(1, ChronoUnit.DAYS);
    return Timestamp.valueOf(y.atStartOfDay());
  }
}
