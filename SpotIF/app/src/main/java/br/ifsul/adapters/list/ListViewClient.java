package br.ifsul.adapters.list;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ListViewClient<T> {

    private ListView listView;
    private ArrayAdapter<T> dataAdapter;

    public ListViewClient(ListView listView, ArrayAdapter<T> dataAdapter) {
        this.listView = listView;
        this.dataAdapter = dataAdapter;
        this.listView.setAdapter(this.dataAdapter);
    }

    public void addAllItems(List<T> items){
        this.dataAdapter.addAll(items);
        this.dataAdapter.notifyDataSetChanged();
    }

}
