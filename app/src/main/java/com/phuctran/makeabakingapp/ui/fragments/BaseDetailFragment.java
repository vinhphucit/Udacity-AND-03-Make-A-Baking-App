package com.phuctran.makeabakingapp.ui.fragments;

import com.phuctran.makeabakingapp.domain.models.Step;

/**
 * Created by phuctran on 9/20/17.
 */

public abstract class BaseDetailFragment extends BaseFragment {
    public void goToRecipeStepFragment(int stepOrder) {
        ((BaseFragmentResponder) getActivity()).goToRecipeStepFragment(stepOrder);
    }



    public interface BaseFragmentResponder extends BaseFragment.BaseFragmentResponder {
        void goToRecipeStepFragment(int recipeId);
    }
}
