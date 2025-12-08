package org.example.ProyectoPOO.multitenancy;

import javax.servlet.*;
import java.io.IOException;

public class TenantFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        try {
            HibernateTenantHelper.applySucursalFilter();
            chain.doFilter(request, response);
        } finally {
            // opcional: lo puedes dejar o incluso eliminar
            TenantContext.clear();
        }
    }
}
