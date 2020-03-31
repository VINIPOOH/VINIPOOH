package db.dao.implementation;

import db.factory.DBConnector;
import db.maper.Mapper;
import entity.User;

public class UserDao extends AbstractDao<User> implements UserDaoConstants {

    public UserDao(DBConnector connector, Mapper<User> mapper, String saveQuery, String findByIdQuery, String findAllQuery, String updateQuery, String deleteQuery) {
        super(connector, mapper, UserDaoConstants.SAVE_QUERY, findByIdQuery, findAllQuery, updateQuery, deleteQuery);
    }

}
