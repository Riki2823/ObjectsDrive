package villanueva.ricardo.Objects.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface ObjectDao {
    void insertObject(String name, String owner, String bucket, String uriR);

    boolean objectExists(String name);
}
