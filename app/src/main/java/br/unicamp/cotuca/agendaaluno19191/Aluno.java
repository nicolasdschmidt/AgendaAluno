package br.unicamp.cotuca.agendaaluno19191;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Aluno implements Serializable {
    private final String ra;
    private final String nome;
    private final String email;

    public Aluno (String ra, String nome, String email) {
        this.ra = ra;
        this.nome = nome;
        this.email = email;
    }

    public String getRa() {
        return ra;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    @NonNull
    @Override
    public String toString() {
        return ra.toString();
    }
}
