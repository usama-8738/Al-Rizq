package com.example.alrizq.ui.restaurent.donationrequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alrizq.R;
import com.example.alrizq.ui.ngo.request.RequestModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.card.MaterialCardView;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestAdapter extends FirebaseRecyclerAdapter<RequestModel, RequestAdapter.viewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    Context context;
    CardClick buttonClick;

    public RequestAdapter(@NonNull FirebaseRecyclerOptions<RequestModel> options, Context context, CardClick buttonClick) {
        super(options);
        this.context = context;
        this.buttonClick = buttonClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull RequestAdapter.viewHolder holder, int position, @NonNull RequestModel model) {
        holder.name.setText(model.getNgoName());
        holder.qty.setText(String.format("Quantity: %s", model.getQuantity()));
        holder.time.setText(String.format("Need Before: %s", model.getNeedBefore()));
        holder.desc.setText(model.getDescription());

//        Glide.with(context)
//                .load(model.get.getProfile())
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .placeholder(R.drawable.ngo)
//                .into(holder.imageView);


        holder.card.setOnClickListener(view -> {
            buttonClick.clickListener(model);
        });


    }

    @NonNull
    @Override
    public RequestAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donationrequest_layout, parent, false);
        return new viewHolder(view);
    }

    public interface CardClick {
        void clickListener(RequestModel model);
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView name, qty, time, desc;
        MaterialCardView card;
        CircleImageView imageView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.itemName);
            qty = itemView.findViewById(R.id.qty);
            time = itemView.findViewById(R.id.time);
            desc = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.foodImage);
            card = itemView.findViewById(R.id.card);

        }
    }
}
