package ir.sarasaghaei.final_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.sarasaghaei.final_project.R;
import ir.sarasaghaei.final_project.entity.Biyab;

public class BiyabAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<Biyab> biyablist;
    int columns;

    public BiyabAdapter(Context context, List<Biyab> list, int columns) {
        this.context = context;
        this.biyablist = list;
        this.columns = columns;
    }

    @Override
    public int getCount() {
        int size;
        if (columns == 2) {
            size = 4;
        } else if(columns == 1) {
            size = 8;
        }else size= biyablist.size();
        return size;
    }

    @Override
    public Object getItem(int position) {
        return biyablist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null && columns == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_biyab, null);
        } else if (view == null && columns == 2) {
            view = LayoutInflater.from(context).inflate(R.layout.gridview_biyab, null);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.listview_biyab, null);
        }

        final Biyab biyab = biyablist.get(position);

        TextView tvid = view.findViewById(R.id.tvid);
        TextView tvTitle = view.findViewById(R.id.etTitle);
        TextView tvPrice = view.findViewById(R.id.etPrice);
        ImageView image = view.findViewById(R.id.img);

        tvid.setText(String.valueOf(biyab.getId()));
        tvTitle.setText(biyab.getTitle());
        tvPrice.setText(biyab.getPrice());
        Picasso.with(context).load(biyab.getImage1())
                .placeholder(R.drawable.loding)
                .error(R.drawable.image)
                .into(image);

        return view;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
