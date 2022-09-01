package com.svalero.aa1pmdmrecu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.svalero.aa1pmdmrecu.adapters.OrderAdapter;
import com.svalero.aa1pmdmrecu.database.AppDatabase;
import com.svalero.aa1pmdmrecu.domain.Car;
import com.svalero.aa1pmdmrecu.domain.Client;
import com.svalero.aa1pmdmrecu.domain.Order;
import com.svalero.aa1pmdmrecu.domain.dto.OrderDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewOrderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, DetailFragment.closeDetails {


    public ArrayList<OrderDTO> ordersDTOArrayList;
    public ArrayList<Order> ordersArrayList;
    public OrderAdapter orderDTOArrayAdapter;
    private Car car;
    private Client client;
    private OrderDTO orderDTO;
    private FrameLayout frameLayout;
    private String orderBy;
    private AppDatabase dbCar, dbClient, dbOrder;
    public Spinner findSpinner;
    private final String[] FIND_SPINNER_OPTIONS = new String[]{"Fecha", "Cliente", "Coche", "Matrícula"};
    private final String DEFAULT_STRING = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);


        car = new Car();
        client = new Client();
        ordersArrayList = new ArrayList<>();
        ordersDTOArrayList = new ArrayList<>();
        frameLayout = findViewById(R.id.frame_layout_order);
        findSpinner = findViewById(R.id.find_spinner_view_order);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, FIND_SPINNER_OPTIONS);
        findSpinner.setAdapter(adapterSpinner);
        orderBy = DEFAULT_STRING;

        dbCar = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "car").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        dbClient = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "client").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        dbOrder = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "order").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();

        orderList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        orderList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_actionbar, menu);
        final MenuItem searchItem = menu.findItem(R.id.app_bar_order_search);
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


    private void orderList() {

        ListView ordersListView = findViewById(R.id.order_listview);
        registerForContextMenu(ordersListView);

        orderDTOArrayAdapter = new OrderAdapter(this, ordersDTOArrayList);

        findBy(DEFAULT_STRING);

        ordersListView.setAdapter(orderDTOArrayAdapter);
        ordersListView.setOnItemClickListener(this);
    }


    private void findBy(String query) {
        orderDTO = new OrderDTO();
        loadOrdersDTO();

        switch (findSpinner.getSelectedItemPosition()) {
            case 0:
                ordersDTOArrayList.removeIf
                        (orderDTO -> (!String.valueOf(orderDTO.getDate()).contains(query)));
                break;
            case 1:
                ordersDTOArrayList.removeIf
                        (orderDTO -> (!orderDTO.getClientNameSurname().toLowerCase().contains(query.toLowerCase())));
                break;
            case 2:
                ordersDTOArrayList.removeIf
                        (orderDTO -> (!orderDTO.getCarBrandModel().toLowerCase().contains(query.toLowerCase())));
                break;
            case 3:
                ordersDTOArrayList.removeIf
                        (orderDTO -> (!orderDTO.getCarLicensePlate().toLowerCase().contains(query.toLowerCase())));
                break;
        }   // End switch
        orderBy(orderBy);
    }


    private void orderBy(String orderBy) {
        this.orderBy = orderBy;

        Collections.sort(ordersDTOArrayList, new Comparator<OrderDTO>() {
            @Override
            public int compare(OrderDTO o1, OrderDTO o2) {
                switch (orderBy) {
                    case "date":
                        return String.valueOf(o1.getDate()).compareToIgnoreCase(String.valueOf(o2.getDate()));
                    case "client_name":
                        return o1.getClientNameSurname().compareToIgnoreCase(o2.getClientNameSurname());
                    case "license_plate":
                        return o1.getCarLicensePlate().compareToIgnoreCase(o2.getCarLicensePlate());
                    case "car_model":
                        return o1.getCarBrandModel().compareToIgnoreCase(o2.getCarBrandModel());
                    default:
                        return String.valueOf(o1.getId()).compareTo(String.valueOf(o2.getId()));
                }   // End switch
            }
        });
        orderDTOArrayAdapter.notifyDataSetChanged();
    }

    private void loadOrdersDTO() {
        ordersDTOArrayList.clear();
        ordersArrayList.clear();

        ordersArrayList.addAll(dbOrder.orderDao().getAll());

        for (Order order : ordersArrayList) {
            client = dbClient.clientDao().getClientById(order.getClientId());
            car = dbCar.carDao().getCarById(order.getCarId());
            orderDTO = new OrderDTO();

            orderDTO.setId(order.getId());
            orderDTO.setDate(order.getDate());
            orderDTO.setClientNameSurname(client.getName() + " " + client.getSurname());
            orderDTO.setCarBrandModel(car.getBrand() + " " + car.getModel());
            orderDTO.setCarLicensePlate(car.getLicensePlate());
            orderDTO.setCarImageOrder(car.getCarImage());
            orderDTO.setDescription(order.getDescription());

            ordersDTOArrayList.add(orderDTO);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.order_by_default_item:
                orderBy("");
                return true;
            case R.id.order_by_date_item:
                orderBy("date");
                return true;
            case R.id.order_by_client_item:
                orderBy("client_name");
                return true;
            case R.id.order_by_license_plate_item:
                orderBy("license_plate");
                return true;
            case R.id.order_by_car_model_item:
                orderBy("car_model");
            default:
                return super.onOptionsItemSelected(item);
        }   // End switch
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.listview_menu, menu);

    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(this, AddOrderActivity.class);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.modify_menu:                      // Modificar moto
                Order order = ordersArrayList.get(info.position);

                intent.putExtra("modify_order", true);
                intent.putExtra("id", order.getId());
                intent.putExtra("date", String.valueOf(order.getDate()));
                intent.putExtra("description", order.getDescription());
                intent.putExtra("carId", order.getCarId());
                intent.putExtra("clientId", order.getClientId());

                startActivity(intent);
                return true;
            case R.id.detail_menu:                      // Detalles de la moto
                showDetails(info.position);
                return true;
            case R.id.add_menu:                         // Añadir moto
                startActivity(intent);
                return true;
            case R.id.delete_menu:                      // Eliminar moto
                deleteOrder(info);
                return true;
            default:
                return super.onContextItemSelected(item);
        }   // End switch
    }



    private void deleteOrder(AdapterView.AdapterContextMenuInfo info) {
        Order order = ordersArrayList.get(info.position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure_delete_order)
                .setPositiveButton(R.string.yes_capital, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                AppDatabase.class, "order").allowMainThreadQueries().build();
                        db.orderDao().delete(order);
                        orderList();
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

    private void showDetails(int position) {

        OrderDTO orderDTO = ordersDTOArrayList.get(position);

        Bundle datos = new Bundle();
        datos.putByteArray("car_image", orderDTO.getCarImageOrder());
        datos.putString("date", String.valueOf(orderDTO.getDate()));
        datos.putString("name", orderDTO.getClientNameSurname());
        datos.putString("model", orderDTO.getCarBrandModel());
        datos.putString("license", orderDTO.getCarLicensePlate());
        datos.putString("description", orderDTO.getDescription());

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(datos);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.order_detail, detailFragment)
                .commit();

        frameLayout.setVisibility(View.VISIBLE);
    }


    public void addOrder(View view) {
        Intent intent = new Intent(this, AddOrderActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDetails(position);
    }

    @Override
    public void hiddeDetails() {
        frameLayout.setVisibility(View.GONE);
    }
}




























