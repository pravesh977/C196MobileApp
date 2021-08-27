package com.wgu.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wgu.c196.Database.DBRepository;
import com.wgu.c196.Entity.AssessmentEntity;
import com.wgu.c196.Entity.CourseEntity;
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
        TermEntity term1 = new TermEntity(1,"First Term", "05/02/2021", "08/11/2021");
        TermEntity term2 = new TermEntity(2,"Second Term", "06/12/2022", "09/09/2022");
        repository.insert(term1);
        repository.insert(term2);
        CourseEntity collegeAlgebra = new CourseEntity(1, "College Algebra", "09/08/2021", "10/12/2021", "Plan To Take", "Fred Flintstones", "4245636623", "fred@stones.com", "Only Chapters 5-10 required for exam.", 1);
        CourseEntity trigonometry = new CourseEntity(2, "Trigonometry", "04/11/2022", "07/02/2022", "Completed", "Roger Rabbit", "5523360068", "roger.rabbit@mail.com", "Group work required for this class.", 1);
        repository.insert(collegeAlgebra);
        repository.insert(trigonometry);
        AssessmentEntity algebraAssessment = new AssessmentEntity(1, "Linear Inequalities", "05/08/2021", "Graph exam", "09/10/2021", 1);
        AssessmentEntity algebraAssessment2 = new AssessmentEntity(2, "Polynomials Submission", "12/12/2021", "Equations exam", "03/08/2022", 1);
        repository.insert(algebraAssessment);
        repository.insert(algebraAssessment2);
    }

    public void enterTermsListScreen(View view) {
        Intent intent = new Intent(MainActivity.this, TermsActivity.class);
        startActivity(intent);
        //Log.i("enterapp","entering application yo");

    }
}