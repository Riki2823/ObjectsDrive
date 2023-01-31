package villanueva.ricardo.Objects.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileDaoImpl implements FileDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void uploadFileFirstTime(byte[] bytesFile, String hash) {
        jdbcTemplate.update("INSERT INTO File (content, hash) VALUES (?,?)", bytesFile, hash);
    }
}
