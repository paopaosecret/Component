package com.xbing.app.basic.router.action;

import android.util.Log;
import com.xbing.app.basic.common.rxbus.RxBus;
import com.xbing.app.basic.router.action.base.IAction;
import com.xbing.app.basic.router.action.event.NativeFunctionEvent;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *
 * @作者 zhaobing04
 *
 * @创建日期 2020/7/30 10:41
 */
public class NativeFunctionAction implements IAction {

    private NativeFunctionAction(){
        init();
    }

    private static class NativeFunctionActionHolder{
        private static NativeFunctionAction instance = new NativeFunctionAction();
    }

    public static NativeFunctionAction getInstance(){
        return NativeFunctionActionHolder.instance;
    }

    @Override
    public void init() {
        Subscription subscription = RxBus.getInstance()
                .tObservable(NativeFunctionEvent.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NativeFunctionEvent>() {
                    @Override
                    public void call(NativeFunctionEvent newEvent) {
                        Log.d("HyRouter","NativeFunctionAction exe");
                    }
                });

        RxBus.getInstance().addSubscription(this, subscription);
    }

    @Override
    public void unInit() {
        RxBus.getInstance().unSubscribe(this);
    }
}
