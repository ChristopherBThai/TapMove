package com.christhai.tapmove;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.christhai.tapmove.basegameutil.BaseGameUtils;
import com.christhai.tapmove.util.IabHelper;
import com.christhai.tapmove.util.IabResult;
import com.christhai.tapmove.util.Inventory;
import com.christhai.tapmove.util.Purchase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.mygdx.game.MyGame;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mygdx.handler.AdHandler;
import com.mygdx.handler.BillingHandler;
import com.mygdx.handler.GooglePlayHandler;
import com.mygdx.handler.ReturnBillingHandler;
import com.mygdx.ui.menu.extra.actors.SignInButton;

public class AndroidLauncher extends AndroidApplication implements AdHandler,BillingHandler,GooglePlayHandler, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;
	protected AdView adView;

	private final int BUY_MONEY = 2;
	private final int BUY_ADS = 3;
	IabHelper mHelper;
	private final String SKU_ADS = "com.christhai.tapmove.ads";
	private final String SKU_MONEY = "com.christhai.tapmove.money.1000";
	ReturnBillingHandler returnBillingHandler;

	private final int SIGN_IN = 4;
	private final int SIGN_OUT = 5;
	private static int RC_SIGN_IN = 9001;
	private static int REQUEST_ACHIEVEMENTS = 9002;
	private GoogleApiClient mGoogleApiClient;
	private boolean mResolvingConnectionFailure = false;
	private boolean mSignInClicked = false;
	private boolean mAutoStartSignInFlow = true;

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch(msg.what){
				case SHOW_ADS:
					adView.setVisibility(View.VISIBLE);
					break;
				case HIDE_ADS:
					adView.setVisibility(View.GONE);
					break;
				case BUY_ADS:
					purchaseAds();
					break;
				case BUY_MONEY:
					purchaseMoney();
					break;
				case SIGN_IN:
					mSignInClicked = true;
					mGoogleApiClient.connect();
					break;
				case SIGN_OUT:
					mSignInClicked = false;
					Games.signOut(mGoogleApiClient);
					mGoogleApiClient.disconnect();
					signedOut();
					break;
			}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		RelativeLayout layout = new RelativeLayout(this);


		//--------------------------------------------------ADS---------------------------------//

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		MyGame game = new MyGame(this,this,this);
		View gameView = initializeForView(game, config);
		returnBillingHandler = game;

		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-6489584930108780/7640569894");
		adView.loadAd(new AdRequest.Builder().addTestDevice("1272AC79A0100ABFC17929DD9B2EBA63").build());

		layout.addView(gameView);

		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		layout.addView(adView, adParams);

		//---------------------------------------------Google Play Game Services -----------------------//


		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(Games.API).addScope(Games.SCOPE_GAMES)
				.build();

		// Hook it all up
		setContentView(layout);

		//-----------------------------------------------BILLING---------------------------------//
		//-----------Establish mHelper------------------------//

		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnkHkSu1Y6NSgk9SHEkg/X6Gt6B/d4msRSX3iEKL+28ickjceVaqdJhg0dgVkyQRhXp+FP/jXbcyePyzFYhHumJV+75Js5QsWEfxwz2ChePW9kTy2vSCs/qt5CNut6mzgdDW50g9d4GI339a28MBXWOK5Y63Exbq278VS6bPHs6Z92dIoTL77NpyiPjN1R6OqyyjlL4t+1I/zugH7v57HvgICkrlzUjKKszkv/k46w43EMPR7W+kcho0wOME3NFDOGwQY7dWDRGYDlHCbrYrnT0w0hbEfKLD0oU3rK82kVMBIK6zHx9FKbUfbohxOYoFrQ4pyz+LeEtPVNLybaayrKQIDAQAB";
		//Creates an instance
		mHelper = new IabHelper(this,base64EncodedPublicKey);
		mHelper.enableDebugLogging(true);
		//Does its setup and outputs error if any
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				if (!result.isSuccess())
					error("Problem setting up In-app Billing: " + result);
				if(mHelper==null)
					return;

				try {
					mHelper.queryInventoryAsync(mQueryFinishedListener);
				} catch (IabHelper.IabAsyncInProgressException e) {
					error("Error querying inventory. Another async operation in progress.");
				}
			}
		});
	}



	//---------------Consumes Purchase----------------------------------------------//
	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
			new IabHelper.OnConsumeFinishedListener() {
				public void onConsumeFinished(Purchase purchase, IabResult result) {
					log("Consuming...");
					if (result.isSuccess()) {
						log("Consuming success!");
						returnPurchaseMoneyRequest();
					}
					else {
						error("Error Consuming: "+result);
					}
				}
			};

	//------------------Checks what items have been purchased already-----------------//

	IabHelper.QueryInventoryFinishedListener mQueryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result, Inventory inventory)
		{
			if (result.isFailure()) {
				error("Error"+result);
			}else {
				if(inventory.hasPurchase(SKU_ADS))
					returnPurchaseAdsRequest(true);

				Purchase purchase = inventory.getPurchase(SKU_MONEY);
				if (purchase != null) {
					try {
						mHelper.consumeAsync(inventory.getPurchase(SKU_MONEY), mConsumeFinishedListener);
					} catch (IabHelper.IabAsyncInProgressException e) {
						error("Error purchasing. Another async operation in progress.");
					}
					return;
				}
			}
		}
	};

	//-----------------What happens when you purchase---------------------------------//

	//Executes Purchases

	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase)
		{
			if (result.isFailure()||purchase==null||mHelper==null) {
				error("Error purchasing: "+result);
				return;
			}else if (purchase.getSku().equals(SKU_MONEY)) {
				log("Purchasing money...");
				try {
					mHelper.consumeAsync(purchase,mConsumeFinishedListener);
				} catch (IabHelper.IabAsyncInProgressException e) {
					error("Error purchasing. Another async operation in progress.");
					return;
				}
			}else if (purchase.getSku().equals(SKU_ADS)) {
				log("Purchasing Ads...");
				returnPurchaseAdsRequest(true);
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		log( "onActivityResult(" + requestCode + "," + resultCode + "," + data);
		if (mHelper == null) return;

		if(requestCode == RC_SIGN_IN){
			log("onActivityResult with requestCode == RC_SIGN_IN, responseCode="
					+ resultCode + ", intent=" + data);
			mSignInClicked = false;
			mResolvingConnectionFailure = false;
			if (resultCode == RESULT_OK) {
				mGoogleApiClient.connect();
			} else {
				error("OnActivityResult GPGS error");
				BaseGameUtils.showActivityResultError(this,requestCode,resultCode, 0);
			}
		}

		// Pass on the activity result to the helper for handling
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			// not handled, so handle it ourselves (here's where you'd
			// perform any handling of activity results not related to in-app
			// billing...
			super.onActivityResult(requestCode, resultCode, data);
		}
		else {
			log( "onActivityResult handled by IABUtil.");
		}
	}


	private void purchaseMoney(){
		log("Launching purchase flow...");
		try{
			mHelper.launchPurchaseFlow(this, SKU_MONEY, 92602, mPurchaseFinishedListener, "ThisIsAPurchaseRequestFromTapMove!");
		}catch(IabHelper.IabAsyncInProgressException e){
			error("Error launching purchase flow. Another async operation in progress.");
		}
	}

	private void purchaseAds(){
		log("Launching purchase flow...");
		try{
			mHelper.launchPurchaseFlow(this, SKU_ADS, 92602, mPurchaseFinishedListener, "ThisIsAPurchaseRequestFromTapMove");
		}catch(IabHelper.IabAsyncInProgressException e){
			error("Error launching purchase flow. Another async operation in progress.");
		}
	}

	private void returnPurchaseMoneyRequest(){
		log("Success! Adding funds!");
		returnBillingHandler.returnBuyMoney();
	}

	private void returnPurchaseAdsRequest(boolean bought){
		log("Success! Removing Ads!");
		returnBillingHandler.returnBuyAds(bought);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mHelper != null){
			try{
				mHelper.dispose();
			}catch(Exception e){
				error("Could not destroy mHelper");
			}
		}
		mHelper = null;
	}

	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

	@Override
	public void buyMoney(){
		handler.sendEmptyMessage(BUY_MONEY);
	}

	@Override
	public void buyAds(){
		handler.sendEmptyMessage(BUY_ADS);
	}

	private void log(String text){
		Gdx.app.log("TapMove",text);
	}

	private void error(String text){
		Gdx.app.error("TapMove",text);
	}

	@Override
	public void signIn(){
		log("Sign in button clicked!");
		handler.sendEmptyMessage(SIGN_IN);
	}

	@Override
	public void signOut(){
		log("Sign out button clicked!");
		handler.sendEmptyMessage(SIGN_OUT);
	}

	@Override
	public void onConnected(@Nullable Bundle bundle){
		log("Sign in successful!");
		signedIn();
	}

	@Override
	public void onConnectionSuspended(int i){
		log("Trying to reconnect.");
		mGoogleApiClient.connect();
	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult){
		error("Connection failed: "+connectionResult);

		if(mResolvingConnectionFailure){
			log("Ignoring connection failure. Already resolving.");
			return;
		}

		if(mSignInClicked || mAutoStartSignInFlow){
			mAutoStartSignInFlow = false;
			mSignInClicked = false;
			mResolvingConnectionFailure = BaseGameUtils.resolveConnectionFailure(this,mGoogleApiClient,connectionResult,RC_SIGN_IN,"Sign in other error");
		}
	}

	@Override
	protected void onStart(){
		super.onStart();
		log("Connecting to GPGS");
		mGoogleApiClient.connect();
	}

	@Override
	protected void onStop(){
		super.onStop();
		if(mGoogleApiClient.isConnected()){
			log("Disconnecting from GPGS");
			mGoogleApiClient.disconnect();
		}
	}

	private void signedIn(){
		SignInButton.signedIn();
	}

	private void signedOut(){
		SignInButton.signedOut();
	}

	@Override
	protected void attachBaseContext(Context base){
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	@Override
	public void achieve(String id){
		if(mGoogleApiClient!=null&&mGoogleApiClient.isConnected()){
			Games.Achievements.unlock(mGoogleApiClient,id);
		}else{
			log("Achievement not earned (id: "+id+")");
		}
	}

	@Override
	public void incAchieve(String id, int amount){
		if(mGoogleApiClient!=null&&mGoogleApiClient.isConnected()){
			Games.Achievements.increment(mGoogleApiClient,id,amount);
		}else{
			log("Achievement not incremented (id: "+id+")");
		}
	}

	@Override
	public void displayAchievements(){
		startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient),REQUEST_ACHIEVEMENTS);
	}
}
