package com.loqua.zhuhunews.core.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loqua.zhuhunews.BuildConfig;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * STAY HUNGRY, STAY FOOLISH!
 *
 * @Prject: ZhuHuNews
 * @Location: com.loqua.zhuhunews.core.http
 * @Description: TODO
 * @author: loQua.Xee
 * @email: shyscool@163.com
 * @date: 16/6/15 下午5:21
 * @version: V1.0
 */
public class HttpClient {

  public static Retrofit retrofit = null;

  public static Retrofit retrofit() {
    if (retrofit == null) {
      OkHttpClient.Builder builder = new OkHttpClient.Builder();

      if (BuildConfig.DEBUG) {
        // Log信息拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置 Debug Log 模式
        builder.addInterceptor(loggingInterceptor);
      }

      /**
       *设置缓存，代码略
       */

      /**
       *  公共参数，代码略
       */

      //公共参数
      Interceptor addQueryParameterInterceptor = new Interceptor() {
        @Override public Response intercept(Chain chain) throws IOException {
          Request originalRequest = chain.request();
          Request request;
          String method = originalRequest.method();
          Headers headers = originalRequest.headers();
          HttpUrl modifiedUrl = originalRequest.url()
              .newBuilder()
              // Provide your custom parameter here
              .addQueryParameter("platform", "android")
              .addQueryParameter("version", "1.0.0")
              .build();
          request = originalRequest.newBuilder().url(modifiedUrl).build();
          return chain.proceed(request);
        }
      };
      //公共参数
      builder.addInterceptor(addQueryParameterInterceptor);

      /**
       * 设置头，代码略
       */

      /**
       * Log信息拦截器，代码略
       */

      /**
       * 设置cookie，代码略
       */

      /**
       * 设置超时和重连，代码略
       */
      //设置超时
      builder.connectTimeout(15, TimeUnit.SECONDS);
      builder.readTimeout(20, TimeUnit.SECONDS);
      builder.writeTimeout(20, TimeUnit.SECONDS);
      //错误重连
      builder.retryOnConnectionFailure(true);

      //以上设置结束，才能build(),不然设置白搭
      OkHttpClient okHttpClient = builder.build();

      Gson gson = new GsonBuilder()
          //配置你的Gson
          .setDateFormat("yyyy-MM-dd hh:mm:ss").create();

      retrofit =
          new Retrofit.Builder().baseUrl(ApiStores.API_SERVER_URL)
              .client(okHttpClient)//设置 Json 转换器
              .addConverterFactory(GsonConverterFactory.create(gson))
              //RxJava 适配器
              .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
              .client(okHttpClient)
              .build();
    }
    return retrofit;
  }
}
