package org.example.ProyectoPOO.multitenancy;

import javax.servlet.*;
import java.io.IOException;

public class TenantFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // ACTIVA el filtro de sucursal ANTES de procesar la petición
        HibernateTenantHelper.applySucursalFilter();

        // Continúa con la cadena de filtros/servlet
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() { }
}
