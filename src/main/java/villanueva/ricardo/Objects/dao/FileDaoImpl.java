package villanueva.ricardo.Objects.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import villanueva.ricardo.Objects.Model.File;
import villanueva.ricardo.Objects.Model.Version;

import java.util.List;

@Repository
public class FileDaoImpl implements FileDao{
    @Autowired
    JdbcTemplate jdbcTemplate;


    private final RowMapper<File> fileRowMapper = (rs, rn ) -> {
        return new File(rs.getInt("id"), rs.getBytes("content"), rs.getString("hash"));
    };

    private final RowMapper<Version> versionRowMapper = (rs, rn) ->{
        return new Version(rs.getInt("idrow"), rs.getInt("idFile"), rs.getInt("idObj"), rs.getString("fechaMod"));
    };
    @Override
    public void uploadFileFirstTime(byte[] bytesFile, String hash) {
        jdbcTemplate.update("INSERT INTO File (content, hash) VALUES (?,?)", bytesFile, hash);
    }

    @Override
    public File getFile(String hash) {
        List<File> files = jdbcTemplate.query("SELECT * FROM File WHERE hash=\""+ hash + "\"", fileRowMapper);
        return files.get(0);
    }

    @Override
    public void newVersion(int idObj, int idFile) {
        jdbcTemplate.update("INSERT INTO FileVersion (idFile, idObj) VALUES (?,?)", idFile, idObj);
    }

    @Override
    public String getHashLastVersion(String uriR, int idObj) {
         List<Version> versions = jdbcTemplate.query("SELECT * FROM FileVersion WHERE idObj=\"" + idObj + "\" ORDER BY fechaMod DESC", versionRowMapper);
         List<File> files = jdbcTemplate.query("SELECT * FROM File WHERE id=\"" + versions.get(0).getIdFile() + "\"", fileRowMapper);

         return files.get(0).getHash();
    }

    @Override
    public List<Version> getVersions(int objId) {
        return jdbcTemplate.query("SELECT * FROM FileVersion WHERE idObj=\"" + objId + "\" ORDER BY fechaMod DESC", versionRowMapper);
    }

}
