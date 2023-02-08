package villanueva.ricardo.Objects.dao;

import org.springframework.stereotype.Repository;
import villanueva.ricardo.Objects.Model.Object;

import java.util.List;

@Repository
public interface ObjectDao {
    void insertObject(String name, String owner, String bucket, String uriR);

    boolean objectExists(String name);

    Object getObject(String uriR);

    List<Object> getAllBucketObjects(String bucket);

    Object getObjectById(String objid);

    void deleteObject(String uri);

    Object getOBjectByUri(String uri);
}
