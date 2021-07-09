package com.wgu.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.wgu.c196.Database.DBRepository;
import com.wgu.c196.Entity.AssessmentEntity;
import com.wgu.c196.Entity.CourseEntity;
import com.wgu.c196.HelperClasses.StringToCalendarConverterClass;
import com.wgu.c196.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {
    private DBRepository repository;
    private int passedCourseId;
    private static int numAlert;
    private String courseTitle;
    private String startDate;
    private String endDate;
    private String courseStatus;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private int termId;
    private EditText editCourseTitle;
    private EditText editStartDate;
    private EditText editEndDate;
    private EditText editCourseStatus;
    private EditText editInstructorName;
    private EditText editInstructorPhone;
    private EditText editInstructorEmail;
    private List<CourseEntity> allCourses;
    private CourseEntity currentCourse;
    private String sop;
    private String farrock;
    private int newTermId;
    private RecyclerView assignmentRecyclerView;
    public static int numAssessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        passedCourseId = getIntent().getIntExtra("courseId", -1);
        repository = new DBRepository(getApplication());
        allCourses = repository.getAllCourses();
        for(CourseEntity element:allCourses) {
            if(element.getCourseId() == passedCourseId) {
                currentCourse = element;
            }
        }
//        String tID = String.valueOf(currentCourse.getTermId());
//        Toast.makeText(getApplicationContext(), tID + " is the term id", Toast.LENGTH_LONG).show();
//        Log.i("currentcourse", currentCourse.getCourseTitle());
        courseTitle = getIntent().getStringExtra("courseTitle");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        courseStatus = getIntent().getStringExtra("courseStatus");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        termId = getIntent().getIntExtra("termId", -1);
        farrock = getIntent().getStringExtra("instructorEmail");

        newTermId = getIntent().getIntExtra("newTermId", -1);
        Log.i("newtermis", String.valueOf(newTermId));

        editCourseTitle = findViewById(R.id.editCourseTitle);
        editStartDate = findViewById(R.id.editCourseStart);
        editEndDate = findViewById(R.id.editCourseEnd);
        editCourseStatus = findViewById(R.id.editCourseStatus);
        editInstructorName = findViewById(R.id.editInstructorName);
        editInstructorPhone = findViewById(R.id.editInstructorPhone);
        editInstructorEmail = findViewById(R.id.editInstructorEmail);
        if(passedCourseId != -1) {
            editCourseTitle.setText(courseTitle);
            editStartDate.setText(startDate);
            editEndDate.setText(endDate);
            editCourseStatus.setText(courseStatus);
            editInstructorName.setText(instructorName);
            editInstructorPhone.setText(instructorPhone);
            editInstructorEmail.setText(instructorEmail);
        }
        assignmentRecyclerView = findViewById(R.id.assignmentsRecyclerView);
        final AssessmentAdapter assessmentAdapter= new AssessmentAdapter(this);
        assignmentRecyclerView.setAdapter(assessmentAdapter);
        assignmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<AssessmentEntity> filteredAssessments = new ArrayList<>();
        for(AssessmentEntity element:repository.getAllAssessments()) {
            if(element.getCourseId() == passedCourseId) {
                filteredAssessments.add(element);
            }
        }
        numAssessments = filteredAssessments.size();
        assessmentAdapter.setAssessments(filteredAssessments);


        editStartDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar startCourseCalendar = StringToCalendarConverterClass.stringToCalendar(editStartDate);
                openDatePickerDialogStartCourse(startCourseCalendar);
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar endCourseCalendar = StringToCalendarConverterClass.stringToCalendar(editStartDate);
                openDatePickerDialogEndCourse(endCourseCalendar);
            }
        });
    }

    public void openDatePickerDialogStartCourse(Calendar givenCalendar){

        DatePickerDialog startDateDialog = new DatePickerDialog(CourseDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                CourseDetailsActivity.this.editStartDate.setText((month + 1) + "/" + day + "/" + year);
            }
        }, givenCalendar.get(Calendar.YEAR), givenCalendar.get(Calendar.MONTH), givenCalendar.get(Calendar.DAY_OF_MONTH));
        startDateDialog.show();
    }

    public void openDatePickerDialogEndCourse(Calendar givenCalendar){

        DatePickerDialog endDateDialog = new DatePickerDialog(CourseDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                CourseDetailsActivity.this.editEndDate.setText((month + 1) + "/" + day + "/" + year);
            }
        }, givenCalendar.get(Calendar.YEAR), givenCalendar.get(Calendar.MONTH), givenCalendar.get(Calendar.DAY_OF_MONTH));
        endDateDialog.show();
    }

    //Adding menu to the Course Screen
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_course, menu);
        return true;
    }

    //Handling menu options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.deleteCourseButton) {
            if(numAssessments == 0) {
                repository.delete(currentCourse);
                Toast.makeText(getApplicationContext(), "Course has been deleted!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CourseDetailsActivity.this, CoursesActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Can't Delete Course. Please delete all its assignments first.", Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }

    public void saveCourse(View view) {
        if(passedCourseId == -1) {
            //Id = allCourses.get(allCourses.size() -1).getCourseId();
            CourseEntity newCourse = new CourseEntity(0, editCourseTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), editCourseStatus.getText().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), newTermId);

            //Log.i("objecttitle", newCourse.getCourseTitle());
            repository.insert(newCourse);
//            Log.i("courseid", String.valueOf(newCourse.getCourseId()));
//            Log.i("termid", String.valueOf(newCourse.getTermId()));
//            Log.i("coursename", String.valueOf(newCourse.getCourseTitle()));

//           Toast.makeText(getApplicationContext(), String.valueOf(termId), Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(), newCourse.getInstructorEmail(), Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(), String.valueOf(newTermId), Toast.LENGTH_LONG).show();

       //     Log.i("title" , editCourseTitle.getText().toString());
            //Log.i("termid" , String.valueOf(newCourse.getTermId()));
         //   Log.i("newTermId" , String.valueOf(newCourse.getTermId()));


        }
        else {
            CourseEntity newCourse = new CourseEntity(passedCourseId, editCourseTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), editCourseStatus.getText().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), termId);
            repository.update(newCourse);
        }
        Intent intent = new Intent(CourseDetailsActivity.this, CoursesActivity.class);
        startActivity(intent);
    }

    public void assessmentsAddScreen(View view) {
        Intent intent = new Intent(CourseDetailsActivity.this, AssessmentDetailsActivity.class);
        intent.putExtra("courseId", passedCourseId);
        startActivity(intent);
    }
}