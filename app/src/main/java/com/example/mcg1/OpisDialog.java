package com.example.mcg1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class OpisDialog extends AppCompatDialogFragment {

    private TextView tv_opis;
    private EditText et_opis;
    private OpisDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle(Aktivnosti.getNaziv())
                .setNegativeButton("Poništi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Potvrdi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String kolicina = et_opis.getText().toString();
                        listener.dodajText(kolicina, Aktivnosti.getNaziv(), Aktivnosti.getID());
                    }
                });
        tv_opis = view.findViewById(R.id.tv_opis);
        et_opis = view.findViewById(R.id.et_opis);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OpisDialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OpisDialogListener");
        }
    }

    public interface OpisDialogListener{
        void dodajText(String kolicina, String naziv, String id);
    }


}
