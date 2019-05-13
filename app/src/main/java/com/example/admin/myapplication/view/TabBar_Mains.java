package com.example.admin.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.myapplication.R;

public class TabBar_Mains extends LinearLayout {

    private String mName;
    private Drawable mIcon;
    private ImageView ivIconTabbar;
    private TextView tvTextTabbar;

    public TabBar_Mains(Context context) {
        this(context, null);
    }

    public TabBar_Mains(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.tabbar_remeber, this, true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabBar_Attrs);
        mName = array.getString(R.styleable.TabBar_Attrs_name);
        mIcon = array.getDrawable(R.styleable.TabBar_Attrs_icon);
        array.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ivIconTabbar = findViewById(R.id.iv_icon_tabbar);
        tvTextTabbar = findViewById(R.id.tv_text_tabbar);
        if(!TextUtils.isEmpty(mName)) {
            setName(mName);
        }
        if(mIcon != null) {
            setIcon(mIcon);
        }
    }

    public void setName(String name) {
        tvTextTabbar.setText(name);
    }

    public void setIcon(Drawable drawable) {
        ivIconTabbar.setImageDrawable(drawable);
    }
}
