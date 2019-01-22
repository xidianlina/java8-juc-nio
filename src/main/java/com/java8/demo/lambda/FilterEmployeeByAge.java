package com.java8.demo.lambda;

import com.java8.demo.lambda.Employee;
import com.java8.demo.lambda.MyPredicate;

public class FilterEmployeeByAge implements MyPredicate<Employee> {

    @Override
    public boolean test(Employee employee) {
        return employee.getAge() >= 35;
    }
}