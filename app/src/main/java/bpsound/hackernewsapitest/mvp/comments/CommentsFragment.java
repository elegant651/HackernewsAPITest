package bpsound.hackernewsapitest.mvp.comments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import bpsound.hackernewsapitest.R;
import bpsound.hackernewsapitest.apis.CommentItem;
import bpsound.hackernewsapitest.apis.HackerNewsService;
import bpsound.hackernewsapitest.common.DialogUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by elegantuniv on 2017. 7. 19..
 */

public class CommentsFragment extends Fragment implements CommentsView{
    @BindView(R.id.list) ExpandableListView mList;

    private Unbinder mUnbinder;
    private CommentsPresenter mPresenter;
    private BaseExpandableAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new CommentsPresenter(HackerNewsService.createService(), this);
        mAdapter = new BaseExpandableAdapter(getContext(), new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_comments, container, false);
        mUnbinder = ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mList.setAdapter(mAdapter);

        if(getArguments() != null){
            ArrayList<Integer> alComments = getArguments().getIntegerArrayList("comments");
            mPresenter.loadCommentsItems(alComments, 0, 0);
        }
    }


    @Override
    public void onSuccessCommentItem(CommentItem item, int depth, int groupId) {
        if(depth==0){
            mAdapter.addGroupData(item);

            ArrayList<Integer> alReplies = (ArrayList)item.getKids();
            if(alReplies!=null && alReplies.size()>0){
                mPresenter.loadCommentsItems(alReplies, 1, groupId);
            }
        } else {
            mAdapter.addChildData(groupId, item);
        }
    }

    @Override
    public void onFailureRequest(String msg) {
        DialogUtils.setAlertDialog(getContext(), msg);
    }

    @Override
    public void showLoading(){
        DialogUtils.showLoadingProgDialog(getContext());
    }

    @Override
    public void hideLoading(){
        DialogUtils.hideLoadingProgDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
