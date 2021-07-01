package com.kelompok2.rudibonsai.ui.cart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.api.ApiClient;
import com.kelompok2.rudibonsai.api.CartInterface;
import com.kelompok2.rudibonsai.model.cart.CartGetResponse;
import com.kelompok2.rudibonsai.model.cart.CartsItem;
import com.kelompok2.rudibonsai.session.SessionManager;
import com.kelompok2.rudibonsai.ui.checkout.CheckoutActivity;
import com.kelompok2.rudibonsai.utils.MyFormatter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements CartAdapter.ListItemListener {

    private RecyclerView recyclerView;
    private CartInterface cartInterface;
    private SessionManager sessionManager;
    private ProgressDialog progressDialog;
    private List<CartsItem> cartsItems;
    private ArrayList<Integer> itemQty;
    private TextView subtotal, cartEmpty;
    private Button btnCheckout, btnReload;
    private CardView cvCheckout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        sessionManager = new SessionManager(getActivity());
        recyclerView = root.findViewById(R.id.list_cart);
        subtotal = root.findViewById(R.id.tv_cart_subtotal);
        btnCheckout = root.findViewById(R.id.btn_cart_checkout);
        cvCheckout = root.findViewById(R.id.cardview_cart_checkout);
        btnReload = root.findViewById(R.id.btn_cart_reload);
        cartEmpty = root.findViewById(R.id.tv_cart_empty);

        cvCheckout.setVisibility(View.INVISIBLE);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Carts...");

        fetchCartItems();

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < CartAdapter.itemQty.size(); i++){
                    if (CartAdapter.itemQty.get(i) > cartsItems.get(i).getProduct().getStock() || CartAdapter.itemQty.get(i) < 1){
                        Toast.makeText(getActivity(), "Input tidak valid", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intent);
            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchCartItems();
            }
        });

        return root;
    }

    private void fetchCartItems() {
        String token = "Bearer " + sessionManager.getTOKEN();
        progressDialog.show();

        cartInterface = ApiClient.getClient().create(CartInterface.class);
        Call<CartGetResponse> cartCall = cartInterface.getCarts(token);
        cartCall.enqueue(new Callback<CartGetResponse>() {
            @Override
            public void onResponse(Call<CartGetResponse> call, Response<CartGetResponse> response) {
                if (!response.isSuccessful()){
                    Log.i("fail", String.valueOf(response));
                    Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    btnReload.setVisibility(View.VISIBLE);
                    return;
                }
                progressDialog.dismiss();
                btnReload.setVisibility(View.GONE);

                cartsItems = response.body().getCarts();

                if (cartsItems.isEmpty()){
                    cartEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    cvCheckout.setVisibility(View.GONE);
                } else {
                    cvCheckout.setVisibility(View.VISIBLE);
                    cartEmpty.setVisibility(View.GONE);
                    itemQty = populateQty();
                    putDataToRecyclerView(cartsItems, itemQty);
                }

                Log.i("success", "oke");

                Toast.makeText(getActivity(), "Fetch success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CartGetResponse> call, Throwable t) {
                t.printStackTrace();
                Log.i("failure", "gagal");
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                btnReload.setVisibility(View.VISIBLE);
            }
        });

    }

    private ArrayList<Integer> populateQty() {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < cartsItems.size(); i++){
            list.add(1);
        }

        return list;
    }

    private void putDataToRecyclerView(List<CartsItem> cartsItems, ArrayList<Integer> quantities) {
        CartAdapter cartAdapter = new CartAdapter(getActivity(), cartsItems, quantities, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(cartAdapter);
    }

    @Override
    public void onSubtotalUpdate(int total) {
        subtotal.setText(MyFormatter.idrFormat(total));
    }

    @Override
    public void onQuantityUpdate(int total) {
        btnCheckout.setText("Checkout (" + String.valueOf(total) + ")");
    }

    @Override
    public void onCartDelete() {
        fetchCartItems();
    }
}