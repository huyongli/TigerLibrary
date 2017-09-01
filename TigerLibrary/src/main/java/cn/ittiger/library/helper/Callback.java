package cn.ittiger.library.helper;

/**
 * @author: laohu on 2017/5/11
 * @site: http://ittiger.cn
 */
public interface Callback<T> {

    void onSuccess(T t);

    void onFailure(Throwable e);
}
