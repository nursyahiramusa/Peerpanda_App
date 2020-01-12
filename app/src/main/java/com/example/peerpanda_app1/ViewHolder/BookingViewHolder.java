package com.example.peerpanda_app1.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.peerpanda_app1.Common.Common;
import com.example.peerpanda_app1.Interface.OnItemClickListener;
import com.example.peerpanda_app1.R;

public class BookingViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener {

    public TextView txtBookingId, txtCoursecode, txtDatetime, txtLoc, txtBookingStatus, txtTotPay, txtBookingPhone, txtTutorName;
    public Button btnCancel;

    private OnItemClickListener itemClickListener;

    public BookingViewHolder(View itemView){
        super(itemView);

        txtBookingId = (TextView)itemView.findViewById(R.id.booking_id);
        txtCoursecode = (TextView)itemView.findViewById(R.id.coursecode);
        txtDatetime = (TextView)itemView.findViewById(R.id.datetime);
        txtLoc = (TextView)itemView.findViewById(R.id.location);
        txtBookingStatus = (TextView)itemView.findViewById(R.id.booking_status);
        txtTotPay = (TextView)itemView.findViewById(R.id.total_pay);
        txtBookingPhone = (TextView)itemView.findViewById(R.id.booking_phone);
        txtTutorName = (TextView)itemView.findViewById(R.id.tutor_name);
        btnCancel = (Button)itemView.findViewById(R.id.btnCancel);
    }


    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select action");

        contextMenu.add(0,1, getAdapterPosition(), Common.CANCEL);
    }
}
