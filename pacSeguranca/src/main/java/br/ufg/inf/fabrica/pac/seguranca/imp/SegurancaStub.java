package br.ufg.inf.fabrica.pac.seguranca.imp;

import br.ufg.inf.fabrica.pac.seguranca.Seguranca;
import java.util.List;

public class SegurancaStub implements Seguranca {

    private static Seguranca seguranca;

    private SegurancaStub() {

    }

    public static Seguranca getInstance() {
        if (seguranca == null) {
            seguranca = new SegurancaStub();
        }
        return seguranca;
    }

    @Override
    public boolean autorizar(String recurso, List<String> papeis) {
        return true;
    }

    @Override
    public boolean autorizar(String recurso, List<String> papeis, String contexto) {
        return true;
    }

}
