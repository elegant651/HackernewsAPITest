package bpsound.hackernewsapitest.mvp.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bpsound.hackernewsapitest.R;
import bpsound.hackernewsapitest.apis.NewsItem;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by elegantuniv on 2017. 6. 19..
 */

public class TopListAdapter extends RecyclerView.Adapter<TopListAdapter.ViewHolder> {
    private final OnItemListener mListener;
    private Context mContext;
    private List<NewsItem> mData;


    public TopListAdapter(Context context, List<NewsItem> data, OnItemListener listener) {
        this.mContext = context;
        this.mData = data;
        this.mListener = listener;
    }

    @Override
    public TopListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_topstory_item, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopListAdapter.ViewHolder holder, int position) {
        NewsItem data = mData.get(position);
        holder.tvNumIdx.setText(""+(position+1));
        holder.tvTitle.setText(data.getTitle());
        holder.tvDetail.setText(data.getScore()+" points by "+data.getBy()+ " "+ DateUtils.getRelativeTimeSpanString(mContext, data.getTime()) + " | " + data.getDescendants() + " comments");
        holder.click(data, mListener);
    }

    public void addData(NewsItem item){
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemListener {
        void onClick(NewsItem item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNumIdx) TextView tvNumIdx;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvDetail) TextView tvDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void click(final NewsItem data, final OnItemListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(data);
                }
            });
        }
    }
}
