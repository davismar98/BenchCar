package davismar98.app.com.benchcar;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class DetalleCarro extends AppCompatActivity implements AsyncResponse{

    int mStackLevel=0;
    ArrayList<Carro> carros;
    int idCarro;
    TextView txt_idCarro;
    String titulo = "Detalle del vehículo";
    ImageView foto_carro, img_logo, img_puntaje, img_pais;
    TextView txt_fabricante, txt_puntaje, txt_cant_opiniones;
    TextView txt_modelo, txt_año, txt_marca, txt_pais, txt_precio;
    TextView txt_combustible, txt_cilindraje, txt_potencia, txt_transmision, txt_4wd, txt_abs;
    TextView txt_ancho, txt_largo, txt_alto, txt_peso, txt_puertas, txt_capacidad;
    Button btnComparar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_carro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        foto_carro = (ImageView) findViewById(R.id.foto_carro);
        img_logo = (ImageView) findViewById(R.id.img_logo);
        img_puntaje = (ImageView) findViewById(R.id.img_puntaje);
        txt_fabricante = (TextView) findViewById(R.id.txt_fabricante);
        txt_puntaje = (TextView)  findViewById(R.id.txt_puntaje);
        txt_cant_opiniones = (TextView)  findViewById(R.id.txt_cant_opiniones);
        txt_modelo = (TextView)  findViewById(R.id.txt_usuario);
        txt_año = (TextView)  findViewById(R.id.txt_comentario);
        txt_marca = (TextView)  findViewById(R.id.txt_fecha);
        txt_pais = (TextView)  findViewById(R.id.txt_pais);
        img_pais = (ImageView) findViewById(R.id.img_pais_mini);
        txt_precio = (TextView)  findViewById(R.id.txt_precio);
        txt_combustible = (TextView)  findViewById(R.id.txt_combustible);
        txt_cilindraje = (TextView)  findViewById(R.id.txt_cilindraje);
        txt_potencia = (TextView)  findViewById(R.id.txt_potencia);
        txt_transmision = (TextView)  findViewById(R.id.txt_transmision);
        txt_4wd = (TextView)  findViewById(R.id.txt_4wd);
        txt_abs = (TextView)  findViewById(R.id.txt_abs);
        txt_ancho = (TextView)  findViewById(R.id.txt_ancho);
        txt_largo = (TextView)  findViewById(R.id.txt_largo);
        txt_alto = (TextView)  findViewById(R.id.txt_alto);
        txt_peso = (TextView)  findViewById(R.id.txt_peso);
        txt_puertas = (TextView)  findViewById(R.id.txt_puertas);
        txt_capacidad = (TextView)  findViewById(R.id.txt_capacidad);

        btnComparar = (Button) findViewById(R.id.btnCompare);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idCarro = extras.getInt("idCarro");

            HashMap postData = new HashMap();
            postData.put("idCarro", idCarro+"");
            PostResponseAsyncTask tarea = new PostResponseAsyncTask(DetalleCarro.this, postData, this);
            tarea.execute("http://benchcar.site90.com/BenchCar/getAuto.php");
            //txt_idCarro = (TextView) findViewById(R.id.txt_idCarro);
            //txt_idCarro.setText(idCarro+" <-- :D");*/
        }

        btnComparar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetalleCarro.this, MainActivity.class);
                i.putExtra("marca", txt_marca.getText().toString());
                i.putExtra("modelo", txt_modelo.getText().toString());
                startActivity(i);
            }
        });



    }


    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(titulo);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        /*appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(titulo);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(titulo);
                    isShow = false;
                }
            }
        });*/
    }

    @Override
    public void processFinish(String s) {
        carros = new JsonConverter<Carro>().toArrayList(s, Carro.class);
        //Toast.makeText(DetalleCarro.this, carros.size()+"", Toast.LENGTH_SHORT).show();

        Carro c = carros.get(0);

        titulo = c.getMarca() + " " + c.getModelo();

        initCollapsingToolbar();

        if (c.getFoto() != null) {
            Glide.with(DetalleCarro.this).load(c.getFoto()).into(foto_carro);
        }
        int id = this.getResources().getIdentifier(c.getLogo(), "drawable", this.getPackageName());
        if (id!=0) {
           img_logo.setImageResource(id);
        }
        int id2 = this.getResources().getIdentifier(c.getImagen(), "drawable", this.getPackageName());
        if (id2!=0) {
            img_pais.setImageResource(id2);
        }
        txt_fabricante.setText(c.getMarca());
        txt_puntaje.setText(c.getCalificacion()+"");
        txt_cant_opiniones.setText(c.getCantidadOpiniones()+"");
        txt_modelo.setText(c.getModelo());
        txt_año.setText(c.getAño());
        txt_marca.setText(c.getMarca());
        txt_pais.setText(c.getPais());
        txt_precio.setText("$"+c.getPrecio()+ " USD");
        txt_combustible.setText(c.getCombustible());
        txt_cilindraje.setText(c.getCilindraje()+ " cc");
        txt_potencia.setText(c.getPotencia()+ " HP");
        txt_transmision.setText(c.getTransmision());
        if (c.isA4wd()){
            txt_4wd.setText("SÍ");
        }else{
            txt_4wd.setText("NO");
        }
        if(c.isAbs()){
            txt_abs.setText("SÍ");
        }else{
            txt_abs.setText("NO");
        }
        txt_ancho.setText(c.getAncho() + " mm");
        txt_largo.setText(c.getLargo() +" mm");
        txt_alto.setText(c.getAlto() + " mm");
        txt_peso.setText(c.getPeso() + "Kg");
        txt_puertas.setText(c.getPuertas() + "");
        txt_capacidad.setText(c.getCapacidad() + " personas");

    }

    void showDialog() {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.


        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        DialogFragment newFragment = ComentariosDFragment.newInstance(idCarro);
        newFragment.show(ft, "dialog");

        MyDialogCloseListener closeListener = new MyDialogCloseListener() {
            @Override
            public void handleDialogClose(DialogInterface dialog) {
                HashMap data = new HashMap();
                data.put("txtIdVehiculo", idCarro + "");
                PostResponseAsyncTask tarea2 = new PostResponseAsyncTask(DetalleCarro.this, data, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        //Toast.makeText(DetalleCarro.this, s, Toast.LENGTH_SHORT).show();
                        String[] info = s.split(",");
                        if(info[0].equalsIgnoreCase("")){
                            txt_puntaje.setText("0");
                        }else{
                            txt_puntaje.setText(info[0]);
                        }
                        txt_cant_opiniones.setText(info[1]);
                    }
                });
                tarea2.execute("http://benchcar.site90.com/BenchCar/getPuntaje.php");
            }
        };

        ((ComentariosDFragment)newFragment).DismissListner(closeListener);

/*
        android.app.FragmentManager fm = getFragmentManager();
        fm.executePendingTransactions();
        newFragment.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Toast.makeText(DetalleCarro.this, "Se cerró :,v", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
