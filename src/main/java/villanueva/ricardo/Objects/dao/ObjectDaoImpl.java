package villanueva.ricardo.Objects.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import villanueva.ricardo.Objects.Model.MyObject;

import java.util.List;

@Repository
public class ObjectDaoImpl implements ObjectDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<MyObject> objectRowMapper = (rs, rn ) -> {
        return new MyObject(rs.getString("name"));
    };
    @Override
    public void insertObject(String name, String owner, String bucket) {
        jdbcTemplate.update("INSERT INTO Object (name, owner, bucketSrcName) VALUES (?,?,?)", name, owner, bucket);
    }

    @Override
    public boolean objectExists(String name) {
        List<MyObject> objects = jdbcTemplate.query("SELECT * FROM Object WHERE name = \"" + name + "\"", objectRowMapper);
        boolean is = objects.size() != 0;
        return is;
    }
}
