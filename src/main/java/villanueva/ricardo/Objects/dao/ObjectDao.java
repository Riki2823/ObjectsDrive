package villanueva.ricardo.Objects.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface ObjectDao {
    void insertObject(String name, String owner, String bucket);

    boolean objectExists(String name);
}
