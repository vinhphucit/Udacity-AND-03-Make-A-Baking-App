package com.phuctran.makeabakingapp.ui.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.phuctran.makeabakingapp.R;
import com.phuctran.makeabakingapp.ui.activities.RecipeDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by phuctran on 9/18/17.
 */

public abstract class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    private Unbinder unbinder;
    private FragmentTransaction fragmentTransaction;
    protected AlertDialog.Builder errorDialog;

    @Nullable
    @BindView(R.id.rl_progress)
    RelativeLayout rl_progress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(getSubclassName(), (new StringBuilder()).append("onCreateView:").append(getClass().getName()).toString());

        final View fragmentView = inflater.inflate(getLayoutResource(), container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        updateFollowingViewBinding();
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.d(getSubclassName(), (new StringBuilder()).append("onDestroyView:").append(getClass().getName()).toString());

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getSubclassName(), (new StringBuilder()).append("onCreate:").append(getClass().getName()).toString());

        errorDialog = new AlertDialog.Builder(getContext())
                .setTitle("Error")
                .setMessage("There is an error in app.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
    }

    protected void showAlertMessage(String content) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        // set title
        alertDialogBuilder.setTitle("Alert");

        // set dialog message
        alertDialogBuilder
                .setMessage(content)
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void showLoading() {
        if (rl_progress != null)
            rl_progress.setVisibility(View.VISIBLE);
    }


    public void hideLoading() {
        if (rl_progress != null)
            rl_progress.setVisibility(View.GONE);
    }

    protected void setActionBarTitle(int i) {
        setActionBarTitle(getResources().getString(i));
    }

    protected void setActionBarTitle(String actionBarTitle) {
        if (getActivity() instanceof RecipeDetailActivity) {
            ((BaseFragmentResponder) getActivity()).setActionBarTitle(actionBarTitle);
        } else {
            ActionBar actionbar = getActionBar();
            if (actionbar != null) {
                actionbar.setTitle(actionBarTitle);
            }
        }
    }

    public void showError(String message) {
        if (TextUtils.isEmpty(message))
            message = "There are some errors in application";
        errorDialog.setMessage(message);
        errorDialog.show();
    }

    protected void setActionBarTitle(CharSequence actionBarTitle) {
        if ((getActivity() instanceof RecipeDetailActivity)) {
            ((BaseFragmentResponder) getActivity()).setActionBarTitle(actionBarTitle);
        }
        if (getActionBar() != null) {
            getActionBar().setTitle(actionBarTitle);
        }
    }

    protected abstract String getSubclassName();


    protected abstract int getLayoutResource();

    protected abstract void updateFollowingViewBinding();

    protected ActionBar getActionBar() {
        Activity localActivity = getActivity();
        if ((localActivity instanceof AppCompatActivity)) {
            return ((AppCompatActivity) localActivity).getSupportActionBar();
        }
        return null;
    }

    public static abstract interface BaseFragmentResponder {
        public abstract void showFragment(BaseFragment fragment, String fragmentTag);

        public abstract void setActionBarTitle(int i);

        public abstract void setActionBarTitle(String actionBarTitle);

        public abstract void setActionBarTitle(CharSequence actionBarTitle);

    }
}
