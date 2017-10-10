package com.phuctran.makeabakingapp.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.phuctran.makeabakingapp.R;
import com.phuctran.makeabakingapp.ui.fragments.BaseFragment;
import com.phuctran.makeabakingapp.utils.Toaster;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by phuctran on 9/18/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseFragment.BaseFragmentResponder{
    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());
        updateFollowingViewBinding(savedInstanceState);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
    }

    protected abstract void updateFollowingViewBinding(Bundle savedInstanceState);

    protected abstract int getLayoutResource();

    public void showFragment(BaseFragment fragment, String fragmentTag) {
        Log.w(TAG, "showFragment - DEFAULT implementation called in BaseActivity");
        FragmentTransaction fragmenttransaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmenttransaction.replace(R.id.fragmentContainer, fragment, fragmentTag);
        fragmenttransaction.addToBackStack(fragmentTag);
        fragmenttransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int iBackStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, (new StringBuilder()).append("onBackPressed - backStackEntryCount is: ").append(iBackStackEntryCount).toString());
        if (iBackStackEntryCount <= 0) finish();

    }

    protected void showToast(int resourceText) {
        showToast(getResources().getString(resourceText), Toaster.Duration.LONG);
    }

    protected void showToast(int resourceText, Toaster.Duration duration) {
        showToast(getResources().getString(resourceText), duration);
    }

    protected void showToast(String text) {
        showToast(text, Toaster.Duration.LONG);
    }

    protected void showToast(String text, Toaster.Duration duration) {
        Toaster.showToast(this, text, duration);
    }

    public void setActionBarTitle(CharSequence charsequence) {
        if (charsequence == null) {
            Log.w(TAG, "setActionBarTitle - title is null, so defaulting to full_app_name.");
            charsequence = getString(R.string.app_name);
        }
        getSupportActionBar().setTitle(charsequence);
    }

    public void setActionBarTitle(int actionBarTitle) {
        setActionBarTitle(getResources().getString(actionBarTitle));
    }

    public void setActionBarTitle(String actionBarTitle) {
        if (actionBarTitle == null) {
            Log.w(TAG, "setActionBarTitle - title is null, so defaulting to full_app_name.");
            actionBarTitle = getString(R.string.app_name);
        }
        getSupportActionBar().setTitle(actionBarTitle);
    }

}
