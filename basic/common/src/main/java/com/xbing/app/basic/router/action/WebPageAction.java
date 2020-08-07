package com.xbing.app.basic.router.action;

import android.util.Log;
import com.xbing.app.basic.common.rxbus.RxBus;
import com.xbing.app.basic.router.action.base.IAction;
import com.xbing.app.basic.router.action.event.WebPageEvent;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *
 * @作者 zhaobing04
 *
 * @创建日期 2020/7/30 10:44
 */
public class WebPageAction implements IAction {

    private WebPageAction(){
        init();
    }

    private static class WebPageActionHolder{
        private static WebPageAction instance = new WebPageAction();
    }

    public static WebPageAction getInstance(){
        return WebPageActionHolder.instance;
    }

    @Override
    public void init() {
        Subscription subscription = RxBus.getInstance()
                .tObservable(WebPageEvent.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebPageEvent>() {
                    @Override
                    public void call(WebPageEvent newEvent) {
                        Log.d("HyRouter","WebPageAction exe");
                    }
                });

        RxBus.getInstance().addSubscription(this, subscription);
    }

    @Override
    public void unInit() {
        RxBus.getInstance().unSubscribe(this);
    }
}
