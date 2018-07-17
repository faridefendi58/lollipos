package id.web.jagungbakar.lollipos.ui;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import id.web.jagungbakar.lollipos.R;
import id.web.jagungbakar.lollipos.domain.LanguageController;
import id.web.jagungbakar.lollipos.domain.customer.Customer;
import id.web.jagungbakar.lollipos.domain.customer.CustomerCatalog;
import id.web.jagungbakar.lollipos.domain.customer.CustomerService;
import id.web.jagungbakar.lollipos.domain.inventory.Inventory;
import id.web.jagungbakar.lollipos.domain.inventory.Product;
import id.web.jagungbakar.lollipos.domain.inventory.ProductCatalog;
import id.web.jagungbakar.lollipos.techicalservices.NoDaoSetException;
import id.web.jagungbakar.lollipos.ui.component.UpdatableFragment;
import id.web.jagungbakar.lollipos.ui.customer.CustomerDetailActivity;
import id.web.jagungbakar.lollipos.ui.inventory.InventoryFragment;
import id.web.jagungbakar.lollipos.ui.inventory.ProductDetailActivity;
import id.web.jagungbakar.lollipos.ui.sale.ReportFragment;
import id.web.jagungbakar.lollipos.ui.sale.SaleFragment;
import id.web.jagungbakar.lollipos.ui.customer.CustomerFragment;

/**
 * This UI loads 3 main pages (Inventory, Sale, Report)
 * Makes the UI flow by slide through pages using ViewPager.
 * 
 * @author Refresh Team
 *
 */
