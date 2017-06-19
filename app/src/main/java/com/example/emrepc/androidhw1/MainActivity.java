package com.example.emrepc.androidhw1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {


    List<String> words = new ArrayList<>();
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean silent = settings.getBoolean("silentMode", false);

        if(!silent)
            generateFile();
    }

    public void trainFunction(View v) {
        Intent i = new Intent(this , TrainActivity.class);
        startActivity(i);
    }

    public void addOnClickFunction(View v) {
        Intent i = new Intent(this , AddNewWords.class);
        startActivity(i);
    }

    public void testOnClickFunction(View v) {
        Intent i = new Intent(this , TestYourself.class);
        startActivity(i);
    }


    public void generateList() {
        words.add(0,"car-araba");
        words.add(1,"pink-pembe");
        words.add(2,"horse-at");
        words.add(3,"computer-bilgisayar");
        words.add(4,"black-siyah");
        words.add(5,"book-kitap");
        words.add(6,"compare-karsılastırmak");
        words.add(7,"inspire-ilhamvermek");
        words.add(8,"elephant-fil");
        words.add(9,"fox-tilki");
        words.add(10,"country-ülke");
        words.add(11,"confuse-şaşırmak");
        words.add(12,"fish-balık");
        words.add(13,"dog-köpek");
        words.add(14,"plane-uçak");
        words.add(15,"lion-aslan");

    }

    public void generateFile() {

        generateList();

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("emre.txt", Context.MODE_PRIVATE));
            for(int i = 0; i < words.size(); i++) {
                outputStreamWriter.write(words.get(i));
                outputStreamWriter.write("\n");
            }

            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("silentMode", true);

        // Commit the edits!
        editor.commit();

    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}