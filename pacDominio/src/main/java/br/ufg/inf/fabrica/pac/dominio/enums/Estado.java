package br.ufg.inf.fabrica.pac.dominio.enums;

/**
 *
 * @author Danillo
 */
public enum Estado {
    NOVO("NOVO", false, "ESTADO DE UM PACOTE CRIADO CUJO ULTIMO TRATAMENTO FOI A CRIAÇÃO"),
    ATRIBUIDO("ATRIBUIDO", false, "ESTADO DE UM PACOTE ATRIBUIDO A UM RESPONSÁVEL");

    private final String nome;
    private final boolean estadoFinal;
    private final String descricao;

    Estado(String nome, boolean estadoFinal, String descricao){
        this.nome = nome;
        this.estadoFinal = estadoFinal;
        this.descricao = descricao;
    }
    
    public String getNome() {
        return nome;
    }

    public boolean isEstadoFinal() {
        return estadoFinal;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public static Estado getPapel(String value){
        for (Estado estado : values()) {
            if(estado.getNome().equalsIgnoreCase(value))
                return estado;
        }
        return null;
    }
}
