package com.loqua.zhuhunews.core.http.subscribers;

/**
 * STAY HUNGRY, STAY FOOLISH!
 *
 * @Prject: ZhuHuNews
 * @Location: com.loqua.zhuhunews.core.http.subscribers
 * @Description: TODO
 * @author: loQua.Xee
 * @email: shyscool@163.com
 * @date: 16/6/15 下午6:24
 * @version: V1.0
 */
public interface SubscriberOnNextListener<T> {
  void onNext(T t);
}