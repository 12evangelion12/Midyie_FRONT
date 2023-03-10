package fr.tom.midyie;

public class SessionProperty {

    private static SessionProperty sessionProperty;
    private String user;
    private String privilege;

    public static SessionProperty getInstance() {
        if (sessionProperty  == null) {
            sessionProperty = new SessionProperty();
        }
        return sessionProperty;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
