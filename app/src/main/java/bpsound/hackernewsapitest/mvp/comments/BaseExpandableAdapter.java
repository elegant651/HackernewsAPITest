package bpsound.hackernewsapitest.mvp.comments;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import bpsound.hackernewsapitest.R;
import bpsound.hackernewsapitest.apis.CommentItem;


/**
 * Created by elegantuniv on 2017. 6. 28..
 */

public class BaseExpandableAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ArrayList<CommentItem> mGroupList = null;
    private ArrayList<ArrayList<CommentItem>> mChildList = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;

    public BaseExpandableAdapter(Context c, ArrayList<CommentItem> groupList,
                                 ArrayList<ArrayList<CommentItem>> childList){
        super();
        this.mContext = c;
        this.inflater = LayoutInflater.from(c);
        this.mGroupList = groupList;
        this.mChildList = childList;
    }

    @Override
    public CommentItem getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.fragment_comment_group_row, parent, false);
            viewHolder.tvGroupName = (TextView) v.findViewById(R.id.tvName);
            viewHolder.tvGroupDetail = (TextView) v.findViewById(R.id.tvDetail);

            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);

        CommentItem item = getGroup(groupPosition);
        viewHolder.tvGroupName.setText("▲ "+item.getBy() + " "+ DateUtils.getRelativeTimeSpanString(mContext, item.getTime()));
        viewHolder.tvGroupDetail.setText(item.getText());
        return v;
    }

    @Override
    public CommentItem getChild(int groupPosition, int childPosition) {
        return mChildList.get(groupPosition).get(childPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList.get(groupPosition).size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.fragment_comment_child_row, null);
            viewHolder.tvChildName = (TextView) v.findViewById(R.id.tvName);
            viewHolder.tvChildDetail = (TextView) v.findViewById(R.id.tvDetail);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        CommentItem item = getChild(groupPosition, childPosition);
        viewHolder.tvChildName.setText("▲ "+item.getBy() + " "+ DateUtils.getRelativeTimeSpanString(mContext, item.getTime()));
        viewHolder.tvChildDetail.setText(item.getText());

        return v;
    }

    public void addGroupData(CommentItem item){
        this.mGroupList.add(item);
        this.mChildList.add(new ArrayList<>());
        notifyDataSetChanged();
    }

    public void addChildData(int groupIdx, CommentItem item) {
        ArrayList<CommentItem> alList = this.mChildList.get(groupIdx);
        alList.add(item);
        this.mChildList.set(groupIdx, alList);
        notifyDataSetChanged();
    }

    public void clearData(){
        this.mGroupList.clear();
        this.mChildList.clear();
        notifyDataSetChanged();
    }

    @Override
    public boolean hasStableIds() { return true; }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    class ViewHolder {
        public TextView tvGroupName;
        public TextView tvGroupDetail;
        public TextView tvChildName;
        public TextView tvChildDetail;
    }

}
