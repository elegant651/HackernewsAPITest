package bpsound.hackernewsapitest.list;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import bpsound.hackernewsapitest.apis.HackerNewsApi;
import bpsound.hackernewsapitest.apis.NewsItem;
import bpsound.hackernewsapitest.mvp.list.MainPresenter;
import bpsound.hackernewsapitest.mvp.list.MainView;
import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by elegantuniv on 2017. 7. 20..
 */

public class TopListPresenterTest {

    private static List<Integer> ITEMS;
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

    }


    @Test
    public void loadItemsFromRepositoryAndLoadIntoView() {
        when(mNewsRepo.getTopStories()).thenReturn(Observable.just(ITEMS));

        mPresenter.loadTopListItems();

        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).showLoading();
        inOrder.verify(mView).hideLoading();
    }


    @Test
    public void loadEachItemFromRepository() {
        when(mNewsRepo.getTopStories()).thenReturn(Observable.just(ITEMS));

        NewsItem item = new NewsItem();
        item.setId(1);
        item.setTitle("title");
        item.setBy("writer");
        item.setScore(22);
        item.setTime(12345678);
        item.setDescendants(8);
        when(mNewsRepo.getNewsItem(eq(item.getId()))).thenReturn(Observable.just(item));

        mPresenter.loadTopListItems();

        verify(mView).onSuccessRequestItem(item);
    }

}
