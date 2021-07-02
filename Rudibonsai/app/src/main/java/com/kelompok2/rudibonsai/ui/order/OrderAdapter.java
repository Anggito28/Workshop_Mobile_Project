package com.kelompok2.rudibonsai.ui.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.model.order.get.OrderGetResponseItem;
import com.kelompok2.rudibonsai.utils.MyFormatter;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context mContext;
    List<OrderGetResponseItem> mData;

    public OrderAdapter(Context mContext, List<OrderGetResponseItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @NotNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_order, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderAdapter.MyViewHolder holder, int position) {
        List<String> products = mData.get(position).getProductsName();
        String title = "";

        for (int i = 0; i < products.size(); i++){
            String separator = ", ";
            if (i == 0){
                separator = "";
            }
            title += separator + products.get(i);
        }

        holder.tvTitle.setText(title);
        holder.tvTotal.setText(MyFormatter.idrFormat(mData.get(position).getOrder().getGrandTotalAmount()));
        holder.tvStatus.setText(mData.get(position).getOrder().getStatus());

        String itemQty = String.valueOf(mData.get(position).getOrder().getQuantityTotal()) + " Item";
        holder.tvQty.setText(itemQty);

        String dateTimeString = mData.get(position).getOrder().getCreatedAt();
        String date = getFormatedDate(dateTimeString);
        String time = getFormatedTime(dateTimeString);

        holder.tvDate.setText(date);
        holder.tvTime.setText(time);
    }

    private String getFormatedTime(String dateTimeString) {
        SimpleDateFormat dateTimeInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat timeOutput = new SimpleDateFormat("HH:mm:ss");
        timeOutput.setTimeZone(TimeZone.getTimeZone("GMT+7"));

        Date time = null;

        try
        {
            time = dateTimeInput.parse(dateTimeString);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return timeOutput.format(time);
    }

    private String getFormatedDate(String dateTimeString) {
        SimpleDateFormat dateTimeInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat dateOutput = new SimpleDateFormat("dd/MM/yyyy");
        dateOutput.setTimeZone(TimeZone.getTimeZone("GMT+7"));

        Date date = null;

        try
        {
            date = dateTimeInput.parse(dateTimeString);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return dateOutput.format(date);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvTotal, tvStatus, tvDate, tvTime, tvQty;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_order_item_products_title);
            tvTotal = itemView.findViewById(R.id.tv_order_item_total);
            tvStatus = itemView.findViewById(R.id.tv_order_item_status);
            tvDate = itemView.findViewById(R.id.tv_order_item_date);
            tvTime = itemView.findViewById(R.id.tv_order_item_time);
            tvQty = itemView.findViewById(R.id.tv_order_item_qty);
        }
    }
}
