package com.example.alrizq.donation;

import android.annotation.SuppressLint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alrizq.databinding.DonationLayoutBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class DonationAdapter extends FirebaseRecyclerAdapter<donationPojo, DonationAdapter.ViewHolder> {


    public DonationAdapter(FirebaseRecyclerOptions<donationPojo> options) {
        super(options);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull donationPojo model) {

        holder.binding.itemName.setText(Html.fromHtml("<b>" + model.getItemName() + "</b> "));
        holder.binding.quantity.setText(Html.fromHtml("<b>" + holder.binding.quantity.getText() + "</b> " + "" + model.getQuantity()));
        holder.binding.timeOPrepration.setText(Html.fromHtml("<b>" + holder.binding.timeOPrepration.getText() + "</b> " + "" + model.getTimeOfPreparation()));
        holder.binding.date.setText(Html.fromHtml("<b>" + holder.binding.date.getText() + "</b> " + "" + model.getCreatedAt()));


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_layout, parent, false);
        DonationLayoutBinding binding = DonationLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new DonationAdapter.ViewHolder(binding);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        DonationLayoutBinding binding;


        public ViewHolder(@NonNull DonationLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;


        }
    }

}
