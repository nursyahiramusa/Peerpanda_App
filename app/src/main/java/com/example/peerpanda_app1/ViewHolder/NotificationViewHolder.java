package com.example.peerpanda_app1.ViewHolder;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.peerpanda_app1.Interface.OnItemClickListener;
import com.example.peerpanda_app1.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tutor_name;
    public TextView coursecode;
    public TextView datetime;
    public TextView location;

    private OnItemClickListener itemClickListener;

    public NotificationViewHolder(View itemView){
        super(itemView);

        tutor_name = (TextView)itemView.findViewById(R.id.tutor_name);
        coursecode = (TextView)itemView.findViewById(R.id.course_code);
        datetime = (TextView)itemView.findViewById(R.id.datetime);
        location = (TextView)itemView.findViewById(R.id.location);

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
