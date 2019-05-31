package com.jake.example.javabase;

import java.util.concurrent.TimeUnit;

/**
 * 死锁
 * 
 * 
 * jps
 * jstack
 */
public class JavaTest1 {
    public static void main(String[] A) {

        String locKa = "AAA";
        String lockB = "bbb";

        new Thread(new ThreadTest(locKa, lockB), "ThreadA").start();
        new Thread(new ThreadTest(lockB, locKa), "ThreadB").start();
    }
}

class ThreadTest implements Runnable {

    String lockA ;
    String lockB ;
    
    ThreadTest(String lockA , String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
        
    }
    
    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println("持有Lock:"+lockA + " 想要拿到Lock:"+lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.print("222222");
            }
        } 
    }
}
