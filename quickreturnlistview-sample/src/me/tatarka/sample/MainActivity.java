package me.tatarka.sample;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import me.tatarka.QuickReturnLayout;

/**
 * User: evantatarka Date: 10/1/13 Time: 9:57 AM
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final QuickReturnLayout quickReturnLayout = (QuickReturnLayout) findViewById(R.id.quick_return_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getActionBar().setDisplayShowCustomEnabled(true);
            getActionBar().setCustomView(R.layout.option_animate);

            final CheckBox mCheckbox = (CheckBox) getActionBar().getCustomView().findViewById(R.id.animate);

            mCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quickReturnLayout.setAnimate(mCheckbox.isChecked());
                }
            });
        } else {
            quickReturnLayout.setAnimate(true);
        }
    }
}
