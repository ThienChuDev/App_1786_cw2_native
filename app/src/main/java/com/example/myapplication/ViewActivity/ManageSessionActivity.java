
package com.example.myapplication.ViewActivity;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ManageSessionActivity extends AppCompatActivity {
    private static final String TAG = "ManageSessionActivity";
    ListView lv;
    ArrayAdapter<Session> adapter;
    ArrayList<Session> cl = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_session);

        dbHelper dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SessionDao SessionDao = new SessionDao(db);

        lv = findViewById(R.id.btnItem);
        cl = SessionDao.getAllSessions(ManageSessionActivity.this);
        adapter = new ArrayAdapter<>(ManageSessionActivity.this, android.R.layout.simple_list_item_1, cl);
        lv.setAdapter(adapter);
        ImageButton backButton = findViewById(R.id.back_home);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ManageSessionActivity.this, HomeActivity.class); // Change HomeActivity to your home activity class name
            startActivity(intent);
            finish();
        });
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ManageSessionActivity.this, "pos" + position, Toast.LENGTH_SHORT).show();
                showdialogEdit(position);
            }
        });

    }


    private void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageSessionActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_create_session, null);

        EditText etLocation = view.findViewById(R.id.etLocation);
        DatePicker datePickerStart = view.findViewById(R.id.datePickerStart);
        DatePicker datePickerEnd = view.findViewById(R.id.datePickerEnd);
        TextView tvCalendarStart = view.findViewById(R.id.tvCalendarStart);
        TextView tvCalendarEnd = view.findViewById(R.id.tvCalendarEnd);


        tvCalendarStart.setOnClickListener(v -> {
            datePickerStart.setVisibility(View.VISIBLE);
            datePickerEnd.setVisibility(View.GONE);
        });


        tvCalendarEnd.setOnClickListener(v -> {
            datePickerEnd.setVisibility(View.VISIBLE);
            datePickerStart.setVisibility(View.GONE);
        });

        Button btnSaveSession = view.findViewById(R.id.btnSaveSession);
        btnSaveSession.setOnClickListener(v -> {
            String location = etLocation.getText().toString();
            int startDay = datePickerStart.getDayOfMonth();
            int startMonth = datePickerStart.getMonth();
            int startYear = datePickerStart.getYear();
            String dayStart = startYear + "-" + (startMonth + 1) + "-" + startDay;

            int endDay = datePickerEnd.getDayOfMonth();
            int endMonth = datePickerEnd.getMonth();
            int endYear = datePickerEnd.getYear();
            String dayEnd = endYear + "-" + (endMonth + 1) + "-" + endDay;

            if (location.isEmpty() || dayStart.isEmpty() || dayEnd.isEmpty()) {
                Toast.makeText(ManageSessionActivity.this, "Please enter full information", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper dbHelper = new dbHelper(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                SessionDao sessionDao = new SessionDao(db);

                boolean success = sessionDao.addSession(location, dayStart, dayEnd);

                if (success) {
                    Toast.makeText(ManageSessionActivity.this, "Session added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageSessionActivity.this, "Failed to add session.", Toast.LENGTH_SHORT).show();
                }

                cl = sessionDao.getAllSessions(ManageSessionActivity.this);
                adapter.clear();
                adapter.addAll(cl);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setView(view)
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

        Dialog dialog = builder.create();
        dialog.show();
    }


    private void showdialogEdit(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageSessionActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_edit_session, null);

        EditText editTextLocation = view.findViewById(R.id.etLocation);
        DatePicker datePickerStart = view.findViewById(R.id.datePickerStart);
        DatePicker datePickerEnd = view.findViewById(R.id.datePickerEnd);
        Button btnEdit = view.findViewById(R.id.btnSaveSession);
        Button btnDelete = view.findViewById(R.id.btnDeleteSession);
        TextView tvCalendarStart = view.findViewById(R.id.tvCalendarStart);
        TextView tvCalendarEnd = view.findViewById(R.id.tvCalendarEnd);
        dbHelper dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SessionDao sessionDao = new SessionDao(db);

        List<Session> sessionList = sessionDao.getAllSessions(ManageSessionActivity.this); // Retrieve session list
        Session selectedSession = sessionList.get(pos);
        Log.d("Session Info", selectedSession.toString());
        editTextLocation.setText(selectedSession.getLocation());

        tvCalendarStart.setOnClickListener(v -> {
            datePickerStart.setVisibility(View.VISIBLE);
            datePickerEnd.setVisibility(View.GONE);
        });


        tvCalendarEnd.setOnClickListener(v -> {
            datePickerEnd.setVisibility(View.VISIBLE);
            datePickerStart.setVisibility(View.GONE);
        });
        btnEdit.setOnClickListener(v -> {
            String location = editTextLocation.getText().toString();
            int startDay = datePickerStart.getDayOfMonth();
            int startMonth = datePickerStart.getMonth();
            int startYear = datePickerStart.getYear();
            int endDay = datePickerEnd.getDayOfMonth();
            int endMonth = datePickerEnd.getMonth();
            int endYear = datePickerEnd.getYear();
            String dayStart = startYear + "-" + (startMonth + 1) + "-" + startDay;
            String dayEnd = endYear + "-" + (endMonth + 1) + "-" + endDay;

            if (location.isEmpty()) {
                Toast.makeText(ManageSessionActivity.this, "Please enter a location", Toast.LENGTH_SHORT).show();
            } else {
                boolean isUpdated = sessionDao.updateSession(
                        selectedSession.getSessionId(),
                        location,
                        dayStart,
                        dayEnd
                );
                if (isUpdated) {
                    List<Session> updatedList = sessionDao.getAllSessions(ManageSessionActivity.this);
                    adapter.clear();
                    adapter.addAll(updatedList);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ManageSessionActivity.this, "Session updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageSessionActivity.this, "Failed to update session", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(v -> {

            int rowsAffected = sessionDao.deleteSession(selectedSession.getSessionId());

            if (rowsAffected > 0) {
                List<Session> updatedList = sessionDao.getAllSessions(ManageSessionActivity.this);
                adapter.clear();
                adapter.addAll(updatedList);
                adapter.notifyDataSetChanged();
                Toast.makeText(ManageSessionActivity.this, "Session deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ManageSessionActivity.this, "Failed to delete session", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(view).setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
        Dialog dialog = builder.create();
        dialog.show();
    }

}

