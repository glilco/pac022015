package br.ufg.inf.fabrica.pac.dominio.enums;

/**
 *
 * @author Danillo
 */
public enum Papel {
    GPR,// ("GPR"), // Gerente de Projetos
    MEG,// ("MEG"), // Membro de equipe gerencial
    MEM;// ("ME");   // Membro de equipe
    
    public static Papel getPapel(String value){
        for (Papel papel : values()) {
            if(papel.name().equalsIgnoreCase(value))
                return papel;
        }
        return null;
    }
//    private String codigo;
//
//    Papel(String codigo) {
//        this.codigo = codigo;
//    }
//
//    public String getCodigo() {
//        return codigo;
//    }
}