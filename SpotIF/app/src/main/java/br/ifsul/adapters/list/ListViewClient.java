package br.ifsul.adapters.list;

import android.view.View;
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

    public void removeAllItems(){
        this.dataAdapter.clear();
        this.dataAdapter.notifyDataSetChanged();
    }

    public void addAllAndShow(List<T> items){
        this.addAllItems(items);
        this.listView.setVisibility(View.VISIBLE);
    }

    public void resetAndHide(){
        this.removeAllItems();
        this.listView.setVisibility(View.GONE);
    }

    public ListView getListView() {
        return listView;
    }
}
