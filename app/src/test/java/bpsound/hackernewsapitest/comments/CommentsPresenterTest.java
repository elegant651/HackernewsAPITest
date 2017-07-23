package bpsound.hackernewsapitest.comments;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import bpsound.hackernewsapitest.apis.CommentItem;
import bpsound.hackernewsapitest.apis.HackerNewsApi;
import bpsound.hackernewsapitest.mvp.comments.CommentsPresenter;
import bpsound.hackernewsapitest.mvp.comments.CommentsView;
import io.reactivex.Observable;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by elegantuniv on 2017. 7. 21..
 */

public class CommentsPresenterTest {

    private static ArrayList<Integer> ITEMS;
    private CommentsPresenter mPresenter;

    @Mock
    private HackerNewsApi mNewsRepo;

    @Mock
    private CommentsView mView;

    @Before
    public void setupTopListPresenter() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new CommentsPresenter(mNewsRepo, mView);

        ITEMS = new ArrayList<>();
        for(int i=0; i<5; i++){
            ITEMS.add(i);
        }

    }

    @Test
    public void loadCommentsFromRepositoryAndLoadIntoView() {
        CommentItem item = new CommentItem();
        item.setId(1);
        item.setText("comment title");
        item.setBy("writer");
        item.setTime(12345678);

        when(mNewsRepo.getCommentItem(eq(item.getId()))).thenReturn(Observable.just(item));

        mPresenter.loadCommentsItems(ITEMS, 0, 0);

        verify(mView).onSuccessCommentItem(item, 0, 0);
    }

    @Test
    public void loadRepliesFromRepositoryAndLoadIntoView() {
        CommentItem item = new CommentItem();
        item.setId(1);
        item.setText("comment title");
        item.setBy("writer");
        item.setTime(12345678);
        ArrayList<Integer> alReplies = new ArrayList<>();
        for(int i=5; i<10; i++){
            alReplies.add(i);
        }
        item.setKids(alReplies);

        when(mNewsRepo.getCommentItem(eq(item.getId()))).thenReturn(Observable.just(item));


        mPresenter.loadCommentsItems((ArrayList)item.getKids(), 1, 0);

        verify(mView).onSuccessCommentItem(item, 1, 0);
    }
}
