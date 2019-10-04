package davismar98.app.com.benchcar;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSeleccionComparar extends Fragment{

    Spinner spMarca1, spMarca2, spModelo1, spModelo2;
    ArrayList<Marca> marcas;
    ArrayList<Modelo> modelos;
    AdapterSpinner adapterSpinner;
    ArrayAdapter<Modelo> aaModelo, aaModelo2;
    ImageView imgCarro1, imgCarro2;
    RelativeLayout rlComparar;
    TextView marca1, marca2, modelo1, modelo2;

    Marca marcaSelected1, marcaSelected2;
    Modelo modeloSelected1, modeloSelecter2;

    Boolean hasFixedCar = false;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MARCA = "marca";
    private static final String ARG_MODELO = "modelo";

    // TODO: Rename and change types of parameters
    private String mMarca;
    private String mModelo;


    public FragmentSeleccionComparar() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MODELO, param1);
        args.putString(ARG_MARCA, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mModelo = getArguments().getString(ARG_MODELO);
            mMarca = getArguments().getString(ARG_MARCA);
            hasFixedCar = true;
        }else {
            hasFixedCar = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_seleccion_comparar, container, false);
        spMarca1 = (Spinner) view.findViewById(R.id.spinnerMarca1);
        spMarca2 = (Spinner) view.findViewById(R.id.spinnerMarca2);
        spModelo1 = (Spinner) view.findViewById(R.id.spinnerModelo1);
        spModelo2 = (Spinner) view.findViewById(R.id.spinnerModelo2);
        imgCarro1 = (ImageView) view.findViewById(R.id.img_carro1);
        imgCarro2 = (ImageView) view.findViewById(R.id.img_carro2);
        rlComparar = (RelativeLayout) view.findViewById(R.id.comparar);
        marca1 = (TextView) view.findViewById(R.id.txtSeleccionMarca1);
        marca2 = (TextView) view.findViewById(R.id.txtSeleccionMarca2);
        modelo1 = (TextView) view.findViewById(R.id.txtSeleccionModelo1);
        modelo2 = (TextView) view.findViewById(R.id.txtSeleccionModelo2);


        rlComparar.setEnabled(false);
        rlComparar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rlComparar.isEnabled()){
                    Toast.makeText(FragmentSeleccionComparar.this.getActivity(), "C:", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spMarca1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                //Toast.makeText(FragmentSeleccionComparar.this.getActivity(), position + " ", Toast.LENGTH_SHORT).show();
                //Toast.makeText(FragmentSeleccionComparar.this.getActivity(), marcas.get(position).getMarca(), Toast.LENGTH_SHORT).show();

                if(position!=0) {
                    HashMap postData = new HashMap();
                    postData.put("idMarca", marcas.get(position).getIdMarca() + "");
                    PostResponseAsyncTask tarea = new PostResponseAsyncTask(FragmentSeleccionComparar.this.getActivity(), postData, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            //Toast.makeText(FragmentSeleccionComparar.this.getActivity(), s, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(FragmentSeleccionComparar.this.getActivity(), modelos.size() + " ", Toast.LENGTH_SHORT).show()
                            if (s.equalsIgnoreCase("null")) {
                                Toast.makeText(FragmentSeleccionComparar.this.getActivity(), "Aún no hay vehículos de esta marca. Lo sentimos.", Toast.LENGTH_SHORT).show();
                                spMarca1.setSelection(0);
                            } else {
                                marcaSelected1 = marcas.get(position);
                                modelos = new JsonConverter<Modelo>().toArrayList(s, Modelo.class);
                                aaModelo = new ArrayAdapter<Modelo>(FragmentSeleccionComparar.this.getActivity(), R.layout.custom_spinner_drop, modelos);
                                spModelo1.setAdapter(aaModelo);

                                if(hasFixedCar){
                                    Modelo mo = modelos.get(0);
                                    for( int i = 0; i < modelos.size(); i++){
                                        if(modelos.get(i).getModelo().equalsIgnoreCase(mModelo)){
                                            mo = modelos.get(i);
                                        }
                                    }
                                    int pos = aaModelo.getPosition(mo);
                                    spModelo1.setSelection(pos);
                                    spModelo1.setEnabled(false);
                                }


                            }
                            //
                        }
                    });
                    tarea.execute("http://benchcar.site90.com/BenchCar/getModelos.php");
                }else{
                    spModelo1.setAdapter(null);
                    imgCarro1.setImageResource(R.drawable.range);
                    marcaSelected1=null;
                    modeloSelected1=null;
                    checkComparación();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spModelo1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (modelos.get(position).getFoto() != null) {
                    Glide.with(FragmentSeleccionComparar.this.getActivity()).load(modelos.get(position).getFoto()).into(imgCarro1);
                }
                modeloSelected1 = modelos.get(position);
                marca1.setText(marcaSelected1.getMarca());
                modelo1.setText(modeloSelected1.getModelo());
                //btnComparar.setText("Comparar " + modelos.get(position).getModelo() + "con ");
                checkComparación();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spMarca2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                //Toast.makeText(FragmentSeleccionComparar.this.getActivity(), position + " ", Toast.LENGTH_SHORT).show();
                //Toast.makeText(FragmentSeleccionComparar.this.getActivity(), marcas.get(position).getMarca(), Toast.LENGTH_SHORT).show();

                if(position!=0) {
                    HashMap postData = new HashMap();
                    postData.put("idMarca", marcas.get(position).getIdMarca() + "");

                    PostResponseAsyncTask tarea2 = new PostResponseAsyncTask(FragmentSeleccionComparar.this.getActivity(), postData, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            //Toast.makeText(FragmentSeleccionComparar.this.getActivity(), s, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(FragmentSeleccionComparar.this.getActivity(), modelos.size() + " ", Toast.LENGTH_SHORT).show();

                            if (s.equalsIgnoreCase("null")) {
                                Toast.makeText(FragmentSeleccionComparar.this.getActivity(), "Aún no hay vehículos de esta marca. Lo sentimos.", Toast.LENGTH_SHORT).show();
                                spMarca2.setSelection(0);
                            } else {
                                marcaSelected2 = marcas.get(position);
                                modelos = new JsonConverter<Modelo>().toArrayList(s, Modelo.class);
                                aaModelo = new ArrayAdapter<Modelo>(FragmentSeleccionComparar.this.getActivity(), R.layout.custom_spinner_drop, modelos);
                                spModelo2.setAdapter(aaModelo);
                            }
                            //
                        }
                    });
                    tarea2.execute("http://benchcar.site90.com/BenchCar/getModelos.php");
                }else{
                    spModelo2.setAdapter(null);
                    imgCarro2.setImageResource(R.drawable.range);
                    marcaSelected2=null;
                    modeloSelecter2=null;
                    checkComparación();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spModelo2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (modelos.get(position).getFoto() != null) {
                    Glide.with(FragmentSeleccionComparar.this.getActivity()).load(modelos.get(position).getFoto()).into(imgCarro2);
                }
                modeloSelecter2 = modelos.get(position);
                marca2.setText(marcaSelected2.getMarca());
                modelo2.setText(modeloSelecter2.getModelo());
                checkComparación();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        PostResponseAsyncTask task1 = new PostResponseAsyncTask(FragmentSeleccionComparar.this.getActivity(), "Cargando marcas", new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                marcas =  new JsonConverter<Marca>().toArrayList(s, Marca.class);
                Marca select = new Marca(9999, "Sin seleccionar", "noset");
                marcas.add(0, select);
                //Toast.makeText(FragmentSeleccionComparar.this.getActivity(), marcas.size() + " .-.", Toast.LENGTH_SHORT).show();
                adapterSpinner = new AdapterSpinner(FragmentSeleccionComparar.this.getActivity(), marcas);
                spMarca1.setAdapter(adapterSpinner);
                //spMarca1.setSelection(adapterSpinner.getCount());
                spMarca2.setAdapter(adapterSpinner);

                /*if (hasFixedCar)
                    Toast.makeText(FragmentSeleccionComparar.this.getActivity(), mMarca + " : " + mModelo, Toast.LENGTH_SHORT).show();
*/
                //Si tiene un carro fijo...
                Marca m = marcas.get(0);
                if(hasFixedCar){
                    for(int i = 0; i < marcas.size(); i++){
                        if (marcas.get(i).getMarca().equalsIgnoreCase(mMarca)){
                            m = marcas.get(i);
                        }
                    }
                    int posicion = marcas.indexOf(m);
                    //Toast.makeText(FragmentSeleccionComparar.this.getActivity(), m.getMarca() + " : " + posicion, Toast.LENGTH_SHORT).show();
                    spMarca1.setSelection(posicion);
                    spMarca1.setEnabled(false);
                }


            }
        });
        task1.execute("http://benchcar.site90.com/BenchCar/getMarcas.php");
        return view;
    }


    public void checkComparación(){

        if(marcaSelected1==null || marcaSelected2==null || modeloSelected1==null || modeloSelecter2==null){
            Toast.makeText(FragmentSeleccionComparar.this.getActivity(), "Incompleto", Toast.LENGTH_SHORT).show();
            rlComparar.setEnabled(false);
            rlComparar.setBackgroundResource(R.drawable.corners);
        }else{
            if(modeloSelected1.getIdVehiculo()==modeloSelecter2.getIdVehiculo()){
                Toast.makeText(FragmentSeleccionComparar.this.getActivity(), "Mismo carro", Toast.LENGTH_SHORT).show();
                rlComparar.setEnabled(false);
                rlComparar.setBackgroundResource(R.drawable.corners);
            }else{
                Toast.makeText(FragmentSeleccionComparar.this.getActivity(), "GOOD TO GO", Toast.LENGTH_SHORT).show();
                rlComparar.setEnabled(true);
                rlComparar.setBackgroundColor(Color.GREEN);
            }
        }


    }

}
