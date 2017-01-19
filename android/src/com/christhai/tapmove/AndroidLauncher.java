package com.christhai.tapmove;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.christhai.tapmove.util.IabHelper;
import com.christhai.tapmove.util.IabResult;
import com.christhai.tapmove.util.Inventory;
import com.christhai.tapmove.util.Purchase;
import com.mygdx.game.MyGame;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mygdx.handler.AdHandler;
import com.mygdx.handler.BillingHandler;
import com.mygdx.handler.ReturnBillingHandler;

public class AndroidLauncher extends AndroidApplication implements AdHandler,BillingHandler{

	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;
	protected AdView adView;

	private final int BUY_MONEY = 2;
	private final int BUY_ADS = 3;
	IabHelper mHelper;
	private final String SKU_ADS = "com.christhai.tapmove.ads";
	private final String SKU_MONEY = "com.christhai.tapmove.money.1000";
	ReturnBillingHandler returnBillingHandler;

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

		MyGame game = new MyGame(this,this);
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

		// Hook it all up
		setContentView(layout);

		//-----------------------------------------------BILLING---------------------------------//
		//-----------Establish mHelper------------------------//

		String base64EncodedPublicKey = "";
		//Creates an instance
		mHelper = new IabHelper(this,base64EncodedPublicKey);
		mHelper.enableDebugLogging(true);
		//Does its setup and outputs error if any
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				if (!result.isSuccess())
					Gdx.app.log("taplog", "Problem setting up In-app Billing: " + result);
				if(mHelper==null)
					return;

				try {
					mHelper.queryInventoryAsync(mQueryFinishedListener);
				} catch (IabHelper.IabAsyncInProgressException e) {
					Gdx.app.log("taplog","Error querying inventory. Another async operation in progress.");
				}
			}
		});
	}



	//---------------Consumes Purchase----------------------------------------------//
	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
			new IabHelper.OnConsumeFinishedListener() {
				public void onConsumeFinished(Purchase purchase, IabResult result) {
					if (result.isSuccess()) {
						returnPurchaseMoneyRequest();
					}
					else {
						Gdx.app.log("taplog","Could not consume purchase.");
					}
				}
			};

	//------------------Checks what items have been purchased already-----------------//

	IabHelper.QueryInventoryFinishedListener mQueryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result, Inventory inventory)
		{
			if (result.isFailure()) {
				Gdx.app.log("taplog", "Could not get Google Play Billing stuff");
			}else {
				returnPurchaseAdsRequest(inventory.hasPurchase(SKU_ADS));

				Purchase purchase = inventory.getPurchase(SKU_MONEY);
				if (purchase != null) {
					try {
						mHelper.consumeAsync(inventory.getPurchase(SKU_MONEY), mConsumeFinishedListener);
					} catch (IabHelper.IabAsyncInProgressException e) {
						Gdx.app.log("taplog","Error consuming gas. Another async operation in progress.");
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
			if (result.isFailure()) {
				Gdx.app.log("taplog", "Error purchasing: " + result);
				return;
			}else if (purchase.getSku().equals(SKU_MONEY)) {
				try {
					mHelper.consumeAsync(purchase,mConsumeFinishedListener);
				} catch (IabHelper.IabAsyncInProgressException e) {
					Gdx.app.log("taplog","Error consuming gas. Another async operation in progress.");
					return;
				}
			}else if (purchase.getSku().equals(SKU_ADS)) {
				returnPurchaseAdsRequest(true);
			}
		}
	};


	private void purchaseMoney(){
		try{
			mHelper.launchPurchaseFlow(this, SKU_MONEY, 92602, mPurchaseFinishedListener, "ThisIsAPurchaseRequestFromTapMove!");
		}catch(Exception e){
			Gdx.app.log("taplog","Could not send purchase request");
		}
	}

	private void purchaseAds(){
		try{
			mHelper.launchPurchaseFlow(this, SKU_ADS, 92602, mPurchaseFinishedListener, "ThisIsAPurchaseRequestFromTapMove");
		}catch(Exception e){
			Gdx.app.log("taplog","Could not send purchase request");
		}
	}

	private void returnPurchaseMoneyRequest(){
		returnBillingHandler.returnBuyMoney();
	}

	private void returnPurchaseAdsRequest(boolean bought){
		returnBillingHandler.returnBuyAds(bought);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mHelper != null){
			try{
				mHelper.dispose();
			}catch(Exception e){
				Gdx.app.log("taplog","Could not destroy mHelper");
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
}
