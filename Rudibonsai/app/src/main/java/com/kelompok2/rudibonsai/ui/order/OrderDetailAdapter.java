package com.kelompok2.rudibonsai.ui.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.constant.ConstantValue;
import com.kelompok2.rudibonsai.model.order.get.detail.OrderDetailItem;
import com.kelompok2.rudibonsai.model.order.get.detail.ProductImagesItem;
import com.kelompok2.rudibonsai.utils.MyFormatter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {

    List<OrderDetailItem> mData;
    Context mContext;

    public OrderDetailAdapter(List<OrderDetailItem> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public OrderDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_small, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderDetailAdapter.MyViewHolder holder, int position) {
        holder.tvTitle.setText(mData.get(position).getProduct().getName());
        holder.tvPrice.setText(MyFormatter.idrFormat(mData.get(position).getProduct().getPrice()));

        String qty = String.valueOf(mData.get(position).getQuantity());
        
        int subtotalInt = mData.get(position).getQuantity() * mData.get(position).getProduct().getPrice();
        
        String subtotal = MyFormatter.idrFormat(subtotalInt);

        holder.tvItemSubtotal.setText("x" + qty + " • " + subtotal);

        setProductImage(holder, position);

    }

    private void setProductImage(MyViewHolder holder, int position) {
        String imgName = mData.get(position).getProductImages().get(0).getName();
        List<ProductImagesItem> productImages = mData.get(position).getProductImages();

        for (ProductImagesItem item : productImages){
            if (item.getIsPrimary() != null){
                imgName = item.getName();
            }
        }

        String imgPath = ConstantValue.BASE_URL + "products/images/" + imgName;

        Glide.with(mContext)
                .load(imgPath)
                .into(holder.ivProduct);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvTitle, tvPrice, tvItemSubtotal;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.iv_checkout_item_img);
            tvTitle = itemView.findViewById(R.id.tv_checkout_item_title);
            tvPrice = itemView.findViewById(R.id.tv_checkout_item_price);
            tvItemSubtotal = itemView.findViewById(R.id.tv_checkout_item_subtotal);
        }
    }
}
