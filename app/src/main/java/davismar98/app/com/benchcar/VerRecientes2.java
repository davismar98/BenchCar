package davismar98.app.com.benchcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;

public class VerRecientes2 extends AppCompatActivity implements AsyncResponse{

    ArrayList<CarroLista> listCarros;
    AdapterRecientes2 adapterRecientes2;
    RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_recientes2);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRecientes2);

        PostResponseAsyncTask task1 = new PostResponseAsyncTask(VerRecientes2.this, "Cargando...", this);
        task1.execute("http://benchcar.site90.com/BenchCar/getAutos.php");

    }

    @Override
    public void processFinish(String s) {
        listCarros = new JsonConverter<CarroLista>().toArrayList(s, CarroLista.class);
        adapterRecientes2 = new AdapterRecientes2(this, listCarros);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterRecientes2);
        //recyclerView.addItemDecoration(new DividerItemDecoration(VerRecientes2.this));

    }

}
