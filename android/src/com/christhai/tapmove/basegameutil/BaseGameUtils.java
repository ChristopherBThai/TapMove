package com.christhai.tapmove.basegameutil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.IntentSender;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.GamesActivityResultCodes;

public class BaseGameUtils {
	public static void showAlert(Activity activity, String message) {
		(new AlertDialog.Builder(activity)).setMessage(message)
				.setNeutralButton(android.R.string.ok, null).create().show();
	}
	public static boolean resolveConnectionFailure(Activity activity,
	                                               GoogleApiClient client, ConnectionResult result, int requestCode,
	                                               String fallbackErrorMessage) {

		if (result.hasResolution()) {
			try {
				result.startResolutionForResult(activity, requestCode);
				return true;
			} catch (IntentSender.SendIntentException e) {
				// The intent was canceled before it was sent.  Return to the default
				// state and attempt to connect to get an updated ConnectionResult.
				client.connect();
				return false;
			}
		} else {
			// not resolvable... so show an error message
			int errorCode = result.getErrorCode();
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(errorCode,
					activity, requestCode);
			if (dialog != null) {
				dialog.show();
			} else {
				// no built-in dialog: show the fallback error message
				showAlert(activity, fallbackErrorMessage);
			}
			return false;
		}
	}
	public static boolean verifySampleSetup(Activity activity, int... resIds) {
		StringBuilder problems = new StringBuilder();
		boolean problemFound = false;
		problems.append("The following set up problems were found:\n\n");

		// Did the developer forget to change the package name?
		if (activity.getPackageName().startsWith("com.google.example.games")) {
			problemFound = true;
			problems.append("- Package name cannot be com.google.*. You need to change the "
					+ "sample's package name to your own package.").append("\n");
		}

		for (int i : resIds) {
			if (activity.getString(i).toLowerCase().contains("replaceme")) {
				problemFound = true;
				problems.append("- You must replace all " +
						"placeholder IDs in the ids.xml file by your project's IDs.").append("\n");
				break;
			}
		}

		if (problemFound) {
			problems.append("\n\nThese problems may prevent the app from working properly.");
			showAlert(activity, problems.toString());
			return false;
		}

		return true;
	}
	public static void showActivityResultError(Activity activity, int requestCode, int actResp, int errorDescription) {
		if (activity == null) {
			Log.e("BaseGameUtils", "*** No Activity. Can't show failure dialog!");
			return;
		}
		Dialog errorDialog;

		switch (actResp) {
			case GamesActivityResultCodes.RESULT_APP_MISCONFIGURED:
				errorDialog = makeSimpleDialog(activity,
						"The application is incorrectly configured. Check that the package name and signing certificate match the client ID created in Developer Console. Also, if the application is not yet published, check that the account you are trying to sign in with is listed as a tester account. See logs for more information.");
				break;
			case GamesActivityResultCodes.RESULT_SIGN_IN_FAILED:
				errorDialog = makeSimpleDialog(activity,
						"Failed to sign in. Please check your network connection and try again.");
				break;
			case GamesActivityResultCodes.RESULT_LICENSE_FAILED:
				errorDialog = makeSimpleDialog(activity,
						"License check failed.");
				break;
			default:
				// No meaningful Activity response code, so generate default Google
				// Play services dialog
				final int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
				errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode,
						activity, requestCode, null);
				if (errorDialog == null) {
					// get fallback dialog
					Log.e("BaseGamesUtils",
							"No standard error dialog available. Making fallback dialog.");
					errorDialog = makeSimpleDialog(activity, activity.getString(errorDescription));
				}
		}

		errorDialog.show();
	}
	public static Dialog makeSimpleDialog(Activity activity, String text) {
		return (new AlertDialog.Builder(activity)).setMessage(text)
				.setNeutralButton(android.R.string.ok, null).create();
	}
	public static Dialog makeSimpleDialog(Activity activity, String title, String text) {
		return (new AlertDialog.Builder(activity))
				.setTitle(title)
				.setMessage(text)
				.setNeutralButton(android.R.string.ok, null)
				.create();
	}

}