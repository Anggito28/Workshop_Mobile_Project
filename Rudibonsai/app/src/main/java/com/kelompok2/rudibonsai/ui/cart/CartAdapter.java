package com.kelompok2.rudibonsai.ui.cart;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.constant.ConstantValue;
import com.kelompok2.rudibonsai.model.cart.CartItemQuantity;
import com.kelompok2.rudibonsai.model.cart.CartsItem;
import com.kelompok2.rudibonsai.model.cart.ProductImagesItem;
import com.kelompok2.rudibonsai.utils.MyFormatter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context mContext;
    List<CartsItem> mData;
    static ArrayList<CartItemQuantity> quantities;
    ArrayList<Integer> subtotalItem;
    private SubtotalListener subtotalListener;

    public CartAdapter(Context mContext, List<CartsItem> mData, ArrayList<CartItemQuantity> quantities, SubtotalListener subtotalListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.quantities = quantities;
        this.subtotalListener = subtotalListener;
        this.subtotalItem = new ArrayList<>();
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_cart, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartAdapter.MyViewHolder holder, int position) {

        holder.tvTitle.setText(mData.get(position).getProduct().getName());
        holder.tvStock.setText("Stok : " + String.valueOf(mData.get(position).getProduct().getStock()));
        holder.tvPrice.setText(MyFormatter.idrFormat(mData.get(position).getProduct().getPrice()));
        holder.etQuantity.setText(String.valueOf(quantities.get(position).getQuantity()));
        setProductImage(holder, position);

        setSubtotalItem(position);
        countSubtotalAmount();
        countQuantity();

        holder.etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateQuantity(s, position);
                updateSubtotalItem(position);
                countSubtotalAmount();
                countQuantity();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    private void countQuantity() {
        int total = 0;
        for (CartItemQuantity item : quantities){
            total += item.getQuantity();
        }

        subtotalListener.onQuantityUpdate(total);
    }

    private void countSubtotalAmount() {
        int total = 0;
        for (int item : subtotalItem){
            total += item;
        }

        subtotalListener.onSubtotalUpdate(total);
    }

    private void updateQuantity(CharSequence s, int position) {
        String qty = s.toString();
        int qtyInt = 0;

        if ( !qty.isEmpty() ){
            qtyInt = Integer.parseInt(qty);
        }

        quantities.get(position).setQuantity(qtyInt);
    }

    private void updateSubtotalItem(int position) {
        int subtotal = quantities.get(position).getQuantity() * mData.get(position).getProduct().getPrice();
        subtotalItem.set(position, subtotal);
    }

    private void setSubtotalItem(int position) {
        int subtotal = quantities.get(position).getQuantity() * mData.get(position).getProduct().getPrice();
        subtotalItem.add(subtotal);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface SubtotalListener{
        void onSubtotalUpdate(int total);
        void onQuantityUpdate(int total);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvTitle,tvPrice,tvStock;
        EditText etQuantity;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.iv_cart_product);
            tvTitle = itemView.findViewById(R.id.tv_cart_product_title);
            tvPrice = itemView.findViewById(R.id.tv_cart_product_price);
            tvStock = itemView.findViewById(R.id.tv_cart_product_stock);
            etQuantity = itemView.findViewById(R.id.et_quantity);

        }
    }
}
