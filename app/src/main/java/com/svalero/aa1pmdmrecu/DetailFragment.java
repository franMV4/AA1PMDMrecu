package com.svalero.aa1pmdmrecu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svalero.aa1pmdmrecu.util.DateUtils;
import com.svalero.aa1pmdmrecu.util.ImageUtils;

import java.time.LocalDate;

public class DetailFragment extends Fragment {

    private closeDetails closeDetails;
    private String activity;
    private final String VIEW_CAR_ACTIVITY = "ViewCarActivity";
    private final String VIEW_CLIENT_ACTIVITY = "ViewClientActivity";
    private final String VIEW_ORDER_ACTIVITY = "ViewOrderActivity";

    public FloatingActionButton closeButton;

    public DetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View detailView = inflater.inflate(R.layout.fragment_order_detail, container, false);

        Activity thisActivity = getActivity();
        if (thisActivity != null) {
            if (thisActivity.toString().contains(VIEW_CAR_ACTIVITY)) {
                activity = VIEW_CAR_ACTIVITY;
            } else if (thisActivity.toString().contains(VIEW_CLIENT_ACTIVITY)) {
                activity = VIEW_CLIENT_ACTIVITY;
            } else if (thisActivity.toString().contains(VIEW_ORDER_ACTIVITY)) {
                activity = VIEW_ORDER_ACTIVITY;
            }
        }

        closeButton = detailView.findViewById(R.id.close_detail_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDetails.hiddeDetails();
            }
        });

        return detailView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = view.findViewById(R.id.fragment_detail_imageview);
        TextView textView1 = view.findViewById(R.id.fragment_detail_textview1);
        TextView textView2 = view.findViewById(R.id.fragment_detail_textview2);
        TextView textView3 = view.findViewById(R.id.fragment_detail_textview3);
        TextView textView4 = view.findViewById(R.id.fragment_detail_textview4);

        switch (activity) {
            case VIEW_CAR_ACTIVITY:
                break;
            case VIEW_CLIENT_ACTIVITY:
                if (getArguments() != null) {
                    if (getArguments().getByteArray("client_image") != null)
                        imageView.setImageBitmap
                                (ImageUtils.getBitmap(getArguments().getByteArray("client_image")));
                    textView1.setText(getArguments().getString("name") + " " + getArguments().getString("surname"));
                    textView2.setText(getArguments().getString("dni"));
                    if (getArguments().getBoolean("vip")) {
                        textView3.setText(R.string.retired);
                    } else {
                        textView3.setText(R.string.no_retired);
                    }
                }
                break;
            case VIEW_ORDER_ACTIVITY:
                if (getArguments() != null) {
                    if (getArguments().getByteArray("car_image") != null)
                        imageView.setImageBitmap
                                (ImageUtils.getBitmap(getArguments().getByteArray("car_image")));
                    textView1.setText(DateUtils.fromLocalDateToMyDateFormatString
                            (LocalDate.parse(getArguments().getString("date"))));
                    textView2.setText(getArguments().getString("name"));
                    textView3.setText(getArguments().getString("model") + " || " + getArguments().getString("license"));
                    textView4.setText(getArguments().getString("description"));
                }
                break;
        }   // End switch
    }



    public interface closeDetails {
        void hiddeDetails();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof closeDetails) {
            closeDetails = (DetailFragment.closeDetails) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }



















}