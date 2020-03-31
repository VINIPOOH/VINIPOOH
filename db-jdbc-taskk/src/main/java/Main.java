import db.dao.implementation.UserDao;
import db.factory.DBConnector;
import db.maper.implementation.UserMapper;
import entity.User;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        // write your code here
        new UserDao(new DBConnector(), new UserMapper(), null, null, null, null, null)
                .save(User.builder()
                        .email("testQ")
                        .password("testQ")
                        .build());
    }
}
