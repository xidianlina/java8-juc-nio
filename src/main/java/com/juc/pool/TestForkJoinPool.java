package com.juc.pool;

import com.java8.demo.forkjoin.ForkJoinCalculate;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class TestForkJoinPool {
    public static void main(String[] args) {
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0L, 1000000000L);
        Long sum = pool.invoke(task);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());
    }

    @Test
    public void test1() {
        Instant start = Instant.now();
        long sum = 0L;
        for (int i = 0; i <= 1000000000L; ++i) {
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();

        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());
    }

    @Test
    public void test2() {
        Instant start = Instant.now();

        Long sum = LongStream.rangeClosed(0, 1000000000L)
                .parallel()
                .reduce(0L, Long::sum);
        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());
    }
}

class ForkJoinSumCalculate extends RecursiveTask<Long> {

    private Long start;
    private Long end;
    private static final Long THRESHOLD = 10000L;

    public ForkJoinSumCalculate(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        Long length = end - start;
        if (length <= THRESHOLD) {
            Long sum = 0L;
            for (Long i = start; i <= end; ++i) {
                sum += i;
            }
            return sum;
        } else {
            Long middle = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork();
            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
            right.fork();
            return left.join() + right.join();
        }
    }
}