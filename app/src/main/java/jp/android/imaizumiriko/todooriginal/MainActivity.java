package jp.android.imaizumiriko.todooriginal;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    public Realm realm;
    public Todo todo;
    public ListView listView;

    private TextView timerText;

    private SimpleDateFormat dataFormat =
            new SimpleDateFormat("mm:ss.SSS", Locale.US);

//TODO Start
    public static int OVERLAY_PERMISSION_REQ_CODE = 1000;
//TODO End
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todo = new Todo();
        //Todoをインスタンス


        // API 23 以上であればPermission chekを行う
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        //}

        // Serviceを開始するためのボタン
        Button buttonStart = findViewById(R.id.start_button);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), TestService.class);
                // Serviceの開始
                // API26以上
                startForegroundService(intent);
            }
        });
        //TODO END

        //TODO Start
        @TargetApi(Build.VERSION_CODES.M)
        public void checkPermission() {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
                if (!Settings.canDrawOverlays(this)) {
                    Log.d("debug", "SYSTEM_ALERT_WINDOW permission not granted...");
                    // SYSTEM_ALERT_WINDOW permission not granted...
                    // nothing to do !
                }
            }
        }
        //TODO END




        realm = Realm.getDefaultInstance();

        listView = findViewById(R.id.list_view);
        //listviewを関連付け

        FloatingActionButton fab = findViewById(R.id.fab);
        //Buttonを右下に置く
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ADDactivity.class);
                //AddActivityに画面遷移する
                startActivity(intent);

            }

        });

        // 3分= 3x60x1000 = 180000 msec
        long countNumber = 180000;
        // インターバル msec
        long interval = 10;

        Button startButton = findViewById(R.id.start_button);
        Button stopButton = findViewById(R.id.stop_button);

        timerText = findViewById(R.id.timer);

        // インスタンス生成
        // CountDownTimer(long millisInFuture, long countDownInterval)
        final CountDown countDown = new CountDown(countNumber, interval);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 開始
                countDown.start();
            }
        });


        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 中止
                countDown.cancel();

            }
        });

    }




    class CountDown extends CountDownTimer {

        CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            // 完了
            timerText.setText(dataFormat.format(0));
        }

        // インターバルで呼ばれる
        @Override
        public void onTick(long millisUntilFinished) {
            // 残り時間を分、秒、ミリ秒に分割
            //long mm = millisUntilFinished / 1000 / 60;
            //long ss = millisUntilFinished / 1000 % 60;
            //long ms = millisUntilFinished - ss * 1000 - mm * 1000 * 60;
            //timerText.setText(String.format("%1$02d:%2$02d.%3$03d", mm, ss, ms));

            timerText.setText(dataFormat.format(millisUntilFinished));


        }
    }


    }















