package villanueva.ricardo.Objects.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import villanueva.ricardo.Objects.Model.Bucket;
import villanueva.ricardo.Objects.Model.User;
import villanueva.ricardo.Objects.dao.BucketDao;
import villanueva.ricardo.Objects.dao.FileDao;
import villanueva.ricardo.Objects.dao.ObjectDao;
import villanueva.ricardo.Objects.dao.UserDao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;


@Service
public class MyService {
    @Autowired
    UserDao userDao;
    @Autowired
    BucketDao bucketDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    ObjectDao objectDao;

    public void addUser(String name, String passwd, String realname) {
        User u = new User(realname, name, passwd);
        userDao.addUser(u);
    }

    public boolean nicknameExists(String user) {
        List<String> nicks = userDao.getAllNicksName();
        for (String s : nicks) {
            if (user.equals(s)) {
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


    public void uploadFileFirstTime(byte[] bytesFile, String bucket, String name, String owner) {

        String hash = getMD5(bytesFile);
        fileDao.uploadFileFirstTime(bytesFile, hash);
        objectDao.insertObject(name, owner, bucket);
    }

    public boolean objectExists(String name) {
        return objectDao.objectExists(name);
    }
    public String getSHA256(String input) {

        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    public static String getMD5(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input);
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void uploadNameUser(String nickname, String newName) {
        userDao.uploadNameUser(nickname, newName);
    }

    public void uploadPasswd(String nickname, String sha256) {
        userDao.uploadPasswd(nickname, sha256);
    }

    public void deleteUser(String nickname) {
        userDao.deleteUser(nickname);
    }
}
