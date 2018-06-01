package com.example;

import java.util.Objects;

/**
 * Created by Administrator on 2017/9/15.
 */

public class ThreadTest {

    public static void main(String args[]){

        ThreadService ts = new ThreadService();
        ts.start();
        ts.setStatus(1);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ts.setStatus(0);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ts.setStatus(1);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ts.setStatus(2);
    }

    static class ThreadService extends Thread{

        private int status = 0;  //0:pause   1:run  2:end
        boolean flag = true;
        int i = 1;
        Object lock = new Object();
        @Override
        public void run() {

            while(flag){
                switch (status){
                    case 0:
                        synchronized (lock){
                            try {
                                System.out.println("thread.run : pause start");
                                lock.wait();
                                System.out.println("thread.run : pause end");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 1:
                        System.out.println("thread.run : i = " + i++);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        System.out.println("thread.end");
                        flag = false;
                        break;
                }

            }
        }

        public void setStatus(int status){
            if(status >= 1){
                synchronized (lock){
                    lock.notifyAll();
                }
            }
            this.status = status;
        }
    }
}
