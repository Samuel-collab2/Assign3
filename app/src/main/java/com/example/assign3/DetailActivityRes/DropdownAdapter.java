package com.example.assign3.DetailActivityRes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.assign3.R;

import java.util.ArrayList;
import java.util.List;

public class DropdownAdapter extends ArrayAdapter<StateVO> {
    private Context mContext;
    private ArrayList<StateVO> listState;
    private DropdownAdapter myAdapter;
    private boolean isFromView = false;
    private int checkedIndex;
    private Spinner mSpinner;

    public DropdownAdapter(Context context, int resource, List<StateVO> objects, Spinner spinner) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<StateVO>) objects;
        this.myAdapter = this;
        this.mSpinner = spinner;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.dropdown_item, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.textViewHidden);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(holder);

            // initialize holder with our db/array values
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mCheckBox.setText(listState.get(position).getTitle());

        // To check weather checked event fire from getview() or user input
        isFromView = true;

        if (listState.get(position).isSelected()) {
            holder.mCheckBox.setChecked(true);
            checkedIndex = position;
        } else {
            holder.mCheckBox.setChecked(false);
        }
//        holder.mCheckBox.setChecked(listState.get(position).isSelected());

        isFromView = false;

        if ((position == 0)) {
            holder.mTextView.setText("Status");
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.mTextView.setVisibility(View.INVISIBLE);
        }

        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if (!isFromView) {
                    if(isChecked) {
                        // case where unchecked box is checked
                        listState.get(checkedIndex).setSelected(false);
                        listState.get(position).setSelected(true);
                    } else {
                        // case where checked box is unchecked
                        listState.get(position).setSelected(false);
                    }


                    if (mSpinner != null) {

                    }

//                    listState.get(checkedIndex).setSelected(false);

                    // listState.get(position).setSelected(isChecked);

                    // Code for updating db/array with changes
                    // ...
                    System.out.println("update to status occurred!");
                    System.out.println(parent);

                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }
}