package villanueva.ricardo.Objects.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface FileDao {

    void uploadFileFirstTime(byte[] bytesFile, String hash);
}
