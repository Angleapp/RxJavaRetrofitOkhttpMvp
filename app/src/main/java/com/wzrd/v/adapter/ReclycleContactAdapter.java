package com.wzrd.v.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wzrd.R;
import com.wzrd.m.been.TSYSCONTANTS;
import com.wzrd.m.db.manger.ContactsManager;
import com.wzrd.p.inteface.AdapterClickPosition;
import com.wzrd.v.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lk on 2018/1/5.
 */

public class ReclycleContactAdapter extends RecyclerView.Adapter<ReclycleContactAdapter.ViewHolder> {
    private Context mContext;
    private List<TSYSCONTANTS> tsyscontantsList;
    private AdapterClickPosition.position adapterClickPosition;

    public ReclycleContactAdapter(Context mContext, List<TSYSCONTANTS> tsyscontantsList, AdapterClickPosition.position adapterClickPosition) {
        this.mContext = mContext;
        this.tsyscontantsList = tsyscontantsList;
        this.adapterClickPosition = adapterClickPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        View view = holder.itemView;
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Utils.ToastShort(mContext,"posiotion--"+position);
                adapterClickPosition.adapterposition(position);
            }
        });


        final TSYSCONTANTS modlie = tsyscontantsList.get(position);
        holder.tv_contact_name.setText(modlie.getT_sys_contacts_name());

        Glide.with(view.getContext())
                .load(modlie.getT_sys_usericonpath())
                .placeholder(R.mipmap.icon_signin_default)
                .bitmapTransform(new GlideCircleTransform(view.getContext()))
                .error(R.mipmap.icon_signin_default)
                .into(holder.iv_contact_iocn);
        holder.btn_delcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Utils.ToastShort(mContext, "删除");
                ContactsManager manager = new ContactsManager(mContext);
                manager.deleteUser(modlie);
                notifyDataSetChanged();
                List<TSYSCONTANTS> list = new ArrayList<>();
                tsyscontantsList.remove(modlie);
                list.addAll(tsyscontantsList);
                tsyscontantsList.clear();
                tsyscontantsList.addAll(list);
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return tsyscontantsList == null ? 0 : tsyscontantsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_contact_name;
        ImageView iv_contact_iocn;
        Button btn_delcontact;
        LinearLayout ll_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_contact_name = (TextView) itemView.findViewById(R.id.tv_contact_name);
            iv_contact_iocn = (ImageView) itemView.findViewById(R.id.iv_contact_iocn);
            btn_delcontact = (Button) itemView.findViewById(R.id.btn_delcontact);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);

        }
    }
}
