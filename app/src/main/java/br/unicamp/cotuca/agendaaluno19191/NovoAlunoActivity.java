package br.unicamp.cotuca.agendaaluno19191;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovoAlunoActivity extends AppCompatActivity {

    private Aluno aluno;

    private EditText etRa;
    private EditText etNome;
    private EditText etEmail;

    private Button btnCriar, btnCancelar;

    private AlunoRetrofit service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_aluno);

        etRa = (EditText) findViewById(R.id.etRa);
        etNome = (EditText) findViewById(R.id.etNome);
        etEmail = (EditText) findViewById(R.id.etEmail);

        btnCriar = (Button) findViewById(R.id.btnCriar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        service = RetrofitClient.getRetrofitInstance(getApplicationContext().getString(R.string.server)).create(AlunoRetrofit.class);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NovoAlunoActivity.this.finish();
            }
        });
        
        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject alunoPost = new JsonObject();
                alunoPost.addProperty("ra", etRa.getText().toString());
                alunoPost.addProperty("nome", etNome.getText().toString());
                alunoPost.addProperty("email", etEmail.getText().toString());

                postAluno(alunoPost);
            }
        });
    }

    private void postAluno(JsonObject aluno) {
        try {
            Call<JsonObject> call = service.postAluno(aluno);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body().get("status").getAsInt() != 200) {
                        String erro = "";
                        try {
                            erro = response.body().get("error").getAsString();
                        } catch (Exception e) {}
                        Toast.makeText(NovoAlunoActivity.this, "Erro" + (!erro.equals("") ? ": " + erro : ""), Toast.LENGTH_SHORT).show();
                    } else NovoAlunoActivity.this.finish();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    System.err.println(t.getMessage());
                    Toast.makeText(NovoAlunoActivity.this, "Erro ao criar aluno", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            System.out.println("POST EXCEPTION: " + e.getMessage());
        }
    }
}