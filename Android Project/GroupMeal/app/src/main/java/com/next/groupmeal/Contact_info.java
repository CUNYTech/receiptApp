package com.next.groupmeal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.ParcelUuid;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
/**
    When the user launch this page he/she will be able to select the contact from the list and add them into the the group


 */

public class Contact_info extends AppCompatActivity implements View.OnClickListener{

    private TextView GroupTitleTextView;
    private Button AddMember;       //When the user click then the list of the contact is loaded with checkbox
    Cursor mCursor;
    String name;
    String phonenumber;
    private ListView mListView;
    private Button ContinuBtn;

    ArrayAdapter<String> adapter ;

    ArrayList<String> listcontact = new ArrayList<>();   //An arraylist that will hold the contact that was fecth from the phone




    public static final int RequestPermissionConde = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        GroupTitleTextView =(TextView) findViewById(R.id.group_title);
        AddMember = (Button) findViewById(R.id.loadContact);
        mListView = (ListView) findViewById(R.id.myContactList);
        ContinuBtn = (Button) findViewById(R.id.continubtn);


        Bundle extra = getIntent().getExtras();

        findViewById(R.id.goBack).setOnClickListener(this);

        String group_name ="Group Name: "+ getIntent().getStringExtra("Key");

        GroupTitleTextView.setText(group_name);



        AddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getContactInfo();

                AddMember.setVisibility(View.GONE);

                getMember();
            }
        });
    }

    //getContactInfo() method fetch the contact information (name and phone number) then display them in
    //Clickable list view

    private void getContactInfo()
    {
        mCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (mCursor.moveToNext())
        {
            name = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phonenumber = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            String contact = name + " " + phonenumber;

            listcontact.add(contact);

        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listcontact);
        mListView.setAdapter(adapter);

     }

     //getMember method set onItemListener to take the member and add into a group

    private void getMember()
    {

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContinuBtn.setVisibility(View.VISIBLE);




                Toast.makeText(getApplicationContext(), "User added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //getGroup method take the member that user add by clicking on a contact/name



    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.goBack:
                startActivity(new Intent(Contact_info.this, Create_Group.class));
                break;
        }

    }
}
