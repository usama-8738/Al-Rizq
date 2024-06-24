package com.example.alrizq.ui.yourdonation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alrizq.R;
import com.example.alrizq.databinding.YourDonationLayoutBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class YourDonationAdap extends FirebaseRecyclerAdapter<yourDonationPojo, YourDonationAdap.ViewHolder> {
    DatabaseReference databaseReference;
    Context context;
    CardClick cardClick;
    int activity;

    public YourDonationAdap(FirebaseRecyclerOptions<yourDonationPojo> options, Context context, int activity, CardClick cardClick) {
        super(options);
        this.context = context;
        this.cardClick = cardClick;
        this.activity = activity;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull yourDonationPojo model) {
        holder.binding.itemName.setText(Html.fromHtml("<b>" + model.getItemName() + "</b> "));
        holder.binding.quantity.setText("Quantity: "+ model.getQuantity());
        holder.binding.timeOPrepration.setText("Time of prepration: "  + model.getTimeOfPreparation());
        holder.binding.date.setText("Date: "+ model.getCreatedAt());


        if (activity == 1) {
            holder.binding.delete.setVisibility(View.VISIBLE);
        }

        holder.binding.delete.setOnClickListener(view -> {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Delete donation!");
            alertDialog.setMessage("Are you sure you want to delete donation?");
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("donation").child(model.getId());
                    databaseReference.removeValue();
                }
            });
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = alertDialog.create();
            dialog.show();
        });

        Glide.with(context)
                .load(model.getImage())
                .placeholder(R.drawable.person1)
                .into(holder.binding.foodImage);

        holder.binding.card.setOnClickListener(view -> {
            cardClick.clickListener(model);
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        YourDonationLayoutBinding binding = YourDonationLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding);
    }


    public interface CardClick {
        void clickListener(yourDonationPojo model);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {


        YourDonationLayoutBinding binding;

        public ViewHolder(@NonNull YourDonationLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
