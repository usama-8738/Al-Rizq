package com.example.alrizq.ui.ngo.publicdonations;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alrizq.R;
import com.example.alrizq.databinding.YourDonationLayoutBinding;
import com.example.alrizq.ui.yourdonation.yourDonationPojo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PublicDonationsAdapter extends FirebaseRecyclerAdapter<yourDonationPojo, PublicDonationsAdapter.viewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    DonationClick donationClick;
    Context context;

    public PublicDonationsAdapter(@NonNull FirebaseRecyclerOptions<yourDonationPojo> options, Context context, DonationClick donationClick) {
        super(options);
        this.context = context;
        this.donationClick = donationClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull PublicDonationsAdapter.viewHolder holder, int position, @NonNull yourDonationPojo model) {
        holder.binding.itemName.setText(Html.fromHtml("<b>" + model.getItemName() + "</b> "));
        holder.binding.quantity.setText(Html.fromHtml("Quantity: " + "<b>" + model.getQuantity() + "</b>"));
        holder.binding.timeOPrepration.setText(Html.fromHtml("Preparation Time: " + "<b>" + model.getTimeOfPreparation() + "</b>"));
        holder.binding.date.setText(Html.fromHtml("Date: " + "<b>" + model.getCreatedAt() + "</b>"));
        holder.binding.card.setOnClickListener(view -> {
            donationClick.clickListener(model);
        });

        Glide.with(context)
                .load(model.getImage())
                .placeholder(R.drawable.rizq)
                .into(holder.binding.foodImage);
    }

    @NonNull
    @Override
    public PublicDonationsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        YourDonationLayoutBinding binding = YourDonationLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new viewHolder(binding);
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        YourDonationLayoutBinding binding;

        public viewHolder(@NonNull YourDonationLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    public interface DonationClick {
        public void clickListener(yourDonationPojo model);
    }
}
