package bpsound.hackernewsapitest.comments;

import org.junit.Assert;
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
    private static CommentItem mItem;
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

        mItem = new CommentItem();
        mItem.setId(1);
        mItem.setText("comment title");
        mItem.setBy("writer");
        mItem.setTime(12345678);

    }

    @Test
    public void testGetCommentItem() throws Exception{
        when(mNewsRepo.getCommentItem(eq(mItem.getId()))).thenReturn(Observable.just(mItem));
        mNewsRepo.getCommentItem(1).subscribe(item -> {
            Assert.assertEquals(item, mItem);
        });
    }


}
