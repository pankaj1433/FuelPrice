package com.cashliquid.fuelprices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FuelActivity extends AppCompatActivity {

    private String[] cities;
    private Spinner city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);
        Button btn=(Button)findViewById(R.id.button);

        btn.setOnClickListener(onClickListener);
        addCities();
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(final View v) {
            switch(v.getId()){
                case R.id.button:
                    goToResultActivity();
                    break;
            }

        }
    };
    private void goToResultActivity(){
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("CITY", getSelectedCity());
        intent.putExtra("FUEL", getSelectedFuel());
        startActivity(intent);
    }
    public void addCities(){

        Spinner drop=(Spinner) findViewById(R.id.cities);
        List<String> list = new ArrayList<String>();
        cities = new String[] {
                "New Delhi", "Kolkata","Mumbai","Chennai","Faridabad","Gurgaon","Noida","Ghaziabad","Agartala","Aizwal","Ambala","Bangalore","Bhopal","Bhubhaneswar","Chandigarh",
                "Dehradun","Gandhinagar","Gangtok","Guwahati","Hyderabad","Imphal","Itanagar","Jaipur","Jammu","Jullunder","Kohima","Lucknow","Panjim","Patna",
                "Pondichery","Port Blair","Raipur","Ranchi","Shillong","Shimla","Srinagar","Trivandrum","Silvasa","Daman"
        };
        Collections.addAll(list, cities);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drop.setAdapter(dataAdapter);

        RadioButton selectedButton = (RadioButton) findViewById(R.id.diesel);
        RadioGroup fuelGroup=(RadioGroup) findViewById(R.id.radioGroup);
        fuelGroup.check(selectedButton.getId());
    }
    private String getSelectedCity(){
        Spinner City=(Spinner) findViewById(R.id.cities);
        String SelectedCity=City.getSelectedItem().toString();
        return SelectedCity;
    }
    private  String getSelectedFuel(){
        RadioGroup Fuel=(RadioGroup) findViewById(R.id.radioGroup);
        RadioButton Selected=(RadioButton) findViewById(Fuel.getCheckedRadioButtonId());
        String SelectedFuel=Selected.getText().toString();
        return SelectedFuel;
    }


}
