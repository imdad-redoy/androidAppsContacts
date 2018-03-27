package com.example.risalat.contactlist;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    DBHandler dbHandler;
    RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new DBHandler(this);

        ContentResolver resolver=getContentResolver();
        Cursor cursor=resolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

        while(cursor.moveToNext()){
            String id=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Cursor phoneCursor= resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id },null);

          //  Log.i("my info", id + " =" + name);
            while(phoneCursor.moveToNext()){
                String phoneNumber=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String phoneName=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                dbHandler.addContacts(new Contact(phoneName,phoneNumber));
              //  Log.i("my info", phoneName + phoneNumber);
            }
        }


        recyclerView= findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter= new RecyclerViewAdapter(getApplicationContext(),dbHandler.getAllContacts());
        recyclerView.setAdapter(mAdapter);
        final Button button=(Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAdapter = new RecyclerViewAdapter(getApplicationContext(), dbHandler.getAllContacts());
                recyclerView.setAdapter(mAdapter);

                if(dbHandler.findEnd()){
                    mAdapter = new RecyclerViewAdapter(getApplicationContext(), dbHandler.getAllContacts());
                    recyclerView.setAdapter(mAdapter);
                    button.setText("No more to load");
              }

              }

        });

    }

    }



