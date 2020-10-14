package com.example.fantapp;

import android.util.JsonToken;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ListingViewHolder> implements Filterable {
    OnClickListener listener = position -> {};
    List<Listing> listings;
    List<Listing> listingsListAll;

    public ListingAdapter(){
        this.listings = new ArrayList<>();
        this.listingsListAll = new ArrayList<>(listings);
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
        //LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listings, parent, false);
        return new ListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingViewHolder holder, int position){
        Listing listing = getListings().get(position);
        System.out.println("Listing id: "+listing.getId());
        System.out.println("onBindViewHolder");
        holder.text.setText(listing.title);
        holder.desc.setText(listing.description);

    }

    @Override
    public int getItemCount(){
        System.out.println("Listing list size: " + getListings().size());
        return getListings().size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Listing> filteredList = new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredList.addAll(listingsListAll);
            }
            else{
                for (Listing listing: listingsListAll){
                    if(listing.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(listing);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listings.clear();
            listings.addAll((Collection<? extends Listing>) results.values);
            notifyDataSetChanged();
        }
    };
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
