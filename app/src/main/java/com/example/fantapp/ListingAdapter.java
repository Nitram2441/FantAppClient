package com.example.fantapp;

import android.util.JsonToken;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ListingViewHolder> {
    OnClickListener listener = position -> {};
    List<Listing> listings;

    public ListingAdapter(){
        this.listings = new ArrayList<>();
    }

    public List<Listing> getListings(){
        return listings;
    }

    public void setListings(List<Listing> listings){
        this.listings = listings;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listings, parent, false);
        return new ListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingViewHolder holder, int position){
        Listing listing = getListings().get(position);
        System.out.println("Listing id: "+listing.getId());
        holder.text.setText(listing.title);
        holder.desc.setText(listing.description);

    }

    @Override
    public int getItemCount(){
        System.out.println("Listing list size: " + getListings().size());
        return getListings().size();
    }

    interface OnClickListener {
        void onClick(int Position);
    }

    class ListingViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        TextView desc;


        public ListingViewHolder(View view){
            super(view);
            view.setOnClickListener(v -> listener.onClick(getAdapterPosition()));
            this.text = view.findViewById(R.id.text);
            this.desc = view.findViewById(R.id.desc);

        }

    }
}
