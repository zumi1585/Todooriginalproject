package jp.android.imaizumiriko.todooriginal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    public Realm realm;
    public Todo todo;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        todo = new Todo();
        //Todoをインスタンス

        realm = Realm.getDefaultInstance();

        listView = findViewById(R.id.list_view);
        //listviewを関連付け

        FloatingActionButton fab = findViewById(R.id.fab);
        //Buttonを右下に置く
        fab.setOnClickListener(new View.OnClickLinistener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),ADDactivity.class);
                //AddActivityに画面遷移する
                startActivity(intent);

            }

        });
    }

    public void addListView(){
        final RealmResults<Todo> list =realm.where(Todo.class).findAll();
        List<Todo>item = realm.copyFromRealm(list);
        // itemにtodoを代入する

        ListAdapter adapter = new ListAdapter(getApplicationContext(),R.layout.layout_item_todo,item);
        listView.setAdapter(adapter);
        //addAdapterをsetする
    }

    @Override
    protected void onResume(){
        super.onResume();
        addListView();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
