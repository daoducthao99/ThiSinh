package com.example.thisinh;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Adapter extends BaseAdapter {
    private Activity activity;//Tham chieu toiactivity su dung  Adapter
    private ArrayList<ThiSinh> data; //Du lieu cua listview
    private ArrayList<ThiSinh> databackup;
    private LayoutInflater inflater; //Doi tuong LayoutInflater de phan tich Layout

    public Adapter(Activity activity, ArrayList<ThiSinh> data) {
        this.activity = activity;
        this.data = data;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArrayList<ThiSinh> getData() {
        return data;
    }

    public void setData(ArrayList<ThiSinh> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return  data.size();
    }

    public ArrayList<ThiSinh> getDatabackup() {
        return databackup;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( final int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null)
            v = inflater.inflate(R.layout.item_layout, null);
        TextView tvSbd = v.findViewById(R.id.tvSbd);
        tvSbd.setText(data.get(i).getSbd());
        TextView tvHoten = v.findViewById(R.id.tvHoten);
        tvHoten.setText(data.get(i).getHoten());
        TextView tvDiem = v.findViewById(R.id.tvDiem);
        tvDiem.setText(""+data.get(i).getTongDiem());
        return  v;
    }

    public Filter getFilter(){
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults fr = new FilterResults();
                if(databackup==null)
                    databackup=new ArrayList<>(data);
                if(constraint==null || constraint.length()==0)
                {
                    fr.values=databackup;
                    fr.count=databackup.size();
                }
                else{
                    ArrayList<ThiSinh> newdata = new ArrayList<>();
                    for(ThiSinh d:data)
                        if(d.getSbd().toLowerCase().contains(constraint.toString().toLowerCase()))
                            newdata.add(d);
                    fr.values=newdata;
                    fr.count=newdata.size();
                }
                return fr;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ArrayList<ThiSinh> tmp=(ArrayList<ThiSinh>) results.values;
                data.clear();
                data.addAll(tmp);
                notifyDataSetChanged();
            }
        };
        return f;
    }
}
