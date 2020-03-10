package com.xbing.app.basic.common.rxbus;

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * RxBus的核心功能是基于Rxjava的，在RxJava中有个Subject类，它继承Observable类，
 * 同时实现了Observer接口，因此Subject可以同时担当订阅者和被订阅者的角色，
 * 这里我们使用Subject的子类PublishSubject来创建一个Subject对象
 * （PublishSubject只有被订阅后才会把接收到的事件立刻发送给订阅者），
 * 在需要接收事件的地方，订阅该Subject对象，之后如果Subject对象接收到事件，
 * 则会发射给该订阅者，此时Subject对象充当被订阅者的角色。完成了订阅，
 * 在需要发送事件的地方将事件发送给之前被订阅的Subject对象，
 * 则此时Subject对象做为订阅者接收事件，然后会立刻将事件转发给订阅该Subject对象的订阅者，
 * 以便订阅者处理相应事件，到这里就完成了事件的发送与处理。最后就是取消订阅的操作了，
 * Rxjava中，订阅操作会返回一个Subscription对象，以便在合适的时机取消订阅，防止内存泄漏，
 * 如果一个类产生多个Subscription对象，我们可以用一个CompositeSubscription存储起来，
 * 以进行批量的取消订阅。
 *
 */
public class RxBus {

    private static volatile RxBus mInstance;
    private SerializedSubject<Object, Object> mSubject;
    private HashMap<String, CompositeSubscription> mSubscriptionMap;

    private RxBus() {
        //PublishSubject:只有被订阅之后才会把接收到的消息立刻发送给订阅者
        mSubject = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getInstance() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                if (mInstance == null) {
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }

    /**
     * 发送事件
     *
     * @param o
     */
    public void post(Object o) {
        mSubject.onNext(o);
    }

    /**
     * 返回指定类型的Observable实例
     *
     * @param type
     * @param <T>
     * @return
     */
    public <T> Observable<T> tObservable(final Class<T> type) {
        return mSubject.ofType(type);
    }

    /**
     * 是否已有观察者订阅
     *
     * @return
     */
    public boolean hasObservers() {
        return mSubject.hasObservers();
    }

    /**
     * 一个默认的订阅方法
     *
     * @param type
     * @param next
     * @param error
     * @param <T>
     * @return
     */
    public <T> Subscription doSubscribe(Class<T> type, Action1<T> next, Action1<Throwable> error) {
        return tObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error);
    }

    /**
     * 保存订阅后的subscription
     *
     * @param o
     * @param subscription
     */
    public void addSubscription(Object o, Subscription subscription) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).add(subscription);
        } else {
            CompositeSubscription compositeSubscription = new CompositeSubscription();
            compositeSubscription.add(subscription);
            mSubscriptionMap.put(key, compositeSubscription);
        }
    }

    /**
     * 取消订阅
     *
     * @param o
     */
    public void unSubscribe(Object o) {
        if (mSubscriptionMap == null) {
            return;
        }

        String key = o.getClass().getName();
        if (!mSubscriptionMap.containsKey(key)) {
            return;
        }
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).unsubscribe();
        }

        mSubscriptionMap.remove(key);
    }
}
