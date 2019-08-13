package jp.android.imaizumiriko.todooriginal;

import io.realm.RealmObject;

public class Todo extends RealmObject {//デフォルトのクラスを作る
    public String title;
    public String limited;
    public String updateDate;
    public Boolean judge;


    public Todo() {
    }

    public Todo(String title, String limited,String updateDate,Boolean judge){
        this.title = title;
        this.limited = limited;
        this.updateDate = updateDate;
        this.judge = judge;
    }

}
