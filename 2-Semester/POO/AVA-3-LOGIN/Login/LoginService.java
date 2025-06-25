public interface LoginService {
    boolean login(String username, String password);
    void logout();
    User getCurrentUser();
}