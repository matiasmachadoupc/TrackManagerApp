package edu.upc.dsa.appretrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Body;
import java.util.List;  // Agrega esta línea para resolver el error


public interface TrackService {
    @GET("/tracks")
    Call<List<Track>> getTracks();

    @POST("/tracks")
    Call<Track> addTrack(@Body Track track);

    @PUT("/tracks/{id}")
    Call<Track> updateTrack(@Path("id") int id, @Body Track track);

    @DELETE("/tracks/{id}")
    Call<Void> deleteTrack(@Path("id") int id);
}
