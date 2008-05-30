package es.project.filtros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FiltroComprobarUsuario implements Filter{
	
	private FilterConfig filterConfig = null;
	
	public void destroy() {
		this.filterConfig = null;
	}
	
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws ServletException, IOException {
		
		/*HttpServletRequest http = (HttpServletRequest)request;
		HttpSession session = http.getSession();
		if (session == null)
			System.out.print("YES");
		else System.out.print("NO");
		
		boolean usuarioActivo = (Boolean)session.getAttribute("usuarioActivo");
		
		if (!usuarioActivo) {
			RequestDispatcher rd = http.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		} 
		else*/ 
		/*HttpServletRequest http = (HttpServletRequest)request;
		HttpSession session = http.getSession();
		session.setAttribute("usuarioActivo", false);*/
		chain.doFilter(request, response);
	}
}
