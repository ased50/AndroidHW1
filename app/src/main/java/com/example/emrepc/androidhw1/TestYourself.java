package com.example.emrepc.androidhw1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by User on 5.4.2017.
 */
public class TestYourself extends ActionBarActivity implements AdapterView.OnItemClickListener{


    private HashMap<String, String> dict;//Hodja
    ArrayList<String> hashMapKeys;
    private ArrayList<String> definitions;
    private String currentWord = "";
    private final int MULTIPLE_CHOICE_COUNT = 5;

    private ListView list;
    private ArrayAdapter<String> adapter;

    double score;

    int numberOfQuestion = 0;
    private EditText scoreBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dict = new HashMap<>();//hodja

        score = 0;
        readFile();

        scoreBox = (EditText)findViewById(R.id.scorebox);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(this);

        generateRandom();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {

        if(numberOfQuestion > hashMapKeys.size()) {
            scoreBox.setText("Final Score: " + score);
            return;
        }

        if (dict.get(currentWord).equals(list.getItemAtPosition(index).toString())) {
            score++;
            generateRandom();
        } else {
            score -= 0.5f;
            generateRandom();
        }

        scoreBox.setText("Score: " + score);


    }

    public void readFile() {
        try {
            InputStream inputStream = openFileInput("emre.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {

                    String[] parts = receiveString.split("-");
                    dict.put(parts[0], parts[1]);

                }

                inputStream.close();

                definitions = new ArrayList<>();
                hashMapKeys = new ArrayList<>(dict.keySet());


            } else {
                Toast.makeText(this, "There are no recovery files", Toast.LENGTH_LONG);
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            Toast.makeText(this, "There are no recovery files", Toast.LENGTH_LONG);
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    private void generateRandom() {

        numberOfQuestion++;

        //shuffle array pick one
        Collections.shuffle(hashMapKeys);
        String word = hashMapKeys.get(0);

        YoYo.with(Techniques.BounceInDown)
                .duration(700)
                .interpolate(new AccelerateDecelerateInterpolator())
                .repeat(1)
                .playOn(findViewById(R.id.the_word));

        //ask question
        TextView the_word = (TextView) findViewById(R.id.the_word);
        the_word.setText(word);
        currentWord = word;

        definitions.clear();
        for (int i = 0; i < MULTIPLE_CHOICE_COUNT; i++) {
            definitions.add(dict.get(hashMapKeys.get(i)));
        }
        Collections.shuffle(definitions);
        adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_layout,
                R.id.content,
                definitions
        );

        list.setAdapter(adapter);


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {

            //onBackPressed();

            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
