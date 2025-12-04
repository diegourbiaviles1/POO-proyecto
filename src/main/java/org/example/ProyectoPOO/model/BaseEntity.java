package org.example.ProyectoPOO.model;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.multitenancy.TenantContext;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.openxava.annotations.ReadOnly;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@FilterDef(
        name = "tenantFilter",
        parameters = @ParamDef(name = "tenantId", type = "string")
)
@Filter(name = "tenantFilter", condition = "tenantId = :tenantId")
@Getter @Setter
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ReadOnly
    private Long id;

    @Column(length = 50, nullable = false)
    @ReadOnly
    private String tenantId;

    @ReadOnly
    private LocalDateTime fechaCreacion;

    @ReadOnly
    private LocalDateTime fechaActualizacion;

    @Column(length = 50)
    private String usuarioCreacion;

    @Column(length = 50)
    private String usuarioActualizacion;

    @PrePersist
    public void prePersist() {
        if (tenantId == null || tenantId.trim().isEmpty()) {
            String currentTenant = TenantContext.getCurrentTenant();
            if (currentTenant == null || currentTenant.trim().isEmpty()) {
                currentTenant = "default";
            }
            tenantId = currentTenant;
        }
        fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        if (tenantId == null || tenantId.trim().isEmpty()) {
            String currentTenant = TenantContext.getCurrentTenant();
            if (currentTenant == null || currentTenant.trim().isEmpty()) {
                currentTenant = "default";
            }
            tenantId = currentTenant;
        }
        fechaActualizacion = LocalDateTime.now();
    }
}
