package bpsound.hackernewsapitest.mvp.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bpsound.hackernewsapitest.MainActivity;
import bpsound.hackernewsapitest.R;
import bpsound.hackernewsapitest.apis.HackerNewsService;
import bpsound.hackernewsapitest.apis.NewsItem;
import bpsound.hackernewsapitest.common.Constants;
import bpsound.hackernewsapitest.common.DialogUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by elegantuniv on 2017. 7. 14..
 */

public class MainFragment extends Fragment implements MainView {
    @BindView(R.id.swipeContainer) SwipeRefreshLayout mSwipeContainer;
    @BindView(R.id.list) RecyclerView mList;

    private Unbinder mUnbinder;
    private MainPresenter mPresenter;
    private TopListAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        mPresenter = new MainPresenter(HackerNewsService.createService(), this);
        mAdapter = new TopListAdapter(getContext(), new ArrayList<>(), new TopListAdapter.OnItemListener() {
            @Override
            public void onClick(NewsItem item) {
                if(item.getKids().size()>0){
                    ((MainActivity)getActivity()).moveCommentFragment((ArrayList)item.getKids());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mUnbinder = ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mList.setAdapter(mAdapter);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeContainer.setRefreshing(false);
                mAdapter.clearData();
                mPresenter.loadTopListItems();
            }
        });

        mPresenter.loadTopListItems();
    }

    @Override
    public void onSuccessTest(List<Integer> val) {
        Log.d(Constants.TAG, ""+val.size());
    }

    @Override
    public void onSuccessRequestItem(NewsItem item) {
        mAdapter.addData(item);
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
