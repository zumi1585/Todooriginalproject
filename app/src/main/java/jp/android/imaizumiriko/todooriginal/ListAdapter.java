package jp.android.imaizumiriko.todooriginal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class ListAdapter extends ArrayAdapter<Todo> {
    //AdapterはListViewを橋渡しする役目を持っている

    private LayoutInflater layoutInflater;

    ListAdapter(Context context, int textViewResourceId, List<Todo> objects){
        super(context,textViewResourceId,objects);
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Todo todo = getItem(position);
        //getItem(position)でTodoクラスに入っている内容からposition番目のデータを取得する

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_item_todo, null);
            //itemをセットする（ListViewに表示する一個のセルのテンプレートのようなもの）
        }

        final TextView textView = (TextView) convertView.findViewById(R.id.content1_text);
        //そのセルのViewの関連付け

        if (todo != null) {//もし、todoの内容に何か入っていれば

            textView.setText(todo.title);


            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Realm realm = Realm.getDefaultInstance();
                    //Realmを使うのに必ず書かないといけない

                    final Todo realmTodo = realm.where(Todo.class).equalTo("updateDate", todo.updateDate).findFirst();
                    //詳しくはAddActivityを参照
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            if (realmTodo.judge) {

                                textView.setText(todo.title);
                                realmTodo.judge = false;

                            } else {

                                textView.setText(todo.limited);
                                realmTodo.judge = true;
                            }

                        }

                    });

                }
            });

            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {//長押ししたら

                    final Context context = getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("updateDate", todo.updateDate);
                    context.startActivity(intent);
                    //DetailActivityに画面遷移する

                    return false;
                }
            });


        }

        return convertView;
    }
}
