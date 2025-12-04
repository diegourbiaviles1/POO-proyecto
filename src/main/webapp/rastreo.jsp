<%--
  Rastreo de paquetes – ProyectoPOO
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.persistence.*" %>
<%@ page import="org.openxava.jpa.XPersistence" %>
<%@ page import="org.example.ProyectoPOO.model.bodega.Paquete" %>
<%@ page import="org.example.ProyectoPOO.model.bodega.Movimiento" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Rastreo de Envíos - ProyectoPOO</title>
    <!-- CSS local, permitido por CSP (style-src 'self' ...) -->
    <link rel="stylesheet" href="rastreo.css">
</head>
<body>

<div class="header">
    <h1>Portal de rastreo de paquetes</h1>
</div>

<div class="container">

    <div class="search-box">
        <form method="get" action="rastreo.jsp" class="search-form">
            <label for="tracking">Ingresa tu código de rastreo</label>
            <input type="text"
                   id="tracking"
                   name="tracking"
                   placeholder="Ej: USA0001, USA0002..."
                   required
                   value="<%= request.getParameter("tracking") != null ? request.getParameter("tracking") : "" %>">
            <button type="submit">
                <span>🔍</span>
                <span>Rastrear</span>
            </button>
        </form>
    </div>

    <%
        String trackingCode = request.getParameter("tracking");

        if (trackingCode != null && !trackingCode.trim().isEmpty()) {
            EntityManager em = XPersistence.getManager();

            try {
                String jpql = "SELECT p FROM Paquete p WHERE p.trackingProveedor = :code";
                TypedQuery<Paquete> query = em.createQuery(jpql, Paquete.class);
                query.setParameter("code", trackingCode.trim());

                Paquete paquete = query.getSingleResult();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    %>

    <div class="result-card">
        <div class="result-header">
            <h3>Paquete #<%= paquete.getId() %></h3>
            <span class="status-badge"><%= paquete.getEstadoActual() %></span>
        </div>

        <p><strong>Descripción:</strong> <%= paquete.getDescripcion() %></p>
        <p><strong>Código de tracking:</strong> <%= paquete.getTrackingProveedor() %></p>

        <hr>

        <h4>Ubicación actual</h4>
        <p>
            <%= (paquete.getUbicacionActual() != null)
                    ? paquete.getUbicacionActual().getNombre()
                    : "En tránsito internacional / Sin sucursal asignada" %>
        </p>

        <%
            List<Movimiento> historial = paquete.getHistorial();
            if (historial != null && !historial.isEmpty()) {
        %>
        <h4>Historial de movimientos</h4>
        <div class="timeline">
            <%
                for (Movimiento mov : historial) {
            %>
            <div class="timeline-item">
                <strong><%= mov.getEstado() %></strong><br>
                <small>
                    <%= (mov.getTimestamp() != null)
                            ? mov.getTimestamp().format(formatter)
                            : "" %>
                </small>
                <p><%= mov.getDescripcion() != null ? mov.getDescripcion() : "" %></p>
            </div>
            <%
                }
            %>
        </div>
        <%
        } else {
        %>
        <p class="info-text">No hay movimientos registrados aún.</p>
        <%
            }
        %>
    </div>

    <%
    } catch (NoResultException e) {
    %>
    <div class="result-card">
        <h3 class="error-msg">No encontrado</h3>
        <p>No pudimos encontrar un paquete con el código <strong><%= trackingCode %></strong>.</p>
        <p class="info-text">Verifica que el código esté correcto y que el envío esté registrado en el sistema.</p>
    </div>
    <%
    } catch (Exception e) {
    %>
    <p class="error-msg">
        Error del sistema: <%= e.getMessage() %>
    </p>
    <%
            e.printStackTrace();
        }
    } else {
    %>
    <p class="info-text">
        Ingresa un código de rastreo en el panel superior para ver el estado de tu envío.
    </p>
    <%
        }
    %>

    <div class="footer">
        © <%= java.time.Year.now() %> ProyectoPOO
    </div>

</div>

</body>
</html>
