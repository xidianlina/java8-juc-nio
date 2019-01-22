package com.java8.demo.lambda;

import com.java8.demo.lambda.Employee;

public interface MyPredicate<T> {
    public boolean test(Employee e);
}