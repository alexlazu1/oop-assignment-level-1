package user;

public class User {
    private Credentials credentials;

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    // TODO: add statistics like tokens count, liked movies..

    // TODO: override to String
}
