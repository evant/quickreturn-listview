package me.tatarka.sample;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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
            getActionBar().setCustomView(R.layout.options);

            final CheckBox mAnimate = (CheckBox) getActionBar().getCustomView().findViewById(R.id.animate);
            final CheckBox mLock = (CheckBox) getActionBar().getCustomView().findViewById(R.id.lock);

            mAnimate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    quickReturnLayout.setAnimate(checked);
                }
            });

            mLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    quickReturnLayout.setLocked(checked);
                }
            });
        } else {
            quickReturnLayout.setAnimate(true);
        }
    }
}
