package davismar98.app.com.benchcar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterSpinner extends BaseAdapter {

    Context context;
    ArrayList<Marca> marcas;
    LayoutInflater inflter;

    public AdapterSpinner(Context applicationContext, ArrayList<Marca> listMarcas) {
        this.context = applicationContext;
        this.marcas = listMarcas;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return marcas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflter.inflate(R.layout.custom_spinner_marcas, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.text1);

        int id = context.getResources().getIdentifier(marcas.get(i).getLogo(), "drawable", context.getPackageName());
        if (id!=0) {
            icon.setImageResource(id);
        }
        names.setText(marcas.get(i).getMarca());

        return view;
    }
}