@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {

	private ViewPager viewPager;
	private ProductCatalog productCatalog;
	private CustomerCatalog customerCatalog;
	private String productId;
	private Product product;
	private String customerId;
	private Customer customer;
	private static boolean SDK_SUPPORTED;
	private PagerAdapter pagerAdapter;
	private Resources res;

	@SuppressLint("NewApi")
	/**
	 * Initiate this UI.
	 */
	private void initiateActionBar() {
		if (SDK_SUPPORTED) {
			ActionBar actionBar = getActionBar();
			
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

			ActionBar.TabListener tabListener = new ActionBar.TabListener() {
				@Override
				public void onTabReselected(Tab tab, FragmentTransaction ft) {
				}

				@Override
				public void onTabSelected(Tab tab, FragmentTransaction ft) {
					viewPager.setCurrentItem(tab.getPosition());
				}

				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				}
			};
			actionBar.addTab(actionBar.newTab().setText(res.getString(R.string.inventory))
					.setTabListener(tabListener), 0, false);
			actionBar.addTab(actionBar.newTab().setText(res.getString(R.string.sale))
					.setTabListener(tabListener), 1, true);
			actionBar.addTab(actionBar.newTab().setText(res.getString(R.string.report))
					.setTabListener(tabListener), 2, false);
			actionBar.addTab(actionBar.newTab().setText(res.getString(R.string.customer))
					.setTabListener(tabListener), 3, false);
	
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color
						.parseColor("#73bde5")));
			}

		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		res = getResources();
		setContentView(R.layout.layout_main);
		viewPager = (ViewPager) findViewById(R.id.pager);
		super.onCreate(savedInstanceState);
		SDK_SUPPORTED = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
		initiateActionBar();
		FragmentManager fragmentManager = getSupportFragmentManager();
		pagerAdapter = new PagerAdapter(fragmentManager, res);
		viewPager.setAdapter(pagerAdapter);
		viewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						if (SDK_SUPPORTED)
							getActionBar().setSelectedNavigationItem(position);
					}
				});
		viewPager.setCurrentItem(1);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			openQuitDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Open quit dialog.
	 */
	private void openQuitDialog() {
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(
				MainActivity.this);
		quitDialog.setTitle(res.getString(R.string.dialog_quit));
		quitDialog.setPositiveButton(res.getString(R.string.quit), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});

		quitDialog.setNegativeButton(res.getString(R.string.no), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		quitDialog.show();
	}

	/**
	 * Option on-click handler.
	 * @param view
	 */
	public void optionOnClickHandler(View view) {
		viewPager.setCurrentItem(0);
		String id = view.getTag().toString();
		productId = id;
		try {
			productCatalog = Inventory.getInstance().getProductCatalog();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}
		product = productCatalog.getProductById(Integer.parseInt(productId));
		openDetailDialog();

	}

	/**
	 * Open detail dialog.
	 */
	private void openDetailDialog() {
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(MainActivity.this);
		quitDialog.setTitle(product.getName());
		quitDialog.setPositiveButton(res.getString(R.string.remove), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				openRemoveDialog();
			}
		});

		quitDialog.setNegativeButton(res.getString(R.string.product_detail), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent newActivity = new Intent(MainActivity.this,
						ProductDetailActivity.class);
				newActivity.putExtra("id", productId);
				startActivity(newActivity);
			}
		});

		quitDialog.show();
	}

	/**
	 * Open remove dialog.
	 */
	private void openRemoveDialog() {
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(
				MainActivity.this);
		quitDialog.setTitle(res.getString(R.string.dialog_remove_product));
		quitDialog.setPositiveButton(res.getString(R.string.no), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		quitDialog.setNegativeButton(res.getString(R.string.remove), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				productCatalog.suspendProduct(product);
				pagerAdapter.update(0);
			}
		});

		quitDialog.show();
	}

	/**
	 * Get view-pager
	 * @return
	 */
	public ViewPager getViewPager() {
		return viewPager;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lang_en:
            	setLanguage("en");
                return true;
            case R.id.lang_id:
            	setLanguage("in");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	/**
	 * Set language
	 * @param localeString
	 */
	private void setLanguage(String localeString) {
		Locale locale = new Locale(localeString);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		LanguageController.getInstance().setLanguage(localeString);

		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());

		//Log.e(MainActivity.class.getSimpleName(), "List Lang : "+ CurrencyController.getInstance().getCurrencies().toString());
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}

	public void optionOnClickHandlerCustomer(View view) {
		viewPager.setCurrentItem(3);
		String id = view.getTag().toString();
		customerId = id;
		try {
			customerCatalog = CustomerService.getInstance().getCustomerCatalog();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}
		customer = customerCatalog.getCustomerById(Integer.parseInt(customerId));
		openDetailCustomerDialog();
	}

	private void openDetailCustomerDialog() {
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(MainActivity.this);
		quitDialog.setTitle(customer.getName());
		quitDialog.setPositiveButton(res.getString(R.string.remove), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				//openRemoveDialog();
			}
		});

		quitDialog.setNegativeButton(res.getString(R.string.product_detail), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent newActivity = new Intent(MainActivity.this,
						CustomerDetailActivity.class);
				newActivity.putExtra("id", customerId);
				startActivity(newActivity);
			}
		});

		quitDialog.show();
	}
}

/**
 * 
 * @author Refresh team
 *
 */
class PagerAdapter extends FragmentStatePagerAdapter {

	private UpdatableFragment[] fragments;
	private String[] fragmentNames;

	/**
	 * Construct a new PagerAdapter.
	 * @param fragmentManager
	 * @param res
	 */
	public PagerAdapter(FragmentManager fragmentManager, Resources res) {
		
		super(fragmentManager);
		
		UpdatableFragment reportFragment = new ReportFragment();
		UpdatableFragment saleFragment = new SaleFragment(reportFragment);
		UpdatableFragment inventoryFragment = new InventoryFragment(
				saleFragment);
		UpdatableFragment customerFragment = new CustomerFragment(saleFragment);

		fragments = new UpdatableFragment[] { inventoryFragment, saleFragment,
				reportFragment, customerFragment };
		fragmentNames = new String[] { res.getString(R.string.inventory),
				res.getString(R.string.sale),
				res.getString(R.string.report),
				res.getString(R.string.customer)
		};

	}

	@Override
	public Fragment getItem(int i) {
		return fragments[i];
	}

	@Override
	public int getCount() {
		return fragments.length;
	}

	@Override
	public CharSequence getPageTitle(int i) {
		return fragmentNames[i];
	}

	/**
	 * Update
	 * @param index
	 */
	public void update(int index) {
		fragments[index].update();
	}

}