package davismar98.app.com.benchcar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by DavidM on 26/03/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.myViewHolder>{

    private final LayoutInflater inflater;

    String[] title = {

            "The Shawshank Redemption",
            "The Godfather",
            "The Dark Knight",
            "Schindler's List",
            "Fight Club",
            "Inception",
            "The Matrix",
            "The Silence of the Lambs",
            "Life Is Beautiful",
            "Interstellar"

    };

    final String[] desc = {

            "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency. ",
            "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
            "In Poland during World War II, Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.",
            "The masterful concoction features a delicious core of mandarin oranges",
            "An insomniac office worker, looking for a way to change his life, crosses paths with a devil-may-care soap maker, forming an underground fight club that evolves into something much, much more",
            "A thief who steals corporate secrets through use of the dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.",
            "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
            "A young F.B.I. cadet must confide in an incarcerated and manipulative killer to receive his help on catching another serial killer who skins his victims.",
            "When an open-minded Jewish librarian and his son become victims of the Holocaust, he uses a perfect mixture of will, humor and imagination to protect his son from the dangers around their camp.",
            "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival."

    };

    int[] images = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six,
            R.drawable.seven,
            R.drawable.eight,
            R.drawable.nine,
            R.drawable.ten

    };

    public MoviesAdapter(Context context, String[] object){
        inflater = LayoutInflater.from(context);
        this.title = object;
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_row,parent,false);
        myViewHolder holder = new myViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        holder._title.setText(title[position]);
        holder._desc.setText(desc[position]);
        holder._imgview.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView _imgview;
        TextView _title;
        TextView _desc;

        public myViewHolder(View itemView) {

            super(itemView);
            _imgview = (ImageView) itemView.findViewById(R.id.img_carro);
            _title = (TextView) itemView.findViewById(R.id.txttitle);
            _desc = (TextView) itemView.findViewById(R.id.txtdesc);
        }
    }
}
