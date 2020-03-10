package com.xbing.app.component.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xbing.app.basic.common.DpPxUtil;
import com.xbing.app.component.R;

public class MainIconItem extends RelativeLayout {
    private String TAG = MainIconItem.class.getSimpleName();

    private int textAlign = 1;
    private int textMarginLeft = 0;

    private ImageView jiaobiao;
    private TextView  title;

    /**
     * 在java代码里new的时候会用到
     * @param context
     */
    public MainIconItem(Context context) {
        super(context);
    }

    /**
     * 在xml布局文件中使用时自动调用
     * @param context
     */
    public MainIconItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MainIconItem);
        textAlign = ta.getInt(R.styleable.MainIconItem_textAlign, 1);
        textMarginLeft = ta.getInteger(R.styleable.MainIconItem_marginLeft, 0);
        Log.e(TAG, "textAlign = " + textAlign + " , textMarginLeft = " + textMarginLeft);
        ta.recycle();
        initView(context);
    }

    /**
     * 不会自动调用，如果有默认style时，在第二个构造函数中调用
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MainIconItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 只有在API版本>21时才会用到
     * 不会自动调用，如果有默认style时，在第二个构造函数中调用
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MainIconItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    //初始化UI，可根据业务需求设置默认值。
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.hy_type_main_icon_item, this, true);
        title = (TextView) findViewById(R.id.tv_title);
        jiaobiao = (ImageView)findViewById(R.id.iv_jiaobiao);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)title.getLayoutParams();
        if(textAlign == 0){
            params.removeRule(CENTER_IN_PARENT);
            params.addRule(CENTER_VERTICAL);
            params.addRule(ALIGN_PARENT_LEFT);
            params.leftMargin = DpPxUtil.dip2px(context, textMarginLeft);
        }else{
            params.removeRule(ALIGN_PARENT_LEFT);
            params.removeRule(CENTER_VERTICAL);
            params.leftMargin = 0;
            params.addRule(CENTER_IN_PARENT);
        }
        title.setLayoutParams(params);

    }

}
