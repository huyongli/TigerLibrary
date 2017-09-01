package cn.ittiger.library.helper;

import rx.Subscriber;

/**
 * @author: laohu on 2017/5/11
 * @site: http://ittiger.cn
 */

public abstract class SimpleSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }
}
