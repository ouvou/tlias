package com.itheima;

public class ThreadLocalTest {
    private static final ThreadLocal<String> local = new ThreadLocal<>();

    public static void main(String[] args) {
        local.set("Main Message");

        //创建线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                local.set("Sub Message");
                System.out.println(Thread.currentThread().getName() + " : " + local.get());
            }
        }).start();


        System.out.println(Thread.currentThread().getName() + " : " + local.get());
        local.remove();
        System.out.println(Thread.currentThread().getName() + " : " + local.get());
    }
}
