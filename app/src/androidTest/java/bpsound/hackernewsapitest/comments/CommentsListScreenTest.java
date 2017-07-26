package bpsound.hackernewsapitest.comments;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import bpsound.hackernewsapitest.apis.CommentItem;
import bpsound.hackernewsapitest.apis.HackerNewsApi;
import bpsound.hackernewsapitest.apis.NewsItem;
import bpsound.hackernewsapitest.mvp.comments.CommentsPresenter;
import bpsound.hackernewsapitest.mvp.comments.CommentsView;
import bpsound.hackernewsapitest.mvp.list.MainPresenter;
import io.reactivex.Observable;

import static com.jayway.awaitility.Awaitility.await;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by willpark on 2017. 7. 26..
 */

@RunWith(AndroidJUnit4.class)
public class CommentsListScreenTest {

    private static ArrayList<Integer> ITEMS;
    private static CommentItem mItem;
    private CommentsPresenter mPresenter;

    private HackerNewsApi mNewsRepo;

    private CommentsView mView;

    @Before
    public void setupTopListPresenter() {
        mView = mock(CommentsView.class);
        mNewsRepo = mock(HackerNewsApi.class);

        mPresenter = new CommentsPresenter(mNewsRepo, mView);

        ITEMS = new ArrayList<>();
        for(int i=0; i<5; i++){
            ITEMS.add(1);
        }

        mItem = new CommentItem();
        mItem.setId(1);
        mItem.setText("comment title");
        mItem.setBy("writer");
        mItem.setTime(12345678);
    }

    @Test
    public void loadCommentsFromRepositoryAndLoadIntoView() {
        final boolean[] finish = {false};

        when(mNewsRepo.getCommentItem(eq(mItem.getId()))).thenReturn(Observable.just(mItem));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                finish[0] = true;
                return invocation;
            }
        }).when(mView).onSuccessCommentItem(mItem, 0, 0);

        mPresenter.loadCommentsItems(ITEMS, 0, 0);

        await().until(new Runnable() {
            @Override
            public void run() {
                while (!finish[0]) {
                    verify(mNewsRepo, atLeastOnce()).getCommentItem(mItem.getId());
                }
            }
        });
        verify(mView, atLeastOnce()).onSuccessCommentItem(mItem, 0, 0);
    }

    @Test
    public void loadRepliesFromRepositoryAndLoadIntoView() {
        final boolean[] finish = {false};

        ArrayList<Integer> alReplies = new ArrayList<>();
        for(int i=5; i<10; i++){
            alReplies.add(1);
        }
        mItem.setKids(alReplies);

        when(mNewsRepo.getCommentItem(eq(mItem.getId()))).thenReturn(Observable.just(mItem));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                finish[0] = true;
                return invocation;
            }
        }).when(mView).onSuccessCommentItem(mItem, 1, 0);

        mPresenter.loadCommentsItems((ArrayList)mItem.getKids(), 1, 0);

        await().until(new Runnable() {
            @Override
            public void run() {
                while (!finish[0]) {
                    verify(mNewsRepo, atLeastOnce()).getCommentItem(mItem.getId());
                }
            }
        });
        verify(mView, atLeastOnce()).onSuccessCommentItem(mItem, 1, 0);
    }
}
