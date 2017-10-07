package com.phuctran.makeabakingapp.ui.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.phuctran.makeabakingapp.R;
import com.phuctran.makeabakingapp.domain.models.Step;
import com.phuctran.makeabakingapp.mvp.presenters.RecipeStepPresenter;
import com.phuctran.makeabakingapp.mvp.views.RecipeStepContract;
import com.phuctran.makeabakingapp.ui.activities.BaseActivity;

import org.parceler.Parcels;

import butterknife.BindView;

/**
 * Created by phuctran on 9/20/17.
 */

public class RecipeStepFragment extends BaseDetailFragment implements RecipeStepContract.View {
    private static final String TAG = RecipeStepFragment.class.getSimpleName();

    public static final String ARGS_RECIPE_STEP = "ARGS_RECIPE_STEP";

    @Nullable
    @BindView(R.id.recipe_step_description)
    TextView recipe_step_description;
    @BindView(R.id.recipe_step_video)
    SimpleExoPlayerView recipe_step_video;

    private RecipeStepContract.Presenter mPresenter;
    private Step mRecipeStep;
    private SimpleExoPlayer mExoPlayer = null;


    public static RecipeStepFragment newInstance(Step step) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_RECIPE_STEP, Parcels.wrap(step));

        RecipeStepFragment fragment = new RecipeStepFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public RecipeStepFragment() {
        this.mPresenter = new RecipeStepPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARGS_RECIPE_STEP))
            this.mRecipeStep = Parcels.unwrap(getArguments().getParcelable(ARGS_RECIPE_STEP));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    protected String getSubclassName() {
        return TAG;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recipe_step;
    }

    @Override
    protected void updateFollowingViewBinding() {
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            ((BaseActivity) getActivity()).getSupportActionBar().show();
        } else {
            View decorView = getActivity().getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            ((BaseActivity) getActivity()).getSupportActionBar().hide();
        }
        if (recipe_step_description != null)
            recipe_step_description.setText(mRecipeStep.getDescription());
        if (mRecipeStep.getVideoURL().isEmpty()) {
            recipe_step_video.setVisibility(View.GONE);
        } else {
            initializePlayer();
        }
    }

    @Override
    public void setPresenter(RecipeStepContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void initializePlayer() {
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        recipe_step_video.setPlayer(mExoPlayer);

        Uri mp4VideoUri = Uri.parse(mRecipeStep.getVideoURL());

        DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), "Making A BakingPreferences App", bandwidthMeterA);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        ExtractorMediaSource videoSource = new ExtractorMediaSource(mp4VideoUri,
                dataSourceFactory, extractorsFactory, null, null);
        mExoPlayer.prepare(videoSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
