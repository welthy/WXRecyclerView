package wxrecyclerview.wx.cn.wxrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;

import wxrecyclerview.wx.cn.wxrecyclerview.adapters.WXRecyclerAdapter;
import wxrecyclerview.wx.cn.wxrecyclerview.viewholder.WXViewHolder;

public class WXItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private WXRecyclerAdapter wxRecyclerAdapter;

    public WXItemTouchHelperCallback(WXRecyclerAdapter wxRecyclerAdapter) {
        this.wxRecyclerAdapter = wxRecyclerAdapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof WXViewHolder){
            int dragFlags = ItemTouchHelper.ACTION_STATE_DRAG;
            int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            return makeMovementFlags(dragFlags,swipeFlags);
        }
        return 0;

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder moveHolder, @NonNull RecyclerView.ViewHolder target) {
        if ((((WXRecyclerView)recyclerView).state == WXRecyclerViewState.SWIPE) && (target instanceof WXViewHolder)){
            Collections.swap(wxRecyclerAdapter.getDatas(),moveHolder.getAdapterPosition(),target.getAdapterPosition());
            wxRecyclerAdapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }
}
