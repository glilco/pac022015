/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.pac.view.servlets;

import br.ufg.inf.fabrica.pac.negocio.IGestorMembros;
import br.ufg.inf.fabrica.pac.dominio.MembroProjeto;
import br.ufg.inf.fabrica.pac.dominio.PapelProjeto;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.negocio.imp.GestorMembrosImpl;
import br.ufg.inf.fabrica.pac.view.beans.BeanAtribuirEquipe;
import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "ServletAdicionarMembro", urlPatterns = {"/adicionarMembro"})
public class ServletAdicionarMembro extends HttpServlet {

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
        String[] usuarios = request.getParameterValues("nomeNaoMembro");
        String[] papeis = request.getParameterValues("papeis");
        
        BeanAtribuirEquipe beanAtribuir = (BeanAtribuirEquipe) request.
                getSession().getAttribute("beanAtribuir");
        Usuario usuarioLogado = beanAtribuir.getUsuarioLogado();
        Projeto projetoSelecionado = beanAtribuir.getProjetoSelecionado();
        
        List<MembroProjeto> membros = new ArrayList<>();
        for (String usuario : usuarios) {
            for (String papel : papeis) {
                MembroProjeto membro = new MembroProjeto();
                membro.setIdProjeto(projetoSelecionado.getId());
                membro.setIdUsuario(Integer.parseInt(usuario));
                membro.setPapel(papel);
                //Somente adiciona se o papel informado estiver consistente
                //com os papéis definidos no Enum PapelProjeto
                try{
                    PapelProjeto p = PapelProjeto.valueOf(papel);
                    membros.add(membro);
                }catch(IllegalArgumentException ex){
                    
                }
            }
        }
        
        IGestorMembros gestor = new GestorMembrosImpl();
        gestor.adicionarMembrosProjeto(usuarioLogado, membros);
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
