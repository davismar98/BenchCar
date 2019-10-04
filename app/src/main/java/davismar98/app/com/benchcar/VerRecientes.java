package davismar98.app.com.benchcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;

public class VerRecientes extends AppCompatActivity implements AsyncResponse{

    ArrayList<CarroLista> listCarros;
    AdapterRecientes adapterRecientes;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_recientes);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRecientes);
        PostResponseAsyncTask task1 = new PostResponseAsyncTask(VerRecientes.this, "Cargando...", this);
        task1.execute("http://benchcar.site90.com/BenchCar/getAutos.php");

    }

    @Override
    public void processFinish(String s) {

        listCarros = new JsonConverter<CarroLista>().toArrayList(s, CarroLista.class);
        adapterRecientes = new AdapterRecientes(this, listCarros);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(VerRecientes.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterRecientes);
        recyclerView.addItemDecoration(new DividerItemDecoration(VerRecientes.this));
    }
}
