package learn.myCookbook.data.mappers;

import learn.myCookbook.models.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewMapper implements RowMapper<Review> {

    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

}
