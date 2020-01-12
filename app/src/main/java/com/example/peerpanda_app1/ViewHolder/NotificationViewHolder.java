package com.example.peerpanda_app1.ViewHolder;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.peerpanda_app1.Interface.OnItemClickListener;
import com.example.peerpanda_app1.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView status;
    public TextView coursecode;
    public TextView datetime;
    public TextView location;
    public TextView totalpay;

    private OnItemClickListener itemClickListener;

    public NotificationViewHolder(View itemView){
        super(itemView);

        status = (TextView)itemView.findViewById(R.id.status);
        coursecode = (TextView)itemView.findViewById(R.id.course_code);
        datetime = (TextView)itemView.findViewById(R.id.datetime);
        location = (TextView)itemView.findViewById(R.id.location);
        totalpay = (TextView)itemView.findViewById(R.id.total_pay);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }

}
