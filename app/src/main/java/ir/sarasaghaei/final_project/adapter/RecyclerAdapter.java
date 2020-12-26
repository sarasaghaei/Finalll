package ir.sarasaghaei.final_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.sarasaghaei.final_project.R;
import ir.sarasaghaei.final_project.entity.Biyab;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.recViewHolder> {

    public static ClickListener clickListener;
   // private OnItemClickListener mItemClickListener;
    private Context context;
    private List<Biyab> biyablist;

    public RecyclerAdapter(Context context, List<Biyab> list) {
        this.context = context;
        this.biyablist = list;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerAdapter.clickListener = clickListener;
    }


    @Override
    public recViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new recViewHolder(LayoutInflater.from(context).inflate(R.layout.gridview_biyab, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull recViewHolder holder, int position) {
        Biyab biyab = biyablist.get(position);
        holder.tvid.setText(String.valueOf(biyab.getId()));
        holder.tvTitle.setText(biyab.getTitle());
        holder.tvPrice.setText(biyab.getPrice());
        Picasso.with(context).load(biyablist.get(position).getImage1())
                .placeholder(R.drawable.loding)
                .error(R.drawable.image)
                .into(holder.image);

    }


    @Override
    public int getItemCount() {
        return biyablist.size();
    }

    public static class recViewHolder extends RecyclerView.ViewHolder {

        public TextView tvid, tvTitle, tvPrice;
        public ImageView image;

        public recViewHolder(View itemView) {
            super(itemView);
            tvid = itemView.findViewById(R.id.tvid);
            tvTitle = itemView.findViewById(R.id.etTitle);
            tvPrice = itemView.findViewById(R.id.etPrice);
            image = itemView.findViewById(R.id.img);
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            /*if (mItemClickListener != null) {
                int adapterPosition = getAdapterPosition();
                mItemClickListener.onItemClick(view, adapterPosition);
            }*/
        }
    }




}
