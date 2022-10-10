package models;

/**
 * Role enum
 */
public enum Role {
    ALUNO,
    FUNCIONARIO,
    DOCENTE;

    public static void print(){
        System.out.println("1-ALUNO, 2-FUNCIONARIO, 3-DOCENTE");
    }
}
