package com.xbing.app.basic.router.action;

import android.util.Log;

import com.xbing.app.basic.common.rxbus.RxBus;
import com.xbing.app.basic.router.action.base.IAction;
import com.xbing.app.basic.router.action.event.FlutterPageEvent;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *
 * @作者 zhaobing04
 *
 * @创建日期 2020/7/30 10:52
 */
public class FlutterPageAction implements IAction {
    private FlutterPageAction(){
        init();
    }

    private static class FlutterPageActionHolder{
        private static FlutterPageAction instance = new FlutterPageAction();
    }

    public static FlutterPageAction getInstance(){
        return FlutterPageActionHolder.instance;
    }

    @Override
    public void init() {
        Subscription subscription = RxBus.getInstance()
                .tObservable(FlutterPageEvent.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FlutterPageEvent>() {
                    @Override
                    public void call(FlutterPageEvent newEvent) {
                        Log.d("HyRouter","FlutterPageAction exe");
                    }
                });
        RxBus.getInstance().addSubscription(this, subscription);
    }

    @Override
    public void unInit() {
        RxBus.getInstance().unSubscribe(this);
    }
}
