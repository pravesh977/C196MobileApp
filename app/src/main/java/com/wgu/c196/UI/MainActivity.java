package com.wgu.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wgu.c196.Database.DBRepository;
import com.wgu.c196.Entity.TermEntity;
import com.wgu.c196.R;

public class MainActivity extends AppCompatActivity {

    public static int numAlert;
    public static int numAlert2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBRepository repository = new DBRepository(getApplication());
        TermEntity term1 = new TermEntity(1,"First Term", "05/02/2021", "08/02/2021");
        TermEntity term2 = new TermEntity(2,"Second Term", "06/12/2022", "09/09/2022");
        repository.insert(term1);
        repository.insert(term2);
    }

    public void enterTermsListScreen(View view) {
        Intent intent = new Intent(MainActivity.this, TermsActivity.class);
        startActivity(intent);
        //Log.i("enterapp","entering application yo");

    }
}