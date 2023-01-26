package villanueva.ricardo.Objects.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import villanueva.ricardo.Objects.Model.Bucket;

import java.util.List;

@Repository
public class BucketDaoImpl implements BucketDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<Bucket> bucketsRowMapper = (rs, rn) -> {
        return new Bucket(rs.getString("name"), rs.getString("owner") );
    };

    @Override
    public List<Bucket> getBucketsByUser(String username) {
        return jdbcTemplate.query("SELECT * FROM Bucket WHERE owner=\"" + username + "\"", bucketsRowMapper);
    }

    @Override
    public void addBucket(String bucketName, String userName) {
        jdbcTemplate.update("INSERT INTO Bucket (owner, name) VALUES(?,?)", userName, bucketName);
    }
}
