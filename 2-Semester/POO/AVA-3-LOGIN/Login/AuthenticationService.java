public class AuthenticationService implements LoginService {

    private User currentUser;

    @Override
    public boolean login(String username, String password) { 
        if ("denys".equals(username) && "poo123456".equals(password)) {
            this.currentUser = new User(username, password);
            UserSession.setCurrentUser(this.currentUser);
            return true;
        }
        return false;
    }

    @Override
    public void logout() {
        this.currentUser = null;
        UserSession.setCurrentUser(null);
    }

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }
}