package com.example.crudapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.TextView;

public class DatabaseController extends SQLiteOpenHelper {
    public DatabaseController(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //when the database is created, this method will be invoked
        //creation of the table Products
        db.execSQL("CREATE TABLE PRODUCTS( ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, CATEGORY TEXT, SERIAL INTEGER UNIQUE)");//inside this method put the SQL statement
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PRODUCTS;");
        onCreate(db);
    }

    public void insertItem(String _name, String _category, int _serialnum){
        ContentValues contentKeyPairs = new ContentValues();
        contentKeyPairs.put("NAME",_name);
        contentKeyPairs.put("CATEGORY",_category);
        contentKeyPairs.put("SERIAL",_serialnum);
        this.getWritableDatabase().insertOrThrow("PRODUCTS","",contentKeyPairs);
    }

    public void deleteItem(int serialNum){
        this.getWritableDatabase().delete("PRODUCTS","SERIAL='" + serialNum + "'",null);
    }

    public void updateItem(String oldName, String newName){
        this.getWritableDatabase().execSQL("UPDATE PRODUCTS SET NAME='" + newName + "' WHERE NAME='" + oldName + "'");
    }

    public void listAllProducts(TextView textView){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM PRODUCTS",null);
        textView.setText("");
        while (cursor.moveToNext()){
            //here we can get the content of each raw in the cursor array(being position 0 the first column)
            textView.append(cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + "\n");
        }
        cursor.close();
    }
}
