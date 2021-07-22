package com.kelompok2.rudibonsai.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.api.ApiClient;
import com.kelompok2.rudibonsai.api.HomeInterface;
import com.kelompok2.rudibonsai.model.product.DataItem;
import com.kelompok2.rudibonsai.model.product.ProductResponse;
import com.kelompok2.rudibonsai.session.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView rvProduct;
    SessionManager sessionManager;
    HomeInterface homeInterface;
    Button btnLoadMore;
    int nextPage;
    String token;
    List<DataItem> dataProducts;
    ProductAdapter productAdapter;
    private ProgressDialog progressDialog;
    TextView productEmpty;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        setHasOptionsMenu(true);

        sessionManager = new SessionManager(getActivity());
        homeInterface = ApiClient.getClient().create(HomeInterface.class);
        rvProduct = root.findViewById(R.id.rv_product_home);
        btnLoadMore = root.findViewById(R.id.btn_home_load_more);
        productEmpty = root.findViewById(R.id.tv_product_empty);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");

        token = "Bearer " + sessionManager.getTOKEN();

        fetchProductItems();

        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMore();
            }
        });

        return root;
    }

    private void loadMore() {
        progressDialog.show();

        Call<ProductResponse> productResponseCall = homeInterface.getNextProducts(token, nextPage);
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (!response.isSuccessful()){
                    Log.i("fail", String.valueOf(response));
                    Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                    return;
                }

                for (DataItem item : response.body().getData()){
                    dataProducts.add(item);
                }

                productAdapter.notifyDataSetChanged();

                if (response.body().getNextPageUrl() == null){
                    btnLoadMore.setVisibility(View.GONE);
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void fetchProductItems() {
        progressDialog.show();
        Call<ProductResponse> productCall = homeInterface.getProducts(token);
        productCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (!response.isSuccessful()){
                    Log.i("fail", String.valueOf(response));
                    Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
                dataProducts = response.body().getData();

                if (dataProducts.isEmpty()){
                    rvProduct.setVisibility(View.GONE);
                    productEmpty.setVisibility(View.VISIBLE);
                }else {
                    putDataToRecyclerView(dataProducts);

                    nextPage = response.body().getCurrentPage() + 1;

                    if (response.body().getNextPageUrl() != null){
                        btnLoadMore.setVisibility(View.VISIBLE);
                    }
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void putDataToRecyclerView(List<DataItem> body) {
        productAdapter = new ProductAdapter(getActivity(), body);
        rvProduct.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvProduct.setAdapter(productAdapter);
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.home_action_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//
//        MenuItem searchItem = menu.findItem(R.id.home_search_menu);
//        MenuItem categoryItem = menu.findItem(R.id.home_category_menu);
//
//        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                categoryItem.setVisible(false);
//
//                return true;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                categoryItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//                categoryItem.setVisible(true);
//
//                return true;
//            }
//        });
//
//        final SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//    }
}