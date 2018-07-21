package id.web.jagungbakar.lollipos.ui.params;

import android.app.Activity;
import android.os.Bundle;

import id.web.jagungbakar.lollipos.R;

public class ParamsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initiateUI();
    }

    private void initiateUI() {
        setContentView(R.layout.layout_splashscreen);
    }
}
