package com.java8.demo.forkjoin;

import java.util.concurrent.RecursiveTask;

public class ForkJoinCalculate extends RecursiveTask<Long> {

    private Long start;
    private Long end;

    private static final Long THRESHOLD = 10000L;

    public ForkJoinCalculate(Long start, Long end) {
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
            ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
            left.fork();
            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
            right.fork();
            return left.join() + right.join();
        }
    }
}