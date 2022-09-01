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
import com.svalero.aa1pmdmrecu.domain.Car;
import com.svalero.aa1pmdmrecu.util.ImageUtils;

import java.util.ArrayList;


public class CarAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Car> carArrayList;
    private LayoutInflater inflater;





    public CarAdapter(Activity context, ArrayList<Car> carArrayList) {
        this.context = context;
        this.carArrayList = carArrayList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return carArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return carArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Car car = (Car) getItem(position);

        convertView = inflater.inflate(R.layout.client_and_car_adapter, null);
        ImageView carImage = (ImageView) convertView.findViewById(R.id.client_car_item_imageView);
        TextView carModel = convertView.findViewById(R.id.client_car_tv1);
        TextView carPlate = convertView.findViewById(R.id.client_car_tv2);

        if (car.getCarImage() != null) {  // Valido si no es null la foto, si no sale fallo nullpoint...
            carImage.setImageBitmap(ImageUtils.getBitmap(car.getCarImage()));
        }
        carModel.setText(car.getBrand() + " " + car.getModel());
        carPlate.setText(car.getLicensePlate());

        return convertView;
    }
}
