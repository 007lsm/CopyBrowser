package com.example.admin.myapplication.module.me;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseFragment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {

    private ImageView ivHomeBg;
    private TextView tvMottoMe;
    private LinearLayout llBg;
    private CircleImageView cycleHeader;
    private TextView tvUsernameMe;
    private RecyclerView rvSetting;
    private FloatingActionButton fab;
    String[] settingDatas = new String[]{"日历","天气","LED字幕","手电筒","二维码", "设置"};
    int[] imageDatas = new int[]{R.drawable.me_rili,R.drawable.me_tianqi,R.drawable.me_led,
            R.drawable.me_deng,R.drawable.me_erweima,R.drawable.me_setting};

    public static MeFragment getInstance(){
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    protected void initView(View view) {
        ivHomeBg = view.findViewById(R.id.iv_home_bg);
        tvMottoMe = view.findViewById(R.id.tv_motto_me);
        llBg = view.findViewById(R.id.ll_bg);
        cycleHeader = view.findViewById(R.id.cycle_header);
        tvUsernameMe = view.findViewById(R.id.tv_username_me);
        rvSetting = view.findViewById(R.id.rv_setting);
        fab = view.findViewById(R.id.fab);

        rvSetting.setLayoutManager(new LinearLayoutManager(getContext()));
        GeneralAdapter adapter = new GeneralAdapter(getContext());
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (position){
                    case 0://日历
                        startActivity(new Intent(getContext(),CalendarActivity.class));
                        break;
                    case 1://天气
                        startActivity(new Intent(getContext(),CalendarActivity.class));
                        break;
                    case 2://手电筒
                        startActivity(new Intent(getContext(),CalendarActivity.class));
                        break;
                    case 3://二维码
                        startActivity(new Intent(getContext(),CalendarActivity.class));
                        break;

                }
            }
        });
        rvSetting.setAdapter(adapter);
    }

    public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.MyViewHolder> {
        Context context;
        public GeneralAdapter(Context context){
            this.context = context;
        }

        private OnItemClickListener onItemClickListener = null;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = View.inflate(context, R.layout.content_me,null);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
            holder.textView.setText(settingDatas[position]);
            holder.imageView.setBackgroundResource(imageDatas[position]);
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null) {
                        onItemClickListener.onItemClick(holder.item, position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return settingDatas.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView textView;
            LinearLayout item;
            public MyViewHolder(View itemView) {
                super(itemView);
                item = itemView.findViewById(R.id.ll_item);
                imageView = itemView.findViewById(R.id.iv_item_me);
                textView = itemView.findViewById(R.id.tv_item_me);
            }
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_me;
    }

    @Override
    protected void managerArguments() {
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
