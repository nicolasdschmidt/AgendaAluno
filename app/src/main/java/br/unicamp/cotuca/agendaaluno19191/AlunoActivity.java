package br.unicamp.cotuca.agendaaluno19191;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoActivity extends AppCompatActivity {

    private Aluno aluno;

    private TextView tvRa;
    private EditText etNome;
    private EditText etEmail;

    private Button btnExcluir, btnSalvar, btnCancelar;

    private AlunoRetrofit service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);

        tvRa = (TextView) findViewById(R.id.tvCadaRa);
        etNome = (EditText) findViewById(R.id.etNome);
        etEmail = (EditText) findViewById(R.id.etEmail);

        btnExcluir = (Button) findViewById(R.id.btnExcluir);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        aluno = (Aluno) extras.getSerializable("ALUNO");

        tvRa.setText(aluno.getRa());
        etNome.setText(aluno.getNome());
        etEmail.setText(aluno.getEmail());

        service = RetrofitClient.getRetrofitInstance(getApplicationContext().getString(R.string.server)).create(AlunoRetrofit.class);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject alunoPut = new JsonObject();
                alunoPut.addProperty("ra", aluno.getRa());
                alunoPut.addProperty("nome", etNome.getText().toString());
                alunoPut.addProperty("email", etEmail.getText().toString());

                putAluno(alunoPut);
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject alunoDelete = new JsonObject();
                alunoDelete.addProperty("ra", aluno.getRa());
                deleteAluno(alunoDelete);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlunoActivity.this.finish();
            }
        });
    }

    private void putAluno(JsonObject aluno) {
        try {
            Call<JsonObject> call = service.putAluno(aluno);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (!response.body().get("status").getAsString().equals("OK")) {
                        System.err.println(response.body().get("status").getAsString());
                        Toast.makeText(AlunoActivity.this, "Erro ao salvar aluno", Toast.LENGTH_SHORT).show();
                    } else AlunoActivity.this.finish();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    System.err.println(t.getMessage());
                    Toast.makeText(AlunoActivity.this, "Erro ao salvar aluno", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            System.out.println("PUT EXCEPTION: " + e.getMessage());
        }
    }

    private void deleteAluno(JsonObject aluno) {
        try {
            Call<JsonObject> call = service.deleteAluno(aluno);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    System.out.println(response.body());
                    if (!response.body().get("status").getAsString().equals("OK")) {
                        Toast.makeText(AlunoActivity.this, "Erro ao excluir aluno", Toast.LENGTH_SHORT).show();
                    } else AlunoActivity.this.finish();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    System.err.println(t.getMessage());
                    Toast.makeText(AlunoActivity.this, "Erro ao excluir aluno", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            System.out.println("DELETE EXCEPTION: " + e.getMessage());
        }
    }
}