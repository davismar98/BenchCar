package davismar98.app.com.benchcar;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVerTodos extends Fragment implements AsyncResponse, ItemClickListener{

    @Override
    public void processFinish(String s) {
        listCarros = new JsonConverter<CarroLista>().toArrayList(s, CarroLista.class);
        adapterRecientes2 = new AdapterRecientes2(FragmentVerTodos.this.getActivity(), listCarros);
        adapterRecientes2.setClickListener(this);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        gridLayoutManager = new GridLayoutManager(FragmentVerTodos.this.getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterRecientes2);
    }


    public FragmentVerTodos() {
        // Required empty public constructor
    }

    ArrayList<CarroLista> listCarros;
    AdapterRecientes2 adapterRecientes2;
    RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_ver_todos, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerFragmentTodos);
        PostResponseAsyncTask task1 = new PostResponseAsyncTask(FragmentVerTodos.this.getActivity(), "Cargando...", this);
        task1.execute("http://benchcar.site90.com/BenchCar/getAutos.php");

        return view;
    }

    @Override
    public void onClick(View view, int position) {
        Toast.makeText(FragmentVerTodos.this.getActivity(), "CLICK", Toast.LENGTH_SHORT).show();
        CarroLista carro = listCarros.get(position);
        Intent intent = new Intent(FragmentVerTodos.this.getActivity(), DetalleCarro.class);
        intent.putExtra("idCarro", carro.getIdVehiculo());
        startActivity(intent);
    }
}
