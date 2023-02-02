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
    public void insertObject(String name, String owner, String bucket) {
        jdbcTemplate.update("INSERT INTO Object (name, owner, bucketSrcName) VALUES (?,?,?)", name, owner, bucket);
    }

    @Override
    public boolean objectExists(String name) {
        List<Object> objects = jdbcTemplate.query("SELECT * FROM Object WHERE name = \"" + name + "\"", objectRowMapper);
        boolean is = objects.size() != 0;
        return is;
    }

}
