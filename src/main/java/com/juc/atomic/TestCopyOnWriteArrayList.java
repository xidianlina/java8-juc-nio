package com.juc.atomic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


/*
 * CopyOnWriteArrayList/CopyOnWriteArraySet : “写入并复制”
 * 注意：添加操作多时，效率低，因为每次添加时都会进行复制，开销非常的大。并发迭代操作多时可以选择。
 *
 * CopyOnWrite容器即写时复制的容器。当往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，
 * 复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。
 * 这样做的好处是可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。
 * 所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。
 *
 * CopyOnWrite并发容器用于读多写少的并发场景。比如白名单，黑名单，商品类目的访问和更新场景，
 * 假如有一个搜索网站，用户在这个网站的搜索框中，输入关键字搜索内容，但是某些关键字不允许被搜索。
 * 这些不能被搜索的关键字会被放在一个黑名单当中，黑名单每天晚上更新一次。当用户搜索时，
 * 会检查当前关键字在不在黑名单当中，如果在，则提示不能搜索。
 *
 * CopyOnWrite的缺点:CopyOnWrite容器有很多优点，但是同时也存在两个问题，即内存占用问题和数据一致性问题。
 * 1、内存占用问题:因为CopyOnWrite的写时复制机制，所以在进行写操作的时候，内存里会同时驻扎两个对象的内存，
 * 旧的对象和新写入的对象（注意:在复制的时候只是复制容器里的引用，只是在写的时候会创建新对象添加到新容器里，
 * 而旧容器的对象还在使用，所以有两份对象内存）。如果这些对象占用的内存比较大，比如说200M左右，那么再写入100M数据进去，
 * 内存就会占用300M，那么这个时候很有可能造成频繁的Yong GC和Full GC。
 * 之前我们系统中使用了一个服务由于每晚使用CopyOnWrite机制更新大对象，造成了每晚15秒的Full GC，应用响应时间也随之变长。
 * 针对内存占用问题，可以通过压缩容器中的元素的方法来减少大对象的内存消耗，比如，如果元素全是10进制的数字，可以考虑把它压缩成36进制或64进制。
 * 或者不使用CopyOnWrite容器，而使用其他的并发容器，如ConcurrentHashMap。
 * 2、数据一致性问题。CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性。
 * 所以如果你希望写入的的数据，马上能读到，请不要使用CopyOnWrite容器。
 */
public class TestCopyOnWriteArrayList {
    public static void main(String[] args) {
        CopyOnWriteArrayListThread ct = new CopyOnWriteArrayListThread();
        for (int i = 0; i < 10; ++i) {
            new Thread(ct).start();
        }
    }
}

class CopyOnWriteArrayListThread implements Runnable {
//    private static List<String> list = Collections.synchronizedList(new ArrayList<String>());

    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());

            list.add("AA");
        }
    }
}