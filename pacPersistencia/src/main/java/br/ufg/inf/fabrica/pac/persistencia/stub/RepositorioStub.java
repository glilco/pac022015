package br.ufg.inf.fabrica.pac.persistencia.stub;

import br.ufg.inf.fabrica.pac.dominio.Andamento;
import br.ufg.inf.fabrica.pac.dominio.Estado;
import br.ufg.inf.fabrica.pac.dominio.Pacote;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.persistencia.IDaoAndamento;
import br.ufg.inf.fabrica.pac.persistencia.IDaoEstado;
import br.ufg.inf.fabrica.pac.persistencia.IDaoPacote;
import br.ufg.inf.fabrica.pac.persistencia.IDaoUsuario;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoAndamento;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoEstado;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoPacote;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoProjeto;
import br.ufg.inf.fabrica.pac.persistencia.imp.DaoUsuario;
import java.util.Date;

/**
 *
 * @author Danillo
 */
public class RepositorioStub {

    public static void inicializaRepositorio() {

        //Cria estados
        Estado estadoNovo = new Estado();
        estadoNovo.setDescricao("Atribuido");
        estadoNovo.setNome("Atribuido");
        estadoNovo.setEstadoFinal(false);

        Estado estadoAtribuido = new Estado();
        estadoAtribuido.setDescricao("Atribuido");
        estadoAtribuido.setNome("Atribuido");
        estadoAtribuido.setEstadoFinal(false);

        Estado estadoContratado = new Estado();
        estadoContratado.setDescricao("Contratado");
        estadoContratado.setNome("Contratado");
        estadoContratado.setEstadoFinal(false);

        IDaoEstado dEstado = new DaoEstado();
        dEstado.salvar(estadoNovo);
        dEstado.salvar(estadoAtribuido);
        dEstado.salvar(estadoContratado);

        //Cria projeto
        Projeto projeto = new Projeto();
        projeto.setDataInicio(new Date());
        projeto.setDataTermino(new Date(new Date().getTime() + 365 * 24 * 60 * 60 * 1000));
        projeto.setDescricao("Gestor de pacotes 2015");
        projeto.setNome("pac2015");
        projeto.setPatrocinador("Eduardo Simões");
        projeto.setStakeholders("Danillo, Afonso, Juliano, Maycon, Murilo, Mariana");

        new DaoProjeto().salvar(projeto);

        //Cria usuarios
        Usuario membro = new Usuario();
        membro.setAtivo(true);
        membro.setId(1);

        Usuario gerente = new Usuario();
        gerente.setAtivo(true);
        gerente.setId(2);

        IDaoUsuario daoUsuario = new DaoUsuario();
        daoUsuario.salvar(membro);
        daoUsuario.salvar(gerente);

        //Cria pacotes
        IDaoPacote daoPacote = new DaoPacote();
        IDaoAndamento daoAndamento = new DaoAndamento();

        Pacote pacoteAtribuido = new Pacote();
        pacoteAtribuido.setAbandonado(false);
        pacoteAtribuido.setDataCriacao(new Date());
        pacoteAtribuido.setDataPrevistaRealizacao(new Date(new Date().getTime() + 24 * 60 * 60 * 1000));
        pacoteAtribuido.setDescricao("Implementar requisito de software RS100");
        pacoteAtribuido.setDocumento("Link do documento");
        pacoteAtribuido.setNome("rs100");
        pacoteAtribuido.setIdEstado(estadoAtribuido.getId());
        pacoteAtribuido.setIdProjeto(projeto.getId());
        pacoteAtribuido.setIdUsuario(membro.getId());
        daoPacote.salvar(pacoteAtribuido);

        //Cria andamentos do pacote atribuido
        Andamento andamento = new Andamento();
        andamento.setDataModificacao(new Date());
        andamento.setDataPrevistaConclusao(new Date());
        andamento.setDescricao("Criação do pacote");
        andamento.setIdEstado(estadoNovo.getId());
        andamento.setIdPacote(pacoteAtribuido.getId());
        andamento.setIdUsuarioRemetente(gerente.getId());
        andamento.setIdUsuarioDestinatario(membro.getId());
        pacoteAtribuido.getAndamentos().add(andamento.getId());
        daoAndamento.salvar(andamento);
        //Distribuido
        andamento = new Andamento();
        andamento.setDataModificacao(new Date());
        andamento.setDataPrevistaConclusao(new Date());
        andamento.setDescricao("Distribuicao do pacote");
        andamento.setIdEstado(estadoAtribuido.getId());
        andamento.setIdPacote(pacoteAtribuido.getId());
        andamento.setIdUsuarioRemetente(membro.getId());
        pacoteAtribuido.getAndamentos().add(andamento.getId());
        daoAndamento.salvar(andamento);

        Pacote pacoteContratado = new Pacote();
        pacoteContratado.setAbandonado(false);
        pacoteContratado.setDataCriacao(new Date());
        pacoteContratado.setDataPrevistaRealizacao(new Date(new Date().getTime() + 86400000));
        pacoteContratado.setDescricao("Implementar requisito de software RS101");
        pacoteContratado.setNome("rs101");
        pacoteContratado.setIdEstado(estadoContratado.getId());
        pacoteContratado.setIdProjeto(projeto.getId());
        pacoteContratado.setIdUsuario(membro.getId());
        daoPacote.salvar(pacoteContratado);
        //Cria andamentos do pacote atribuido
        andamento = new Andamento();
        andamento.setDataModificacao(new Date());
        andamento.setDataPrevistaConclusao(new Date());
        andamento.setDescricao("Criação do pacote");
        andamento.setIdEstado(estadoNovo.getId());
        andamento.setIdPacote(pacoteContratado.getId());
        andamento.setIdUsuarioRemetente(gerente.getId());
        andamento.setIdUsuarioDestinatario(membro.getId());
        pacoteAtribuido.getAndamentos().add(andamento.getId());
        daoAndamento.salvar(andamento);
        //Distribuido
        andamento = new Andamento();
        andamento.setDataModificacao(new Date());
        andamento.setDataPrevistaConclusao(new Date());
        andamento.setDescricao("Distribuicao do pacote");
        andamento.setIdEstado(estadoAtribuido.getId());
        andamento.setIdPacote(pacoteContratado.getId());
        andamento.setIdUsuarioRemetente(membro.getId());
        pacoteAtribuido.getAndamentos().add(andamento.getId());
        daoAndamento.salvar(andamento);
        //Contratado
        andamento = new Andamento();
        andamento.setDescricao("Pacote aceito");
        andamento.setDataModificacao(new Date());
        andamento.setDataPrevistaConclusao(new Date());
        andamento.setIdEstado(estadoContratado.getId());
        andamento.setIdPacote(pacoteContratado.getId());
        andamento.setIdUsuarioRemetente(gerente.getId());
        andamento.setIdUsuarioDestinatario(membro.getId());
        pacoteAtribuido.getAndamentos().add(andamento.getId());
        daoAndamento.salvar(andamento);
    }

}
