package com.juc.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//生产者和消费者案例 lock实现
public class TestProductorAndConsumerByLock {
    public static void main(String[] args) {
        Source source = new Source();

        Pro productor = new Pro(source);
        Con consumer = new Con(source);

        new Thread(productor, "生产者A").start();
        new Thread(consumer, "消费者B").start();
        new Thread(productor, "生产者C").start();
        new Thread(consumer, "消费者D").start();
    }
}

//生产者
class Pro implements Runnable {
    private Source source;

    public Pro(Source source) {
        this.source = source;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; ++i) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            source.get();
        }
    }
}

//消费者

class Con implements Runnable {
    private Source source;

    public Con(Source source) {
        this.source = source;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; ++i) {
            source.sale();
        }
    }
}

class Source {
    private int product = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //进货
    public void get() {
        lock.lock();
        try {
            while (product >= 1) {//为了避免虚假唤醒问题，应该总是使用在循环中
                System.out.println("产品以满!");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "  " + ++product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    //卖货
    public synchronized void sale() {
        lock.lock();
        try {
            while (product <= 0) {//为了避免虚假唤醒问题，应该总是使用在循环中
                System.out.println("缺货!");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "  " + --product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}