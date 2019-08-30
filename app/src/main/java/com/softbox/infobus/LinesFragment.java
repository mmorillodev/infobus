package com.softbox.infobus;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class LinesFragment extends Fragment {
    Button linha1, i07;

    public LinesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_lines, container, false);


        linha1 =  view.findViewById(R.id.btnLinha1);
        i07 = view.findViewById(R.id.btnLinha2);
        linha1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreTableas = new Intent(getActivity(), Tabelas.class);
                abreTableas.putExtra("linha", "vazio2");
                startActivity(abreTableas);
            }
        });
        i07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreTabelas = new Intent(getActivity(), Tabelas.class);
                abreTabelas.putExtra("linha", "i07");
                startActivity(abreTabelas);
            }
        });
        return view;
    }

}
