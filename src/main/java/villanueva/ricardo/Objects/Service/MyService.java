package villanueva.ricardo.Objects.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import villanueva.ricardo.Objects.Model.Bucket;
import villanueva.ricardo.Objects.Model.User;
import villanueva.ricardo.Objects.dao.BucketDao;
import villanueva.ricardo.Objects.dao.UserDao;

import java.util.List;

@Service
public class MyService {
    @Autowired
    UserDao userDao;
    @Autowired
    BucketDao bucketDao;

    public void addUser(String name, String passwd, String realname){
        User u = new User(realname, name, passwd);
        userDao.addUser(u);
    }

    public boolean nicknameExists(String user) {
        List<String> nicks = userDao.getAllNicksName();
        for(String s: nicks){
            if (user.equals(s)){
            return true;
            }
        }
        return false;
    }

    public String getPasswdByUser(String user) {
        return userDao.getPasswdByUser(user);
    }

    public boolean userExist(String user) {
        return userDao.userExist(user);
    }

    public User getUser(String user) {
        return userDao.getUser(user);
    }

    public List<Bucket> getBucketsByUser(String username) {
        return bucketDao.getBucketsByUser(username);
    }

    public void addBucket(String bucketName, String userName) {
        bucketDao.addBucket(bucketName, userName);
    }
}
