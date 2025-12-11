package org.example.ProyectoPOO.config;

import org.example.ProyectoPOO.annotations.FilterRestrictiva;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.hibernate.SessionFactory;
import org.openxava.jpa.XPersistence;
import org.openxava.util.Users;
import org.openxava.util.Is;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Table;
import javax.persistence.Query;
import javax.persistence.metamodel.EntityType;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RestrictivaStatementInspector implements StatementInspector {

    private volatile Set<String> tablasFiltrables;
    // Regex para detectar tablas en el FROM o JOIN
    private static final Pattern FROM_JOIN_PATTERN =
            Pattern.compile("(from|join)\\s+([a-zA-Z0-9_]+)\\s+([a-zA-Z0-9_]+)", Pattern.CASE_INSENSITIVE);

    private static final ThreadLocal<Long> sucursalActual = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> resolviendoSucursal = ThreadLocal.withInitial(() -> false);

    public RestrictivaStatementInspector() { }

    private void inicializarTablasFiltrables() {
        if (tablasFiltrables != null) return;
        synchronized (this) {
            if (tablasFiltrables != null) return;
            EntityManagerFactory emf = XPersistence.getManager().getEntityManagerFactory();
            SessionFactory sf = emf.unwrap(SessionFactory.class);

            tablasFiltrables = sf.getMetamodel()
                    .getEntities()
                    .stream()
                    .map(EntityType::getJavaType)
                    .filter(clazz -> clazz.isAnnotationPresent(FilterRestrictiva.class))
                    .map(clazz -> {
                        Table t = clazz.getAnnotation(Table.class);
                        // Si tiene @Table usa ese nombre, sino el simpleName
                        return t != null ? t.name().toLowerCase() : clazz.getSimpleName().toLowerCase();
                    })
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public String inspect(String sql) {
        inicializarTablasFiltrables();
        if (sql == null || resolviendoSucursal.get()) return sql;

        String lowerSql = sql.toLowerCase();

        // Ignoramos operaciones que no sean SELECT (aunque podrías filtrar UPDATE/DELETE si quisieras)
        if (lowerSql.startsWith("insert") || lowerSql.startsWith("update") || lowerSql.startsWith("delete")) {
            return sql;
        }

        // Evitar bucle infinito al consultar el propio usuario
        if (lowerSql.contains("usuario")) {
            return sql;
        }

        Long sucursalId = obtenerSucursalActual();
        if (sucursalId == null) return sql; // Si es admin o no tiene sucursal, ve todo

        Map<String, String> aliasFiltrados = extraerAlias(sql);
        if (aliasFiltrados.isEmpty()) return sql;

        // Crear la condición: alias.sucursal_id = ID
        String condiciones = aliasFiltrados.values().stream()
                .map(alias -> alias + ".sucursal_id = " + sucursalId)
                .collect(Collectors.joining(" AND "));

        String filtro = "(" + condiciones + ")";

        // Inyectar el filtro en el lugar correcto (antes de ORDER BY, LIMIT, etc.)
        return inyectarFiltro(sql, lowerSql, filtro);
    }

    private String inyectarFiltro(String sql, String lowerSql, String filtro) {
        int idxOrder = lowerSql.indexOf(" order by ");
        int idxLimit = lowerSql.indexOf(" limit "); // Postgres/MySQL
        int idxOffset = lowerSql.indexOf(" offset ");

        // SQL Server a veces usa 'TOP', pero Hibernate suele traducirlo distinto.
        // Si usas SQL Server y paginación, 'offset' suele estar presente en versiones modernas.

        int corte = -1;
        if (idxOrder != -1) corte = idxOrder;
        if (idxLimit != -1 && (corte == -1 || idxLimit < corte)) corte = idxLimit;
        if (idxOffset != -1 && (corte == -1 || idxOffset < corte)) corte = idxOffset;

        if (lowerSql.contains(" where ")) {
            if (corte != -1) {
                return sql.substring(0, corte) + " AND " + filtro + " " + sql.substring(corte);
            } else {
                return sql + " AND " + filtro;
            }
        } else {
            if (corte != -1) {
                return sql.substring(0, corte) + " WHERE " + filtro + " " + sql.substring(corte);
            } else {
                return sql + " WHERE " + filtro;
            }
        }
    }

    private Map<String, String> extraerAlias(String sql) {
        Map<String, String> resultado = new LinkedHashMap<>();
        Matcher m = FROM_JOIN_PATTERN.matcher(sql); // matcher sin lowercase para preservar case de alias si fuera necesario
        while (m.find()) {
            String tabla = m.group(2).toLowerCase();
            String alias = m.group(3);
            if (tablasFiltrables.contains(tabla)) {
                resultado.put(tabla, alias);
            }
        }
        return resultado;
    }

    private Long obtenerSucursalActual() {
        Long id = sucursalActual.get();
        if (id != null) return id;
        if (resolviendoSucursal.get()) return null;

        resolviendoSucursal.set(true);
        try {
            String login = Users.getCurrent();
            if (Is.emptyString(login)) return null;
            if (login.equalsIgnoreCase("admin")) return null; // Admin ve todo

            // Consulta ajustada a tu modelo 'Usuario' en ProyectoPOO
            Query q = XPersistence.getManager()
                    .createQuery("select u.sucursal.id from Usuario u where u.username = :user");
            q.setParameter("user", login);

            id = (Long) q.getSingleResult();
            sucursalActual.set(id);
            return id;
        } catch (Exception ex) {
            return null; // Si falla (ej. usuario sin sucursal), no filtramos (o podrías lanzar error)
        } finally {
            resolviendoSucursal.set(false);
        }
    }
}