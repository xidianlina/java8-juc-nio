package com.java8.demo.forkjoin;

import com.java8.demo.forkjoin.ForkJoinCalculate;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class TestForkJoin {
    @Test
    public void test1() {
        Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0L, 1000000000L);

        long sum = pool.invoke(task);
        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println("耗费的时间为: " + Duration.between(start, end).toMillis());//
    }

    @Test
    public void test2() {
        Instant start = Instant.now();

        long sum = 0L;

        for (long i = 0L; i <= 1000000000L; i++) {
            sum += i;
        }

        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println("耗费的时间为: " + Duration.between(start, end).toMillis());//29756
    }

    @Test
    public void test3() {
        Instant start = Instant.now();

        Long sum = LongStream.rangeClosed(0L, 1000000000L)
                .parallel()
                .sum();
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费的时间为: " + Duration.between(start, end).toMillis());
    }
}