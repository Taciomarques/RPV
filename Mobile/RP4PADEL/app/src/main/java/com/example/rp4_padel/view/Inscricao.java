package com.example.rp4_padel.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rp4_padel.R;



public class Inscricao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscricao_dupla);

        Spinner spinner = findViewById(R.id.impedimento);

//      Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);

//      Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//      Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        Spinner spinner2 = findViewById(R.id.categoria);

//      Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.categoria_array, android.R.layout.simple_spinner_item);

//      Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//      Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);


    }


    public void salvarDupla(View view) {
//            retornar para a tela inicial do app
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
