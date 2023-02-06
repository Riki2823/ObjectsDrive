package villanueva.ricardo.Objects.dao;

import org.springframework.stereotype.Repository;
import villanueva.ricardo.Objects.Model.File;

@Repository
public interface FileDao {

    void uploadFileFirstTime(byte[] bytesFile, String hash);

    File getFile(String hash);

    void newVersion(byte[] bytesFile, int idObj, int idFile);
}
