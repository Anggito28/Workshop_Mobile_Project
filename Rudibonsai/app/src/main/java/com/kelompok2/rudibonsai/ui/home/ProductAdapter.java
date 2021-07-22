package com.kelompok2.rudibonsai.ui.home;

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
import com.kelompok2.rudibonsai.model.product.DataItem;
import com.kelompok2.rudibonsai.model.product.ProductImagesItem;
import com.kelompok2.rudibonsai.utils.MyFormatter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.HomeViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    Context mContext;
    List<DataItem> mData;

    public ProductAdapter(Context mContext, List<DataItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @NotNull
    @Override
    public ProductAdapter.HomeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_item_product, parent, false);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductAdapter.HomeViewHolder holder, int position) {
        setProductImage(holder, position);
        holder.tvTitle.setText(mData.get(position).getName());
        holder.tvPrice.setText(MyFormatter.idrFormat(mData.get(position).getPrice()));

        String length = String.valueOf(mData.get(position).getLength()) + "cm X ";
        String width = String.valueOf(mData.get(position).getWidth()) + "cm X ";
        String height = String.valueOf(mData.get(position).getHeight()) + "cm";

        String dimension = length + width + height;
    }

    private void setProductImage(HomeViewHolder holder, int position) {
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

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvTitle, tvDimension, tvPrice;

        public HomeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.iv_img_card_item_product);
            tvTitle = itemView.findViewById(R.id.tv_title_card_item_product);
            tvDimension = itemView.findViewById(R.id.tv_dimension_card_item_product);
            tvPrice = itemView.findViewById(R.id.tv_price_card_item_product);
        }
    }
}
