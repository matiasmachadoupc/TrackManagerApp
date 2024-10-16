package edu.upc.dsa.appretrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTrackActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextArtist;
    private EditText editTextAlbum;
    private Button buttonAddTrack;
    private TrackService trackService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextArtist = findViewById(R.id.editTextArtist);
        editTextAlbum = findViewById(R.id.editTextAlbum);
        buttonAddTrack = findViewById(R.id.buttonAddTrack);

        // Inicializar Retrofit
        trackService = ApiClient.getClient().create(TrackService.class);

        // Configurar el botón para agregar la pista
        buttonAddTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTrack();
            }
        });
    }

    private void addTrack() {
        String title = editTextTitle.getText().toString().trim();
        String artist = editTextArtist.getText().toString().trim();
        String album = editTextAlbum.getText().toString().trim();

        if (title.isEmpty() || artist.isEmpty() || album.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un nuevo objeto Track
        Track newTrack = new Track();
        newTrack.setTitle(title);
        newTrack.setArtist(artist);
        newTrack.setAlbum(album);

        // Hacer la llamada al servicio web para agregar la pista
        Call<Track> call = trackService.addTrack(newTrack);
        call.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddTrackActivity.this, "Track added successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Cierra la actividad y vuelve a la anterior
                } else {
                    Toast.makeText(AddTrackActivity.this, "Failed to add track", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Toast.makeText(AddTrackActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
