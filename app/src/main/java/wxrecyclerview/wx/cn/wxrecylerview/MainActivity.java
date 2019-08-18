package wxrecyclerview.wx.cn.wxrecylerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import wxrecyclerview.wx.cn.wxrecylerview.swiperecyclerview_demo.SwipeRecyclerViewDemo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button toWXRecyclerView,toSwipeRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initView(){
        toWXRecyclerView = findViewById(R.id.toWXRecycler);
        toSwipeRecycler = findViewById(R.id.toSwipeRecycler);
    }

    private void initEvent(){
        toWXRecyclerView.setOnClickListener(this);
        toSwipeRecycler.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toWXRecycler:
                Intent wxRecyclerIt = new Intent(MainActivity.this,WXRecyclerViewDemo.class);
                startActivity(wxRecyclerIt);
                break;
            case R.id.toSwipeRecycler:
                Intent swipeRecyclerIt = new Intent(MainActivity.this, SwipeRecyclerViewDemo.class);
                startActivity(swipeRecyclerIt);
                break;
            default:
                break;
        }
    }
}
