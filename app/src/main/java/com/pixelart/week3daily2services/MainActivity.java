package com.pixelart.week3daily2services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String audio1 = "Toccata-and-Fugue-Dm.mp3";
    private String audio2 = "book1-fugue24-string-quartet.mp3";

    private Intent musicPlayerServiceIntent;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecylerViewAdapter adapter;

    private List<Person > personList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        personList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter = new RecylerViewAdapter(personList);
        recyclerView.setAdapter(adapter);
        initPerson();
        adapter.notifyDataSetChanged();
    }
//TODO: Figure out a way to pass the data using broadcast receiver and display it on the recylerview
    private void initPerson()
    {
        //Person person = new Person();

        personList.add(new Person("sam", 20, 150, "placeholder"));
        personList.add(new Person("john", 15, 115, "placeholder"));
        personList.add(new Person("joe", 31, 200, "placeholder"));
        personList.add(new Person("jake", 30, 120, "placeholder"));
        personList.add(new Person("sally", 25, 100, "placeholder"));
        personList.add(new Person("josh", 22, 150, "placeholder"));
        personList.add(new Person("mercy", 18, 250, "placeholder"));
    }

    public void onClick(View view) {
        musicPlayerServiceIntent = new Intent(this, MusicPlayerService.class);
        switch (view.getId())
        {
            case R.id.btnPlayAudio1:
                musicPlayerServiceIntent.putExtra("url", audio1);
                musicPlayerServiceIntent.setAction("audio1");
                startService(musicPlayerServiceIntent);
                break;

            case R.id.btnPlayAudio2:
                musicPlayerServiceIntent.putExtra("url", audio2);
                musicPlayerServiceIntent.setAction("audio2");
                startService(musicPlayerServiceIntent);
                break;

            case R.id.btnIntentService:
                Intent intent = new Intent(this, MyIntentService.class);
                intent.setAction("sendPerson");
                for (int i = 0; i<personList.size();i++) {
                    Person person = personList.get(i);
                    intent.putExtra("person", person);

                    Log.d(TAG, "" + person.toString());
                }
                startService(intent);
                break;
        }
    }
}
