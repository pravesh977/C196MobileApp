package com.wgu.c196.HelperClasses;

import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import com.wgu.c196.UI.CoursesActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StringToCalendarConverterClass {

    public static Calendar stringToCalendar(EditText givenDate){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        int mMonth = 0;
        int mDay = 0;
        int mYear = 0;
        //Log.i("hilog", "hihihihi");
        try {
            //Log.i("startnow", String.valueOf(editTermStart.getText()));
            Date theDate = sdf.parse(givenDate.getText().toString());
            cal.setTime(theDate);
            mMonth = cal.get(Calendar.MONTH);
            mDay = cal.get(Calendar.DAY_OF_MONTH);
            mYear = cal.get(Calendar.YEAR);
            Log.i("whoa", String.valueOf(theDate));
            int selectedMonth = cal.get(Calendar.MONTH);
            Log.i("selectedmonth", String.valueOf(theDate));
            Log.i("thecalendar", String.valueOf(cal.get(Calendar.MONTH)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        DatePickerDialog datePickerDialog = new DatePickerDialog(DatePickerClass.this, new DatePickerDialog.OnDateSetListener() {
//
//
//
//            //editTermStart.setText(sdf.format(myCalendar.getTime()));
//
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                givenDate.setText((month + 1) + "/" + day + "/" + year);
//            }
//            //}, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR));
//        }, mYear, mMonth, mDay);
//        datePickerDialog.show();
        return cal;
    }
}
