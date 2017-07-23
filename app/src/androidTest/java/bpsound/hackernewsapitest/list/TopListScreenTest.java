package bpsound.hackernewsapitest.list;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import bpsound.hackernewsapitest.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkArgument;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by elegantuniv on 2017. 7. 21..
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TopListScreenTest {

    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(
                        isDescendantOfA(isAssignableFrom(RecyclerView.class)),
                        withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA RV with text " + itemText);
            }
        };
    }

    @Test
    public void addItemToList() throws Exception {
        String findingText = "test";

        onView(withId(R.id.list)).perform(
                scrollTo(hasDescendant(withText(findingText))));

        onView(withItemText(findingText)).check(matches(isDisplayed()));
    }

    @Test
    public void clickItem_moveCommentFragment() throws Exception {
        // Click on the add note button
//        onView(withId(R.id.fab_add_notes)).perform(click());

        // Check if the add note screen is displayed
//        onView(withId(R.id.add_note_title)).check(matches(isDisplayed()));
    }
}
