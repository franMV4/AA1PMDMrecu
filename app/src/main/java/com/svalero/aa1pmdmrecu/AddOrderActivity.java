package com.svalero.aa1pmdmrecu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.svalero.aa1pmdmrecu.database.AppDatabase;
import com.svalero.aa1pmdmrecu.domain.Car;
import com.svalero.aa1pmdmrecu.domain.Client;
import com.svalero.aa1pmdmrecu.domain.Order;
import com.svalero.aa1pmdmrecu.util.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddOrderActivity extends AppCompatActivity {


    private Spinner clientSpinner;
    private Spinner carSpinner;
    private EditText etDescription;
    private TextView tvDate;
    private Button addButton;
    private Intent intent;

    private Order order;
    private ArrayList<Client> clients;
    private ArrayList<Car> cars;
    private boolean modifyOrder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);


        clientSpinner = findViewById(R.id.client_spinner_add_order);
        carSpinner = findViewById(R.id.car_spinner_add_order);
        etDescription = findViewById(R.id.description_edittext_add_order);
        tvDate = findViewById(R.id.date_textlabel_add_order);
        addButton = findViewById(R.id.add_order_button);

        order = new Order(0, null, 0, 0, "");

        clients = new ArrayList<>();
        cars = new ArrayList<>();
        rellenarSpinners(0);
        intent();

        clientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("id_spinner_client", "position " + position + ", id " + id);
                Log.i("id_spinner_client", String.valueOf(clients.get(clientSpinner.getSelectedItemPosition()).getId()));
                rellenarSpinners(clients.get(clientSpinner.getSelectedItemPosition()).getId());
                Log.i("id_spinner_car", String.valueOf(carSpinner.getCount()));
                if (carSpinner.getCount() > 0) {
                    Log.i("id_spinner_car", String.valueOf(cars.get(carSpinner.getSelectedItemPosition()).getId()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        rellenarSpinners(0);
    }


    public void selectDate(View view) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String stringMonth = String.valueOf(month + 1);
                if ((month + 1) < 10) {
                    stringMonth = "0" + stringMonth;
                }
                tvDate.setText(dayOfMonth + "/" + stringMonth + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }




    private void intent() {

        intent = getIntent();
        modifyOrder = intent.getBooleanExtra("modify_order", false);

        if (modifyOrder) {
            order.setId(intent.getIntExtra("id", 0));
            tvDate.setText(DateUtils.fromLocalDateToMyDateFormatString
                    (LocalDate.parse(intent.getStringExtra("date"))));
            etDescription.setText(intent.getStringExtra("description"));

            addButton.setText(R.string.modify_capital);
        }
    }




    public void addOrder(View view) {

        order.setDate(DateUtils.fromMyDateFormatStringToLocalDate(tvDate.getText().toString().trim()));
        order.setDescription(etDescription.getText().toString().trim());

        if (carSpinner.getCount() == 0) {
            Toast.makeText(this, R.string.select_car, Toast.LENGTH_SHORT).show();
        } else if ((order.getDescription().equals("")) || (String.valueOf(order.getDate()).equals(""))) {
            Toast.makeText(this, R.string.complete_all_fields, Toast.LENGTH_SHORT).show();
        } else {
            order.setClientId(clients.get(clientSpinner.getSelectedItemPosition()).getId());
            order.setCarId(cars.get(carSpinner.getSelectedItemPosition()).getId());

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "order").allowMainThreadQueries().build();

            if (modifyOrder) {
                Log.i("modifyed_order", order.toString());
                modifyOrder = false;
                addButton.setText(R.string.add_button);
                db.orderDao().update(order);
                Toast.makeText(this, R.string.modified_order, Toast.LENGTH_SHORT).show();
            } else {
                order.setId(0);
                Log.i("new_order", order.toString());
                db.orderDao().insert(order);
                Toast.makeText(this, R.string.added_order, Toast.LENGTH_SHORT).show();
            }

            etDescription.setText("");
            tvDate.setText("");

        }
    }




    private void rellenarSpinners(int idClient) {

        if (idClient == 0) {

            clients.clear();

            AppDatabase dbClient = Room.databaseBuilder(getApplicationContext(),
                            AppDatabase.class, "client").allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
            clients.addAll(dbClient.clientDao().getAll());


            String[] arrayClientSpinner = new String[clients.size()];

            int i = 0;
            for (Client client : clients) {
                arrayClientSpinner[i] = client.getName() + " " + client.getSurname();
                Log.i("spinner_client", arrayClientSpinner[i]);
                i++;
            }

            ArrayAdapter<String> adapterClientSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayClientSpinner);
            clientSpinner.setAdapter(adapterClientSpinner);

        } else {

            cars.clear();

            AppDatabase dbCar = Room.databaseBuilder(getApplicationContext(),
                            AppDatabase.class, "car").allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
            cars.addAll(dbCar.carDao().getCarsByClientId(idClient));

            String[] arrayCarSpinner = new String[cars.size()];

            int j = 0;
            for (Car car : cars) {
                arrayCarSpinner[j] = car.getBrand() + " " + car.getModel();
                Log.i("spinner_car", arrayCarSpinner[j]);
                j++;
            }

            ArrayAdapter<String> adapterCarSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayCarSpinner);
            carSpinner.setAdapter(adapterCarSpinner);

        }
    }









}