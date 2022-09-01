package com.svalero.aa1pmdmrecu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.svalero.aa1pmdmrecu.database.AppDatabase;
import com.svalero.aa1pmdmrecu.domain.Client;
import com.svalero.aa1pmdmrecu.util.ImageUtils;


public class AddClientActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {




    private Client client;
    private GoogleMap map;
    private Marker marker;
    private Switch retiredSwitch;
    private ImageView clientImage;
    private EditText etName;
    private EditText etSurname;
    private EditText etDni;
    private Intent intent;
    private Button addButton;

    private boolean modifyClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        retiredSwitch = findViewById(R.id.retired_switch_add_client);
        clientImage = findViewById(R.id.client_imageView);
        etName = findViewById(R.id.name_edittext_add_client);
        etSurname = findViewById(R.id.surname_edittext_add_client);
        etDni = findViewById(R.id.dni_edittext_add_client);
        addButton = findViewById(R.id.add_client_button);

        client = new Client();


        if (ContextCompat.checkSelfPermission(AddClientActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AddClientActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddClientActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000
            );
        }



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        intent();

    }


    private void intent() {

        intent = getIntent();
        modifyClient = intent.getBooleanExtra("modify_client", false);

        if (modifyClient) {
            client.setId(intent.getIntExtra("id", 0));
            client.setRetired(intent.getBooleanExtra("retired", false));
            client.setLatitude(intent.getFloatExtra("latitud", 0));
            client.setLongitude(intent.getFloatExtra("longitud", 0));

            if (intent.getByteArrayExtra("client_image") != null)
                clientImage.setImageBitmap(ImageUtils.getBitmap(intent.getByteArrayExtra("client_image")));
            etName.setText(intent.getStringExtra("name"));
            etSurname.setText(intent.getStringExtra("surname"));
            etDni.setText(intent.getStringExtra("dni"));
            retiredSwitch.setChecked(intent.getBooleanExtra("retired", false));

            addButton.setText(R.string.modify_capital);
        }
    }




    public void addClient(View view) {

        client.setName(etName.getText().toString().trim());
        client.setSurname(etSurname.getText().toString().trim());
        client.setDni(etDni.getText().toString().trim());
        client.setClientImage(ImageUtils.fromImageViewToByteArray(clientImage));

        if ((client.getName().equals("")) || (client.getSurname().equals("")) || (client.getDni().equals(""))) {
            Toast.makeText(this, R.string.complete_all_fields, Toast.LENGTH_SHORT).show();
        } else if (client.getLatitude() == 0 && client.getLongitude() == 0) {
            Toast.makeText(this, R.string.select_map_position, Toast.LENGTH_SHORT).show();
        } else {

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "client").allowMainThreadQueries().build();

            if (modifyClient) {
                modifyClient = false;
                addButton.setText(R.string.add_button);
                db.clientDao().update(client);
                Toast.makeText(this, R.string.modified_client, Toast.LENGTH_SHORT).show();
            } else {
                client.setId(0);
                db.clientDao().insert(client);
                Toast.makeText(this, R.string.added_client, Toast.LENGTH_SHORT).show();
            }

            clientImage.setImageResource(R.drawable.ic_menu_camera);
            etName.setText("");
            etSurname.setText("");
            etDni.setText("");
            retiredSwitch.setChecked(false);

            client.setRetired(false);
            client.setLatitude(0);
            client.setLongitude(0);
            marker.remove();

        }
    }


    public void switchRetired(View view) {

        if (retiredSwitch.isChecked()) {
            retiredSwitch.setText(R.string.retired);
            client.setRetired(true);
        } else {
            retiredSwitch.setText(R.string.no_retired);
            client.setRetired(false);
        }

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
            clientImage.setImageBitmap(imageBitmap);
        }
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;    // Asignamos el mapa pasado por parámetro a nuestra variable de tipo GoogleMap
        googleMap.setOnMapClickListener(this);  // Establecemos un listener de click sencillo para el mapa

        if (client.getLatitude() != 0 && client.getLongitude() != 0) {  // Si el cliente tiene ubicación
            onMapClick(new LatLng(client.getLatitude(), client.getLongitude()));    // Pone un Marker
            map.moveCamera(CameraUpdateFactory.newLatLng    // Centra la camara y asigna un zoom
                    (new LatLng(client.getLatitude(), client.getLongitude())));
            map.moveCamera(CameraUpdateFactory.zoomTo(11));
        }
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

        if (marker != null)
            marker.remove();
        marker = map.addMarker(new MarkerOptions().position(latLng));
        client.setLatitude((float) latLng.latitude);
        client.setLongitude((float) latLng.longitude);

    }







}