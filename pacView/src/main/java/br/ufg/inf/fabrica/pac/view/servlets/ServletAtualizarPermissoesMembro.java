
package br.ufg.inf.fabrica.pac.view.servlets;

import br.ufg.inf.fabrica.pac.negocio.IGestorMembros;
import br.ufg.inf.fabrica.pac.dominio.MembroProjeto;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.negocio.imp.GestorMembrosImpl;
import br.ufg.inf.fabrica.pac.view.apoio.UsuarioView;
import br.ufg.inf.fabrica.pac.view.beans.BeanAtribuirEquipe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Danillo
 */
@WebServlet(name = "BeanAtualizarPermissoesMembro", urlPatterns = {"/atualizarPermissoesMembro"})
public class ServletAtualizarPermissoesMembro extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        BeanAtribuirEquipe bean = (BeanAtribuirEquipe) request.getSession().
                getAttribute("beanAtribuir");
        int idUsuarioEmAlteracao = bean.getUsuarioEmAlteracao();
        Usuario usuarioLogado = bean.getUsuarioLogado();
        Projeto projetoSelecionado = bean.getProjetoSelecionado();
        
        
        List<String> novosPapeis;
        
        if(request.getParameterValues("papeis")==null){
            novosPapeis = new ArrayList<>();
        } else {
            novosPapeis = Arrays.asList(request.getParameterValues("papeis"));
        }
        
        List<MembroProjeto> papeisAntigos = null;
        
        //Busca a lista de membros ja carregas no bean e encontra o objeto do 
        // respectivo usuario
        List<UsuarioView> listaDeMembrosDeProjeto = bean.getUsuarios();
        for (UsuarioView usuarioView : listaDeMembrosDeProjeto) {
            if(usuarioView.getId()==idUsuarioEmAlteracao){
                papeisAntigos = usuarioView.getMembros();
                break;
            }
        }
        
        //Busca os papéis para remoção
        List<MembroProjeto> papeisRemovidos = new ArrayList<>();
        for (MembroProjeto papelAntigo : papeisAntigos) {
            if(!novosPapeis.contains(papelAntigo.getPapel()))
                papeisRemovidos.add(papelAntigo);
        }
        
        //Busca os papéis para adição
        List<MembroProjeto> papeisAdicionados = new ArrayList<>();
        for (String novoPapel : novosPapeis) {
            boolean naoExistia = true;
            for (MembroProjeto membro : papeisAntigos) {
                if(novoPapel.equals(membro.getPapel())){
                    naoExistia = false;
                    break;
                }
            }
            if(naoExistia){
                MembroProjeto membro = new MembroProjeto();
                membro.setIdProjeto(projetoSelecionado.getId());
                membro.setIdUsuario(idUsuarioEmAlteracao);
                membro.setPapel(novoPapel);
                papeisAdicionados.add(membro);
            }
        }
        
        IGestorMembros gestor = new GestorMembrosImpl();
        gestor.atualizarPapeisDeUsuarioEmUmProjeto(usuarioLogado, papeisRemovidos, papeisAdicionados);
        bean.setUsuarioEmAlteracao(0);
        request.getRequestDispatcher("atribuirEquipe.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
