package com.java8.exer;

import com.java8.demo.lambda.Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 1、调用Collections.sort()方法，通过定制排序比较两个Employee(先按年龄比，年龄相同按姓名比)，使用Lambda作为参数传递
 * 2、(1)声明函数式接口，接口中声明抽象方法,public String getValue(String str);
 * (2)声明类TestLambda,类中编写方法使用接口作为参数，将一个字符串转换成大写，并作为方法的返回值
 * (3)再将一个字符串的第2个和第4个索引位置进行截取字串
 * 3、(1)声明一个带两个泛型带函数式接口，泛型类型为<T,R>，T为参数，R为返回值
 * (2)接口中声明对应抽象方法
 * (3)在TestLambda类中声明方法，使用接口作为参数，计算两个long型参数对和
 * (4)再计算两个long型参数的乘积
 */
public class ExerLambda {
    List<Employee> emps = Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 59, 6666.66),
            new Employee("王五", 28, 3333.33),
            new Employee("赵六", 8, 7777.77),
            new Employee("田七", 38, 5555.55)
    );

    @Test
    public void test1() {
        Collections.sort(emps, (e1, e2) -> {
            if (e1.getAge() == e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return -Integer.compare(e1.getAge(), e2.getAge());
            }
        });
        for (Employee emp : emps) {
            System.out.println(emp);
        }
    }

    @Test
    public void test2() {
        String s = strHandler("\t\t\tHello World!!!", (str) -> str.trim());
        System.out.println(s);

        s = strHandler("abcd", (str) -> str.toUpperCase());
        System.out.println(s);

        s = strHandler("abcdefg", (str) -> str.substring(2, 5));
        System.out.println(s);
    }

    //需求：用于处理字符串
    public String strHandler(String str, MyFunction mf) {
        return mf.getValue(str);
    }

    @Test
    public void test3() {
        op(100L, 200L, (x, y) -> x + y);
        op(100L, 200L, (x, y) -> x * y);
    }

    //需求：对于两个 Long 型数据进行处理
    public void op(Long l1, Long l2, MyFunction2<Long, Long> mf) {
        System.out.println(mf.getSum(l1, l2));
    }
}