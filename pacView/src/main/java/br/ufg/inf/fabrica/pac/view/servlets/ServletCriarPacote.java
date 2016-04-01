package br.ufg.inf.fabrica.pac.view.servlets;

import br.ufg.inf.fabrica.pac.negocio.ICriarPacote;
import br.ufg.inf.fabrica.pac.dominio.Pacote;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.negocio.imp.GestorDePacotes;
import br.ufg.inf.fabrica.pac.dominio.utils.FileService;
import br.ufg.inf.fabrica.pac.persistencia.util.Util;
import br.ufg.inf.fabrica.pac.view.apoio.AtributosSessao;
import br.ufg.inf.fabrica.pac.view.apoio.util.UtilVisao;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author auf
 */
@WebServlet(name = "ServletCriarPacote", urlPatterns = {"/criarPacote"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 10,// 10MB
        location = "C://"
)
public class ServletCriarPacote extends HttpServlet {

    private static final String BASE_DIR = "Users\\auf\\Desktop\\UploadsPAC";
    private static final String DRIVE = "C:\\";
    Resposta<Pacote> resposta = new Resposta<>();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.text.ParseException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession();
            Usuario autor = (Usuario) session.getAttribute(AtributosSessao.USUARIO_LOGADO);
            Projeto projeto = (Projeto) session.getAttribute(AtributosSessao.PROJETO_SELECIONADO);
            Pacote pacote = new Pacote();
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            pacote.setNome(request.getParameter("nomePacote"));
            pacote.setDescricao(request.getParameter("descricaoPacote"));
            String dataPrevista = request.getParameter("dataPrevistaRealizacao");
            if (dataPrevista != null) {
                Date dataPrevistaRealizacao = null;
                try {
                    dataPrevistaRealizacao = sdf.parse(dataPrevista);
                } catch (ParseException ex) {
                    session.setAttribute(AtributosSessao.MENSAGENS_DE_ERRO,
                            "Data de previsão de realização inválida");
                    UtilVisao.direcionar(request, response, "erro.jsp");
                }
                pacote.setDataPrevistaRealizacao(dataPrevistaRealizacao);
            }
            pacote.setDocumento(request.getParameter("documento"));
            
        } catch (IOException | ServletException ex) {
            Logger.getLogger(ServletCriarPacote.class.getName()).log(Level.SEVERE, null, ex);
            
            UtilVisao.direcionarPaginaErro(request, response, ex.getMessage());
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

    private HttpServletRequest salvarDocumento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FileService service = new FileService();
        String nomeCompletoDocumento;

        Path destination = service.createFolder(DRIVE + BASE_DIR);
        String destinationString = destination.toString().replace(DRIVE, "");

        if (request.getPart("documento") != null && Files.exists(destination)) {
            nomeCompletoDocumento = DRIVE + service.saveFile(destinationString, request.getPart("documento"));
            if (nomeCompletoDocumento != null) {
                request.setAttribute("documento", nomeCompletoDocumento);
            }
        }

        if (request.getAttribute("documento") == null) {
            resposta.addItemLaudo("Documento não pode ser enviado!");
            request.setAttribute("resposta", resposta);
            request.getRequestDispatcher("criarPacote.jsp").forward(request, response);
        }

        return request;
    }
}
