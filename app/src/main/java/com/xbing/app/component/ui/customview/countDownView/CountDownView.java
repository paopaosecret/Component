package com.xbing.app.component.ui.customview.countDownView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Chronometer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CountDownView extends Chronometer {
    private long time;
    private long nextTime;
    private OnTimeCompleteListener clistener;
    private SimpleDateFormat format;
    public CountDownView(Context context) {
        super(context);
        format=new SimpleDateFormat("mm:ss");
        this.setOnChronometerTickListener(listener);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        format=new SimpleDateFormat("mm:ss");
        this.setOnChronometerTickListener(listener);
    }

    public void setTimeFormat(String pattern){
        format=new SimpleDateFormat(pattern);
    }

    public void setOnTimeCompleteListener(OnTimeCompleteListener l) {
        clistener=l;
    }

    OnChronometerTickListener listener=new OnChronometerTickListener() {
        @Override
        public void onChronometerTick(Chronometer chronometer) {
            // TODO Auto-generated method stub
            if (nextTime<=0) {
                if (nextTime==0) {
                    stop();
                    if (null!=clistener) {
                        clistener.onTimeComplete();
                    }
                }
                nextTime=0;
                updateTimeNext();
                return;
            }
            nextTime--;
            updateTimeNext();
        }
    };

    public void initTime(long _time_s){
        time=nextTime=_time_s;
        updateTimeNext();
    }
    private void updateTimeNext() {
        // TODO Auto-generated method stub
        String[] strArray = format.format(new Date(nextTime*1000)).split(":");
        this.setText("还剩" + strArray[0] + "分" + strArray[1] + "秒");
        setTextColor(Color.GREEN);
    }

    public void reStart(long _time_s){
        if (_time_s==-1) {
            nextTime=time;
        }else{
            time=nextTime=_time_s;
        }
        this.start();
    }
    public void reStart(){
        reStart(-1);
    }

    public void onResume(){
        this.start();
    }

    public void onPause(){
        this.stop();
    }

    public interface OnTimeCompleteListener{
        void onTimeComplete();
    }

}
