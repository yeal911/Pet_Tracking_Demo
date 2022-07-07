package com.example.pettrackingdemo.ui.pet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pettrackingdemo.R;

import java.io.Serializable;
import java.util.List;

public class PetListRowAdapter extends BaseAdapter  {

    static class PetInfo implements Serializable {
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    private List<PetInfo> data;
    private Context context;

    public PetListRowAdapter(Context context,List<PetInfo> data) {
        this.data = data;
        this.context = context;
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static LayoutInflater inflater = null;

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public PetInfo getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View localView = view;
        if (localView == null) {
            localView = inflater.inflate(R.layout.pet_row, null);
            TextView text = localView.findViewById(R.id.id_pet_name);
            text.setText(data.get(i).getName());
        }
        return localView;
    }
}
