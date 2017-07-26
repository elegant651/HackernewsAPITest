package bpsound.hackernewsapitest.list;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import bpsound.hackernewsapitest.R;
import bpsound.hackernewsapitest.apis.HackerNewsApi;
import bpsound.hackernewsapitest.apis.NewsItem;
import bpsound.hackernewsapitest.mvp.list.MainPresenter;
import bpsound.hackernewsapitest.mvp.list.MainView;
import io.reactivex.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkArgument;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.jayway.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by elegantuniv on 2017. 7. 21..
 */

@RunWith(AndroidJUnit4.class)
public class TopListScreenTest {

    private static List<Integer> ITEMS;
    private static NewsItem mItem;
    private MainPresenter mPresenter;

    private HackerNewsApi mNewsRepo;

    private MainView mView;

    @Before
    public void setupTopListPresenter() {
        mView = mock(MainView.class);
        mNewsRepo = mock(HackerNewsApi.class);

        mPresenter = new MainPresenter(mNewsRepo, mView);

        ITEMS = new ArrayList<>();
        for(int i=0; i<5; i++){
            ITEMS.add(1);
        }

        mItem = new NewsItem();
        mItem.setId(1);
        mItem.setTitle("title");
        mItem.setBy("writer");
        mItem.setScore(22);
        mItem.setTime(12345678);
        mItem.setDescendants(8);
    }

    @Test
    public void testPresenterWithRequestItem() {
        final boolean[] finish = {false};

        when(mNewsRepo.getTopStories()).thenReturn(Observable.just(ITEMS));
        when(mNewsRepo.getNewsItem(eq(mItem.getId()))).thenReturn(Observable.just(mItem));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                finish[0] = true;
                return invocation;
            }
        }).when(mView).onSuccessRequestItem(mItem);

        mPresenter.loadTopListItems();

        await().until(new Runnable() {
            @Override
            public void run() {
                while (!finish[0]) {
                    verify(mNewsRepo, atLeastOnce()).getTopStories();
                }
            }
        });
        verify(mView, atLeastOnce()).onSuccessRequestItem(mItem);
    }

}
