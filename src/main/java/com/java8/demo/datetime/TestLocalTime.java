package com.java8.demo.datetime;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

public class TestLocalTime {
    //1、LocalDate LocalTime LocalDateTime
    @Test
    public void test1() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        System.out.println("-------------------");

        LocalDateTime ldt2 = LocalDateTime.of(2018, 10, 21, 13, 22, 59);
        System.out.println(ldt2);
        System.out.println("--------------------");

        LocalDateTime ldt3 = ldt.plusYears(2);
        System.out.println(ldt3);
        System.out.println("--------------------");

        LocalDateTime ldt4 = ldt.minusMonths(3);
        System.out.println(ldt4);
        System.out.println("--------------------");

        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonthValue());
        System.out.println(ldt.getDayOfMonth());
        System.out.println(ldt.getHour());
        System.out.println(ldt.getMonth());
        System.out.println(ldt.getMinute());
    }

    //2. Instant : 时间戳。 （使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
    @Test
    public void test2() {
        Instant instant = Instant.now();//默认使用 UTC 时区
        System.out.println(instant);
        System.out.println("------------------");

        OffsetDateTime odt = instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println(odt);
        System.out.println("------------------");

        long l = instant.toEpochMilli();
        System.out.println(l);
        System.out.println("-------------------");

        Instant ins = Instant.ofEpochMilli(1000);
        System.out.println(ins);
    }

    //3、Duration : 用于计算两个“时间”间隔   Period : 用于计算两个“日期”间隔
    @Test
    public void test3() {
        Instant ins1 = Instant.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }

        Instant ins2 = Instant.now();

        Duration duration = Duration.between(ins1, ins2);
        System.out.println(duration.toMillis());
        System.out.println("----------------");

        LocalTime lt1 = LocalTime.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        LocalTime lt2 = LocalTime.now();
        System.out.println(Duration.between(lt1, lt2).toMillis());
        System.out.println("--------------------");

        LocalDate ld1 = LocalDate.of(2018, 3, 10);
        LocalDate ld2 = LocalDate.now();

        Period period = Period.between(ld1, ld2);
        System.out.println(period);
        System.out.println("-------------------");

        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());
    }

    //4. TemporalAdjuster : 时间校正器
    @Test
    public void test4() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ldt2 = ldt.withDayOfMonth(10);
        System.out.println(ldt2);
        System.out.println("-----------");

        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt3);

        //自定义：下一个工作日
        LocalDateTime ldt5 = ldt.with((l) -> {
            LocalDateTime ldt4 = (LocalDateTime) l;
            DayOfWeek dow = ldt4.getDayOfWeek();

            if (dow.equals(DayOfWeek.FRIDAY)) {
                return ldt4.plusDays(3);
            } else if (dow.equals(DayOfWeek.SATURDAY)) {
                return ldt4.plusDays(2);
            } else {
                return ldt4.plusDays(1);
            }
        });
        System.out.println(ldt5);
    }

    //5. DateTimeFormatter : 解析和格式化日期或时间
    @Test
    public void test5() {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
        LocalDateTime ldt = LocalDateTime.now();

        String strDate = ldt.format(dtf);
        System.out.println(strDate);
        System.out.println("---------------------");

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String strDate2 = dtf2.format(ldt);
        System.out.println(strDate2);
        System.out.println("---------------------");

        LocalDateTime newDate = ldt.parse(strDate2, dtf2);
        System.out.println(newDate);
    }

    //6.ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
    @Test
    public void test6() {
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);
        System.out.println("-------------------");
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(ldt);

        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("US/Pacific"));
        System.out.println(zdt);
    }
}