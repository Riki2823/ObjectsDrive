package villanueva.ricardo.Objects.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import villanueva.ricardo.Objects.Model.File;
import villanueva.ricardo.Objects.Model.Object;

import java.util.List;

@Repository
public class FileDaoImpl implements FileDao{
    @Autowired
    JdbcTemplate jdbcTemplate;


    private final RowMapper<File> fileRowMapper = (rs, rn ) -> {
        return new File(rs.getInt("id"), rs.getBytes("content"), rs.getString("hash"));
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
    public void newVersion(byte[] bytesFile, int idObj, int idFile) {
        jdbcTemplate.update("INSERT INTO FileVersion (idFile, idObj, content) VALUES (?,?,?)", idFile, idObj, bytesFile);
    }

}
