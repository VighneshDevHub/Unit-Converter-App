package com.example.unitconverter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class TorqueConversion extends AppCompatActivity {

    private CardView cv_fromUnit, cv_toUnit, cv_convert;
    private RelativeLayout mCLayout;
    private String fromUnit = "Newton Meters";
    private String toUnit = "Pound-Feet";
    private TextView tv_fromUnit, tv_toUnit;
    private EditText et_fromUnit, et_toUnit;
    private final String[] units = {
            "Newton Meters", "Kilonewton Meters", "Millinewton Meters",
            "Dyne Centimeters", "Kilogram-Force Meters", "Gram-Force Meters",
            "Ounce-Force Inches", "Pound-Feet"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torque_conversion);

        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);
        mCLayout = findViewById(R.id.torque_relativeLayout);
        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);
        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        tv_fromUnit.setText(units[0]);
        tv_toUnit.setText(units[7]);

        cv_convert.setOnClickListener(v -> convertTorque());

        cv_toUnit.setOnClickListener(v -> showUnitSelectorDialog(tv_toUnit, selected -> toUnit = selected));
        cv_fromUnit.setOnClickListener(v -> showUnitSelectorDialog(tv_fromUnit, selected -> fromUnit = selected));
    }

    private void showUnitSelectorDialog(TextView textView, UnitSelectionListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Unit");
        builder.setSingleChoiceItems(units, -1, (dialog, which) -> {
            String selectedUnit = units[which];
            textView.setText(selectedUnit);
            listener.onUnitSelected(selectedUnit);
            dialog.dismiss();
        });
        builder.create().show();
    }

    private void convertTorque() {
        String torqueInput = et_fromUnit.getText().toString();
        if (torqueInput.isEmpty()) {
            et_fromUnit.setError("Please enter some value");
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(torqueInput);
        } catch (NumberFormatException e) {
            et_fromUnit.setError("Invalid input. Please enter a numeric value.");
            return;
        }

        double outputValue = convert(inputValue, fromUnit, toUnit);
        et_toUnit.setText(String.valueOf(outputValue));
    }

    @FunctionalInterface
    interface UnitSelectionListener {
        void onUnitSelected(String unit);
    }

    private double convert(double value, String from, String to) {
        double newtonMeters = toNewtonMeters(value, from);
        return fromNewtonMeters(newtonMeters, to);
    }

    private double toNewtonMeters(double value, String from) {
        switch (from) {
            case "Kilonewton Meters":
                return value * 1000;
            case "Millinewton Meters":
                return value / 1000;
            case "Dyne Centimeters":
                return value * 1e-7;
            case "Kilogram-Force Meters":
                return value * 9.80665;
            case "Gram-Force Meters":
                return value * 9.80665e-3;
            case "Ounce-Force Inches":
                return value * 0.112984829;
            case "Pound-Feet":
                return value * 1.35582;
            default:
                return value; // Newton Meters
        }
    }

    private double fromNewtonMeters(double value, String to) {
        switch (to) {
            case "Kilonewton Meters":
                return value / 1000;
            case "Millinewton Meters":
                return value * 1000;
            case "Dyne Centimeters":
                return value / 1e-7;
            case "Kilogram-Force Meters":
                return value / 9.80665;
            case "Gram-Force Meters":
                return value / 9.80665e-3;
            case "Ounce-Force Inches":
                return value / 0.112984829;
            case "Pound-Feet":
                return value / 1.35582;
            default:
                return value; // Newton Meters
        }
    }
}
