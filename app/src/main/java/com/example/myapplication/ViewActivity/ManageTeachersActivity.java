package com.example.myapplication.ViewActivity;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ManageTeachersActivity extends AppCompatActivity {
    private static final String TAG = "ManageTeachersActivity";
    ListView lv;
    ArrayAdapter<Teacher> adapter;
    ArrayList<Teacher> cl = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_teacher);
        ImageButton backButton = findViewById(R.id.back_home);
        backButton.setOnClickListener(v -> finish());
        dbHelper dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        TeacherDao TeacherDao = new TeacherDao(db);

        lv = findViewById(R.id.teacherListView);
        cl = TeacherDao.getAllTeachers( ManageTeachersActivity.this);
        adapter = new ArrayAdapter<>(ManageTeachersActivity.this, android.R.layout.simple_list_item_1, cl);
        lv.setAdapter(adapter);
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
                Toast.makeText(ManageTeachersActivity.this, "pos" + position, Toast.LENGTH_SHORT).show();
                showdialogEdit(position);
            }
        });

    }
    private void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageTeachersActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_create_teacher, null);
        EditText etTeacherName = view.findViewById(R.id.etTeacherName);
        EditText etExperience = view.findViewById(R.id.etExperience);
        EditText etEmail = view.findViewById(R.id.etEmail);
        RadioGroup rgSex = view.findViewById(R.id.rgSex);

        Button btnSaveTeacher = view.findViewById(R.id.btnSaveTeacher);


        btnSaveTeacher.setOnClickListener(v -> {

            String teacherName = etTeacherName.getText().toString();
            String experienceStr = etExperience.getText().toString();
            String email = etEmail.getText().toString();
            int selectedSexId = rgSex.getCheckedRadioButtonId();
            String sex = selectedSexId == R.id.rbMale ? "Male" : "Female";

            if (teacherName.isEmpty() || experienceStr.isEmpty() || email.isEmpty() || selectedSexId == -1) {
                Toast.makeText(ManageTeachersActivity.this, "Please enter full information", Toast.LENGTH_SHORT).show();
            } else {
                int experience = Integer.parseInt(experienceStr);

                dbHelper dbHelper = new dbHelper(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                TeacherDao teacherDao = new TeacherDao(db);

                boolean success = teacherDao.addTeacher(teacherName, experience, email, sex);

                if (success) {
                    Toast.makeText(ManageTeachersActivity.this, "Teacher added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageTeachersActivity.this, "Failed to add teacher.", Toast.LENGTH_SHORT).show();
                }

                cl = teacherDao.getAllTeachers(ManageTeachersActivity.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageTeachersActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_edit_teacher, null);

        // Khởi tạo các View
        EditText editTextNameTeacher = view.findViewById(R.id.etTeacherName);
        EditText editTextExperience = view.findViewById(R.id.etExperience);
        EditText editTextEmail = view.findViewById(R.id.etEmail);
        RadioGroup rgSex = view.findViewById(R.id.rgSex);
        Button btnEdit = view.findViewById(R.id.btnSaveTeacher);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        dbHelper dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        TeacherDao teacherDaoDAO = new TeacherDao(db);

        List<Teacher> teacherList = teacherDaoDAO.getAllTeachers(ManageTeachersActivity.this);
        Teacher selectedTeacher = teacherList.get(pos);

        // Gán dữ liệu vào các trường
        editTextNameTeacher.setText(selectedTeacher.getNameTeacher());
        editTextExperience.setText(String.valueOf(selectedTeacher.getExperience()));
        editTextEmail.setText(selectedTeacher.getEmail());


        if (selectedTeacher.getSex().equalsIgnoreCase("Male")) {
            rgSex.check(R.id.rbMale);
        } else {
            rgSex.check(R.id.rbFemale);
        }

        btnEdit.setOnClickListener(v -> {
            String nameTeacher = editTextNameTeacher.getText().toString();
            int experience = Integer.parseInt(editTextExperience.getText().toString());
            String email = editTextEmail.getText().toString();


            String sex = (rgSex.getCheckedRadioButtonId() == R.id.rbMale) ? "Male" : "Female";

            // Kiểm tra dữ liệu nhập vào
            if (nameTeacher.isEmpty() || experience < 0 || email.isEmpty() || sex.isEmpty()) {
                Toast.makeText(ManageTeachersActivity.this, "Please enter full information", Toast.LENGTH_SHORT).show();
            } else {
                boolean isUpdated = teacherDaoDAO.updateTeacher(selectedTeacher.getTeacherId(), nameTeacher, experience, email, sex);
                if (isUpdated) {
                    List<Teacher> updatedList = teacherDaoDAO.getAllTeachers(ManageTeachersActivity.this);
                    adapter.clear();
                    adapter.addAll(updatedList);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ManageTeachersActivity.this, "Teacher updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageTeachersActivity.this, "Failed to update teacher", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(v -> {
            int rowsAffected = teacherDaoDAO.deleteTeacher(selectedTeacher.getTeacherId());
            if (rowsAffected > 0) {
                List<Teacher> updatedList = teacherDaoDAO.getAllTeachers(ManageTeachersActivity.this);
                adapter.clear();
                adapter.addAll(updatedList);
                adapter.notifyDataSetChanged();
                Toast.makeText(ManageTeachersActivity.this, "Teacher deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ManageTeachersActivity.this, "Failed to delete teacher", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(view).setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
        Dialog dialog = builder.create();
        dialog.show();
    }



}
