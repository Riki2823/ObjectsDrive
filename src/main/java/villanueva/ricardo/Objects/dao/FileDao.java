package villanueva.ricardo.Objects.dao;

import org.springframework.stereotype.Repository;
import villanueva.ricardo.Objects.Model.File;
import villanueva.ricardo.Objects.Model.Version;

import java.util.List;

@Repository
public interface FileDao {

    void uploadFileFirstTime(byte[] bytesFile, String hash);

    File getFile(String hash);

    void newVersion(int idObj, int idFile);

    String getHashLastVersion(String uriR, int idObj);

    List<Version> getVersions(int objId);
}
