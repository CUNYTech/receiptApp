package com.next.groupmeal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionManager
{
	/**
	 * The current context, this might be a Service, or an Activity, and cannot be null
	 */
	private final Context context;
	/**
	 * The current Activity, this is the Activity that instantiated this,
	 * and can be null if this was initialized from a Service. Since
	 * a Service cannot be an Activity but is a Context.
	 */
	private final Activity activity;

	/**
	 * Initialize a new PermissionManager provided an Activity. This is
	 * so that if a Permission isn't there, it can be requested from the
	 * user.
	 *
	 * @param activity The Activity using the permission manager, usually
	 *                    "this"
	 */
	public PermissionManager(Activity activity)
	{
		this.context = activity;
		this.activity = activity;
	}

	/**
	 * Initialize a new PermissionManager provided a Context, instead of
	 * an Activity. This will set the Activity to null if the context
	 * provided is not an Activity.
	 *
	 * @param context
	 */
	public PermissionManager(Context context)
	{
		this.context = context;
		this.activity = context instanceof Activity ? (Activity) context : null;
	}

	/**
	 * This returns whether our application has the "READ CONTACTS"
	 * permission already granted or not.
	 */
	public boolean hasContactPermission()
	{
		return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
	}

	/**
	 * This returns whether our application has the "READ SMS" and
	 * "READ PHONE STATE" permissions already granted or not.
	 */
	public boolean hasNumberPermission()
	{
		return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
				ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
	}

	/**
	 * This method attempts to request the permission from the user. Only
	 * if the user gave an Activity to create this PermissionHandler.
	 */
	public void askContactPermission()
	{
		if(!hasContactPermission() && activity != null)
		{
			ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.READ_CONTACTS }, REQUEST_READ_CONTACTS);
		}
	}

	/**
	 * This is callback from the Activity that should redirect to here to
	 * handle the response from the user to whether they accepted or
	 * denied the permission
	 */
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
