package davismar98.app.com.benchcar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by DavidM on 26/03/2017.
 */

public class AdapterRecientes2 extends RecyclerView.Adapter<AdapterRecientes2.myViewHolder>{

    private final LayoutInflater inflater;
    Context contextAct;
    ArrayList<CarroLista> listCarros;
    private ItemClickListener clickListener;

    public AdapterRecientes2(Context context, ArrayList<CarroLista> listData){
        inflater = LayoutInflater.from(context);
        this.listCarros = listData;
        this.contextAct=context;
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.cardview_todos,parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        CarroLista carro = listCarros.get(position);

        holder._modelo.setText(carro.getMarca() + " " + carro.getModelo());
        holder._año.setText(carro.getAño());

        holder._estrellas.setIsIndicator(true);
        holder._estrellas.setStepSize(0.5f);
        holder._estrellas.setRating((carro.getCalificacion()/10));

        holder._calificacion.setText(""+carro.getCalificacion());

        if (holder._imgcarro != null) {
            Glide.with(contextAct).load(carro.getFoto()).into(holder._imgcarro);
        }

        int id = contextAct.getResources().getIdentifier(carro.getImagen(), "drawable", contextAct.getPackageName());
        if (id!=0) {
            holder._imgpais.setImageResource(id);
        }
        int id2 = contextAct.getResources().getIdentifier(carro.getLogo(), "drawable", contextAct.getPackageName());
        if (id2!=0) {
            holder._imgmarca.setImageResource(id2);
        }

    }

    @Override
    public int getItemCount() {
        return listCarros.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView _imgcarro;
        TextView _modelo;
        ImageView _imgpais;
        ImageView _imgmarca;
        TextView _año;
        TextView _calificacion;
        RatingBar _estrellas;

        public myViewHolder(View itemView) {
            super(itemView);

            _imgcarro = (ImageView) itemView.findViewById(R.id.img_carro);
            _modelo = (TextView)  itemView.findViewById(R.id.txt_usuario);
            _imgpais = (ImageView) itemView.findViewById(R.id.img_pais);
            _imgmarca = (ImageView) itemView.findViewById(R.id.img_marca);
            _año = (TextView) itemView.findViewById(R.id.txt_comentario);
            _calificacion = (TextView) itemView.findViewById(R.id.txt_calificacion);
            _estrellas = (RatingBar) itemView.findViewById(R.id.rb_calificacion);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
}
