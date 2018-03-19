package com.next.groupmeal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionManager
{
	private final Context context;
	private final Activity activity;

	public PermissionManager(Activity activity)
	{
		this.context = activity;
		this.activity = activity;
	}

	public PermissionManager(Context context)
	{
		this.context = context;
		this.activity = null;
	}

	public boolean hasContactPermission()
	{
		return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
	}

	public boolean hasNumberPermission()
	{
		return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
				ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
	}

	public void askContactPermission()
	{
		if(!hasContactPermission() && activity != null)
		{
			ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.READ_CONTACTS }, REQUEST_READ_CONTACTS);
		}
	}

	public void onRequestPermissionResult(int requestCode, String permissions[], int[] grantResults)
	{
		switch (requestCode)
		{
			case REQUEST_READ_CONTACTS:
			{
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					System.out.println("Permission for Reading the Contacts was granted!");
				}
			}
		}
	}

	private static final int REQUEST_READ_CONTACTS = 0xB00B;
}
