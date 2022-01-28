package com.selfproject.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.TimeAnimator;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

public class UpdateActivity extends AppCompatActivity {

    EditText et_update_item, et_update_description, et_update_price;
    Button btn_update, btn_delete, btn_update_date_picker;
    String id, item, description, price, date, category;
    Spinner dropdown;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initDatePicker();



        et_update_item = findViewById(R.id.et_update_item);
        et_update_description = findViewById(R.id.et_update_description);
        et_update_price = findViewById(R.id.et_update_price);
        btn_update_date_picker = findViewById(R.id.btn_update_date_picker);
        btn_update_date_picker.setText(getTodaysDate());
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        dropdown = findViewById(R.id.et_update_item_2);
        getAndSetIntentData();

        //create a list of items for the spinner.
        String[] items = new String[]{"Food & Beverage", "Clothing", "Groceries & Fruits", "Shopping", "Transportation", "Housing", "Travel", "Water & Electric Bill", "Gifts/Donations", "Education", "Telephone Bill", "Baby Supplies", "Sport", "Tax", "Digital Accessories", "Insurance", "Social", "Beauty/Salons"};

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        //Toast.makeText(this, String.valueOf(adapter.getPosition(category)), Toast.LENGTH_LONG).show();
        dropdown.setSelection(adapter.getPosition(category));

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                item = et_update_item.getText().toString().trim();
                description = et_update_description.getText().toString().trim();
                price = et_update_price.getText().toString().trim();
                date = btn_update_date_picker.getText().toString().trim();
                category =  items[dropdown.getSelectedItemPosition()];

                myDB.updateData(item, description, price, id, date, category);
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + item + " ?");
        builder.setMessage("Are you sure you want to delete "+item+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("budget_id") &&
                getIntent().hasExtra("budget_item") &&
                getIntent().hasExtra("budget_description") &&
                getIntent().hasExtra("budget_price")&&
                getIntent().hasExtra("budget_date")&&
                getIntent().hasExtra("budget_category")){

            //get intent data
            id = getIntent().getStringExtra("budget_id");
            item = getIntent().getStringExtra("budget_item");
            description = getIntent().getStringExtra("budget_description");
            price = getIntent().getStringExtra("budget_price");
            category = getIntent().getStringExtra("budget_category");

            //set intent data
            et_update_item.setText(item);
            et_update_description.setText(description);
            et_update_price.setText(price);

        }else{
            Toast.makeText(this, "No data found.", Toast.LENGTH_LONG).show();
        }
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
                btn_update_date_picker.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;
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