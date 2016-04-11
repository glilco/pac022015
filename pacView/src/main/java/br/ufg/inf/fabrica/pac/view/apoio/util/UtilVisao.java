package br.ufg.inf.fabrica.pac.view.apoio.util;

import br.ufg.inf.fabrica.pac.view.apoio.AtributosSessao;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Danillo
 */
public class UtilVisao {

    public static void direcionar(HttpServletRequest request, 
            HttpServletResponse response, String destino) throws ServletException, IOException{
        RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
        dispatcher.forward(request, response);
    }
    
    public static void direcionarPaginaErro(HttpServletRequest request, 
            HttpServletResponse response, String mensagemErro){
        RequestDispatcher dispatcher = request.getRequestDispatcher("erro.jsp");
        request.getSession().setAttribute(AtributosSessao.MENSAGENS_DE_ERRO, mensagemErro);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(UtilVisao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Date converterStringParaDate(String dateValue) 
            throws ParseException{
        return new SimpleDateFormat("yyyy-MM-dd").parse(dateValue);
    }
    
    public static String converterDataParaString(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
}
