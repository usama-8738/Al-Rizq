package com.example.alrizq.ui.donor.aspay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.alrizq.R;
import com.example.alrizq.login.User;

import java.util.ArrayList;
import java.util.List;

public class NgoListAdapter extends ArrayAdapter<User> {
    private final ListFilter listFilter = new ListFilter();
    String link;
    private Context mContext;
    private List<User> dataListAllItems;
    List<User> userList;

    public NgoListAdapter(@NonNull Context context, List<User> userList) {
        super(context, 0, userList);
        this.userList = userList;

    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    /**
     * Used in filter operation
     *
     * @return listFilter
     */
    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.ngolist, parent, false

            );
        }

        TextView ngoname = convertView.findViewById(R.id.ngoname);
        ImageView ngoicon = convertView.findViewById(R.id.ngoicon);


        ngoname.setText(userList.get(position).getName());

        Glide.with(getContext())
                .load(link + "/assets/uploads/bank/" + userList.get(position).getImage())
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ngoicon);

        return convertView;
    }


    public class ListFilter extends Filter {
        private final Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList(userList);
                }
            }
            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();
                ArrayList<User> matchValues = new ArrayList<User>();
                for (User dataItem : dataListAllItems) {
                    if (dataItem.getName().toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }
                results.values = matchValues;
                results.count = matchValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                userList = (List<User>) results.values;
            } else {
                userList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
