package id.web.jagungbakar.lollipos.ui;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import id.web.jagungbakar.lollipos.R;
import id.web.jagungbakar.lollipos.ui.dashboard.DashboardFragment;
import id.web.jagungbakar.lollipos.ui.dashboard.ReportFragment;

public class DashboardActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loading the default fragment
        loadFragment(new DashboardFragment());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.title_home));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(getResources().getDrawable(R.drawable.ic_launcher));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new DashboardFragment();
                actionBar.setTitle(getResources().getString(R.string.title_home));
                break;

            case R.id.navigation_transaction:
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
                break;

            case R.id.navigation_reports:
                fragment = new ReportFragment();
                actionBar.setTitle(getResources().getString(R.string.title_reports));
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.navigation_home);
        item.setVisible(false);

        return true;
    }
}
