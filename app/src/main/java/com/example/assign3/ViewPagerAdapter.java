package com.example.assign3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    ArrayList<ViewPagerItem> viewPagerItemArrayList;
    final String[] select_qualification = {"status", "red", "blue", "green"};
    Context context;

    public ViewPagerAdapter(ArrayList<ViewPagerItem> viewPagerItemArrayList, Context context) {
        this.viewPagerItemArrayList = viewPagerItemArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_pager, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdapter.ViewHolder holder, int position) {
        ViewPagerItem viewPagerItem = viewPagerItemArrayList.get(position);

        holder.imageView.setImageResource(viewPagerItem.imageId);
        holder.nameView.setText("" + viewPagerItem.firstName + " " + viewPagerItem.lastName);
        holder.addressView.setText(viewPagerItem.address);

        ArrayList<StateVO> listVOs = new ArrayList<>();

        StateVO stateVO1 = new StateVO();
        stateVO1.setTitle(select_qualification[0]);
        stateVO1.setSelected(false);
        listVOs.add(stateVO1);

        for (int i = 0; i < select_qualification.length - 1; i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(select_qualification[i]);
            stateVO.setSelected(viewPagerItem.status[i]);

            listVOs.add(stateVO);
        }
        DropdownAdapter myAdapter = new DropdownAdapter(context, 0,
                listVOs);
        holder.spinnerView.setAdapter(myAdapter);
    }

    @Override
    public int getItemCount() {
        return viewPagerItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameView;
        TextView addressView;
        Spinner spinnerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivImage);
            nameView = itemView.findViewById(R.id.nameText);
            addressView = itemView.findViewById(R.id.addressText);
            spinnerView = itemView.findViewById(R.id.spinner);
        }
    }

}
