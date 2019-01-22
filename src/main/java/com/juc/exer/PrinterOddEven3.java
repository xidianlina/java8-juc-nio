package com.juc.exer;

//两个线程分别打印 1- 100，A 打印偶数， B打印奇数
public class PrinterOddEven3 {
    //private static Object lock = new Object();
    private static int i = 1;
    private static int wait = 1;
    private static final int TOTAL = 100;

    public static void main(String[] args) {
        Thread thread1 = new Thread("A") {
            @Override
            public void run() {
                while (i <= TOTAL) {
                    synchronized (this) {
                        if (i % 2 == 1) {
                            System.out.println(Thread.currentThread().getName() + "  " + i++);
                        } else {
                            this.notify();
                            try {
                                this.wait(wait);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };

        Thread thread2 = new Thread("B") {
            @Override
            public void run() {
                while (i <= TOTAL) {
                    synchronized (this) {
                        if (i % 2 == 0) {
                            System.out.println(Thread.currentThread().getName() + "  " + i++);
                        } else {
                            this.notify();
                            try {
                                this.wait(wait);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        thread1.start();
        thread2.start();
    }
}