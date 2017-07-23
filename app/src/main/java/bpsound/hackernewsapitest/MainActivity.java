package bpsound.hackernewsapitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import bpsound.hackernewsapitest.mvp.comments.CommentsFragment;
import bpsound.hackernewsapitest.mvp.list.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState==null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new MainFragment(), this.toString())
                    .commit();
        }
    }

    public void moveCommentFragment(ArrayList<Integer> list) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList("comments", list);
        fragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, fragment, this.toString())
                .commit();
    }
}
