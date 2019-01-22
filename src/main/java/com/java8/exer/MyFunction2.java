package com.java8.exer;

@FunctionalInterface
public interface MyFunction2<T, R> {
    public R getSum(T t1, T t2);
}