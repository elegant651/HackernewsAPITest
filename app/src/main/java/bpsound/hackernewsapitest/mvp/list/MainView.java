package bpsound.hackernewsapitest.mvp.list;

import java.util.ArrayList;
import java.util.List;

import bpsound.hackernewsapitest.apis.NewsItem;

/**
 * Created by elegantuniv on 2017. 7. 14..
 */

public interface MainView {

    public void onSuccessRequestItem(NewsItem item);
    public void showLoading();
    public void hideLoading();
    public void onFailureRequest(String msg);

    public void onSuccessTest(List<Integer> val);
}
