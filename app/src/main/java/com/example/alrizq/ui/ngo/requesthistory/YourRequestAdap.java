package com.example.alrizq.ui.ngo.requesthistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alrizq.databinding.YourRequestLayoutBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class YourRequestAdap extends FirebaseRecyclerAdapter<YourRequestModel, YourRequestAdap.ViewHolder> {

    HireClick hireClick;
    DeliveredClick deliveredClick;
    CardClick cardClick;

    public YourRequestAdap(FirebaseRecyclerOptions<YourRequestModel> options, HireClick hireClick, DeliveredClick deliveredClick, CardClick cardClick) {
        super(options);
        this.deliveredClick = deliveredClick;
        this.hireClick = hireClick;
        this.cardClick = cardClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull YourRequestAdap.ViewHolder holder, int position, @NonNull YourRequestModel model) {

        holder.binding.quantity.setText(String.format("Quantity: %s", model.getQuantity()));
        holder.binding.status.setText(String.format("Status: %s", model.getStatus()));
        holder.binding.date.setText(String.format("Date: %s", model.getCreatedAt()));

        if (model.status.equals("Accepted")) {
            holder.binding.accept.setVisibility(View.VISIBLE);
            holder.binding.accept.setText(model.getAcceptName());

            holder.binding.hire.setVisibility(View.VISIBLE);
        } else if (model.status.equals("Assigned")) {
            holder.binding.assigned.setVisibility(View.VISIBLE);
            holder.binding.assigned.setText(model.getAcceptName());

            holder.binding.delivered.setVisibility(View.VISIBLE);

        } else {
            holder.binding.hire.setVisibility(View.GONE);
            holder.binding.delivered.setVisibility(View.GONE);
            holder.binding.assigned.setVisibility(View.GONE);
            holder.binding.accept.setVisibility(View.GONE);
        }

        holder.binding.hire.setOnClickListener(view -> {
            hireClick.hireListener(model);
        });

        holder.binding.delivered.setOnClickListener(view -> {
            deliveredClick.deliveredListener(model);
        });
        holder.binding.requestCard.setOnClickListener(view -> {
            cardClick.cardListener(model);
        });
    }

    @NonNull
    @Override
    public YourRequestAdap.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        YourRequestLayoutBinding binding = YourRequestLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new YourRequestAdap.ViewHolder(binding);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        YourRequestLayoutBinding binding;

        public ViewHolder(@NonNull YourRequestLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    public interface HireClick {
        public void hireListener(YourRequestModel model);
    }

    public interface CardClick {
        public void cardListener(YourRequestModel model);
    }

    public interface DeliveredClick {
        public void deliveredListener(YourRequestModel model);
    }
}
