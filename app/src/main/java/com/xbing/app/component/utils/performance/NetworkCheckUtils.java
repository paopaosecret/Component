package com.xbing.app.component.utils.performance;

import android.net.TrafficStats;
import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class NetworkCheckUtils {

    private static final String TAG = NetworkCheckUtils.class.getSimpleName();

    /**
     * 获取当前下载流量总和
     *
     * @return
     */
    public static long getNetworkRxBytes() {
        return TrafficStats.getTotalRxBytes();
    }

    /**
     * 获取当前上传流量总和
     *
     * @return
     */
    public static long getNetworkTxBytes() {
        return TrafficStats.getTotalTxBytes();
    }

    /**
     * 获取当前下载网速
     *
     * @return
     */
    public static double getDownloadNetSpeed() {
        long lastRxBytes = getNetworkRxBytes();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long currentRxBytes = getNetworkRxBytes();
        long bytes = currentRxBytes - lastRxBytes;

        double kb = (double) bytes / (double) 1024;
        BigDecimal bd = new BigDecimal(kb);
        return bd.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * 获取当前上传网速
     *
     * @return
     */
    public static double getUploadNetSpeed() {
        long lastTxBytes = getNetworkTxBytes();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long currentTxBytes = getNetworkTxBytes();
        long bytes = currentTxBytes - lastTxBytes;

        double kb = (double) bytes / (double) 1024;
        BigDecimal bd = new BigDecimal(kb);
        return bd.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void getNetworkInfo(final TextView view){
        new AsyncTask<Void,Void,Double[]>(){

            @Override
            protected Double[] doInBackground(Void... voids) {
                Double[] speed =  new Double[2];
                FutureTask downloadTask = new FutureTask(new Callable() {
                    @Override
                    public Double call() throws Exception {
                        return getDownloadNetSpeed();
                    }
                });

                FutureTask uploadTask = new FutureTask(new Callable() {
                    @Override
                    public Double call() throws Exception {
                        return getUploadNetSpeed();
                    }
                });
                new Thread(uploadTask).start();
                new Thread(downloadTask).start();
                try {
                    speed[0] = (Double)(downloadTask.get());
                    speed[1] = (Double)(uploadTask.get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return speed;
            }

            @Override
            protected void onPostExecute(Double[] result) {
                String text ="当前下载网速：" + result[0]  + "\n当前上传网速：" + result[1];
                view.setText(text);
            }

        }.execute();
    }
}
