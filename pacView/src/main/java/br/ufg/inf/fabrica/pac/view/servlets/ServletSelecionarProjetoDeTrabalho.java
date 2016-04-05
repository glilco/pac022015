package br.ufg.inf.fabrica.pac.view.servlets;

import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.view.apoio.AtributosSessao;
import br.ufg.inf.fabrica.pac.view.apoio.util.UtilVisao;
import br.ufg.inf.fabrica.pac.view.beans.BeanListagemProjetos;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Danillo
 */
@WebServlet(name = "ServletSelecionarProjetoDeTrabalho", urlPatterns = {"/selecionarProjeto"})
public class ServletSelecionarProjetoDeTrabalho extends HttpServlet {

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
        String strIdProjeto = request.getParameter("idProjeto");
        if(strIdProjeto==null||strIdProjeto.isEmpty()){
            UtilVisao.direcionarPaginaErro(request, response, "Nenhum projeto foi informado");
        }
        long idProjeto = Long.valueOf(strIdProjeto);
        HttpSession session = request.getSession();
        BeanListagemProjetos bean = 
                (BeanListagemProjetos) session.
                        getAttribute(AtributosSessao.BEAN_LISTAGEM_PROJETOS);
        Projeto projetoSelecionado = bean.getProjetoSelecionado(idProjeto);
        session.setAttribute(AtributosSessao.PROJETO_SELECIONADO, projetoSelecionado);
        UtilVisao.direcionar(request, response, "index.jsp");
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
