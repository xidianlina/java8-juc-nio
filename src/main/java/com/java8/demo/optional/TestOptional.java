package com.java8.demo.optional;

import com.java8.demo.lambda.Employee;
import org.junit.Test;

import java.util.Optional;

/*
 * 一、Optional 容器类：用于尽量避免空指针异常
 * 	Optional.of(T t) : 创建一个 Optional 实例
 * 	Optional.empty() : 创建一个空的 Optional 实例
 * 	Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
 * 	isPresent() : 判断是否包含值
 * 	orElse(T t) :  如果调用对象包含值，返回该值，否则返回t
 * 	orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
 * 	map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
 * 	flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
 */
public class TestOptional {

    @Test
    public void test1() {
        Optional<Employee> op = Optional.of(null);
        Employee emp = op.get();
        System.out.println(emp);
    }

    @Test
    public void test2() {
        Optional<Employee> op = Optional.empty();
        System.out.println(op.get());
    }

    @Test
    public void test3() {
        Optional<Employee> op = Optional.ofNullable(null);
        if (op.isPresent()) {
            System.out.println(op.get());
        }
        Employee emp = op.orElse(new Employee("zhang", 45, 32.2));
        System.out.println(emp);
        System.out.println("-----------------------");

        Optional<Employee> op2 = Optional.ofNullable(new Employee("wang", 65, 90.4));
        Employee employee = op2.orElseGet(Employee::new);
        System.out.println(employee);
    }

    @Test
    public void test4() {
        Optional<Employee> op = Optional.ofNullable(new Employee("li", 43, 21.98));
        Optional<String> n = op.map((e) -> e.getName());
        System.out.println(n.get());
        System.out.println("---------------------------");

        Optional<String> name = op.flatMap((e) -> Optional.of(e.getName()));
        System.out.println(name.get());
    }

    @Test
    public void test5() {
        Man man = new Man();

        String name = getGodnessName(man);
        System.out.println(name);
    }

    //需求：获取一个男人心中女神的名字
    public String getGodnessName(Man man) {
        if (man != null) {
            Godness g = man.getGod();

            if (g != null) {
                return g.getName();
            }
        }

        return "美女";
    }

    //运用 Optional 的实体类
    @Test
    public void test6() {
        Optional<Godness> godness = Optional.ofNullable(new Godness("林志玲"));

        Optional<NewMan> op = Optional.ofNullable(new NewMan(godness));
        String name = getGodnessName2(op);
        System.out.println(name);
    }

    public String getGodnessName2(Optional<NewMan> man) {
        return man.orElse(new NewMan())
                .getGodness()
                .orElse(new Godness("美人鱼"))
                .getName();
    }
}