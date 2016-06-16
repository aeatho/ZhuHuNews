package com.loqua.zhuhunews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.loqua.zhuhunews.core.http.HttpMethods;
import com.loqua.zhuhunews.core.http.subscribers.ProgressSubscriber;
import com.loqua.zhuhunews.core.http.subscribers.SubscriberOnNextListener;
import com.loqua.zhuhunews.entity.Subject;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Bind(R.id.result_TV) TextView resultTV;

  private SubscriberOnNextListener<List<Subject>> getTopMovieOnNext;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    getTopMovieOnNext = new SubscriberOnNextListener<List<Subject>>() {
      @Override public void onNext(List<Subject> subjects) {
        resultTV.setText(subjects.toString());
      }
    };
  }

  @OnClick(R.id.click_me_BN) public void onClick() {
    getMovie();
  }

  //进行网络请求
  private void getMovie() {
    HttpMethods.getInstance()
        .getTopMovie(new ProgressSubscriber<>(getTopMovieOnNext, MainActivity.this), 0, 10);
  }
}
