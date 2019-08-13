package jp.android.imaizumiriko.todooriginal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {

    public Realm realm;

    public EditText titleEditText;
    public EditText limitedEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        realm = Realm.getDefaultInstance();
        //Realmを使うのに必ず書かないといけない

        titleEditText = (EditText) findViewById(R.id.titleDetailEditText);
        limitedEditText = (EditText) findViewById(R.id.limitedDetailEditText);
        //Viewとの関連付け

        showData();
        //showDataを実行
    }


    public void showData(){

        final Todo todo = realm.where(Todo.class).equalTo("updateDate",
                getIntent().getStringExtra("updateDate")).findFirst();
        //realm.where(クラス名）でrealmからTodoクラス型の情報を取得する
        //equalTo("変数名"、”探す値”）でrealmから”変数名”が”探す値”と一致するデータを検索する
        //getIntent.getStringExtra("推移時の名前”）でListAdapterで書いた、putExtraで書いた名前と一致させることで、
        //値を取得することができる
        //(ListAdapterから抜粋）intent.putExtra("updateDate",todo.updateDate);
        //findFirst()検索して見つかった要素から一番初めにヒットした結果を返す

        titleEditText.setText(todo.title);
        limitedEditText.setText(todo.limited);
        //取得した情報をeditTextにsetする
    }
}
