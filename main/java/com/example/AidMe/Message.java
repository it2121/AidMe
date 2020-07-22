package com.example.AidMe;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public  class Message extends DialogFragment
{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void show(String title, String message)
    {
        dialog = new AlertDialog.Builder(getActivity()) // Pass a reference to your main activity here
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialogInterface, i) -> dialog.cancel())
                .show();
    }

    private AlertDialog dialog;
}