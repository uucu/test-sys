package org.example;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.example.base.BaseServlet;

import com.google.common.base.Strings;

/**
 * @author Kenneth
 * @date 2020/2/20
 */
public class Lock extends BaseServlet {
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        if (!Strings.isNullOrEmpty(request.getParameter("g"))
                && !Strings.isNullOrEmpty(System.getenv().get("HOST_ID"))) {
            if (!System.getenv().get("HOST_ID").equals(request.getParameter("g"))) {
                defaultResponse(request, response);
            }
        }
        String msg;
        if (Strings.isNullOrEmpty(request.getParameter("sec"))) {
            crazyLock();
            msg = "死锁:crazyLock";
        } else {
            lock();
            msg = "sec为空时进入死循环死锁模式慎用!<br/>" + "死锁:lock";
        }
        defaultResponse(request, response, msg);
    }


    public static final Object LOCK_A = new Object();
    public static final Object LOCK_B = new Object();

    private void crazyLock() {
        // 死循环 为了增加死锁的几率
        while (true) {

            // 线程一
            new Thread(() -> {
                System.out.println("Thread a starter");
                synchronized (LOCK_A) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread a: into lock a!");
                    synchronized (LOCK_B) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Thread a: into lock b!");
                    }
                    System.out.println("Thread a: release lock b!");
                }
                System.out.println("Thread a: release lock a!");
            }).start();

            // 线程二
            new Thread(() -> {
                System.out.println("Thread b starter");
                synchronized (LOCK_B) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread b: into lock b!");
                    synchronized (LOCK_A) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Thread b: into lock a!");
                    }
                    System.out.println("Thread b: release lock a!");
                }
                System.out.println("Thread b: release lock b!");

            }).start();
        }
    }

    public static final Object LOCK_C = new Object();
    public static final Object LOCK_D = new Object();

    private void lock() {
        // 线程一
        new Thread(() -> {
            System.out.println("Thread a starter");
            synchronized (LOCK_C) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread a: into lock a!");
                synchronized (LOCK_D) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread a: into lock b!");
                }
                System.out.println("Thread a: release lock b!");
            }
            System.out.println("Thread a: release lock a!");
        }).start();

        // 线程二
        new Thread(() -> {
            System.out.println("Thread b starter");
            synchronized (LOCK_D) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread b: into lock b!");
                synchronized (LOCK_C) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread b: into lock a!");
                }
                System.out.println("Thread b: release lock a!");
            }
            System.out.println("Thread b: release lock b!");

        }).start();
    }
}
