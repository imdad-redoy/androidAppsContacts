package com.example.risalat.contactlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {

    public static final int Database_Version = 1;
    public static final String Database_Name = "contact.db";
    public static final String Table_Name = "contacts";
    public static final String Key_Id = "id";
    public static final String Column_Name = "name";
    public static final String Key_Number = "number";
    int start;
    int end=10;

    public DBHandler(Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String create_DB_Table = "CREATE TABLE " + Table_Name + " (" +
                Key_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Column_Name + " TEXT NOT NULL, " +
                Key_Number + " TEXT NOT NULL "  + ");";

        sqLiteDatabase.execSQL(create_DB_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Name);

        onCreate(sqLiteDatabase);
    }

    public void addContacts(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Column_Name, contact.getName());
        values.put(Key_Number, contact.getNumber());

         db.insert(Table_Name,null, values);


    }

    public List<Contact> getAllContacts() {

        List<Contact> contactList = new ArrayList<>();
        String sql_query = "SELECT * FROM " + Table_Name;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_query, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();

                contact.setId((Integer.parseInt(cursor.getString(0))));
                contact.setName(cursor.getString(1));
                contact.setNumber(cursor.getString(2));

                contactList.add(contact);
            }
            while (cursor.moveToNext());
        }

        List<Contact> contactLimit=new ArrayList<>();

        for ( start=0  ; start<end;start++){
            contactLimit.add(contactList.get(start)) ;
        }
        end+=10;
        if(!(end<=contactList.size())){
           end=contactList.size();
        }
        return contactLimit;
    }


    public boolean findEnd(){
        List<Contact> contactList = new ArrayList<>();
        String sql_query = "SELECT * FROM " + Table_Name;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_query, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();

                contact.setId((Integer.parseInt(cursor.getString(0))));
                contact.setName(cursor.getString(1));
                contact.setNumber(cursor.getString(2));

                contactList.add(contact);
            }
            while (cursor.moveToNext());
        }
        if((end==contactList.size())){
            return true;
        }
        else
        {
            return false;
        }
    }
}
