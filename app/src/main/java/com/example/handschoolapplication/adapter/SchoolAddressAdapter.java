package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.AddressBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/10.
 */

public class SchoolAddressAdapter extends BaseAdapter {
    private Context context;
    private List<AddressBean.DataBean> mList;
    private int size = 2;
    private DeleteClick deleteClick;

    public SchoolAddressAdapter(Context context, List<AddressBean.DataBean> mList, DeleteClick deleteClick) {
        this.context = context;
        this.mList = mList;
        this.deleteClick = deleteClick;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            size = mList.size();
        }
        return size;
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
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.school_address_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        AddressBean.DataBean addressBean = mList.get(position);
        holder.tvAddress.setText(addressBean.getSd_city() + "\n" + addressBean.getSd_content());
        holder.tv.setText("地址" + ":");
        holder.tvAddressDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteClick != null) {
                    deleteClick.delet(position);
                }
            }
        });

        holder.tvEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteClick != null) {
                    deleteClick.edit(position);
                }
            }
        });
        return view;

    }

    public interface DeleteClick {
        void delet(int position);

        void edit(int pisition);
    }

    static class ViewHolder {
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_address_delete)
        TextView tvAddressDelete;
        @BindView(R.id.tv_address_edit)
        TextView tvEditAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
