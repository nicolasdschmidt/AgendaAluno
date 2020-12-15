package br.unicamp.cotuca.agendaaluno19191;

import java.util.List;

public class ApiResponse {
    private int rowsAffected;
    private List<Aluno> recordset;
    private String error;

    public ApiResponse(int rowsAffected, List<Aluno> recordset, String error) {
        this.rowsAffected = rowsAffected;
        this.recordset = recordset;
        this.error = error;
    }

    public int getRowsAffected() {
        return rowsAffected;
    }

    public List<Aluno> getRecordset() {
        return recordset;
    }

    public String getError() {
        return error;
    }
}
