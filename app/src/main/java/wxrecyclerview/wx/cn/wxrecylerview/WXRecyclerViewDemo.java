package wxrecyclerview.wx.cn.wxrecylerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import wxrecyclerview.wx.cn.wxrecyclerview.WXItemTouchHelperCallback;
import wxrecyclerview.wx.cn.wxrecyclerview.base.BaseWXRecyclerAdapter;
import wxrecyclerview.wx.cn.wxrecyclerview.adapters.WXRecyclerAdapter;
import wxrecyclerview.wx.cn.wxrecyclerview.WXRecyclerView;
import wxrecyclerview.wx.cn.wxrecyclerview.bean.WXRecyclerItem;
import wxrecyclerview.wx.cn.wxrecyclerview.utils.WXConstants;

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
        initWXRecyclerView();
        initEvent();

    }

    private void initView(){
        wxRecyclerView = findViewById(R.id.wx_recyclerview);
    }

    private void initEvent(){
        //wxRecyclerView.setDragEnable(true);
    }

    private void initWXRecyclerView(){
        wxRecyclerAdapter = new WXRecyclerAdapter(this,datas);
        wxRecyclerAdapter.setOnItemClickListener(new DemoAdapterItemClickListener());
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
            if (i % 4 == 0){
                item.setType(WXConstants.TYPE_TITLE);
                item.setName("Title "+i);
            }else {
                item.setType(WXConstants.TYPE_NORMAL);
                item.setName("A"+i);
            }
            datas.add(item);
        }
    }

    class DemoAdapterItemClickListener implements BaseWXRecyclerAdapter.OnWXItemClickListener {
        @Override
        public void onWXItemClick(View view) {
            Toast.makeText(WXRecyclerViewDemo.this, "WXItem点击了.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(WXRecyclerViewDemo.this, "第" + position + "点击了。", Toast.LENGTH_SHORT).show();
        }
    }

}
