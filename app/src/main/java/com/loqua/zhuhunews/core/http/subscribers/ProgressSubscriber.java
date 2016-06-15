package com.loqua.zhuhunews.core.http.subscribers;

import android.content.Context;
import android.widget.Toast;
import com.loqua.zhuhunews.core.http.progress.ProgressCancelListener;
import com.loqua.zhuhunews.core.http.progress.ProgressDialogHandler;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import rx.Subscriber;

/**
 * STAY HUNGRY, STAY FOOLISH!
 *
 * @Prject: ZhuHuNews
 * @Location: com.loqua.zhuhunews.core.http.subscribers
 * @Description: TODO
 * @author: loQua.Xee
 * @email: shyscool@163.com
 * @date: 16/6/15 下午6:21
 * @version: V1.0
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

  private SubscriberOnNextListener<T> mSubscriberOnNextListener;
  private ProgressDialogHandler mProgressDialogHandler;

  private Context context;

  public ProgressSubscriber(SubscriberOnNextListener<T> mSubscriberOnNextListener,
      Context context) {
    this.mSubscriberOnNextListener = mSubscriberOnNextListener;
    this.context = context;
    mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
  }

  private void showProgressDialog() {
    if (mProgressDialogHandler != null) {
      mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG)
          .sendToTarget();
    }
  }

  private void dismissProgressDialog() {
    if (mProgressDialogHandler != null) {
      mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG)
          .sendToTarget();
      mProgressDialogHandler = null;
    }
  }

  /**
   * 订阅开始时调用
   * 显示ProgressDialog
   */
  @Override public void onStart() {
    showProgressDialog();
  }

  /**
   * 完成，隐藏ProgressDialog
   */
  @Override public void onCompleted() {
    dismissProgressDialog();
    Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
  }

  /**
   * 对错误进行统一处理
   * 隐藏ProgressDialog
   */
  @Override public void onError(Throwable e) {
    if (e instanceof SocketTimeoutException) {
      Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
    } else if (e instanceof ConnectException) {
      Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
    dismissProgressDialog();
  }

  /**
   * 将onNext方法中的返回结果交给Activity或Fragment自己处理
   *
   * @param t 创建Subscriber时的泛型类型
   */
  @Override public void onNext(T t) {
    if (mSubscriberOnNextListener != null) {
      mSubscriberOnNextListener.onNext(t);
    }
  }

  /**
   * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
   */
  @Override public void onCancelProgress() {
    if (!this.isUnsubscribed()) {
      this.unsubscribe();
    }
  }
}
