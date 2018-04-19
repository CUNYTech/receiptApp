package com.next.groupmeal;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.security.acl.Group;

public class ListenerService extends Service
{
	/**
	 * Hold the database object
	 */
	private FirebaseDatabase db;
	/**
	 * This is the current phone's number
	 */
	private String number;
	/**
	 * The user account credentials
	 */
	private String username, password;
	/**
	 * This is to check if we have the phone number permissions
	 */
	private PermissionManager permissionManager;

	@Override
	@SuppressLint({ "MissingPermission", "HardwareIds" })
	public void onCreate()
	{
		super.onCreate();
		permissionManager = new PermissionManager(this);
		db = FirebaseDatabase.getInstance();

		// This is personal save data for an app
		// Checking if there is a username and password saved on the app
		SharedPreferences pf = getSharedPreferences("fs", Context.MODE_PRIVATE);
		username = pf.getString("username", null);
		password = pf.getString("password", null);

		// Get the phone number
		if (permissionManager.hasNumberPermission())
		{
			StringBuilder n = new StringBuilder();
			TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			if (tMgr != null)
			{
				// Retrieve the number
				String number = tMgr.getLine1Number();
				number = number == null ? "" : number;

				// Trim the number down to only digits
				// So "+1 (718) 555-555" will give us
				// "1718555555" for the database
				for(int i = 0; i < number.length(); i++)
				{
					char c = number.charAt(i);
					if(Character.isDigit(c))
					{
						n.append(c);
					}
				}
			}
			this.number = n.toString();
		}
		else
		{
			this.number = null;
		}
	}

	@Override
	public int onStartCommand(final Intent intent, int flags, int startId)
	{
		Toast.makeText(this, "Started Listening", Toast.LENGTH_SHORT).show();


		// If we have a number
		if(number != null && !number.isEmpty())
		{
			Log.w("com.next.groupmeal", "Added Listener");
			// Get the database path we want to listen to
			DatabaseReference ref = db.getReference(number);
			// If something changes in our database, this will catch that
			ref.addChildEventListener(new ChildEventListener()
			{
				@Override
				public void onChildAdded(DataSnapshot dataSnapshot, String s)
				{
					// Convert the new info added to the DB into a GroupInvite object
					final GroupInvite invite = dataSnapshot.getValue(GroupInvite.class);
					if(invite != null)
					{
						// Retrieve the reference to the group we were invited to using the ID
						DatabaseReference ref = db.getReference("groups").child(invite.groupID);
						ref.runTransaction(new Transaction.Handler()
						{
							@Override
							public Transaction.Result doTransaction(MutableData mutableData)
							{
								// Convert from the DB to a GroupWrap object
								GroupWrap group = mutableData.getValue(GroupWrap.class);
								if(group != null)
								{
									// Set the group's id, this isn't read from the database
									group.groupID = invite.groupID;
									// Send a notification to the user's phone
									newMessage(group, invite.from);
								}
								return Transaction.success(mutableData);
							}

							@Override
							public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot)
							{

							}
						});
					}
					// Once we get something, we want to remove it
					dataSnapshot.getRef().removeValue();
				}

				@Override
				public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

				@Override
				public void onChildRemoved(DataSnapshot dataSnapshot) {}

				@Override
				public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

				@Override
				public void onCancelled(DatabaseError databaseError) {}
			});
			// This tells Android that if our Service is closed or stopped, we want to restart then
			return START_STICKY;
		}
		else
		{
			// This means that we're done, the Service can be discarded once closed or stopped
			stopSelf();
			return START_NOT_STICKY;
		}
	}

	public void newMessage(GroupWrap group, String from)
	{
		// Create a new Notification Builder
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "groupmealnotify");

		// Setting the look of the notification, title, content, icon...
		mBuilder.setSmallIcon(R.mipmap.ic_launcher);
		mBuilder.setContentTitle("New Groupmeal Invite");
		mBuilder.setContentText("Join " + group.groupName + "\nFrom " + from);
		mBuilder.setAutoCancel(true);

		// Create an Intent with the new activity
		Intent intent = new Intent(this, InviteActivity.class);
		intent.putExtra("groupID", group.groupID);

		// Convert the intent to a pending intent so we can pass it to the notification
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		mBuilder.setContentIntent(pi);

		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// Saw this in the online docs, too scared to remove it
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
		{
			NotificationChannel channel = new NotificationChannel("groupmealnotify", "Groupmeal Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
			mNotificationManager.createNotificationChannel(channel);
		}

		// Build and send the notification to the Android System
		mNotificationManager.notify("com.next.groupmeal".hashCode(), mBuilder.build());
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onDestroy()
	{
		Toast.makeText(this, "Stopped Listening", Toast.LENGTH_SHORT).show();
	}

	public static class GroupInvite
	{
		public String groupID;
		public String from;

		public GroupInvite() {}

		public GroupInvite(String groupID, String from)
		{
			this.groupID = groupID;
			this.from = from;
		}

		@Override
		public String toString()
		{
			return groupID + "[" + from + "]";
		}
	}
}
