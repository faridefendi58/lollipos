package id.web.jagungbakar.lollipos.ui;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import id.web.jagungbakar.lollipos.R;
import id.web.jagungbakar.lollipos.domain.CurrencyController;
import id.web.jagungbakar.lollipos.domain.DateTimeStrategy;
import id.web.jagungbakar.lollipos.domain.LanguageController;
import id.web.jagungbakar.lollipos.domain.inventory.Inventory;
import id.web.jagungbakar.lollipos.domain.sale.Register;
import id.web.jagungbakar.lollipos.domain.sale.SaleLedger;
import id.web.jagungbakar.lollipos.techicalservices.AndroidDatabase;
import id.web.jagungbakar.lollipos.techicalservices.Database;
import id.web.jagungbakar.lollipos.techicalservices.DatabaseExecutor;
import id.web.jagungbakar.lollipos.techicalservices.inventory.InventoryDao;
import id.web.jagungbakar.lollipos.techicalservices.inventory.InventoryDaoAndroid;
import id.web.jagungbakar.lollipos.techicalservices.sale.SaleDao;
import id.web.jagungbakar.lollipos.techicalservices.sale.SaleDaoAndroid;

/**
 * This is the first activity page, core-app and database created here.
 * Dependency injection happens here.
 * 
 * @author Refresh Team
 * 
 */
public class SplashScreenActivity extends Activity {

	public static final String POS_VERSION = "LolliPOS 1.0";
	private static final long SPLASH_TIMEOUT = 2000;
	private Button goButton;
	private boolean gone;
	
	/**
	 * Loads database and DAO.
	 */
	private void initiateCoreApp() {
		Database database = new AndroidDatabase(this);
		InventoryDao inventoryDao = new InventoryDaoAndroid(database);
		SaleDao saleDao = new SaleDaoAndroid(database);
		DatabaseExecutor.setDatabase(database);
		LanguageController.setDatabase(database);
		CurrencyController.setDatabase(database);

		Inventory.setInventoryDao(inventoryDao);
		Register.setSaleDao(saleDao);
		SaleLedger.setSaleDao(saleDao);
		
		DateTimeStrategy.setLocale("id", "ID");
		setLanguage(LanguageController.getInstance().getLanguage());
		CurrencyController.setCurrency("idr");

		Log.d("Core App", "INITIATE");
	}
	
	/**
	 * Set language.
	 * @param localeString
	 */
	private void setLanguage(String localeString) {
		Locale locale = new Locale(localeString);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		initiateUI(savedInstanceState);
		initiateCoreApp();
	}
	
	/**
	 * Go.
	 */
	private void go() {
		gone = true;
		Intent newActivity = new Intent(SplashScreenActivity.this,
				MainActivity.class);
		startActivity(newActivity);
		SplashScreenActivity.this.finish();	
	}

	private ProgressBar progressBar;
	private int progressStatus = 0;
	private Handler handler = new Handler();

	/**
	 * Initiate this UI.
	 * @param savedInstanceState
	 */
	private void initiateUI(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splashscreen);
		goButton = (Button) findViewById(R.id.goButton);
		goButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				go();
			}

		});

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		// Start long running operation in a background thread
		new Thread(new Runnable() {
			public void run() {
				while (progressStatus < 100) {
					progressStatus += 5;
					// Update the progress bar and display the
					//current value in the text view
					handler.post(new Runnable() {
						public void run() {
							progressBar.setProgress(progressStatus);
						}
					});
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (!gone) go();
			}
		}, SPLASH_TIMEOUT);
	}
}