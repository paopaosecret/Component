package com.xbing.app.basic.router.action;

import android.util.Log;

import com.xbing.app.basic.common.rxbus.RxBus;
import com.xbing.app.basic.router.action.base.IAction;
import com.xbing.app.basic.router.action.event.NativePageEvent;

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
public class NativePageAction implements IAction {
    private NativePageAction(){
        init();
    }

    private static class NativePageActionHolder{
        private static NativePageAction instance = new NativePageAction();
    }

    public static NativePageAction getInstance(){
        return NativePageActionHolder.instance;
    }


    @Override
    public void init() {
        Subscription subscription = RxBus.getInstance()
                .tObservable(NativePageEvent.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NativePageEvent>() {
                    @Override
                    public void call(NativePageEvent newEvent) {
                        Log.d("HyRouter","NativePageAction exe");
                        newEvent.getCallBack().onResult("result");
                    }
                });

        RxBus.getInstance().addSubscription(this, subscription);
    }

    @Override
    public void unInit() {
        RxBus.getInstance().unSubscribe(this);
    }
}
