package com.example.test8;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.example.test8.view.ZoomSwifrefresh;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView rv_test;
    private HomeAdapter mAdapter;
    private ZoomSwifrefresh gzoomswifrefresh;
    private ArrayList<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gzoomswifrefresh = findViewById(R.id.gzoomswifrefresh);
        rv_test = findViewById(R.id.rv_test);


        rv_test.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new HomeAdapter();
        strings = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            strings.add("数据" + i);
        }

        rv_test.setAdapter(mAdapter);

        mAdapter.setmDatas(strings);


        initRefreshLayout();
    }

    private void initRefreshLayout() {

        gzoomswifrefresh.setColorSchemeColors(Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE);
        gzoomswifrefresh.setOnRefreshListener(new ZoomSwifrefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hd.sendEmptyMessageDelayed(0, 3000);
            }
        });

        gzoomswifrefresh.setBottomColorSchemeColors(Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED);
        //在这里新增上拉刷新方法
        gzoomswifrefresh.setOnBottomRefreshListenrer(new ZoomSwifrefresh.OnBottomRefreshListener() {
            @Override
            public void onBottomRefresh() {
                Toast.makeText(MainActivity.this, "上拉刷新了", Toast.LENGTH_SHORT).show();
                hd.sendEmptyMessageDelayed(0, 3000);
            }
        });
    }


    Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            cancleLoading();

        }
    };

    /**
     * 取消加载
     */
    public void cancleLoading() {
//        这表示的是：是否下拉刷新
        if (gzoomswifrefresh.isRefreshing()) {
            gzoomswifrefresh.setRefreshing(false);
            strings.clear();
            for (int i = 0; i < 20; i++) {
                strings.add("头部" + i);
            }
            mAdapter.setmDatas(strings);
        } else {
//    上拉刷新
            gzoomswifrefresh.setBottomRefreshing(false);
            for (int i = 0; i < 5; i++) {
                strings.add("尾部" + i);
            }
            mAdapter.setmDatas(strings);
        }


    }


}
