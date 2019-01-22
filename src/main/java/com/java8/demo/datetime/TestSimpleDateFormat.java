package com.java8.demo.datetime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class TestSimpleDateFormat {

    /*存在线程安全问题
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Callable<Date> task = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return sdf.parse("20180912");
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<Date>> res = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            res.add(pool.submit(task));
        }

        for (Future future : res) {
            System.out.println(future.get());
        }
        pool.shutdown();
    }
    */

    /*
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Date> task = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return DateFormatThreadLocal.convert("20180912");
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<Date>> res = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            res.add(pool.submit(task));
        }

        for (Future future : res) {
            System.out.println(future.get());
        }
        pool.shutdown();
    }
    */

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        Callable<LocalDate> task = new Callable<LocalDate>() {
            @Override
            public LocalDate call() throws Exception {
                return LocalDate.parse("20180912", dtf);
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<LocalDate>> res = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            res.add(pool.submit(task));
        }

        for (Future future : res) {
            System.out.println(future.get());
        }
        pool.shutdown();
    }
}