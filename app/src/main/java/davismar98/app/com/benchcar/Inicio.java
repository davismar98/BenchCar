package davismar98.app.com.benchcar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Inicio extends AppCompatActivity implements  AsyncResponse{

    Button verTodos, verRecientes, verRecientes2, openMain;
    ArrayList<CarroLista> listCarro;
    final String LOG = "INICIO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        verTodos = (Button) findViewById(R.id.VerTodos);
        verTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Inicio.this, verTodos.class);
                startActivity(in);
            }
        });

        PostResponseAsyncTask task1 = new PostResponseAsyncTask(Inicio.this, this);
        task1.execute("http://benchcar.site90.com/BenchCar/getAutos.php");

        verRecientes = (Button) findViewById(R.id.btn_recientes);
        verRecientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Inicio.this, VerRecientes.class);
                startActivity(in);
            }
        });

        verRecientes = (Button) findViewById(R.id.btn_recientes2);
        verRecientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Inicio.this, VerRecientes2.class);
                startActivity(in);
            }
        });

        openMain = (Button) findViewById(R.id.openMain);
        openMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Inicio.this, MainActivity.class);
                startActivity(in);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(String s) {
        listCarro= new JsonConverter<CarroLista>().toArrayList(s, CarroLista.class);
        Toast.makeText(Inicio.this, "Cantidad: " + listCarro.size(), Toast.LENGTH_SHORT).show();
        //Log.d(LOG, s);
    }
}
