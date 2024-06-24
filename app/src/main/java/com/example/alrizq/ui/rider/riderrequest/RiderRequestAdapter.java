package com.example.alrizq.ui.rider.riderrequest;

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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import de.hdodenhof.circleimageview.CircleImageView;

public class RiderRequestAdapter extends FirebaseRecyclerAdapter<RequestModel, RiderRequestAdapter.viewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    AcceptClick acceptClick;
    RefuseClick refuseClick;
    DeleteClick deleteClick;
    CardClick cardClick;
    int activity;

    public RiderRequestAdapter(@NonNull FirebaseRecyclerOptions<RequestModel> options, int activity, Context context, AcceptClick acceptClick, RefuseClick refuseClick, DeleteClick deleteClick, CardClick cardClick) {
        super(options);
        this.context = context;
        this.acceptClick = acceptClick;
        this.refuseClick = refuseClick;
        this.deleteClick = deleteClick;
        this.cardClick = cardClick;
        this.activity = activity;

    }

    @Override
    protected void onBindViewHolder(@NonNull RiderRequestAdapter.viewHolder holder, int position, @NonNull RequestModel model) {
        holder.ngoName.setText(model.getNgoName());
        holder.address.setText(model.getAddress());
        holder.date.setText(String.format("Date: %s", model.getCreatedAt()));
        holder.status.setText(String.format("Status: %s", model.getStatus()));

        if (model.getStatus().equals("Request Accepted")) {
            holder.accept.setVisibility(View.GONE);
        } else if (model.getStatus().equals("Request Refused")) {
            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
        }

        if (activity == 1) {
            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
            holder.delete.setVisibility(View.VISIBLE);
        }

        holder.accept.setOnClickListener(view -> {
            acceptClick.acceptListener(model);
        });

        holder.reject.setOnClickListener(view -> {
            refuseClick.refuseListener(model);
        });

        holder.delete.setOnClickListener(view -> {
            deleteClick.deleteListener(model);
        });

        holder.card.setOnClickListener(view -> {
            cardClick.clickListener(model);
        });
    }

    @NonNull
    @Override
    public RiderRequestAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiring_request_layout, parent, false);

        return new viewHolder(view);
    }

    public interface AcceptClick {
        void acceptListener(RequestModel model);
    }

    public interface RefuseClick {
        void refuseListener(RequestModel model);
    }

    public interface DeleteClick {
        void deleteListener(RequestModel model);
    }

    public interface CardClick {
        void clickListener(RequestModel model);
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        CircleImageView userImage;
        TextView ngoName, date, status, address;
        MaterialCardView card;
        MaterialButton accept, reject, delete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.hireCard);
            userImage = itemView.findViewById(R.id.userImage);
            ngoName = itemView.findViewById(R.id.ngoName);
            date = itemView.findViewById(R.id.date);
            address = itemView.findViewById(R.id.location);
            status = itemView.findViewById(R.id.status);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.refuse);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
