package br.unicamp.cotuca.agendaaluno19191;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AliasActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etRa;
    private Button btnPesquisar;
    private ListView lvAlunos;
    private TextView tvAlunoRa;
    private FloatingActionButton fab;

    private ArrayList<Aluno> alunos;

    private AlunoRetrofit service;

    private final static int ALUNO_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etRa = (EditText) findViewById(R.id.etRa);
        btnPesquisar = (Button) findViewById(R.id.btnPesquisar);
        lvAlunos = (ListView) findViewById(R.id.lvAlunos);
        tvAlunoRa = (TextView) findViewById(R.id.tvAlunoRa);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        alunos = new ArrayList<Aluno>();

        service = RetrofitClient.getRetrofitInstance(getApplicationContext().getString(R.string.server)).create(AlunoRetrofit.class);

        atualizarLista();

        lvAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MainActivity.this, AlunoActivity.class);
                Aluno alunoIntent = (Aluno) lvAlunos.getItemAtPosition(position);
                intent.putExtra("ALUNO", alunoIntent);
                startActivityForResult(intent, ALUNO_REQUEST_CODE);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NovoAlunoActivity.class);
                startActivityForResult(intent, ALUNO_REQUEST_CODE);
            }
        });

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ra = etRa.getText().toString();
                if (ra == null) {
                    Toast.makeText(MainActivity.this, "Insira um RA para pesquisar", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (Aluno a: alunos) {
                    if (a.getRa().equals(ra)) {
                        Intent intent = new Intent(MainActivity.this, AlunoActivity.class);
                        intent.putExtra("ALUNO", a);
                        startActivityForResult(intent, ALUNO_REQUEST_CODE);
                        return;
                    }
                }

                Toast.makeText(MainActivity.this, "RA não encontrado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void atualizarLista() {
        Call<ApiResponse> call = service.getAlunos();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                alunos = new ArrayList<Aluno>();
                System.out.println(response.body().getRecordset());
                for (Aluno a: response.body().getRecordset()) {
                    alunos.add(a);
                }
                AlunoListAdapter adapter = new AlunoListAdapter(MainActivity.this, alunos);
                lvAlunos.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                System.err.println(t.getMessage());
                Toast.makeText(MainActivity.this, "Erro ao acessar lista de alunos", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ALUNO_REQUEST_CODE:
                atualizarLista();
                break;
        }
    }
}