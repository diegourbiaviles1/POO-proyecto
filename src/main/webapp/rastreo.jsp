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
    <style>
        body {
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f6;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            min-height: 100vh;
        }

        .header {
            width: 100%;
            background: linear-gradient(135deg, #2980b9, #2c3e50);
            color: white;
            padding: 20px 40px;
            box-sizing: border-box;
            text-align: center;
            box-shadow: 0 2px 8px rgba(0,0,0,0.2);
        }

        .header h1 {
            margin: 0;
            font-size: 26px;
            letter-spacing: 1px;
        }

        .container {
            width: 100%;
            max-width: 900px;
            padding: 30px 15px 40px;
            box-sizing: border-box;
        }

        .search-box {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            gap: 10px;
        }

        .search-box label {
            flex-basis: 100%;
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 5px;
        }

        .search-box input[type="text"] {
            flex: 1;
            min-width: 180px;
            padding: 10px;
            border-radius: 4px;
            border: 1px solid #ccc;
            font-size: 14px;
        }

        .search-box button {
            padding: 10px 20px;
            font-size: 14px;
            background-color: #e67e22;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background 0.3s;
        }

        .search-box button:hover {
            background-color: #d35400;
        }

        .result-card {
            background: white;
            margin-top: 25px;
            padding: 22px;
            border-radius: 10px;
            border-left: 4px solid #27ae60;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        .result-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 10px;
            flex-wrap: wrap;
        }

        .result-header h3 {
            margin: 0;
        }

        .status-badge {
            background-color: #27ae60;
            color: white;
            padding: 4px 10px;
            border-radius: 20px;
            font-size: 0.9em;
            font-weight: bold;
        }

        .result-card p {
            margin: 5px 0;
            color: #34495e;
        }

        .timeline {
            margin-top: 18px;
            border-left: 2px solid #ddd;
            padding-left: 18px;
        }

        .timeline-item {
            margin-bottom: 15px;
            position: relative;
        }

        .timeline-item::before {
            content: "";
            width: 10px;
            height: 10px;
            background-color: #3498db;
            border-radius: 50%;
            position: absolute;
            left: -6px;
            top: 4px;
        }

        .timeline-item strong {
            color: #2c3e50;
        }

        .timeline-item small {
            color: #7f8c8d;
        }

        .info-text {
            margin-top: 20px;
            color: #7f8c8d;
        }

        .error-msg {
            color: #c0392b;
            font-weight: bold;
            margin-top: 15px;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to   { opacity: 1; transform: translateY(0); }
        }

        .result-card, .search-box {
            animation: fadeIn 0.3s;
        }
    </style>
</head>
<body>

<div class="header">
    <h1>Courier Express – Rastreo de Envíos</h1>
</div>

<div class="container">

    <div class="search-box">
        <form method="get" action="rastreo.jsp" style="display:flex; flex-wrap:wrap; gap:10px; width:100%;">
            <label for="tracking">Ingresa tu código de rastreo</label>
            <input type="text"
                   id="tracking"
                   name="tracking"
                   placeholder="Ej: USA123456"
                   required
                   value="<%= request.getParameter("tracking") != null ? request.getParameter("tracking") : "" %>">
            <button type="submit">Rastrear</button>
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
            <h3>📦 Paquete #<%= paquete.getId() %></h3>
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
                <br>
                <%= mov.getDescripcion() != null ? mov.getDescripcion() : "" %>
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
    <div class="search-box info-text" style="margin-top: 20px;">
        <h3 class="error-msg">No encontrado</h3>
        <p>No pudimos encontrar un paquete con el código <strong><%= trackingCode %></strong>.</p>
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
        }
    %>

</div>

</body>
</html>
