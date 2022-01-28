package com.selfproject.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tv_total_expense;
    FloatingActionButton add_button;
    MyDatabaseHelper myDatabaseHelper;
    ArrayList<String> id, item, description, price, date, category;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_01);
        add_button = findViewById(R.id.fb_add);
        tv_total_expense = findViewById(R.id.tv_total_expense);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDatabaseHelper = new MyDatabaseHelper(MainActivity.this);
        id = new ArrayList<>();
        item = new ArrayList<>();
        description = new ArrayList<>();
        price = new ArrayList<>();
        date = new ArrayList<>();
        category = new ArrayList<>();

        storeDataInArrays();
        double total = calculateTotalExpenses();
        tv_total_expense.setText("RM "+total+"");

        customAdapter = new CustomAdapter(MainActivity.this, id, item, description, price, MainActivity.this, date, category);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private double calculateTotalExpenses() {
        double total=0;
        for(int i=0; i<price.size(); i++){
            total+=Double.parseDouble(price.get(i));
        }
        return total;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDatabaseHelper.readAllData();
        if(cursor.getCount()==0){
            Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                id.add(cursor.getString(0));
                item.add(cursor.getString(1));
                price.add(cursor.getString(2));
                description.add(cursor.getString(3));
                date.add(cursor.getString(4));
                category.add(cursor.getString(5));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btn_delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Item ?");
        builder.setMessage("Are you sure you want to delete all item?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper mydb = new MyDatabaseHelper(MainActivity.this);
                mydb.deleteAllData();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
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



}