package com.example.alrizq.ui.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alrizq.R;
import com.example.alrizq.login.User;
import com.example.alrizq.utils.Constant;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends FirebaseRecyclerAdapter<User, UserListAdapter.viewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     */
    Context context;
    ButtonClick buttonClick;
    CardClick cardLink;
    int activity;

    public UserListAdapter(@NonNull FirebaseRecyclerOptions<User> options, Context context, ButtonClick buttonClick, CardClick cardLink) {
        super(options);
        this.context = context;
        this.buttonClick = buttonClick;
        this.cardLink = cardLink;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserListAdapter.viewHolder holder, int position, @NonNull User model) {

        activity = Constant.activity;

        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.address.setText(model.getAddress());

        if (activity == 1) {
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardLink.clickListener(model);
                }
            });
        } else {
            if (model.getStatus().equals("0")) {
                holder.block.setVisibility(View.VISIBLE);
                holder.unBlock.setVisibility(View.GONE);
                holder.block.setOnClickListener(view -> {
                    buttonClick.clickListener(model.getId(), "1");
                });
            } else {
                holder.unBlock.setVisibility(View.VISIBLE);
                holder.block.setVisibility(View.GONE);
                holder.unBlock.setOnClickListener(view -> {
                    buttonClick.clickListener(model.getId(), "0");
                });
            }
        }

        Glide.with(context)
                .load(model.getImage())
                .placeholder(R.drawable.person1)
                .into(holder.userImage);
    }

    @NonNull
    @Override
    public UserListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_details_layout, parent, false);
        return new viewHolder(view);
    }

    public interface ButtonClick {
        void clickListener(String id, String status);
    }

    public interface CardClick {
        void clickListener(User model);
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView userImage;
        TextView name, email, address;
        MaterialCardView card;
        MaterialButton block, unBlock;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            name = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.userEmail);
            address = itemView.findViewById(R.id.userAddress);
            card = itemView.findViewById(R.id.userCard);
            block = itemView.findViewById(R.id.block);
            unBlock = itemView.findViewById(R.id.unblock);

        }
    }
}
