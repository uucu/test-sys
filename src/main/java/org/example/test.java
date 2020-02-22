package org.example;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;

/**
 * @author Kenneth
 * @date 2020/2/21
 */
public class test {
    public static final Object LOCK_A = new Object();
    public static final Object LOCK_B = new Object();

    private void lock() {

        // 线程一
        new Thread(() -> {
            System.out.println("Thread a starter");
            synchronized (LOCK_A) {
                System.out.println("Thread a: into lock a!");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK_B) {
                    System.out.println("Thread a: into lock b!");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Thread a: release lock b!");
            }
            System.out.println("Thread a: release lock a!");
        }).start();

        // 线程二
        new Thread(() -> {
            System.out.println("Thread b starter");
            synchronized (LOCK_B) {
                System.out.println("Thread b: into lock b!");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK_A) {
                    System.out.println("Thread b: into lock a!");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Thread b: release lock a!");
            }
            System.out.println("Thread b: release lock b!");
        }).start();
    }

    public static void main(String[] args) {
        List<String> list = Lists.newArrayListWithCapacity(10);
        while (true) {
            list.add("xxxx");
            System.out.println(list.size());

            if (list.size() == 20) {
                break;
            }
        }
    }

}
