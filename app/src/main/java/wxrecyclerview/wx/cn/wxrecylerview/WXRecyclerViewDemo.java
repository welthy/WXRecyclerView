package wxrecyclerview.wx.cn.wxrecylerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import wxrecyclerview.wx.cn.wxrecyclerview.WXRecyclerAdapter;
import wxrecyclerview.wx.cn.wxrecyclerview.WXRecyclerView;
import wxrecyclerview.wx.cn.wxrecyclerview.bean.WXRecyclerItem;

public class WXRecyclerViewDemo extends Activity {

    private final String TAG = "wx_demo";
    private final int INITIAL_DATA_SIZE = 30;

    private WXRecyclerView wxRecyclerView;
    private WXRecyclerAdapter wxRecyclerAdapter;
    private List<WXRecyclerItem> datas;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wx_recyclerview_demo);

        initView();
        initData();
        initEvent();

    }

    private void initView(){
        wxRecyclerView = findViewById(R.id.wx_recyclerview);
    }

    private void initEvent(){
        wxRecyclerAdapter = new WXRecyclerAdapter(this,datas);
        wxRecyclerView.setAdapter(wxRecyclerAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        wxRecyclerView.setLayoutManager(llm);

        wxRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private void initData(){
        datas = new ArrayList<>();
        for (int i=0; i<INITIAL_DATA_SIZE; i++){
            WXRecyclerItem item = new WXRecyclerItem();
            item.setName("A"+i);
            datas.add(item);
        }
    }
}
