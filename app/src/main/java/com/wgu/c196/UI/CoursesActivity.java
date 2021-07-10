package com.wgu.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wgu.c196.Database.DBRepository;
import com.wgu.c196.Entity.CourseEntity;
import com.wgu.c196.Entity.TermEntity;
import com.wgu.c196.HelperClasses.StringToCalendarConverterClass;
import com.wgu.c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CoursesActivity extends AppCompatActivity {
    private DBRepository repository;
    private List<TermEntity> allTerms;
    private int Id;
    //private String titlewow;
    private EditText editTermTitle;
    private EditText editTermStart;
    private EditText editTermEnd;
    private TextView termIdTextView;
    private TermEntity currentTerm;
    public static int numCourses;
    private RecyclerView recyclerView;
    Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog startDateDialog;
    private Long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Id = getIntent().getIntExtra("termId", -1);
        Log.i("termIdincourse", String.valueOf(Id));
        repository = new DBRepository(getApplication());
        allTerms = repository.getAllTerms();
        for(TermEntity element:allTerms) {
            if(element.getTermId()==Id) {
                currentTerm = element;
            }
        }
//        String tId = String.valueOf(currentTerm.getTermId());
//        Toast.makeText(getApplicationContext(), tId, Toast.LENGTH_LONG).show();



            editTermTitle = findViewById(R.id.editTermName);
            editTermStart = findViewById(R.id.editTermStart);
            editTermEnd = findViewById(R.id.editTermEnd);
            termIdTextView = findViewById(R.id.termIdTextView);

        //Setting onClick on term start EditText
        editTermStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openDatePickerDialog();
                Calendar startTermCal = StringToCalendarConverterClass.stringToCalendar(editTermStart);
                openDatePickerDialogStartTerm(startTermCal);
            }
        });

        //Setting onClick on term end EditText
        editTermEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openDatePickerDialog();
                Calendar endTermCal = StringToCalendarConverterClass.stringToCalendar(editTermEnd);
                openDatePickerDialogEndTerm(endTermCal);
            }
        });

        if(currentTerm != null) {
            editTermTitle.setText(getIntent().getStringExtra("termTitle"));
            editTermStart.setText(getIntent().getStringExtra("termStart"));
            editTermEnd.setText(getIntent().getStringExtra("termEnd"));
            //termIdTextView.setText(getIntent().getIntExtra("termId", -99));
            termIdTextView.setText(String.valueOf(Id));
        }

        recyclerView = findViewById(R.id.coursesRecyclerView);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CourseEntity> filteredCourse = new ArrayList<>();
        for(CourseEntity element:repository.getAllCourses()) {
            if(element.getTermId() == Id) {
                filteredCourse.add(element);
            }
        }
        numCourses = filteredCourse.size();
        adapter.setCourses(filteredCourse);
    }

    public void openDatePickerDialogStartTerm(Calendar givenCalendar){

        DatePickerDialog startDateDialog = new DatePickerDialog(CoursesActivity.this, new DatePickerDialog.OnDateSetListener() {



            //editTermStart.setText(sdf.format(myCalendar.getTime()));

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                CoursesActivity.this.editTermStart.setText((month + 1) + "/" + day + "/" + year);
            }
        //}, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR));
        }, givenCalendar.get(Calendar.YEAR), givenCalendar.get(Calendar.MONTH), givenCalendar.get(Calendar.DAY_OF_MONTH));
        startDateDialog.show();
    }

    public void openDatePickerDialogEndTerm(Calendar givenCalendar){

        DatePickerDialog endDateDialog = new DatePickerDialog(CoursesActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                CoursesActivity.this.editTermEnd.setText((month + 1) + "/" + day + "/" + year);
            }
        }, givenCalendar.get(Calendar.YEAR), givenCalendar.get(Calendar.MONTH), givenCalendar.get(Calendar.DAY_OF_MONTH));
        endDateDialog.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        String id = (findViewById(R.id.deleteTerm)).toString();
//        if(item.getItemId().toString() == id) {

        //}
        if(item.getItemId() == R.id.deleteTerm) {
            if(numCourses ==0) {
                repository.delete(currentTerm);
                Toast.makeText(getApplicationContext(), "Term has been deleted!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CoursesActivity.this, TermsActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Can't Delete Term. Please delete all its courses first.", Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }
//    private void updateLabel() {
//        String myFormat = "MM/dd/yy";
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//        editTermStart.setText(sdf.format(myCalendar.getTime()));
//    }

    public void addTerm(View view) {
       // Log.i("titleadded", newTerm.getTermTitle());
        //Log.i("titlestart", newTerm.getTermStart());
        //Log.i("titleend", newTerm.getTermEnd());
        //Log.i("newtermid", String.valueOf(newTerm.getTermId()));
        //Log.i("Idonly" , String.valueOf(Id));
        if(Id == -1) {
            TermEntity newTerm = new TermEntity(0, editTermTitle.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
            repository.insert(newTerm);
        } else {
            TermEntity newTerm = new TermEntity(Id, editTermTitle.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
            repository.update(newTerm);
        }
        //Log.i("newtermid", String.valueOf(newTerm.getTermId()));


        Intent intent = new Intent(CoursesActivity.this, TermsActivity.class);
        startActivity(intent);
    }

    public void courseAddScreen(View view) {
        Intent intent = new Intent(CoursesActivity.this, CourseDetailsActivity.class);
        intent.putExtra("newTermId", Id);
        startActivity(intent);
    }
}