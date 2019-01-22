package com.juc.exer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//两个线程分别打印 1- 100，A 打印偶数， B打印奇数
public class PrinterOddEven {
    public static void main(String[] args) {
        OddEvenThread oddEvenThread = new OddEvenThread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                oddEvenThread.printOdd();
            }
        }, "A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                oddEvenThread.printEven();
            }
        }, "B").start();
    }
}

class OddEvenThread {
    private volatile int num = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();

    public void printOdd() {
        while (num <= 100) {
            lock.lock();
            try {
                if (num % 2 != 1) {
                    condition1.await();
                }
                System.out.println(Thread.currentThread().getName() + " " + num);
                ++num;
                condition2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void printEven() {
        while (num <= 100) {
            lock.lock();
            try {
                if (num % 2 != 0) {
                    condition2.await();
                }
                System.out.println(Thread.currentThread().getName() + " " + num);
                ++num;
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}