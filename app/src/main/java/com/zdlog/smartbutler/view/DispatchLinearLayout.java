package com.zdlog.smartbutler.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * 项目名：SmartButle
 * 包名 ： com.zdlog.smartbutler.view
 * 文件名：DispatchLinearLayout
 * 创建者：jiushaba
 * 创建时间：2017/4/17 0017下午 12:30
 * 描述： 事件分发
 */
public class DispatchLinearLayout extends LinearLayout{
    private DispatchKeyEventListener dispatchKeyEventListener;

    public DispatchKeyEventListener getDispatchKeyEventListener() {
        return dispatchKeyEventListener;
    }

    public void setDispatchKeyEventListener(DispatchKeyEventListener dispatchKeyEventListener) {
        this.dispatchKeyEventListener = dispatchKeyEventListener;
    }

    public DispatchLinearLayout(Context context) {
        super(context);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //接口
    public static interface DispatchKeyEventListener{
       boolean dispatchKeyEvent(KeyEvent event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //如果不为空，说明调用了 去获取事件
        if (dispatchKeyEventListener != null) {
            return dispatchKeyEventListener.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);

    }

}
