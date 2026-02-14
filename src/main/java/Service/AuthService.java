package Service;

import dao.UserDAO;
import model.User;
import utils.JwtUtil;
import utils.PasswordUtil;

public class AuthService {
    private UserDAO userDAO = new UserDAO();

    public boolean register(String username, String password, String fullname) {

        if (userDAO.findByUsername(username) != null) {
            return false; // user exists
        }

        String hashedPassword = PasswordUtil.hashPassword(password);

        User user = new User(null, username, hashedPassword, fullname);

        return userDAO.save(user);
    }

    public String login(String username, String password) {

        User user = userDAO.findByUsername(username);

        if (user == null) {
            return null;
        }

        if (!PasswordUtil.checkPassword(password, user.getPassword())) {
            return null;
        }

        return JwtUtil.generateToken(user.getUsername());
    }


}
