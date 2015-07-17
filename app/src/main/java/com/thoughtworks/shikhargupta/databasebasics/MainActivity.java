package com.thoughtworks.shikhargupta.databasebasics;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ActionBarActivity {

    Time today = new Time(Time.getCurrentTimezone());
    EditText task;
    DBAdapter myDBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        task = (EditText) findViewById(R.id.edit_task);
        openDB();
        populateListView();
        listViewItemClick();
    }


    private void openDB() {

        myDBAdapter = new DBAdapter(this);
        myDBAdapter.open();
    }


    public void onClick_addTask ( View v ){

        today.setToNow();
        String timeStamp =  today.format("%Y-%m-%d %H:%M:%S");
        if( !TextUtils.isEmpty(task.getText().toString())){
            myDBAdapter.insertRow(task.getText().toString() , timeStamp);
        }
        populateListView();
    }


    private void populateListView(){

        Cursor cursor = myDBAdapter.getAllRows();
        String[] fieldNames = new String[]{DBAdapter.KEY_ROWID,DBAdapter.KEY_TASK};
        int[] toField = new int[]{R.id.textView , R.id.textView2};
        SimpleCursorAdapter myAdapter;
        myAdapter = new SimpleCursorAdapter(getBaseContext() , R.layout.item_layout,cursor,fieldNames,toField,0);
        ListView myList = (ListView) findViewById(R.id.listView);
        myList.setAdapter(myAdapter);
    }


    private void updateTask(long id){

        Cursor cursor = myDBAdapter.getRow(id);
        if( cursor.moveToFirst()){
            String newTask = task.getText().toString();
            today.setToNow();
            String date = today.format("%Y-%m-%d %H:%M:%S");
            myDBAdapter.updateRow(id , newTask , date);
        }

        cursor.close();
    }


    private void listViewItemClick(){
        ListView myList = (ListView) findViewById(R.id.listView);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateTask(id);
                populateListView();
            }
        });
    }
}
