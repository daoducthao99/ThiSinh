package com.example.thisinh;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thisinh.R;
import com.example.thisinh.ThiSinh;
import com.example.thisinh.ThiSinhDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    ListView lstThiSinh;
    ArrayList<ThiSinh> listdata;
    Adapter listadapter;
    ThiSinhDB thiSinhDB;
    EditText txtSearch;
    int Selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSearch = findViewById(R.id.txtSearch);
        lstThiSinh = findViewById(R.id.listView);
        registerForContextMenu(lstThiSinh);

        thiSinhDB = new ThiSinhDB(this,"SQLThiSinhDB",null,2);
        thiSinhDB.addThiSinh(new ThiSinh("GHA01839","Vũ Trường An",9.0,7.5,8.5));
        thiSinhDB.addThiSinh(new ThiSinh("GHA01258","Lê Hải Hà",8.0,8.0,5.0));
        thiSinhDB.addThiSinh(new ThiSinh("GHA01823","Lê Đình Quang",8.5,7.5,6.5));
        thiSinhDB.addThiSinh(new ThiSinh("GHA01432","Nguyễn Văn Đức",8.0,8.0,8.0));
        thiSinhDB.addThiSinh(new ThiSinh("GHA01329","Mạc Văn Minh",8.0,8.0,7.5));
        listdata = thiSinhDB.getAllThiSinh();
        listadapter = new Adapter(this,listdata);
        lstThiSinh.setAdapter(listadapter);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listadapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lstThiSinh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Selected=position;
                return false;
            }
        });

        Collections.sort(listdata, new Comparator<ThiSinh>() {
            @Override
            public int compare(ThiSinh o1, ThiSinh o2) {
                return o2.getTen().toLowerCase().compareTo(o1.getTen().toLowerCase());
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menuitem,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuSua:
                break;
            case R.id.mnuXoa:
                int count=0;
                for(int i =0;i<listdata.size();++i){
                    if(listdata.get(i).getTongDiem()<listdata.get(Selected).getTongDiem())
                        count++;
                }
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Xác nhận");
                dialog.setMessage("Bạn có muốn xóa "+ count+"TS dưới "+ listdata.get(Selected).getTongDiem()+" điểm?");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i =0;i<listdata.size();++i){
                            if(listdata.get(i).getTongDiem()<listdata.get(Selected).getTongDiem()) {
                                thiSinhDB.deleteThiSinh(listdata.get(i).getSbd());
                            }
                        }
                        listdata = thiSinhDB.getAllThiSinh();
                        listadapter.setData(listdata);
                        listadapter.notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
