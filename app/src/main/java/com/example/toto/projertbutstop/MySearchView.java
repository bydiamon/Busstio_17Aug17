package com.example.toto.projertbutstop;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;


import java.util.ArrayList;

public class MySearchView extends AppCompatActivity implements android.widget.SearchView.OnQueryTextListener {

    // Declare Variables
    ListView list;
    ListViewAdapter adapter;
    android.widget.SearchView editsearch;
    String[] animalNameList;
    ArrayList<AnimalNames> arraylist = new ArrayList<AnimalNames>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        // Generate sample data

//        animalNameList = new String[]{"Lion", "Tiger", "Dog",
//                "Cat", "Tortoise", "Rat", "Elephant", "Fox",
//                "Cow","Donkey","Monkey"};

        animalNameList = getBusStopFromServer();

        // Locate the ListView in listview_main.xml
        list = (ListView) findViewById(R.id.listview);

        for (int i = 0; i < animalNameList.length; i++) {
            AnimalNames animalNames = new AnimalNames(animalNameList[i]);
            // Binds all strings into an array
            arraylist.add(animalNames);
        }

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = (android.widget.SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(MySearchView.this);
    }

    private String[] getBusStopFromServer() {

        String tag = "18AugV2";
        String[] resultStrings = new String[0];

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM busstopTABLE", null);
            cursor.moveToFirst();

            resultStrings = new String[cursor.getCount()];

            for (int i=0;i<resultStrings.length; i+=1) {
                resultStrings[i] = cursor.getString(3);
                Log.d(tag, "result[" + i + "] ==> " + resultStrings[i]);
                cursor.moveToNext();
            }

            return resultStrings;
        } catch (Exception e) {
            Log.d(tag, "e ==> " + e.toString());

        }


        return resultStrings;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}