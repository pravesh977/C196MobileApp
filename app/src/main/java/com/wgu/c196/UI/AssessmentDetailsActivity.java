package com.wgu.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wgu.c196.Database.DBRepository;
import com.wgu.c196.Entity.AssessmentEntity;
import com.wgu.c196.R;

import java.util.List;

public class AssessmentDetailsActivity extends AppCompatActivity {

    private int passedCourseId;
    private DBRepository repository;
    private int assessmentId;
    private String performanceTitle;
    private String performanceDate;
    private String objectiveTitle;
    private String objectiveDate;
    private List<AssessmentEntity> allAssessments;
    private AssessmentEntity currentAssessment;
    private EditText editPerformanceTitle;
    private EditText editPerformanceDate;
    private EditText editObjectiveTitle;
    private EditText editObjectiveDate;
    private TextView courseIdTextView;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        courseId = getIntent().getIntExtra("courseId", -1);
        assessmentId = getIntent().getIntExtra("assessmentId", -1);
        repository = new DBRepository(getApplication());
        allAssessments = repository.getAllAssessments();
        for(AssessmentEntity element:allAssessments) {
            if(element.getAssessmentId() == assessmentId) {
                currentAssessment = element;
            }
        }
        performanceTitle = getIntent().getStringExtra("assessmentPerformanceTitle");
        performanceDate = getIntent().getStringExtra("assessmentPerformanceDate");
        objectiveTitle = getIntent().getStringExtra("assessmentObjectiveTitle");
        objectiveDate = getIntent().getStringExtra("assessmentObjectiveDate");
        editPerformanceTitle = findViewById(R.id.editPerformanceTitle);
        editPerformanceDate = findViewById(R.id.editPerformanceDate);
        editObjectiveTitle = findViewById(R.id.editObjectiveTitle);
        editObjectiveDate = findViewById(R.id.editObjectiveDate);
        courseIdTextView = findViewById(R.id.courseIdTextView);
        if (assessmentId != -1) {
            editPerformanceTitle.setText(performanceTitle);
            editPerformanceDate.setText(performanceDate);
            editObjectiveTitle.setText(objectiveTitle);
            editObjectiveDate.setText(objectiveDate);
            courseIdTextView.setText(String.valueOf(courseId));
        }

    }

    //Adding menu to the Assessment screen
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_assessment, menu);
        return true;
    }

    //Handling menu options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.deleteAssessmentButton) {
            //if(numAssessments == 0) {
                repository.delete(currentAssessment);
                Toast.makeText(getApplicationContext(), "Assessment has been deleted!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AssessmentDetailsActivity.this, CoursesActivity.class);
                startActivity(intent);
            //} else {
           //     Toast.makeText(getApplicationContext(), "Can't Delete Course. Please delete all its assignments first.", Toast.LENGTH_LONG).show();
            //}
        }
        return true;
    }


    //FIXMEx redirect after saving/updating
    public void saveAssessment(View view) {
//        Toast.makeText(getApplicationContext(), courseId, Toast.LENGTH_LONG).show();
//        Log.i("course-is", String.valueOf(courseId));
        if(assessmentId == -1) {
            AssessmentEntity newAssessment = new AssessmentEntity(0, editPerformanceTitle.getText().toString(), editPerformanceDate.getText().toString(), editObjectiveTitle.getText().toString(), editObjectiveDate.getText().toString(), courseId);
            repository.insert(newAssessment);
            //Log.i("courseof", String.valueOf(newAssessment.getCourseId()));
        }
        else {
            AssessmentEntity newAssessment = new AssessmentEntity(assessmentId, editPerformanceTitle.getText().toString(), editPerformanceDate.getText().toString(), editObjectiveTitle.getText().toString(), editObjectiveDate.getText().toString(), courseId);
            repository.update(newAssessment);
        }
    }
}