package com.example.pushoffer.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pushoffer.Activities.PushOffer;
import com.example.pushoffer.POJO.Offer;
import com.example.pushoffer.R;

import java.util.ArrayList;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.OfferViewHolder>{

    private ArrayList<Offer> offerArrayList;
    private Context mContext;

    public OfferListAdapter(ArrayList<Offer> offerArrayList, Context mContext) {
        this.offerArrayList = offerArrayList;
        this.mContext = mContext;
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvBody, tvDate;
        ImageView img;
        View rootView;

        public OfferViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
//            cancel = itemView.findViewById(R.id.list_cancel);
            tvTitle = itemView.findViewById(R.id.list_msg_title);
            tvBody = itemView.findViewById(R.id.list_msg_body);
            tvDate = itemView.findViewById(R.id.list_msg_date);
            img = itemView.findViewById(R.id.list_img);
        }

    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

        OfferViewHolder holder = new OfferViewHolder(convertView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder offerViewHolder, final int i) {
        final Offer of = offerArrayList.get(i);

        if (of.isOpen()) {
            offerViewHolder.rootView.setAlpha(0.6f);
        }
        PushOffer.setImage(offerViewHolder.img, of.getUrl());
        offerViewHolder.tvTitle.setText(of.getTitle());
        offerViewHolder.tvBody.setText(of.getBody());
        offerViewHolder.tvDate.setText(of.getValidity());
    }

    @Override
    public int getItemCount() {
        return offerArrayList.size();
    }

    public void removeItem(int position) {
        PushOffer.deleteOffer(offerArrayList.get(position).getMsgID());
        offerArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Offer offer, int position) {
        offerArrayList.add(position, offer);
        PushOffer.addOffer(offer);
        notifyItemInserted(position);
    }
}

