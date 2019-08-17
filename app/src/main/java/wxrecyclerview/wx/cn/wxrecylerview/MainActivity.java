package wxrecyclerview.wx.cn.wxrecylerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button toWXRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initView(){
        toWXRecyclerView = findViewById(R.id.toWXRecycler);
    }

    private void initEvent(){
        toWXRecyclerView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toWXRecycler:
                Intent wxRecyclerIt = new Intent(MainActivity.this,WXRecyclerViewDemo.class);
                startActivity(wxRecyclerIt);
                break;
                default:
                    break;
        }
    }
}
