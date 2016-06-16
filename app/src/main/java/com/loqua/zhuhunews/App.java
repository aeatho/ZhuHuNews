package com.loqua.zhuhunews;

import android.app.Application;
import com.facebook.stetho.Stetho;

/**
 * STAY HUNGRY, STAY FOOLISH!
 *
 * @Prject: ZhuHuNews
 * @Location: com.loqua.zhuhunews
 * @Description: TODO
 * @author: loQua.Xee
 * @email: shyscool@163.com
 * @date: 16/6/16 上午11:01
 * @version: V1.0
 */
public class App extends Application {

  @Override public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
  }
}
