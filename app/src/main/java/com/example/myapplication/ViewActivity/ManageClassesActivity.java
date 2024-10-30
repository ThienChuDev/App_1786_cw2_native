package com.example.myapplication.ViewActivity;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ManageClassesActivity extends AppCompatActivity {
    private static final String TAG = "ManageClassesActivity";
    ListView lv;
    ArrayAdapter<yogaClasses> adapter;
    ArrayList<yogaClasses> cl = new ArrayList<>();
    AutoCompleteTextView autoCompleteTextView;
    String url = "http://192.168.1.4:3000/crud/createClass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_classe);


        ImageButton btnCloudAll = findViewById(R.id.btnCloudAll);
        ImageButton backButton = findViewById(R.id.back_home);
        backButton.setOnClickListener(v -> finish());
        autoCompleteTextView = findViewById(R.id.editTextSearch);
        dbHelper dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        yogaClassDAO yogaClassDAO = new yogaClassDAO(db);



        lv = findViewById(R.id.btnItem);
        cl = yogaClassDAO.getAllClasses(ManageClassesActivity.this);
        adapter = new ArrayAdapter<>(ManageClassesActivity.this, android.R.layout.simple_list_item_1, cl);
        lv.setAdapter(adapter);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
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
                Toast.makeText(ManageClassesActivity.this, "pos" + position, Toast.LENGTH_SHORT).show();
                showdialogEdit(position);
            }
        });

        btnCloudAll.setOnClickListener(v -> {
            RequestQueue queue = Volley.newRequestQueue(this);
            for (int i = 0; i < cl.size(); i++) {
                yogaClasses yogaClass = cl.get(i);
                String sessionId = yogaClass.getSessionId() == 1 ? "Yoga Studio A" :
                        yogaClass.getSessionId() == 2 ? "Yoga Studio B" :
                                yogaClass.getSessionId() == 3 ? "Yoga Studio C" : "Unknown";

                String teacherId = yogaClass.getTeacherID() == 1 ? "John Doe" :
                        yogaClass.getTeacherID() == 2 ? "Jane Smith" :
                                yogaClass.getTeacherID() == 3 ? "Emily Johnson" : "Unknown";

                JSONObject jsonBody = new JSONObject();

                try {
                    jsonBody.put("sessionId", sessionId);
                    jsonBody.put("teacherId", teacherId);
                    jsonBody.put("dayOfWeek", yogaClass.getDay_of_week());
                    jsonBody.put("startTime", yogaClass.getTimeStart());
                    jsonBody.put("endTime", yogaClass.getTimeEnd());
                    jsonBody.put("capacity", String.valueOf(yogaClass.getCapacity()));
                    jsonBody.put("duration", String.valueOf(yogaClass.getDuration()));
                    jsonBody.put("pricePerClass", String.valueOf(yogaClass.getPrice_per_class()));
                    jsonBody.put("kind_of_classe", yogaClass.getkind_of_classe());
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("VolleyResponse", "Response is: " + response);
                                Toast.makeText(ManageClassesActivity.this, "updated push ALL cloud successfully", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error.networkResponse != null) {
                                    Log.d("VolleyError", "Error Code: " + error.networkResponse.statusCode);
                                    Log.d("VolleyError", "Error Response: " + new String(error.networkResponse.data));
                                } else {
                                    Toast.makeText(ManageClassesActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                                    Log.d("VolleyError", "Network Error: " + error.toString());
                                }
                            }
                        }) {
                    @Override
                    public byte[] getBody() {
                        return jsonBody.toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                };
                try {
                    queue.add(stringRequest);
                } catch (Exception e) {
                    Log.e("Error", "Error while sending request: " + e.getMessage());
                }
            }
        });
    }

    private void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageClassesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_create_yoga_class, null);

        Spinner spinnerSessionId = view.findViewById(R.id.spinnerSessionId);
        Spinner spinnerTeacherId = view.findViewById(R.id.spinnerTeacherId);
        EditText editTextDayOfweek = view.findViewById(R.id.etDayOfWeek);
        TimePicker editTextStartTime = view.findViewById(R.id.timePickerStart);
        TimePicker editTextEndTime = view.findViewById(R.id.timePickerEnd);
        EditText editTextCapacity = view.findViewById(R.id.etCapacity);
        EditText editTextDuration = view.findViewById(R.id.etDuration);
        EditText editTextPricePer = view.findViewById(R.id.etPricePerClass);
        EditText editTextDescription = view.findViewById(R.id.etDescription);
        Button btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            int sessionId;
            if (spinnerSessionId.getSelectedItem() != null) {
                String selectedSession = spinnerSessionId.getSelectedItem().toString();
                sessionId = selectedSession.equals("Yoga Studio A") ? 1 :
                        selectedSession.equals("Yoga Studio B") ? 2 :
                                selectedSession.equals("Yoga Studio C") ? 3 : -1;
            } else {
                sessionId = -1;
            }

            int teacherId;
            if (spinnerTeacherId.getSelectedItem() != null) {
                String selectedTeacher = spinnerTeacherId.getSelectedItem().toString();
                teacherId = selectedTeacher.equals("John Doe") ? 1 :
                        selectedTeacher.equals("Jane Smith") ? 2 :
                                selectedTeacher.equals("Emily Johnson") ? 3 : -1;
            } else {
                teacherId = -1;
            }

            String dayOfWeek = editTextDayOfweek.getText().toString();
             int capacity = -1,duration = -1, pricePerClass = -1;
            String description = editTextDescription.getText().toString();

            try {
                capacity = Integer.parseInt(editTextCapacity.getText().toString());
                duration = Integer.parseInt(editTextDuration.getText().toString());
                pricePerClass = Integer.parseInt(editTextPricePer.getText().toString());
            } catch (NumberFormatException e) {

            }

            int startHour = editTextStartTime.getHour();
            int startMinute = editTextStartTime.getMinute();
            int endHour = editTextEndTime.getHour();
            int endMinute = editTextEndTime.getMinute();

            String startTime = String.format(Locale.getDefault(), "%02d:%02d", startHour, startMinute);
            String endTime = String.format(Locale.getDefault(), "%02d:%02d", endHour, endMinute);

            if (sessionId == -1 || teacherId == -1 || dayOfWeek.isEmpty() || startTime.isEmpty() ||
                    endTime.isEmpty() || capacity < 0 || duration < 0 || pricePerClass < 0 ||
                    description.isEmpty()) {
                Toast.makeText(ManageClassesActivity.this, "Please enter full information", Toast.LENGTH_SHORT).show();
            } else {

                dbHelper dbHelper = new dbHelper(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                yogaClassDAO yogaClassDAO = new yogaClassDAO(db);

                boolean success = yogaClassDAO.addClass(sessionId, teacherId, dayOfWeek, startTime, endTime, capacity, duration, pricePerClass, description);
                if (success) {
                    Toast.makeText(ManageClassesActivity.this, "Class added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageClassesActivity.this, "Failed to add class.", Toast.LENGTH_SHORT).show();
                }

                cl = yogaClassDAO.getAllClasses(ManageClassesActivity.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageClassesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_edit_yoga_class, null);

        Spinner spinnerSessionId = view.findViewById(R.id.spinnerSessionIdE);
        Spinner spinnerTeacherId = view.findViewById(R.id.spinnerTeacherIdE);
        EditText editTextDayOfweek = view.findViewById(R.id.etDayOfWeekE);
        TimePicker editTextStartTime = view.findViewById(R.id.etTimeStartE);
        TimePicker editTextEndTime = view.findViewById(R.id.etTimeEndE);
        EditText editTextCapacity = view.findViewById(R.id.etCapacityE);
        EditText editTextDuration = view.findViewById(R.id.etDurationE);
        EditText editTextPricePer = view.findViewById(R.id.etPricePerClassE);
        EditText editTextDescription = view.findViewById(R.id.etDescriptionE);
        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnDelete = view.findViewById(R.id.btnDelete);
        ImageButton btnCloudEdit = view.findViewById(R.id.btnCloudEdit);


        dbHelper dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        yogaClassDAO yogaClassDAO = new yogaClassDAO(db);

        List<yogaClasses> yogaClassesList = yogaClassDAO.getAllClasses(ManageClassesActivity.this);
        yogaClasses selectedClass = yogaClassesList.get(pos);

        int startHour = editTextStartTime.getHour();
        int startMinute = editTextStartTime.getMinute();
        int endHour = editTextEndTime.getHour();
        int endMinute = editTextEndTime.getMinute();

        String startTime = String.format(Locale.getDefault(), "%02d:%02d", startHour, startMinute);
        String endTime = String.format(Locale.getDefault(), "%02d:%02d", endHour, endMinute);

        spinnerSessionId.setSelection(selectedClass.getSessionId() - 1);
        spinnerTeacherId.setSelection(selectedClass.getTeacherID() - 1);
        editTextDayOfweek.setText(selectedClass.getDay_of_week());

        editTextCapacity.setText(String.valueOf(selectedClass.getCapacity()));
        editTextDuration.setText(String.valueOf(selectedClass.getDuration()));
        editTextPricePer.setText(String.valueOf(selectedClass.getPrice_per_class()));
        editTextDescription.setText(selectedClass.getkind_of_classe());

        btnEdit.setOnClickListener(v -> {
            int sessionId = spinnerSessionId.getSelectedItem().toString().equals("Yoga Studio A") ? 1 :
                    spinnerSessionId.getSelectedItem().toString().equals("Yoga Studio B") ? 2 :
                            spinnerSessionId.getSelectedItem().toString().equals("Yoga Studio C") ? 3 : -1;

            int teacherId = spinnerTeacherId.getSelectedItem().toString().equals("John Doe") ? 1 :
                    spinnerTeacherId.getSelectedItem().toString().equals("Jane Smith") ? 2 :
                            spinnerTeacherId.getSelectedItem().toString().equals("Emily Johnson") ? 3 : -1;

            String dayOfWeek = editTextDayOfweek.getText().toString();

            int capacity = Integer.parseInt(editTextCapacity.getText().toString());
            int duration = Integer.parseInt(editTextDuration.getText().toString());
            int pricePerClass = Integer.parseInt(editTextPricePer.getText().toString());
            String description = editTextDescription.getText().toString();


            if (sessionId == -1 || teacherId == -1 || dayOfWeek.isEmpty() || startTime.isEmpty() ||
                    endTime.isEmpty() || capacity <= 0 || duration <= 0 || pricePerClass <= 0 ||
                    description.isEmpty()) {
                Toast.makeText(ManageClassesActivity.this, "Please enter full information", Toast.LENGTH_SHORT).show();
            } else {
                int isUpdated = yogaClassDAO.updateClass(selectedClass.getClassesId(), sessionId, teacherId, dayOfWeek, startTime, endTime, capacity, duration, pricePerClass, description);
                if (isUpdated > 0) {
                    List<yogaClasses> cl = yogaClassDAO.getAllClasses(ManageClassesActivity.this);
                    adapter.clear();
                    adapter.addAll(cl);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ManageClassesActivity.this, "Class updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageClassesActivity.this, "Failed to update class", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(v -> {
            int rowsAffected = yogaClassDAO.deleteClass(selectedClass.getClassesId());
            if (rowsAffected > 0) {
                List<yogaClasses> cl = yogaClassDAO.getAllClasses(ManageClassesActivity.this);
                adapter.clear();
                adapter.addAll(cl);
                adapter.notifyDataSetChanged();
                Toast.makeText(ManageClassesActivity.this, "Class deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ManageClassesActivity.this, "Failed to delete class", Toast.LENGTH_SHORT).show();
            }
        });


        btnCloudEdit.setOnClickListener(v -> {
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("sessionId", spinnerSessionId.getSelectedItem().toString());
                jsonBody.put("teacherId", spinnerTeacherId.getSelectedItem().toString());
                jsonBody.put("dayOfWeek", editTextDayOfweek.getText().toString() );
                jsonBody.put("startTime", startTime);
                jsonBody.put("endTime", endTime);
                jsonBody.put("capacity", editTextCapacity.getText().toString());
                jsonBody.put("duration", editTextDuration.getText().toString());
                jsonBody.put("pricePerClass", editTextPricePer.getText().toString());
                jsonBody.put("kind_of_classe", editTextDescription.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("VolleyResponse", "Response is: " + response);
                            Toast.makeText(ManageClassesActivity.this, "updated push cloud successfully", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse != null) {
                                Log.d("VolleyError", "Error Code: " + error.networkResponse.statusCode);
                                Log.d("VolleyError", "Error Response: " + new String(error.networkResponse.data));
                            } else {
                                Toast.makeText(ManageClassesActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                                Log.d("VolleyError", "Network Error: " + error.toString());
                            }
                        }
                    }) {
                @Override
                public byte[] getBody() {
                    return jsonBody.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };
            try {
                queue.add(stringRequest);
            } catch (Exception e) {
                Log.e("Error", "Error while sending request: " + e.getMessage());
            }
        });


        builder.setView(view).setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
        Dialog dialog = builder.create();
        dialog.show();
    }

}
