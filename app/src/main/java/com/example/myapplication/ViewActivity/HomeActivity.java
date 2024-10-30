package com.example.myapplication.ViewActivity;
//import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.R;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ImageButton informationButton = findViewById(R.id.manage_classe);
        ImageButton manage_teacher = findViewById(R.id.manage_teacher);
        ImageButton manage_schedule = findViewById(R.id.manage_schedule);
        informationButton.setOnClickListener(v->
        {
            Intent intent =new Intent(HomeActivity.this, ManageClassesActivity.class);
            startActivity(intent);
        });

        manage_teacher.setOnClickListener(v -> {
            {
                Intent intent =new Intent(HomeActivity.this, ManageTeachersActivity.class);
                startActivity(intent);
            }
        });

        manage_schedule.setOnClickListener(v -> {
            {
                Intent intent =new Intent(HomeActivity.this, ManageSessionActivity.class);
                startActivity(intent);
            }
        });
    }
}
