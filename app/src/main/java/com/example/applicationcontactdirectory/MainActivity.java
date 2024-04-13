package com.example.applicationcontactdirectory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationcontactdirectory.entities.Contact;
import com.example.applicationcontactdirectory.entities.ContactAdapter;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText txtName, txtPhone, txtEmail;
    RecyclerView lstContacts;
    ArrayList<Contact> contacts = new ArrayList<>();

    ContactAdapter contactAdapter = new ContactAdapter(contacts);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        lstContacts = findViewById(R.id.lstContacts);

        // Cargar los contactos almacenados y actualizar el RecyclerView
        contacts = loadContacts();  // Asegúrate de que esta lista es la misma que el adaptador utiliza
        contactAdapter = new ContactAdapter(contacts);
        lstContacts.setAdapter(contactAdapter);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));


    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private ArrayList<Contact> loadContacts() {
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        String contactsJson = preferences.getString("contacts", null);
        if (contactsJson == null) {
            return new ArrayList<>();
        } else {
            Type contactListType = new TypeToken<ArrayList<Contact>>(){}.getType();
            return new Gson().fromJson(contactsJson, contactListType);
        }
    }

    private void saveContacts(ArrayList<Contact> contacts) {
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String contactsJson = new Gson().toJson(contacts);
        editor.putString("contacts", contactsJson);
        editor.apply();
    }



    public void cmdSave_onClick(View view) {
        // Obtén los valores actuales de los campos de texto
        String name = txtName.getText().toString();
        String phone = txtPhone.getText().toString();
        String email = txtEmail.getText().toString();

        // Crear un nuevo objeto Contact
        Contact newContact = new Contact(name, phone, email);

        // Cargar los contactos existentes y agregar el nuevo contacto
        contacts.add(newContact);

        // Guardar la lista actualizada de contactos
        saveContacts(contacts);

        // Actualizar el RecyclerView
        contactAdapter.notifyDataSetChanged();

        // Limpiar los campos de texto
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");

        Toast.makeText(this, "Contacto agregado", Toast.LENGTH_SHORT).show();
        hideKeyboard(view);
    }



    public void cmdRead_onClick(View view) {
        String searchName = txtName.getText().toString();
        boolean found = false;

        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(searchName)) {
                txtPhone.setText(contact.getPhone());
                txtEmail.setText(contact.getEmail());
                Toast.makeText(this, "Usuario encontrado", Toast.LENGTH_SHORT).show();
                found = true;
                break; // Salir del bucle una vez que se encuentra el contacto
            }
        }

        if (!found) {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            txtPhone.setText(""); // Limpiar los campos si se desea
            txtEmail.setText("");
        }
    }



}