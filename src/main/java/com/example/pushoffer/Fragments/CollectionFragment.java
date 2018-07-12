package com.example.pushoffer.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pushoffer.Adapters.OfferListAdapter;
import com.example.pushoffer.POJO.Offer;
import com.example.pushoffer.R;

import java.util.ArrayList;

public class CollectionFragment extends Fragment {

    ArrayList<Offer> offerArrayList;
    RecyclerView rvOffer;

    public CollectionFragment() {
        // Required empty public constructor
    }

    public static CollectionFragment newInstance(ArrayList<Offer> arrayList){
        Bundle args=new Bundle();
        args.putParcelableArrayList("array",arrayList);
        CollectionFragment collectionFragment=new CollectionFragment();
        collectionFragment.setArguments(args);
        return collectionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);

        offerArrayList=getArguments().getParcelableArrayList("array");
        rvOffer = rootView.findViewById(R.id.rv_Offer);
        setOfferArrayList();
        return rootView;
    }

    private void setOfferArrayList(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final OfferListAdapter offerListAdapter;
        offerListAdapter = new OfferListAdapter(offerArrayList,getContext());
        rvOffer.setLayoutManager(layoutManager);
        rvOffer.setAdapter(offerListAdapter);
        rvOffer.addItemDecoration(new DividerItemDecoration(rvOffer.getContext(), DividerItemDecoration.VERTICAL));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                final Offer deletedItem = offerArrayList.get(viewHolder.getAdapterPosition());
                final int deletedIndex = viewHolder.getAdapterPosition();
                offerListAdapter.removeItem(viewHolder.getAdapterPosition());

                Snackbar snackbar = Snackbar
                        .make(rvOffer, " Removed! ", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        offerListAdapter.restoreItem(deletedItem, deletedIndex);
                    }
                });
                snackbar.show();
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvOffer);
    }

}
