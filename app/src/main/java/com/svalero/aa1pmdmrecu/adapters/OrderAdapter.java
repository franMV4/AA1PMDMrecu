package com.svalero.aa1pmdmrecu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.svalero.aa1pmdmrecu.R;
import com.svalero.aa1pmdmrecu.domain.dto.OrderDTO;
import com.svalero.aa1pmdmrecu.util.DateUtils;
import com.svalero.aa1pmdmrecu.util.ImageUtils;

import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<OrderDTO> orderArrayList;
    private LayoutInflater inflater;

    public OrderAdapter(Activity context, ArrayList<OrderDTO> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderDTO orderDTO = (OrderDTO) getItem(position);

        convertView = inflater.inflate(R.layout.client_and_car_adapter, null);
        ImageView orderCarImage = convertView.findViewById(R.id.client_car_item_imageView);
        TextView orderDateAndCarModel = convertView.findViewById(R.id.client_car_tv1);
        TextView orderLicensePlateAndDescription = convertView.findViewById(R.id.client_car_tv2);

        orderDateAndCarModel.setTextSize(22);
        orderLicensePlateAndDescription.setTextSize(18);

        if (orderDTO.getCarImageOrder() != null) {  // Valido si no es null la foto, si no sale fallo nullpoint...
            orderCarImage.setImageBitmap(ImageUtils.getBitmap(orderDTO.getCarImageOrder()));
        }
        orderDateAndCarModel.setText(DateUtils.fromLocalDateToMyDateFormatString(orderDTO.getDate()) + " || " + orderDTO.getCarBrandModel());
        orderLicensePlateAndDescription.setText(orderDTO.getCarLicensePlate() + " || " + orderDTO.getDescription());


        return convertView;
    }

    @Override
    public int getCount() {
        return orderArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}






