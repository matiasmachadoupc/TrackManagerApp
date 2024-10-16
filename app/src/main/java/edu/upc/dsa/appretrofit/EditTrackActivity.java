package edu.upc.dsa.appretrofit;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTrackActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextArtist, editTextAlbum;
    private Button buttonSave, buttonDelete;
    private TrackService trackService;
    private Track track; // Pista que se va a editar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_track);

        // Inicializa Retrofit para hacer las llamadas a la API
        trackService = ApiClient.getClient().create(TrackService.class);

        // Obtener las vistas de los campos de texto
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextArtist = findViewById(R.id.editTextArtist);
        editTextAlbum = findViewById(R.id.editTextAlbum);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDelete = findViewById(R.id.buttonDelete);

        // Obtener el track que se pasó desde el intent
        Intent intent = getIntent();
        track = (Track) intent.getSerializableExtra("track");

        if (track != null) {
            // Mostrar los datos actuales de la pista en los campos de texto
            editTextTitle.setText(track.getTitle());
            editTextArtist.setText(track.getArtist());
            editTextAlbum.setText(track.getAlbum());
        }

        // Manejar la acción de guardar los cambios
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrackChanges();
            }
        });

        // Manejar la acción de eliminar la pista (opcional)
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTrack();
            }
        });
    }

    // Método para guardar los cambios en el track
    private void saveTrackChanges() {
        // Actualiza los valores del objeto track con los datos editados
        track.setTitle(editTextTitle.getText().toString());
        track.setArtist(editTextArtist.getText().toString());
        track.setAlbum(editTextAlbum.getText().toString());

        // Hacer una llamada PUT para actualizar la pista
        Call<Track> call = trackService.updateTrack(track.getId(), track);
        call.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditTrackActivity.this, "Track updated successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Volver a la actividad anterior
                } else {
                    Toast.makeText(EditTrackActivity.this, "Failed to update track", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Toast.makeText(EditTrackActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para eliminar la pista
    private void deleteTrack() {
        // Hacer una llamada DELETE para eliminar la pista
        Call<Void> call = trackService.deleteTrack(track.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditTrackActivity.this, "Track deleted successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Volver a la actividad anterior
                } else {
                    Toast.makeText(EditTrackActivity.this, "Failed to delete track", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditTrackActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
