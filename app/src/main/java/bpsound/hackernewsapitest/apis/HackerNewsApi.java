package bpsound.hackernewsapitest.apis;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by elegantuniv on 2017. 7. 14..
 */

public interface HackerNewsApi {

    @GET("/v0/topstories.json")
    Observable<List<Integer>> getTopStories();

    @GET("/v0/item/{item_id}.json")
    Observable<NewsItem> getNewsItem(@Path("item_id") int item_id);

    @GET("/v0/item/{item_id}.json")
    Observable<CommentItem> getCommentItem(@Path("item_id") int item_id);
}
