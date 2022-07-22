package com.svalero.aa1pmdmrecu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.svalero.aa1pmdmrecu.domain.Client;


import java.util.ArrayList;

public class ClientAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Client> clientArrayList;
    private LayoutInflater inflater;

    public ClientAdapter(Activity context, ArrayList<Client> clientArrayList) {
        this.context = context;
        this.clientArrayList = clientArrayList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Client client = (Client) getItem(position);
/*
        convertView = inflater.inflate(R.layout.client_and_car_adapter, null);
        ImageView clientImage = (ImageView) convertView.findViewById(R.id.client_car_item_imageView);
        TextView clientNameAndSurnameTv = convertView.findViewById(R.id.client_car_tv1);
        TextView clientDniTv = convertView.findViewById(R.id.client_car_tv2);

        if (client.getClientImage() != null) {  // Valido si no es null la foto, si no sale fallo nullpoint...
            clientImage.setImageBitmap(ImageUtils.getBitmap(client.getClientImage()));
        }
        clientNameAndSurnameTv.setText(client.getName() + " " + client.getSurname());
        clientDniTv.setText(client.getDni());
*/
        return convertView;
    }

    @Override
    public int getCount() {
        return clientArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return clientArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}




