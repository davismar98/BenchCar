package davismar98.app.com.benchcar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DavidM on 26/03/2017.
 */

public class AdapterRecientes extends RecyclerView.Adapter<AdapterRecientes.myViewHolder>{

    private final LayoutInflater inflater;
    Context contextAct;
    ArrayList<CarroLista> listCarros;

    public AdapterRecientes(Context context, ArrayList<CarroLista> listData){
        inflater = LayoutInflater.from(context);
        this.listCarros = listData;
        this.contextAct=context;
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.car_list_row,parent,false);
        myViewHolder holder = new myViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        CarroLista carro = listCarros.get(position);
        holder._modelo.setText(carro.getModelo());
        holder._año.setText(carro.getAño());
        holder._marca.setText(carro.getMarca());
        holder._calificacion.setText(""+carro.getCalificacion());

        if (holder._imgcarro != null) {
            new ImageDownloaderTask(holder._imgcarro)
                    .execute(carro.getFoto());
        }

        int id = contextAct.getResources().getIdentifier("alemania", "drawable", contextAct.getPackageName());
        if (id!=0) {
            holder._imgpais.setImageResource(id);
        }
        int id2 = contextAct.getResources().getIdentifier("volkswagen", "drawable", contextAct.getPackageName());
        if (id2!=0) {
            holder._imgmarca.setImageResource(id2);
        }
    }

    @Override
    public int getItemCount() {
        return listCarros.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView _imgcarro;
        ImageView _imgpais;
        ImageView _imgmarca;
        TextView _modelo;
        TextView _año;
        TextView _marca;
        TextView _calificacion;

        public myViewHolder(View itemView) {

            super(itemView);
            _imgcarro = (ImageView) itemView.findViewById(R.id.img_carro);
            _imgpais = (ImageView) itemView.findViewById(R.id.img_pais);
            _imgmarca = (ImageView) itemView.findViewById(R.id.img_marca);
            _modelo = (TextView)  itemView.findViewById(R.id.txt_usuario);
            _año = (TextView) itemView.findViewById(R.id.txt_comentario);
            _marca = (TextView) itemView.findViewById(R.id.txt_fecha);
            _calificacion = (TextView) itemView.findViewById(R.id.rb_calificacion);

        }
    }
}
