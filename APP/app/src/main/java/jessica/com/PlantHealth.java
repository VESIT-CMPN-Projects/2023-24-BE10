package jessica.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PlantHealth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_health);

        CardView tomato = findViewById(R.id.tomato);
        tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlantHealth.this,TomatoPlant.class));
            }
        });
        CardView potato = findViewById(R.id.potato);
        potato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlantHealth.this,Potato.class));
            }
        });

        CardView apple = findViewById(R.id.apple);
        apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlantHealth.this,Apple.class));
            }
        });
        CardView cherry = findViewById(R.id.cherry);
        cherry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlantHealth.this,Cherry.class));
            }
        });
        CardView strawberry = findViewById(R.id.strawberry);
        strawberry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlantHealth.this,Strawberry.class));
            }
        });
        CardView corn = findViewById(R.id.corn);
        corn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlantHealth.this,Corn.class));
            }
        });
        CardView grape = findViewById(R.id.grapes);
        grape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlantHealth.this,Grape.class));
            }
        });
        CardView peach = findViewById(R.id.peach);
        peach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlantHealth.this,Peach.class));
            }
        });
        CardView bellpepper = findViewById(R.id.bellpepper);
        bellpepper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlantHealth.this,BellPepper.class));
            }
        });

    }
}