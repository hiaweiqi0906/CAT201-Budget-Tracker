package com.selfproject.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText et_description, et_price, et_item;
    Spinner et_item_2;
    Button btn_add, btn_date_picker;

    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initDatePicker();
        et_item = findViewById(R.id.et_item);
        //get the spinner from the xml
        Spinner dropdown = findViewById(R.id.et_item_2);

        //create a list of items for the spinner.
        String[] items = new String[]{"Food & Beverage", "Clothing", "Groceries & Fruits", "Shopping", "Transportation", "Housing", "Travel", "Water & Electric Bill", "Gifts/Donations", "Education", "Telephone Bill", "Baby Supplies", "Sport", "Tax", "Digital Accessories", "Insurance", "Social", "Beauty/Salons"};

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);



        et_description = findViewById(R.id.et_description);
        et_price = findViewById(R.id.et_price);
        btn_add = findViewById(R.id.btn_add);
        btn_date_picker = findViewById(R.id.btn_date_picker);
        btn_date_picker.setText(getTodaysDate());
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddActivity.this, dropdown.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

                //Toast.makeText(AddActivity.this, btn_date_picker.getText().toString(), Toast.LENGTH_LONG).show();
                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(AddActivity.this);
                myDatabaseHelper.addBudget(et_item.getText().toString().trim(), et_description.getText().toString().trim(), Integer.valueOf(et_price.getText().toString().trim()), btn_date_picker.getText().toString(), dropdown.getSelectedItem().toString());
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = makeDateString(day, month, year);
                btn_date_picker.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return day+" "+ getMonthFormat(month) +" "+ year;
    }

    private String getMonthFormat(int month) {
        if(month==1) return "JAN";
        else if(month==2) return "FEB";
        else if(month==3) return "MAR";
        else if(month==4) return "APR";
        else if(month==5) return "MAY";
        else if(month==6) return "JUN";
        else if(month==7) return "JUL";
        else if(month==8) return "AUG";
        else if(month==9) return "SEP";
        else if(month==10) return "OCT";
        else if(month==11) return "NOV";
        else return "DEC";

    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}