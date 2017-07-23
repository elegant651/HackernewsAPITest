package bpsound.hackernewsapitest.mvp.comments;

import android.util.Log;

import java.util.ArrayList;

import bpsound.hackernewsapitest.apis.CommentItem;
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
 * Created by elegantuniv on 2017. 7. 19..
 */

public class CommentsPresenter {

    private HackerNewsApi mService;
    private CommentsView mView;
    private CompositeDisposable mDisposables;

    public CommentsPresenter(HackerNewsApi service, CommentsView view) {
        this.mService = service;
        this.mView = view;
        this.mDisposables = new CompositeDisposable();
    }

    public void loadCommentsItems(ArrayList<Integer> commentIds, final int depth, final int groupId) {
        mDisposables.add(
                Observable.fromIterable(commentIds)
                        .flatMap(
                                item -> {
                                    Observable<CommentItem> commentObservable = mService.getCommentItem(item);
                                    return commentObservable;
                                })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(
                                new DisposableObserver<CommentItem>(){
                                    @Override
                                    public void onComplete() {
                                        Log.d(Constants.TAG, "OnComplete");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(Constants.TAG, "woops we got an error while getting the data");
                                        mView.hideLoading();
                                        mView.onFailureRequest(Constants.MSG_RESPONSE_FLAG_0);
                                    }

                                    @Override
                                    public void onNext(CommentItem item) {
                                        if(depth==0){
                                            mView.onSuccessCommentItem(item, depth, commentIds.indexOf(item.getId()));
                                        } else {
                                            mView.onSuccessCommentItem(item, depth, groupId);
                                        }
                                    }
                                }));
    }

    public void dispose() {
        this.mDisposables.dispose();
    }
}
