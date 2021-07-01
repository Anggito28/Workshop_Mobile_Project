package com.kelompok2.rudibonsai.ui.cart;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.api.ApiClient;
import com.kelompok2.rudibonsai.api.CartInterface;
import com.kelompok2.rudibonsai.constant.ConstantValue;
import com.kelompok2.rudibonsai.model.cart.CartDeleteResponse;
import com.kelompok2.rudibonsai.model.cart.CartsItem;
import com.kelompok2.rudibonsai.model.cart.ProductImagesItem;
import com.kelompok2.rudibonsai.session.SessionManager;
import com.kelompok2.rudibonsai.utils.MyFormatter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context mContext;
    public static List<CartsItem> mData;
    public static ArrayList<Integer> itemQty;
    public static ArrayList<Integer> itemSubtotal;
    public static ArrayList<Integer> itemWeight;
    public static int qtyTotal;
    private ListItemListener listItemListener;
    SessionManager sessionManager;

    public CartAdapter(Context mContext, List<CartsItem> mData, ArrayList<Integer> itemQty, ListItemListener listItemListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.itemQty = itemQty;
        this.listItemListener = listItemListener;
        this.itemSubtotal = new ArrayList<>();
        this.itemWeight = new ArrayList<>();
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
        holder.etQuantity.setText(String.valueOf(itemQty.get(position)));
        setProductImage(holder, position);

        int subtotal = itemQty.get(position) * mData.get(position).getProduct().getPrice();
        itemSubtotal.add(subtotal);

        int weight = mData.get(position).getProduct().getWeight();
        itemWeight.add(weight);

        countSubtotalAmount();
        countQuantity();

        holder.etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String qty = s.toString();
                int qtyInt = 0;

                if ( !qty.isEmpty() ){
                    qtyInt = Integer.parseInt(qty);
                }

                updateItemQty(qtyInt, position);
                updateItemSubtotal(position);
                updateItemWeight(position, qtyInt);

                countSubtotalAmount();
                countQuantity();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.loading.show();
                sessionManager = new SessionManager(mContext);
                String token = "Bearer " + sessionManager.getTOKEN();

                CartInterface deleteCart = ApiClient.getClient().create(CartInterface.class);
                Call<CartDeleteResponse> cartDeleteCall = deleteCart.deleteCart(token, mData.get(position).getId());
                cartDeleteCall.enqueue(new Callback<CartDeleteResponse>() {
                    @Override
                    public void onResponse(Call<CartDeleteResponse> call, Response<CartDeleteResponse> response) {
                        if (!response.isSuccessful()){
                            holder.loading.dismiss();
                            Toast.makeText(mContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        holder.loading.dismiss();

                        listItemListener.onCartDelete();

                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<CartDeleteResponse> call, Throwable t) {
                        holder.loading.dismiss();
                        t.printStackTrace();
                        Toast.makeText(mContext, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void updateItemWeight(int position, int qtyInt) {
        int weightOnUpdate = mData.get(position).getProduct().getWeight() * qtyInt;
        itemWeight.set(position, weightOnUpdate);
    }

    private void updateItemSubtotal(int position) {
        int subtotal = itemQty.get(position) * mData.get(position).getProduct().getPrice();
        itemSubtotal.set(position, subtotal);
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
        for (int item : itemQty){
            total += item;
        }

        qtyTotal = total;
        listItemListener.onQuantityUpdate(total);
    }

    private void countSubtotalAmount() {
        int total = 0;
        for (int item : itemSubtotal){
            total += item;
        }

        listItemListener.onSubtotalUpdate(total);
    }

    private void updateItemQty(int qtyInt, int position) {
        itemQty.set(position, qtyInt);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface ListItemListener {
        void onSubtotalUpdate(int total);
        void onQuantityUpdate(int total);
        void onCartDelete();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvTitle,tvPrice,tvStock;
        EditText etQuantity;
        ImageButton btnDelete;
        ProgressDialog loading;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.iv_cart_product);
            tvTitle = itemView.findViewById(R.id.tv_cart_product_title);
            tvPrice = itemView.findViewById(R.id.tv_cart_product_price);
            tvStock = itemView.findViewById(R.id.tv_cart_product_stock);
            etQuantity = itemView.findViewById(R.id.et_quantity);
            btnDelete = itemView.findViewById(R.id.btn_cart_delete);
            loading = new ProgressDialog(mContext);

            loading.setCancelable(false);
            loading.setMessage("Memuat...");
        }
    }
}
