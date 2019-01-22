package com.juc.exer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//两个线程分别打印 1- 100，A 打印偶数， B打印奇数
public class PrinterOddEven2 {
    public static void main(String[] args) {
        for (int i = 1; i <= 100; ++i) {
            new Thread(new PrintOddThread(i), "A").start();
            new Thread(new PrintEvenThread(i), "B").start();
        }
    }
}

class PrintOddThread implements Runnable {
    private int i;

    public PrintOddThread(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        if (i % 2 == 1) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }
}

class PrintEvenThread implements Runnable {
    private int i;

    public PrintEvenThread(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        if (i % 2 == 0) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }
}