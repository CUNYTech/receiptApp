package com.next.groupmeal;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
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
		if (!permissionManager.hasNumberPermission())
		{
			return;
		}
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
		Log.d("groupmeal", "--------------------------------------------------------------------------------------");
		Log.d("groupmeal", "--------------------------------------------------------------------------------------");
		Log.d("groupmeal", "Got Number: \"" + this.number + "\"");
		Log.d("groupmeal", "--------------------------------------------------------------------------------------");
		Log.d("groupmeal", "--------------------------------------------------------------------------------------");
	}

	@Override
	public int onStartCommand(final Intent intent, int flags, int startId)
	{
		Toast.makeText(this, "Started Listening", Toast.LENGTH_SHORT).show();

		if(number != null && !number.isEmpty() && username != null && password != null && !username.isEmpty() && !password.isEmpty())
		{
			DatabaseReference ref = db.getReference(number);
			ref.addChildEventListener(new ChildEventListener()
			{
				@Override
				public void onChildAdded(DataSnapshot dataSnapshot, String s)
				{
					GroupInvite invite = dataSnapshot.getValue(GroupInvite.class);
					if(invite != null)
					{
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
		final Notification.Builder bd = new Notification.Builder(this);
		bd.setSmallIcon(android.R.drawable.ic_dialog_info);
		int dfs = 0;
		dfs = dfs | Notification.DEFAULT_SOUND;
		dfs = dfs | Notification.DEFAULT_VIBRATE;
		bd.setDefaults(dfs);
		bd.setAutoCancel(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			bd.setColor(0xFFC0E1F3);
		}
		bd.setLights(0xFFC0E1F3, 2000, 2000);
		bd.setOngoing(false);
		bd.setContentText("Join " + invite.groupName + "\nFrom " + invite.from);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH)
		{
			bd.setSortKey("com.next.groupmeal");
		}
		bd.setContentTitle("New Groupmeal Invite!");
		Notification n = bd.build();
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		if (nm != null)
		{
			nm.notify(invite.from.hashCode(), n);
		}
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
		private String groupName;
		private String from;

		public GroupInvite()
		{
			groupName = from = null;
		}

		public GroupInvite(String groupName, String from)
		{
			this.groupName = groupName;
			this.from = from;
		}
	}
}
