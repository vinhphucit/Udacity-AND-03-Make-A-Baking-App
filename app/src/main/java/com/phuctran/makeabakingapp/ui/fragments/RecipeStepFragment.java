package com.phuctran.makeabakingapp.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.phuctran.makeabakingapp.Injection;
import com.phuctran.makeabakingapp.R;
import com.phuctran.makeabakingapp.domain.models.Step;
import com.phuctran.makeabakingapp.mvp.presenters.RecipeStepPresenter;
import com.phuctran.makeabakingapp.mvp.views.RecipeStepContract;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by phuctran on 9/20/17.
 */

public class RecipeStepFragment extends BaseDetailFragment implements RecipeStepContract.View {
    private static final String TAG = RecipeStepFragment.class.getSimpleName();

    public static final String ARGS_RECIPE_ID = "ARGS_RECIPE_ID";
    public static final String ARGS_STEP_ORDER = "KEY_STEP_ORDER";
    public static final String ARGS_CURRENT_PLAY_POSSITION = "ARGS_CURRENT_PLAY_POSSITION";
    public static final String ARGS_PLAYING_STATE = "ARGS_PLAYING_STATE";

    @Nullable
    @BindView(R.id.recipe_step_description)
    TextView recipe_step_description;
    @BindView(R.id.recipe_step_video)
    SimpleExoPlayerView recipe_step_video;
    @BindView(R.id.btnBack)
    Button backBtn;
    @BindView(R.id.btnNext)
    Button nextBtn;

    private long mCurrentPlayPosition = -1;
    private int mRecipeId;
    private int mStepOrder;
    private boolean isPlayingWhenReady = true;
    private Step mRecipeStep;
    private RecipeStepContract.Presenter mPresenter;
    private SimpleExoPlayer mExoPlayer = null;


    public static RecipeStepFragment newInstance(int recipeId, int step) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_RECIPE_ID, recipeId);
        bundle.putInt(ARGS_STEP_ORDER, step);
        RecipeStepFragment fragment = new RecipeStepFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public RecipeStepFragment() {
        this.mPresenter = new RecipeStepPresenter(this, Injection.provideGetRecipeUseCase());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARGS_RECIPE_ID))
            this.mRecipeId = getArguments().getInt(ARGS_RECIPE_ID);
        if (getArguments() != null && getArguments().containsKey(ARGS_STEP_ORDER))
            this.mStepOrder = getArguments().getInt(ARGS_STEP_ORDER);
        if (getArguments() != null && getArguments().containsKey(ARGS_PLAYING_STATE))
            this.isPlayingWhenReady = getArguments().getBoolean(ARGS_PLAYING_STATE);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ARGS_RECIPE_ID, mRecipeId);
        outState.putInt(ARGS_STEP_ORDER, mStepOrder);
        outState.putBoolean(ARGS_PLAYING_STATE, isPlayingWhenReady);
        if (mCurrentPlayPosition > 0)
            outState.putLong(ARGS_CURRENT_PLAY_POSSITION, mCurrentPlayPosition);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ARGS_RECIPE_ID))
                this.mRecipeId = savedInstanceState.getInt(ARGS_RECIPE_ID);
            if (savedInstanceState.containsKey(ARGS_STEP_ORDER))
                this.mStepOrder = savedInstanceState.getInt(ARGS_STEP_ORDER);
            if (savedInstanceState.containsKey(ARGS_CURRENT_PLAY_POSSITION))
                this.mCurrentPlayPosition = savedInstanceState.getLong(ARGS_CURRENT_PLAY_POSSITION);
            if (savedInstanceState.containsKey(ARGS_PLAYING_STATE))
                this.isPlayingWhenReady = savedInstanceState.getBoolean(ARGS_PLAYING_STATE);

        }
        mPresenter.setCurrentStep(mStepOrder);
        mPresenter.getRecipe(mRecipeId);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        if (mRecipeStep != null) {
            showStep(mRecipeStep);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
        releasePlayer();
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

    }

    public void goToStep(int stepOrder) {
        mStepOrder = stepOrder;
        mPresenter.showStepAt(stepOrder);
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
        mExoPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Timber.d("PlayWhenRead " + playWhenReady);
                isPlayingWhenReady = playWhenReady;
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity() {

            }
        });
    }

    private void playVideo() {
        initializePlayer();
        Uri mp4VideoUri = Uri.parse(mRecipeStep.getVideoURL());

        DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), "Making A BakingPreferences App", bandwidthMeterA);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        ExtractorMediaSource videoSource = new ExtractorMediaSource(mp4VideoUri,
                dataSourceFactory, extractorsFactory, null, null);
        mExoPlayer.prepare(videoSource);
        mExoPlayer.setPlayWhenReady(isPlayingWhenReady);
        if (mCurrentPlayPosition > 0) {
            mExoPlayer.seekTo(mCurrentPlayPosition);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mCurrentPlayPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void showStep(Step step) {
        if (recipe_step_video == null) return;
        if (mExoPlayer != null) {
            mExoPlayer.stop();
        }
        this.mRecipeStep = step;
        if (recipe_step_description != null)
            recipe_step_description.setText(step.getDescription());
        if (step.getVideoURL().isEmpty()) {
            recipe_step_video.setVisibility(View.GONE);
        } else {
            recipe_step_video.setVisibility(View.VISIBLE);
            playVideo();
        }
    }

    @Override
    public void setNextVisibility(boolean isVisible) {
        if (isVisible)
            nextBtn.setVisibility(View.VISIBLE);
        else
            nextBtn.setVisibility(View.GONE);
    }

    @Override
    public void setBackVisibility(boolean isVisible) {
        if (isVisible)
            backBtn.setVisibility(View.VISIBLE);
        else
            backBtn.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnBack)
    void onBackPressed() {
        mStepOrder--;
        mCurrentPlayPosition = -1;
        mPresenter.showStepAt(mStepOrder);
    }

    @OnClick(R.id.btnNext)
    void onNextPressed() {
        mStepOrder++;
        mCurrentPlayPosition = -1;
        mPresenter.showStepAt(mStepOrder);
    }
}
