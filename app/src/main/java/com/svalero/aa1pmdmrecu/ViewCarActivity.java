package com.svalero.aa1pmdmrecu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.svalero.aa1pmdmrecu.adapters.CarAdapter;
import com.svalero.aa1pmdmrecu.database.AppDatabase;
import com.svalero.aa1pmdmrecu.domain.Car;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ViewCarActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{



    public ArrayList<Car> cars;
    public CarAdapter carArrayAdapter;
    public Spinner findSpinner;
    private String orderBy;
    private final String[] FIND_SPINNER_OPTIONS = new String[]{"Marca", "Modelo", "Matricula"};
    private final String DEFAULT_STRING = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car);

        findSpinner = findViewById(R.id.find_spinner_view_car);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, FIND_SPINNER_OPTIONS);
        findSpinner.setAdapter(adapterSpinner);

        cars = new ArrayList<>();
        orderBy = DEFAULT_STRING;

        carList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        carList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.car_actionbar, menu);
        final MenuItem searchItem = menu.findItem(R.id.app_bar_car_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findBy(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                findBy(newText.trim());
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void carList() {

        ListView carsListView = findViewById(R.id.car_lisview);
        registerForContextMenu(carsListView);

        carArrayAdapter = new CarAdapter(this, cars);

        findBy(DEFAULT_STRING);

        carsListView.setAdapter(carArrayAdapter);
        carsListView.setOnItemClickListener(this);

    }

    private void findBy(String query) {
        cars.clear();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "car").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();

        if (query.equalsIgnoreCase(DEFAULT_STRING)) {
            cars.addAll(db.carDao().getAll());
        } else {
            switch (findSpinner.getSelectedItemPosition()) {
                case 0:
                    cars.addAll(db.carDao().getByBrandString("%" + query + "%"));
                    break;
                case 1:
                    cars.addAll(db.carDao().getByModelString("%" + query + "%"));
                    break;
                case 2:
                    cars.addAll(db.carDao().getByLicensePlateString("%" + query + "%"));
                    break;
            }
        }
        orderBy(orderBy);
    }


    private void orderBy(String orderBy) {
        this.orderBy = orderBy;

        Collections.sort(cars, new Comparator<Car>() {
            @Override
            public int compare(Car o1, Car o2) {
                switch (orderBy) {
                    case "brand":
                        return o1.getBrand().compareToIgnoreCase(o2.getBrand());
                    case "model":
                        return o1.getModel().compareToIgnoreCase(o2.getModel());
                    case "license_plate":
                        return o1.getLicensePlate().compareToIgnoreCase(o2.getLicensePlate());
                    default:
                        return String.valueOf(o1.getId()).compareTo(String.valueOf(o2.getId()));
                }
            }
        });
        carArrayAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.listview_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.order_by_default_item:
                orderBy("");
                return true;
            case R.id.order_by_brand_item:
                orderBy("brand");
                return true;
            case R.id.order_by_model_item:
                orderBy("model");
                return true;
            case R.id.order_by_license_plate_item:
                orderBy("license_plate");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(this, AddCarActivity.class);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final int itemSelected = info.position;

        switch (item.getItemId()) {
            case R.id.modify_menu:                      // Modificar moto
                Car car = cars.get(itemSelected);
                intent.putExtra("modify_car", true);

                Log.i("case_menu", String.valueOf(car.getId()));
                intent.putExtra("id", car.getId());

                Log.i("case_menu_imagen", Arrays.toString(car.getCarImage()));
                intent.putExtra("car_image", car.getCarImage());

                Log.i("case_menu", String.valueOf(car.getBrand()));
                intent.putExtra("brand", car.getBrand());

                Log.i("case_menu", String.valueOf(car.getModel()));
                intent.putExtra("model", car.getModel());

                Log.i("case_menu", String.valueOf(car.getLicensePlate()));
                intent.putExtra("license_plate", car.getLicensePlate());

                Log.i("case_menu", String.valueOf(car.getClientId()));
                intent.putExtra("clientId", car.getClientId());

                Log.i("car_intent", car.toString());

                startActivity(intent);
                return true;

            case R.id.detail_menu:                      // Detalles de la moto

                // Todo FALTA usar un fragment para mostrar una ficha con los detalles de la moto

                return true;

            case R.id.add_menu:                         // Añadir moto
                startActivity(intent);
                return true;

            case R.id.delete_menu:                      // Eliminar moto
                deleteCar(info);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }




    private void deleteCar(AdapterView.AdapterContextMenuInfo info) {
        Car car = cars.get(info.position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure_delete_car)
                .setPositiveButton(R.string.yes_capital, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                AppDatabase.class, "car").allowMainThreadQueries().build();
                        db.carDao().delete(car);
                        carList();
                    }
                })
                .setNegativeButton(R.string.no_capital, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }



    public void addCar(View view) {
        Intent intent = new Intent(this, AddCarActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }















}