package com.kelompok2.rudibonsai.ui.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.constant.ConstantValue;
import com.kelompok2.rudibonsai.model.product.detail.ProductImagesItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ProductImgViewHolder> {

    Context mContext;
    List<ProductImagesItem> mData;

    public ProductImageAdapter(Context mContext, List<ProductImagesItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @NotNull
    @Override
    public ProductImgViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.slide_image_item_product, parent, false);

        return new ProductImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductImgViewHolder holder, int position) {

        String imgPath = ConstantValue.BASE_URL + "products/images/" + mData.get(position).getName();

        Glide.with(mContext)
                .load(imgPath)
                .into(holder.productImg);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ProductImgViewHolder extends RecyclerView.ViewHolder {

        ImageView productImg;

        public ProductImgViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            productImg = itemView.findViewById(R.id.iv_product_detail_img);
        }
    }
}
