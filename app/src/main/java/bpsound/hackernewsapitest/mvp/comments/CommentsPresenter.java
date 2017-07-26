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

    public CommentsPresenter(HackerNewsApi service, CommentsView view) {
        this.mService = service;
        this.mView = view;
    }

    public void loadCommentsItems(ArrayList<Integer> commentIds, final int depth, final int groupId) {
            Observable.fromIterable(commentIds)
                    .flatMap(
                            item -> {
                                Observable<CommentItem> commentObservable = mService.getCommentItem(item)
                                        .filter(item2 -> item2.getText() != null && !item2.getText().isEmpty());
                                return commentObservable;
                            })
                    .subscribeOn(Schedulers.io())
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
                            });
    }

}
