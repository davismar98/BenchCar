package davismar98.app.com.benchcar;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DavidM on 14/04/2017.
 */

public class ComentariosDFragment extends DialogFragment implements AsyncResponse{
    int idCarro;
    RecyclerView recyclerView;
    ArrayList<Opinion> opinions;
    AdapterComentarios adapterComentarios;
    RatingBar ratingCarro;
    CheckBox cbComentario;
    EditText etComentario, etUsuario;
    Button btnEnviar;
    TextView txtRating, noComentarios;
    String user, comment, rating;
    int nErrores;
    int mNum;
    MyDialogCloseListener closeListener;

    /**
     * Create a new instance of ComentariosDFragment, providing "num"
     * as an argument.
     */
    static ComentariosDFragment newInstance(int idCarro) {
        ComentariosDFragment f = new ComentariosDFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("idCarro", idCarro);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idCarro = getArguments().getInt("idCarro");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_comentarios, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerDialogFragment);
        Toast.makeText(ComentariosDFragment.this.getActivity(), idCarro + " ", Toast.LENGTH_SHORT).show();
        // View tv = v.findViewById(R.id.text);
        //((TextView)tv).setText("Dialog #" + mNum);
        // Watch for button clicks.
        HashMap postData = new HashMap();
        postData.put("txtIdVehiculo", idCarro + "");
        PostResponseAsyncTask tarea = new PostResponseAsyncTask(ComentariosDFragment.this.getActivity(), postData, this);
        tarea.execute("http://benchcar.site90.com/BenchCar/getComentarios.php");
        this.getDialog().setTitle("Opiniones sobre este carro");

        ratingCarro = (RatingBar) v.findViewById(R.id.ratingCarro);
        etComentario = (EditText) v.findViewById(R.id.etComent);
        etUsuario = (EditText) v.findViewById(R.id.etUser);
        cbComentario = (CheckBox) v.findViewById(R.id.cbComentario);
        btnEnviar = (Button) v.findViewById(R.id.btnEnviar);
        txtRating = (TextView) v.findViewById(R.id.txtRating);
        noComentarios = (TextView) v.findViewById(R.id.noComentarios);

        cbComentario.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    etComentario.setEnabled(true);
                    etComentario.setHint("Escribe qué piensas sobre este carro");
                }else{
                    etComentario.setText("");
                    etComentario.setHint("¿Sin comentario? ¡No importa!");
                    etComentario.setEnabled(false);
                }
            }
        });

        ratingCarro.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txtRating.setText((int) (10*rating) + "/100");
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEnviar.setText("Clicked");
                if (cantidadErrores()==0){
                    HashMap postData = new HashMap();
                    postData.put("txtCalificacion", rating);
                    postData.put("txtComentario", comment);
                    postData.put("txtUsuario", user);
                    postData.put("txtidVehiculo", idCarro+"");

                    PostResponseAsyncTask tarea = new PostResponseAsyncTask(ComentariosDFragment.this.getActivity(), postData, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if(s.equalsIgnoreCase("success")){
                                Toast.makeText(ComentariosDFragment.this.getActivity(), "Done!", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }else{
                                Toast.makeText(ComentariosDFragment.this.getActivity(), "Ha ocurrido un error!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    tarea.execute("http://benchcar.site90.com/BenchCar/insertOpinion.php");
                }
            }
        });

        return v;

    }

    public void DismissListner(MyDialogCloseListener closeListener) {
        this.closeListener = closeListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(closeListener!=null) {
            closeListener.handleDialogClose(null);
        }

    }

    @Override
    public void processFinish(String s) {
        if(s.equalsIgnoreCase("null")){
            noComentarios.setVisibility(View.VISIBLE);
        }else{
            opinions = new JsonConverter<Opinion>().toArrayList(s, Opinion.class);
            adapterComentarios = new AdapterComentarios(ComentariosDFragment.this.getActivity(), opinions);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager layoutManager = new LinearLayoutManager(ComentariosDFragment.this.getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapterComentarios);
            recyclerView.addItemDecoration(new DividerItemDecoration(ComentariosDFragment.this.getActivity()));
        }


    }

    public int cantidadErrores(){
        nErrores=0;
        String errores = "";
        if (ratingCarro.getRating()==0){
            errores = "* Ingresa una calificación para el vehículo.\n";
            txtRating.setError("Ingresa una calificación");
            nErrores+=1;
        }else{
            rating = (int)ratingCarro.getRating()*10 +"";
            txtRating.setError(null);
        }

        user = etUsuario.getText().toString();
        if(TextUtils.isEmpty(user)){
            etUsuario.setError("Ingresa un nombre");
            nErrores+=1;
        }

        comment = etComentario.getText().toString();
        if(cbComentario.isChecked()){
            if(TextUtils.isEmpty(comment)){
                etComentario.setError("Ingresa un comentario");
                nErrores+=1;
            }
        }
        return nErrores;
    }
}
