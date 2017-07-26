package bpsound.hackernewsapitest.list;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import bpsound.hackernewsapitest.apis.HackerNewsApi;
import bpsound.hackernewsapitest.apis.NewsItem;
import bpsound.hackernewsapitest.mvp.list.MainPresenter;
import bpsound.hackernewsapitest.mvp.list.MainView;
import io.reactivex.Observable;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by elegantuniv on 2017. 7. 20..
 */

public class TopListPresenterTest {

    private static List<Integer> ITEMS;
    private static NewsItem mItem;
    private MainPresenter mPresenter;

    @Mock
    private HackerNewsApi mNewsRepo;

    @Mock
    private MainView mView;

    @Before
    public void setupTopListPresenter() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new MainPresenter(mNewsRepo, mView);

        ITEMS = new ArrayList<>();
        for(int i=0; i<5; i++){
            ITEMS.add(i);
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
    public void testGetTopStories() throws Exception{
        when(mNewsRepo.getTopStories()).thenReturn(Observable.just(ITEMS));
        ArrayList<Integer> alList = new ArrayList<>();
        mNewsRepo.getTopStories().flatMap(Observable::fromIterable).subscribe(item -> {
            alList.add(item);
        });
        Assert.assertEquals(alList.size(), ITEMS.size());
    }

    @Test
    public void testGetNewsItem() {
        when(mNewsRepo.getNewsItem(eq(mItem.getId()))).thenReturn(Observable.just(mItem));
        mNewsRepo.getNewsItem(1).subscribe(item -> {
            Assert.assertEquals(item, mItem);
        });
    }

}
