package com.next.groupmeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Create_Group extends AppCompatActivity  implements View.OnClickListener{

    private EditText groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__group);

        groupName = (EditText) findViewById(R.id.groupname);

        findViewById(R.id.goBack).setOnClickListener(this);
        findViewById(R.id.nextStep).setOnClickListener(this);
    }
    /*
    nextStepAddContact() method check if the user omit to specify the name of the group
    then take the name to next activity to add the member from its contact assigned to group's name
     */
    private void nextStepAddContact()
    {

        String nameGroup = groupName.getText().toString().trim();

        if (nameGroup.isEmpty())
        {
            groupName.setError("This field is required");
            groupName.requestFocus();
            return;
        }

        Intent intent = new Intent(Create_Group.this, Contact_info.class);
        intent.putExtra("Key", nameGroup);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.goBack:
                startActivity(new Intent(Create_Group.this, MenuPage.class));
                break;
            case R.id.nextStep:
                nextStepAddContact();
                break;

        }

    }
}
