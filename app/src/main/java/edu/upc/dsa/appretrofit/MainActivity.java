package edu.upc.dsa.appretrofit;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TrackAdapter adapter;
    private TrackService trackService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Retrofit and make the API call
        trackService = ApiClient.getClient().create(TrackService.class);
        getTracks();
    }

    private void getTracks() {
        Call<List<Track>> call = trackService.getTracks();
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Track> tracks = response.body();
                    adapter = new TrackAdapter(tracks, track -> {
                        // Handle track click by opening EditTrackActivity
                        openEditTrackActivity(track);
                    });
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
             Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // Handle failure
            }
        });
    }

    // Método para abrir la actividad de edición y pasarle el Track seleccionado
    private void openEditTrackActivity(Track selectedTrack) {
        Intent intent = new Intent(MainActivity.this, EditTrackActivity.class);
        intent.putExtra("track", selectedTrack); // Pasar el objeto Track como parámetro
        startActivity(intent);
    }
}
