package br.ufg.inf.fabrica.pac.view.apoio.util;

import java.io.IOException;
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
}
