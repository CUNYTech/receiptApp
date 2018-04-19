package com.next.groupmeal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.List;

public class InviteActivity extends AppCompatActivity
{
	// The group that the user was invited to
	private GroupWrap group;
	// Create a new handler for the firebase database
	private FirebaseDatabase db;
	// Create the List Adapter that'll handle the contents of the user list
	private UserListAdapter adpt;
	// Hold the manager to request permission if the user doesn't have it
	private PermissionManager permissionManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite);
		permissionManager = new PermissionManager(this);
		apply(null);

		ListView userList = findViewById(R.id.iaUserList);
		userList.setAdapter(adpt = new UserListAdapter());

		Button joinButton = findViewById(R.id.iaJoinButton);
		joinButton.setOnClickListener(adpt);
		Button declineButton = findViewById(R.id.iaDeclineButton);
		declineButton.setOnClickListener(adpt);

		final String groupID = getIntent().getStringExtra("groupID");

		db = FirebaseDatabase.getInstance();
		db.getReference("groups").child(groupID).runTransaction(new Transaction.Handler()
		{
			@Override
			public Transaction.Result doTransaction(MutableData mutableData)
			{
				final GroupWrap g = mutableData.getValue(GroupWrap.class);
				if(g != null)
				{
					g.groupID = groupID;
				}
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						apply(g);
					}
				});
				return Transaction.success(mutableData);
			}

			@Override
			public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot)
			{

			}
		});
	}

	private void apply(GroupWrap group)
	{
		this.group = group;

		Toolbar toolbar = (Toolbar) findViewById(R.id.iaToolbar);
		setSupportActionBar(toolbar);
		ActionBar bar = getSupportActionBar();
		if(bar == null) throw new RuntimeException("Something went wrong that shouldn't have");

		bar.setTitle(group == null ? "" : "Groupmeal Invite From");
		bar.setSubtitle(group == null ? "" : group.groupName);

		ProgressBar progress = (ProgressBar) findViewById(R.id.iaProgress);
		LinearLayout middleLayout = (LinearLayout) findViewById(R.id.iaMiddleLayout);

		if(group == null)
		{
			progress.setVisibility(View.VISIBLE);
			middleLayout.setVisibility(View.GONE);
		}
		else
		{
			progress.setVisibility(View.GONE);
			middleLayout.setVisibility(View.VISIBLE);
			adpt.notifyDataSetChanged();

			((TextView) findViewById(R.id.iaOwnerView)).setText(group.owner);
		}
	}

	@SuppressLint({"MissingPermission", "HardwareIds"})
	@Nullable
	public String getNumber()
	{
		String from = null;
		if (permissionManager.hasNumberPermission())
		{
			TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			if (tMgr != null) from = tMgr.getLine1Number();
		}
		return from;
	}

	public String properNum(String number)
	{
		return number.replaceAll("[^\\d.]", "");
	}

	private class UserListAdapter extends BaseAdapter implements View.OnClickListener
	{
		private final List<String> myUsers = new ArrayList<>();

		@Override
		public int getCount()
		{
			return myUsers.size();
		}

		@Override
		public String getItem(int position)
		{
			return myUsers.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public void notifyDataSetChanged()
		{
			myUsers.clear();
			if(group != null && group.users != null)
			{
				myUsers.addAll(group.users);
			}
			super.notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if(convertView == null)
			{
				LayoutInflater li = LayoutInflater.from(InviteActivity.this);
				convertView = li.inflate(android.R.layout.simple_list_item_1, parent, false);
			}
			((TextView) convertView).setText(getItem(position));
			return convertView;
		}

		@Override
		public void onClick(View v)
		{
			if(v.getId() == R.id.iaJoinButton || v.getId() == R.id.iaDeclineButton)
			{
				String number = getNumber();
				boolean declined = v.getId() == R.id.iaDeclineButton;
				if(!declined && number != null)
				{
					if(group.users == null)
					{
						group.users = new ArrayList<>();
					}

					if(!group.users.contains(number))
					{
						group.users.add(number);
					}

					AlertDialog.Builder bd = new AlertDialog.Builder(InviteActivity.this);
					bd.setTitle("Success");
					bd.setMessage("You can now eat out!");

					bd.create().show();
					InviteActivity.this.finish();
				}
				else if(declined)
				{
					InviteActivity.this.finish();
				}
				db.getReference("groups").child(group.groupID).runTransaction(new Transaction.Handler()
				{
					@Override
					public Transaction.Result doTransaction(MutableData mutableData)
					{
						mutableData.setValue(group);
						return Transaction.success(mutableData);
					}

					@Override
					public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot)
					{

					}
				});
			}
		}
	}
}
