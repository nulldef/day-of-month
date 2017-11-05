package me.hextech.zeus;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MonthsAdapter extends RecyclerView.Adapter<MonthsAdapter.ViewHolder> {
    private MonthClickListener clickListener = null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.month_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String month = holder.itemView.getContext().getResources().getStringArray(R.array.months)[position];
        holder.text.setText(month);
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) clickListener.onClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    void setOnClickListener(MonthClickListener listener) {
        this.clickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView;
        }
    }

    interface MonthClickListener {
        void onClick(int position);
    }
}
