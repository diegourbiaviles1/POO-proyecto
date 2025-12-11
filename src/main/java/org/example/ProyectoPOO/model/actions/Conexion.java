package org.example.ProyectoPOO.model.actions;

import com.openxava.naviox.actions.ForwardToOriginalURIBaseAction;
import com.openxava.naviox.impl.SignInHelper;
import org.openxava.jpa.XPersistence;
import org.openxava.util.Is;

import javax.persistence.Query;

public class Conexion extends ForwardToOriginalURIBaseAction {

    @Override
    public void execute() throws Exception {
        try {
            SignInHelper.initRequest(getRequest(), getView());
            if (getErrors().contains()) return;

            String userName = getView().getValueString("user");
            String password = getView().getValueString("password");

            if (Is.emptyString(userName, password)) {
                addError("unauthorized_user");
                return;
            }

            Query query = XPersistence.getManager().createQuery(
                    "SELECT COUNT(u) FROM Usuario u " +
                            "WHERE u.username = :username AND u.passwordHash = :password"
            );
            query.setParameter("username", userName);
            query.setParameter("password", password);

            Long count = (Long) query.getSingleResult();

            if (count == 0) {
                addError("unauthorized_user");
                return;
            }

            SignInHelper.signIn(getRequest(), userName);
            getView().reset();
            getContext().resetAllModulesExceptCurrent(getRequest());
            forwardToOriginalURI();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            addError("unauthorized_user");
        }
    }
}