package villanueva.ricardo.Objects.dao;

import org.springframework.stereotype.Repository;
import villanueva.ricardo.Objects.Model.Bucket;

import java.util.List;
@Repository
public interface BucketDao {
    List<Bucket> getBucketsByUser(String username);

    void addBucket(String bucketName, String userName);

    String getOwnerBucket(String bucket);

    boolean bucketExists(String bucketName);

    void deleteBucket(String bucket);
}
