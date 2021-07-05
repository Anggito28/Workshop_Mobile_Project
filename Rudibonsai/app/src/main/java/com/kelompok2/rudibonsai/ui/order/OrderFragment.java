package com.kelompok2.rudibonsai.ui.order;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.api.ApiClient;
import com.kelompok2.rudibonsai.api.OrderInterface;
import com.kelompok2.rudibonsai.model.order.get.OrderGetResponseItem;
import com.kelompok2.rudibonsai.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderFragment extends Fragment {

    RecyclerView recyclerView;
    SessionManager sessionManager;
    OrderInterface orderInterface;
    ProgressDialog progressDialog;
    List<OrderGetResponseItem> orderList;
    TabLayout tabOrder;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        recyclerView = root.findViewById(R.id.rv_order);
        tabOrder = root.findViewById(R.id.tab_order_status);
        sessionManager = new SessionManager(getActivity());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Orders...");

        fetchOrderItems();

        tabOrder.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String status = "";

                switch (tab.getPosition()){
                    case 0:
                        putDataToRecyclerView(orderList);
                        break;
                    case 1:
                        status = "Tertunda";
                        break;
                    case 2:
                        status = "Diproses";
                        break;
                    case 3:
                        status = "Dikirim";
                        break;
                    case 4:
                        status = "Selesai";
                        break;
                    case 5:
                        status = "Batal";
                        break;
                }

                if (!status.equals("")){
                    ArrayList<OrderGetResponseItem> list = new ArrayList<>();

                    for (OrderGetResponseItem item : orderList){
                        if (item.getOrder().getStatus().equals(status)){
                            list.add(item);
                        }
                    }

                    putDataToRecyclerView(list);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }

    private void fetchOrderItems() {
        progressDialog.show();
        String token = "Bearer " + sessionManager.getTOKEN();

        orderInterface = ApiClient.getClient().create(OrderInterface.class);
        Call<List<OrderGetResponseItem>> orderGetResponseCall = orderInterface.getOrders(token);
        orderGetResponseCall.enqueue(new Callback<List<OrderGetResponseItem>>() {
            @Override
            public void onResponse(Call<List<OrderGetResponseItem>> call, Response<List<OrderGetResponseItem>> response) {
                if (!response.isSuccessful()){
                    progressDialog.dismiss();

                    Log.i("fail", String.valueOf(response));
                    Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.dismiss();

                orderList = response.body();

                putDataToRecyclerView(orderList);

                Log.i("success", "oke");
                Toast.makeText(getActivity(), "Fetch success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<OrderGetResponseItem>> call, Throwable t) {
                progressDialog.dismiss();

                t.printStackTrace();
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void putDataToRecyclerView(List<OrderGetResponseItem> orderGetResponse) {
        OrderAdapter orderAdapter = new OrderAdapter(getActivity(), orderGetResponse);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(orderAdapter);
    }
}