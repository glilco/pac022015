package br.ufg.inf.fabrica.pac.view.servlets;

import br.ufg.inf.fabrica.pac.dominio.Pacote;
import br.ufg.inf.fabrica.pac.dominio.Projeto;
import br.ufg.inf.fabrica.pac.dominio.Resposta;
import br.ufg.inf.fabrica.pac.dominio.Usuario;
import br.ufg.inf.fabrica.pac.dominio.enums.Estado;
import br.ufg.inf.fabrica.pac.negocio.imp.GestorDePacotes;
import br.ufg.inf.fabrica.pac.negocio.utils.UtilsNegocio;
import br.ufg.inf.fabrica.pac.view.apoio.AtributosConfiguracao;
import br.ufg.inf.fabrica.pac.view.apoio.AtributosSessao;
import br.ufg.inf.fabrica.pac.view.apoio.util.UtilVisao;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author auf
 */
@WebServlet(name = "ServletCriarPacote", urlPatterns = {"/criarPacote"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 10// 10MB
)
public class ServletCriarPacote extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        
        String newFileName = null;
        try {
            HttpSession session = request.getSession();
            Usuario autor = (Usuario) session.getAttribute(AtributosSessao.USUARIO_LOGADO);
            Projeto projeto = (Projeto) session.getAttribute(AtributosSessao.PROJETO_SELECIONADO);

            Pacote pacote = new Pacote();

            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            pacote.setNome(request.getParameter("nomePacote"));
            pacote.setDescricao(request.getParameter("descricaoPacote"));
            String dataPrevista = request.getParameter("dataPrevistaRealizacao");
            pacote.setEstado(Estado.NOVO);
            pacote.setDataCriacao(UtilsNegocio.buscarDataAtual());
            pacote.setIdProjeto(projeto.getId());
            pacote.setProjeto(projeto);
            pacote.setIdUsuario(autor.getId());
            pacote.setUsuario(autor);
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
            try {
                newFileName = salvarArquivo(request, response);
                pacote.setDocumento(newFileName);
            } catch (ServletException | IOException ex) {
                UtilVisao.direcionarPaginaErro(request, response,
                        "Falha ao criar arquivo");
            }
            
            GestorDePacotes gestor = GestorDePacotes.getInstance();
            Resposta<Pacote> resposta = gestor.criarPacote(autor, pacote, projeto);
            if (resposta.isSucesso()) {
                UtilVisao.direcionar(request, response, "index.jsp");
            } else {
                deletarArquivo(newFileName);
                UtilVisao.direcionarPaginaErro(request, response,
                        resposta.getLaudo().toString());
            }

        } catch (IOException | ServletException ex) {
            Logger.getLogger(ServletCriarPacote.class.getName()).log(
                    Level.SEVERE, null, ex);
            deletarArquivo(newFileName);
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

    private String salvarArquivo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Properties prop = (Properties) getServletContext().
                getAttribute(AtributosConfiguracao.VARIAVEL_PROPRIEDADES);
        String pathFolder = prop.getProperty(
                AtributosConfiguracao.FILE_FOLDERS);
        Part partFile = request.getPart("documento");
        String origalName = getOriginalName(partFile);
        String extension = origalName.substring(origalName.length() - 4);
        DateFormat df = new SimpleDateFormat("yyyyMMddkkmmssSSS");
        Date dateFile = UtilsNegocio.buscarDataAtual();
        String newFileName = df.format(dateFile) + extension;
        OutputStream out = null;
        InputStream fileContent = null;
        File newFile = new File(pathFolder + File.separator + newFileName);
        try {
            out = new FileOutputStream(newFile);
            fileContent = partFile.getInputStream();
            int read;
            final byte[] bytes = new byte[1024];
            while ((read = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } finally {
            if (out != null) {
                out.close();
            }
            if (fileContent != null) {
                fileContent.close();
            }
        }
        return newFileName;
    }

    private void deletarArquivo(String newFileName) {
        try {
            Properties prop = (Properties) getServletContext().
                getAttribute(AtributosConfiguracao.VARIAVEL_PROPRIEDADES);
            String pathFolder = prop.getProperty(
                    AtributosConfiguracao.FILE_FOLDERS);
            File newFile = new File(pathFolder + File.separator + newFileName);
            if (newFile.exists()) {
                newFile.delete();
            }
        } catch (Exception ex) {
            Logger.getLogger(ServletCriarPacote.class.getName()).log(Level.INFO,
                    "Falha na exclusão do arquivo");
        }

    }

    private String getOriginalName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
