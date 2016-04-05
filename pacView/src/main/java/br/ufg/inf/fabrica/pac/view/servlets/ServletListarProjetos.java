package br.ufg.inf.fabrica.pac.view.servlets;

import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.negocio.IGestorDeProjeto;
import br.ufg.inf.fabrica.pac.negocio.imp.GestorDeProjetos;
import br.ufg.inf.fabrica.pac.view.apoio.AtributosSessao;
import br.ufg.inf.fabrica.pac.view.apoio.util.UtilVisao;
import java.io.IOException;
import java.util.List;
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
@WebServlet(name = "ServletListarProjetos", urlPatterns = {"/listarProjetos"})
public class ServletListarProjetos extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Usuario usuario
                = (Usuario) session.getAttribute(AtributosSessao.USUARIO_LOGADO);
        IGestorDeProjeto gestor = GestorDeProjetos.getInstance();
        Resposta<List<Projeto>> resposta
                = gestor.buscarTodos(usuario);
        if(resposta.isSucesso()){
            request.setAttribute("todosProjetos", resposta.getChave());
            UtilVisao.direcionar(request, response, "listagemProjetos.jsp");
        } else {
            UtilVisao.direcionarPaginaErro(request, response, resposta.getLaudo().toString());
        }
        
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
