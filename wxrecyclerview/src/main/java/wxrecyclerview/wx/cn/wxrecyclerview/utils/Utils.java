package wxrecyclerview.wx.cn.wxrecyclerview.utils;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.DisplayMetrics;

public class Utils {

    public static int getScreenWidth(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }
}
