package davismar98.app.com.benchcar;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.widget.Toast;

        import com.kosalgeek.android.json.JsonConverter;
        import com.kosalgeek.genasync12.AsyncResponse;
        import com.kosalgeek.genasync12.PostResponseAsyncTask;

        import java.util.ArrayList;

public class verTodos extends AppCompatActivity {

    ArrayList<CarroLista> listCarro;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_todos);

        MoviesAdapter adpater = new MoviesAdapter(verTodos.this, title);
        RecyclerView rview = (RecyclerView) findViewById(R.id.my_recycler_view);
        rview.setItemAnimator(new DefaultItemAnimator());
        rview.setAdapter(adpater);
        LinearLayoutManager layoutManager = new LinearLayoutManager(verTodos.this);
        rview.setLayoutManager(layoutManager);
        rview.addItemDecoration(new DividerItemDecoration(verTodos.this));

        PostResponseAsyncTask tarea = new PostResponseAsyncTask(verTodos.this, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                //Toast.makeText(verTodos.this, s, Toast.LENGTH_SHORT).show();
                listCarro= new JsonConverter<CarroLista>().toArrayList(s, CarroLista.class);
                Toast.makeText(verTodos.this, "Cantidad: " + listCarro.size(), Toast.LENGTH_SHORT).show();
            }
        });
        tarea.execute("/http://benchcar.site90.com/BenchCar/getAutos.php");
    }
}
