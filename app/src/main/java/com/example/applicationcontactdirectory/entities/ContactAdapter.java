package com.example.applicationcontactdirectory.entities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationcontactdirectory.R;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private ArrayList<Contact> contacts;

    public ContactAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.txtContactName.setText(contact.getName());
        holder.txtContactPhone.setText(contact.getPhone());
        holder.txtContactEmail.setText(contact.getEmail());
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtContactName, txtContactPhone, txtContactEmail;

        public ViewHolder(View view) {
            super(view);
            txtContactName = view.findViewById(R.id.txtContactName);
            txtContactPhone = view.findViewById(R.id.txtContactPhone);
            txtContactEmail = view.findViewById(R.id.txtContactEmail);
        }
    }

}