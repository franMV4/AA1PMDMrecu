package com.svalero.aa1pmdmrecu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.svalero.aa1pmdmrecu.database.AppDatabase;
import com.svalero.aa1pmdmrecu.domain.Car;
import com.svalero.aa1pmdmrecu.domain.Client;
import com.svalero.aa1pmdmrecu.util.ImageUtils;

import java.util.ArrayList;

public class AddCarActivity extends AppCompatActivity {


    private Car car;

    private Button addButton;
    private ImageView carImage;
    private Spinner clientSpinner;
    private EditText etBrand;
    private EditText etModel;
    private EditText etLicensePlate;
    private Intent intent;

    private boolean modifyCar;
    public ArrayList<Client> clients;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        carImage = findViewById(R.id.car_imageView);
        etBrand = findViewById(R.id.brand_edittext_add_car);
        etModel = findViewById(R.id.model_edittext_add_car);
        etLicensePlate = findViewById(R.id.license_plate_edittext_add_car);
        clientSpinner = findViewById(R.id.client_spinner_add_car);
        addButton = findViewById(R.id.add_car_button);

        car = new Car();
        clients = new ArrayList<>();
        fillSpinner();
        intent();

    }

    @Override
    protected void onResume() {
        super.onResume();
        fillSpinner();
    }

    private void intent() {

        intent = getIntent();
        modifyCar = intent.getBooleanExtra("modify_car", false);

        if (modifyCar) {
            car.setId(intent.getIntExtra("id", 0));
            car.setClientId(intent.getIntExtra("clientId", 0));

            if (intent.getByteArrayExtra("car_image") != null) {
                carImage.setImageBitmap(ImageUtils.getBitmap(intent.getByteArrayExtra("car_image")));
            }
            etBrand.setText(intent.getStringExtra("brand"));
            etModel.setText(intent.getStringExtra("model"));
            etLicensePlate.setText(intent.getStringExtra("license_plate"));

            addButton.setText(R.string.modify_capital);
        }
    }


    public void addCar(View view) {

        car.setBrand(etBrand.getText().toString().trim());
        car.setModel(etModel.getText().toString().trim());
        car.setLicensePlate(etLicensePlate.getText().toString().trim());
        car.setClientId(clients.get(clientSpinner.getSelectedItemPosition()).getId());
        car.setCarImage(ImageUtils.fromImageViewToByteArray(carImage));

        if ((car.getBrand().equals("")) || (car.getModel().equals("")) || (car.getLicensePlate().equals(""))) {
            Toast.makeText(this, R.string.complete_all_fields, Toast.LENGTH_SHORT).show();
        } else {

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "car").allowMainThreadQueries().build();

            if (modifyCar) {
                modifyCar = false;
                addButton.setText(R.string.add_button);
                db.carDao().update(car);
                Toast.makeText(this, R.string.modified_car, Toast.LENGTH_SHORT).show();
            } else {
                car.setId(0);
                db.carDao().insert(car);
                Toast.makeText(this, R.string.added_car, Toast.LENGTH_SHORT).show();
            }

            carImage.setImageResource(R.drawable.ic_menu_camera);
            etBrand.setText("");
            etModel.setText("");
            etLicensePlate.setText("");

        }
    }



    private void fillSpinner() {

        clients.clear();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "client").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        clients.addAll(db.clientDao().getAll());

        String[] arraySpinner = new String[clients.size()];

        int i = 0;
        for (Client client : clients) {
            arraySpinner[i] = client.getName() + " " + client.getSurname();
            i++;
        }

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinner);
        clientSpinner.setAdapter(adapterSpinner);
    }


    public void takePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            carImage.setImageBitmap(imageBitmap);
        }
    }


}