package com.phuctran.makeabakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.phuctran.makeabakingapp.matchers.RecyclerViewMatcher;
import com.phuctran.makeabakingapp.ui.activities.RecipesActivity;
import com.phuctran.makeabakingapp.utils.testing.idlingResource.SimpleIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by phuctran on 10/8/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeListTest {
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
    @Rule
    public ActivityTestRule<RecipesActivity> mActivityRule = new ActivityTestRule<RecipesActivity>(RecipesActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }
    @Test
    public void shouldDisplayCoffeeOrderList() {
        onView(withRecyclerView(R.id.recycler_view).atPosition(0)).check(matches(
                hasDescendant(allOf(withId(R.id.recipe_title), withText("Nutella Pie")))));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}
