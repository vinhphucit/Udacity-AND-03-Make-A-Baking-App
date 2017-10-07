package com.phuctran.makeabakingapp.mvp.views;

/**
 * Created by PhucTV on 6/23/16.
 */
public interface LoadDataBaseView<T> extends BaseView<T> {
    /**
     * Show a view with a progress bar indicating a loading process.
     */
    void showLoading();

    /**
     * Hide a loading view.
     */
    void hideLoading();

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    void showError(String message);

}
