package bpsound.hackernewsapitest.mvp.comments;

import bpsound.hackernewsapitest.apis.CommentItem;

/**
 * Created by elegantuniv on 2017. 7. 19..
 */

public interface CommentsView {
    public void onSuccessCommentItem(CommentItem item, int depth, int groupId);
    public void showLoading();
    public void hideLoading();
    public void onFailureRequest(String msg);
}
