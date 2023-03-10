package villanueva.ricardo.Objects.dao;

import org.springframework.stereotype.Repository;
import villanueva.ricardo.Objects.Model.User;

import java.util.List;

@Repository
public interface UserDao {

    void addUser(User u);

    List<String> getAllNicksName();

    String getPasswdByUser(String user);

    boolean userExist(String user);

    User getUser(String user);

    void uploadNameUser(String nickname, String newName);

    void uploadPasswd(String nickname, String sha256);

    void deleteUser(String nickname);
}
