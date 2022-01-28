package com.selfproject.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "BudgetTrack.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_budget_table";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ITEM = "budget_item";
    private static final String COLUMN_CATEGORY = "budget_category";
    private static final String COLUMN_DESCRIPTION = "budget_description";
    private static final String COLUMN_PRICE = "budget_price";
    private static final String COLUMN_DATE = "budget_date";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("+ COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_ITEM+ " TEXT, "+COLUMN_PRICE + " REAL, "+COLUMN_DESCRIPTION+ " TEXT, " + COLUMN_DATE+" TEXT, " + COLUMN_CATEGORY+ " TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    void addBudget(String budget_item, String budget_description, double budget_price, String budget_date, String budget_category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ITEM, budget_item);
        cv.put(COLUMN_DESCRIPTION, budget_description);
        cv.put(COLUMN_PRICE, budget_price);
        cv.put(COLUMN_DATE, budget_date);
        cv.put(COLUMN_CATEGORY, budget_category);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    void updateData(String item, String description, String price, String row_id, String date, String budget_category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM, item);
        contentValues.put(COLUMN_DESCRIPTION, description);
        contentValues.put(COLUMN_PRICE, price);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_CATEGORY, budget_category);

        long result = db.update(TABLE_NAME, contentValues, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "Successfully updated!", Toast.LENGTH_LONG).show();
        }
    }

    Cursor readAllData(){
        String query ="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase myDB = this.getWritableDatabase();
        long result = myDB.delete(TABLE_NAME, "_id=?", new String[] {row_id});

        if(result == -1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "Successfully deleted!", Toast.LENGTH_LONG).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
