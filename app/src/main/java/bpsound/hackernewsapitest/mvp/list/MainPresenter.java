package bpsound.hackernewsapitest.mvp.list;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import bpsound.hackernewsapitest.apis.HackerNewsApi;
import bpsound.hackernewsapitest.apis.NewsItem;
import bpsound.hackernewsapitest.common.Constants;
import bpsound.hackernewsapitest.mvp.list.MainView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by elegantuniv on 2017. 7. 14..
 */

public class MainPresenter {
    private HackerNewsApi mService;
    private MainView mView;

    public MainPresenter(HackerNewsApi service, MainView view) {
        this.mService = service;
        this.mView = view;
    }

    public void loadTopListItems() {

          mService.getTopStories()
                  .subscribeOn(Schedulers.io())
                  .flatMap(Observable::fromIterable)
                  .flatMap(
                    item -> {
                        Observable<NewsItem> newsObservable = mService.getNewsItem(item)
                                .filter(item2 -> item2.getTitle() != null && !item2.getTitle().isEmpty());
                        return newsObservable;
                    })
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribeWith(
                        new DisposableObserver<NewsItem>(){
                            @Override
                            public void onComplete() {
                                Log.d(Constants.TAG, "OnComplete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(Constants.TAG, "woops we got an error while getting the data");
                                mView.onFailureRequest(Constants.MSG_RESPONSE_FLAG_0);
                            }

                            @Override
                            public void onNext(NewsItem item) {
                                Log.d(Constants.TAG, item.getTitle());
                                mView.onSuccessRequestItem(item);
                            }
                  });

    }
}
