package com.example;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ThreadLocalTest {

    public static void main(String args[]){
        ThreadLocalTest tlt = new ThreadLocalTest();
        tlt.setTlt(1);
        ThreadClient client1 = new ThreadClient(tlt);
        tlt.setTlt(2);
        ThreadClient client2 = new ThreadClient(tlt);
        tlt.setTlt(3);
        ThreadClient client3 = new ThreadClient(tlt);

        client1.start();
        client2.start();
        client3.start();
    }

    private static ThreadLocal<Integer> mThreadLocal = new ThreadLocal(){
        @Override
        protected Object initialValue() {
            System.out.println(Thread.currentThread().getName() + "：initialValue");
            return 0;
        }
    };

    public int getTlt(){
        return mThreadLocal.get();
    }

    public int getAddTlt(){
        mThreadLocal.set(mThreadLocal.get() + 1);
        return  mThreadLocal.get();
    }

    public void setTlt(int value){
        mThreadLocal.set(value);
    }


    private static class ThreadClient extends Thread{

        private ThreadLocalTest tlt;

        private ThreadClient(ThreadLocalTest t){
            tlt = t;
        }

        @Override
        public void run() {
            for(int i = 0; i<5;i++){
                System.out.println(Thread.currentThread().getName() + ":" + tlt.getTlt() + ",addtlt:0" + tlt.getAddTlt());
            }
        }
    }
}
