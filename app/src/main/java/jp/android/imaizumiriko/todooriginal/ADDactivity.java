package jp.android.imaizumiriko.todooriginal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class ADDactivity extends AppCompatActivity {

    public Realm realm;
    public Todo todo;

    public EditText titleEditText;
    public EditText limitedEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);

        todo = new Todo();
        //todoクラスをインスタンス

        realm = Realm.getDefaultInstance();

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        limitedEditText= (EditText) findViewById(R.id.limitedEditText);
        //Viewとの関連付け
    }

    public void save(View v){
        Date date = new Date();
        //Dateクラスをインスタンス
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPAN);

        final String updateDate = sdf.format(date);

        final String title = titleEditText.getText().toString();
        final String limited = limitedEditText.getText().toString();
        //EditTextに記入した内容をStringに代入

        final boolean judge = false;
        //todoのやるべきことが終わったか確認するためのboolean型（falseは未完了、trueは完了を表す）

        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                todo = realm.createObject(todo.class);
                //todoクラスに情報を代入してあげると自動的に保存してくれる。
                todo.title = title;
                todo.limited = limited;
                todo.updateDate = updateDate;
                todo.judge = judge

            }
        });

        finish();
        //AddActivityを閉じる

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.close();
        //Activity終了時にRealmはrealm.closeで閉じないといけない
    }
}
