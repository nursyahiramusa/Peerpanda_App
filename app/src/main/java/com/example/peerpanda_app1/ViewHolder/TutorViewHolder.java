package com.example.peerpanda_app1.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.peerpanda_app1.Interface.OnItemClickListener;
import com.example.peerpanda_app1.R;

public class TutorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tutor_name;
    public TextView courseTeach;
    public ImageView tutor_image;
    public RatingBar ratingBar;

    private OnItemClickListener itemClickListener;

    public TutorViewHolder(View itemView){
        super(itemView);

        tutor_name = (TextView)itemView.findViewById(R.id.tutor_name);
        courseTeach = (TextView)itemView.findViewById(R.id.courseTeach);
        tutor_image = (ImageView) itemView.findViewById(R.id.tutor_image);
        ratingBar = (RatingBar)itemView.findViewById(R.id.ratingBar);

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
