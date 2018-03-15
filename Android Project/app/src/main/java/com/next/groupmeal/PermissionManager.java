package com.next.groupmeal;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionManager
{
	private final Activity activity;

	public PermissionManager(Activity activity)
	{
		this.activity = activity;
	}

	public boolean hasReadContactPermission()
	{
		return ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
	}

	public void tryAskingPermission()
	{
		if(!hasReadContactPermission())
		{
			ActivityCompat.requestPermissions(activity, new String[] { android.Manifest.permission.READ_CONTACTS }, REQUEST_READ_CONTACTS);
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

	private static final int REQUEST_READ_CONTACTS = 0xFEEF;
}
