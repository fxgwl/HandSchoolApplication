package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.ListItemBean;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/7/25.
 */

public class ListItemAdapter extends BaseAdapter {

    private Context context;
    private List<ListItemBean> mlist;
    private int num;

    public ListItemAdapter(Context context, List<ListItemBean> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_listitem_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final ViewHolder finalHolder = holder;
        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = finalHolder.tvNum.getText().toString();
                num = Integer.parseInt(string);
                num++;
                finalHolder.tvNum.setText(num + "");
            }
        });

        holder.tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = finalHolder.tvNum.getText().toString();
                num = Integer.parseInt(string);
                if (num == 1 || num == 0) {
                    num = 0;
                } else {
                    num--;
                }
                finalHolder.tvNum.setText(num + "");
            }
        });

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_select)
        CheckBox ivSelect;
        @BindView(R.id.iv_imag)
        ImageView ivImag;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_sub)
        TextView tvSub;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_add)
        TextView tvAdd;
        @BindView(R.id.ll_edit_num)
        LinearLayout llEditNum;
        @BindView(R.id.ll_number)
        LinearLayout llNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
