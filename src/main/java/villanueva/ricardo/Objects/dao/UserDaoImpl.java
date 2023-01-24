package villanueva.ricardo.Objects.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import villanueva.ricardo.Objects.Model.User;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<String> nicknamesRowMapper =  (rs, rn) ->{
        return (rs.getString("nickname"));
    };

    private final RowMapper<String> passwdRowWapper = (rs, rn) ->{
        return (rs.getString("password"));
    };

    private final RowMapper<User> userRowMapper = (rs, rn) ->{
        return new User(rs.getString("nameC"), rs.getString("nickname"), rs.getString("password"));
    };
    @Override
    public void addUser(User u) {
        jdbcTemplate.update("insert into Users (nickname, password, nameC) values(?,?,?)", u.getUsername(), u.getPasswd(), u.getRealname());
    }

    @Override
    public List<String> getAllNicksName() {
      return jdbcTemplate.query("select * from Users", nicknamesRowMapper);
    }

    @Override
    public String getPasswdByUser(String user) {
        List<String> passwd = jdbcTemplate.query("select * from Users WHERE nickname = \"" + user + "\"", passwdRowWapper);
        return passwd.get(0);
    }

    @Override
    public boolean userExist(String user) {
        List<String> dUser = jdbcTemplate.query("select * from Users WHERE nickname = \"" + user + "\"" , nicknamesRowMapper);
        return dUser.size() != 0;
    }

    @Override
    public User getUser(String user) {
        List<User> users = jdbcTemplate.query("select* from Users where nickname = \"" + user + "\"", userRowMapper);
        return users.get(0);
    }
}
