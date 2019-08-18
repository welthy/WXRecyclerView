package wxrecyclerview.wx.cn.wxrecylerview.swiperecyclerview_demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;

import wxrecyclerview.wx.cn.wxrecylerview.R;

public class SwipeRecyclerViewDemo extends Activity {

    private final String TAG = "swipe";

    private SwipeRecyclerView swipeRecyclerview;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.swipe_recyclerview_layout);
        swipeRecyclerview = findViewById(R.id.swipeRecyclerview);

        // 默认构造，传入颜色即可。
        int color = getResources().getColor(R.color.colorAccent);
        RecyclerView.ItemDecoration itemDecoration = new DefaultItemDecoration(color);

// 或者：颜色，宽，高，最后一个参数是不画分割线的ViewType，可以传入多个。
      //  itemDecoration = new DefaultDecoration(color, width, height, excludeViewType);

// 或者：例如下面的123都是不画分割线的ViewType：
     //   itemDecoration = new DefaultDecoration(color, width, height, 1, 2, 3);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        swipeRecyclerview.addItemDecoration(itemDecoration);

        swipeRecyclerview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG,"Swipe Item Click --- "+position);
            }
        });

        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem leftItem = new SwipeMenuItem(mContext);
                // 各种文字和图标属性设置。
                leftMenu.addMenuItem(leftItem); // 在Item左侧添加一个菜单。

                // 各种文字和图标属性设置。
                SwipeMenuItem rightItem = new SwipeMenuItem(mContext);
                // 在Item右侧添加一个菜单。
                leftMenu.addMenuItem(rightItem);

                // 注意：哪边不想要菜单，那么不要添加即可。
            }
        };

        //添加Item监听器
        OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();

                // 左侧还是右侧菜单：
                int direction = menuBridge.getDirection();
                // 菜单在Item中的Position：
                int menuPosition = menuBridge.getPosition();
            }
        };

        // 设置监听器。
        swipeRecyclerview.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        swipeRecyclerview.setOnItemMenuClickListener(mItemMenuClickListener);

        // 拖拽排序，默认关闭。
        swipeRecyclerview.setLongPressDragEnabled(true);
        // 侧滑删除，默认关闭。
        swipeRecyclerview.setItemViewSwipeEnabled(true);
    }
}
