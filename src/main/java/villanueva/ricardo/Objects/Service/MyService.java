package villanueva.ricardo.Objects.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import villanueva.ricardo.Objects.Model.*;
import villanueva.ricardo.Objects.Model.Object;
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


    public void uploadFileFirstTime(byte[] bytesFile, String bucket, String name, String owner, String uri, User u) {

        String hash = getMD5(bytesFile);
        String uriR = uri.replace("/objects", "") + "/" + name;

        fileDao.uploadFileFirstTime(bytesFile, hash);
        objectDao.insertObject(name, owner, bucket, uriR);

        int idFile = fileDao.getFile(hash).getId();
        int idObj = objectDao.getObject(uriR).getId();
        fileDao.newVersion(idObj, idFile);
    }

    public boolean objectExists(String name, String uri) {
        String urir = uri.replace("/objects", "") + "/" + name;
        return objectDao.objectExists(urir);
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

    public String getOwnerBucket(String bucket) {
        return bucketDao.getOwnerBucket(bucket);
    }

    public boolean bucketExists(String bucketName) {
        return bucketDao.bucketExists(bucketName);
    }

    public void deleteBucket(String bucket) {
        bucketDao.deleteBucket(bucket);
    }

    public void checkVersion(byte[] bytesFile, User user, String uri, String name) {
        String uriR = uri.replace("/objects", "") + "/" + name;
        String hashLastVersion = fileDao.getHashLastVersion(uriR, objectDao.getObject(uriR).getId());
        String actualHash = getMD5(bytesFile);

        if (!hashLastVersion.equals(actualHash)){
            fileDao.uploadFileFirstTime(bytesFile, actualHash);
            fileDao.newVersion(objectDao.getObject(uriR).getId(), fileDao.getFile(actualHash).getId());
        }

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

    public List<Object> getAllBucketObjects(String bucket) {
        return objectDao.getAllBucketObjects(bucket);

    }

    public Object getObject(String object) {
        return objectDao.getObject(object);
    }

    public List<Version> getAllVersions(int objId) {
        return fileDao.getVersions(objId);
    }

    public File getFileById(String fid) {
        return fileDao.getFilebyId(fid);
    }

    public Object getObjectById(String objid) {
       return objectDao.getObjectById(objid);
    }
}
