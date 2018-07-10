package com.example.pushoffer;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pushoffer.POJO.Offer;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    ArrayList<Offer> offerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        offerArrayList = PushOffer.getAllOffers();

        RecyclerView rvOffer = findViewById(R.id.rv_Offer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        final OfferListAdapter offerListAdapter = new OfferListAdapter();
        rvOffer.setLayoutManager(layoutManager);
        rvOffer.setAdapter(offerListAdapter);
        rvOffer.addItemDecoration(new DividerItemDecoration(rvOffer.getContext(), DividerItemDecoration.VERTICAL));

        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PushOffer.deleteAllOffers();
                offerArrayList.clear();
                offerListAdapter.notifyDataSetChanged();
            }
        });
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private class OfferViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvBody,tvDate;
        ImageView cancel;
        View rootView;

        public OfferViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            cancel=itemView.findViewById(R.id.list_cancel);
            tvTitle=itemView.findViewById(R.id.list_msg_title);
            tvBody=itemView.findViewById(R.id.list_msg_body);
            tvDate=itemView.findViewById(R.id.list_msg_date);
        }

    }

    private class OfferListAdapter extends RecyclerView.Adapter<OfferViewHolder> {
        @NonNull
        @Override
        public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            LayoutInflater li =   getLayoutInflater();
//            View convertView = li.inflate(R.layout.list_item, viewGroup, false);

            View convertView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);

            OfferViewHolder holder = new OfferViewHolder(convertView);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull OfferViewHolder offerViewHolder, final int i) {
            final Offer of = offerArrayList.get(i);

            if(of.isOpen()){
                offerViewHolder.rootView.setAlpha(0.6f);
            }
//            offerViewHolder.tvId.setText(of.getMsgID());
//            offerViewHolder.tvAct.setText(of.getScreen());
//            offerViewHolder.tvPriority.setText(of.getPriority()+"");
//            offerViewHolder.tvValid.setText(of.getPosition());

//            offerViewHolder.b1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    Toast.makeText(getApplicationContext(), "Clicked on Offer: " + i, Toast.LENGTH_LONG).show();
//                    offerArrayList.remove(i);
//                    PushOffer.deleteOffer(of.getMsgID());
//                    notifyDataSetChanged();
//                }
//            });

            offerViewHolder.tvTitle.setText(of.getTitle());
            offerViewHolder.tvBody.setText(of.getBody());
            offerViewHolder.tvDate.setText(of.getValidity());
            offerViewHolder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    offerArrayList.remove(i);
                    PushOffer.deleteOffer(of.getMsgID());
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return offerArrayList.size();
        }
    }

}
