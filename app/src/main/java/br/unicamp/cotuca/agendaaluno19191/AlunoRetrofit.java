package br.unicamp.cotuca.agendaaluno19191;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AlunoRetrofit {
    @GET("/alunos")
    Call<ApiResponse> getAlunos();

    @POST("/alunos")
    Call<JsonObject> postAluno(@Body JsonObject body);

    @PUT("/alunos")
    Call<JsonObject> putAluno(@Body JsonObject body);

    @HTTP(method = "DELETE", path = "/alunos", hasBody = true)
    Call<JsonObject> deleteAluno(@Body JsonObject body);
}
