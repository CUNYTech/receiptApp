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

public class ListenerService extends Service
{
	private FirebaseDatabase db;
	private String number;
	private String username, password;
	private PermissionManager permissionManager;

	@Override
	@SuppressLint({ "MissingPermission", "HardwareIds" })
	public void onCreate()
	{
		super.onCreate();
		permissionManager = new PermissionManager(this);
		db = FirebaseDatabase.getInstance();
		SharedPreferences pf = getSharedPreferences("fs", Context.MODE_PRIVATE);
		username = pf.getString("username", null);
		password = pf.getString("password", null);
		if (permissionManager.hasNumberPermission())
		{
			StringBuilder n = new StringBuilder();
			TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			if (tMgr != null)
			{
				String number = tMgr.getLine1Number();
				number = number == null ? "" : number;

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
		Log.w("com.next.groupmeal", "--------------------------------------------------------------------------------------");
		Log.w("com.next.groupmeal", "--------------------------------------------------------------------------------------");
		Log.w("com.next.groupmeal", "Got Number: \"" + this.number + "\"");
		Log.w("com.next.groupmeal", "--------------------------------------------------------------------------------------");
		Log.w("com.next.groupmeal", "--------------------------------------------------------------------------------------");
	}

	@Override
	public int onStartCommand(final Intent intent, int flags, int startId)
	{
		Toast.makeText(this, "Started Listening", Toast.LENGTH_SHORT).show();

		if(number != null && !number.isEmpty())
		{
			Log.w("com.next.groupmeal", "Added Listener");
			DatabaseReference ref = db.getReference(number);
			ref.addChildEventListener(new ChildEventListener()
			{
				@Override
				public void onChildAdded(DataSnapshot dataSnapshot, String s)
				{
					GroupInvite invite = dataSnapshot.getValue(GroupInvite.class);
					if(invite != null)
					{
						Log.w("com.next.groupmeal", "--------------------------------------------------------------------------------------");
						Log.w("com.next.groupmeal", String.valueOf(invite));
						Log.w("com.next.groupmeal", "--------------------------------------------------------------------------------------");
						newMessage(invite);
					}
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
		}

		return START_STICKY;
	}

	public void newMessage(GroupInvite invite)
	{
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
		mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
		mBuilder.setContentTitle("New Groupmeal Invite");
		mBuilder.setContentText("Join " + invite.groupName + "\nFrom " + invite.from);
		mBuilder.setPriority(Notification.PRIORITY_MAX);

		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
		{
			NotificationChannel channel = new NotificationChannel("notify_001", "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
			mNotificationManager.createNotificationChannel(channel);
		}

		mNotificationManager.notify(0, mBuilder.build());
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
		public String groupName;
		public String from;

		public GroupInvite()
		{
			groupName = from = null;
		}

		public GroupInvite(String groupName, String from)
		{
			this.groupName = groupName;
			this.from = from;
		}

		@Override
		public String toString()
		{
			return groupName + "[" + from + "]";
		}
	}
}
