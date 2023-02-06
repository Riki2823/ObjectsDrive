package villanueva.ricardo.Objects.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import villanueva.ricardo.Objects.Model.Object;

import java.util.List;

@Repository
public class ObjectDaoImpl implements ObjectDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<Object> objectRowMapper = (rs, rn ) -> {
        return new Object(rs.getInt("id"), rs.getString("name"), rs.getString("owner"), rs.getString("bucketSrcName"));
    };
    @Override
    public void insertObject(String name, String owner, String bucket, String uriR) {
        jdbcTemplate.update("INSERT INTO Object (name, owner, bucketSrcName, uri) VALUES (?,?,?,?)", name, owner, bucket, uriR);
    }

    @Override
    public boolean objectExists(String uri) {
        List<Object> objects = jdbcTemplate.query("SELECT * FROM Object WHERE uri = \"" + uri + "\"", objectRowMapper);
        boolean is = objects.size() != 0;
        return is;
    }
}
