package com.example.assign3.DetailActivityRes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assign3.R;

import java.util.ArrayList;
import java.util.Objects;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    ArrayList<ViewPagerItem> viewPagerItemArrayList;

    // First item in select_qualification won't be a check box, but the label text for the dropdown spinner
    final String[] select_qualification = {"status", "completed", "refused", "partial"};
    Context context;
    String authTok;


    public ViewPagerAdapter(ArrayList<ViewPagerItem> viewPagerItemArrayList, Context context, String authTok) {
        this.viewPagerItemArrayList = viewPagerItemArrayList;
        this.context = context;
        this.authTok = authTok;
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

//        holder.imageView.setImageResource(viewPagerItem.imageId);
        holder.imageView.setImageBitmap(viewPagerItem.decodedBitmap);

        holder.nameView.setText("" + viewPagerItem.firstName + " " + viewPagerItem.lastName);
        holder.addressView.setText(viewPagerItem.address);

        holder.ageView.setText(String.valueOf(viewPagerItem.age));
        holder.emailView.setText(viewPagerItem.email);
        holder.phoneView.setText(viewPagerItem.phone);

        ArrayList<StateVO> listVOs = new ArrayList<>();

        StateVO stateVO1 = new StateVO();
        stateVO1.setTitle(select_qualification[0]);
        stateVO1.setSelected(false);
        listVOs.add(stateVO1);

        for (int i = 1; i < select_qualification.length; i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(select_qualification[i]);
            stateVO.setSelected(Objects.equals(select_qualification[i], viewPagerItem.status));
            listVOs.add(stateVO);
        }
        DropdownAdapter myAdapter = new DropdownAdapter(context, 0,
                listVOs, authTok);
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
        // CustomSpinner spinnerView;

        TextView ageView;
        TextView emailView;
        TextView phoneView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivImage);
            nameView = itemView.findViewById(R.id.nameText);
            addressView = itemView.findViewById(R.id.addressText);
            spinnerView = itemView.findViewById(R.id.spinner);

            ageView = itemView.findViewById(R.id.ageText);
            emailView = itemView.findViewById(R.id.emailText);
            phoneView = itemView.findViewById(R.id.phoneText);
        }
    }

}
