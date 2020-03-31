package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface Mapper<E> {
    abstract void insertStatementMapper(E entity, PreparedStatement preparedStatement) throws SQLException;

    abstract void updateStatementMapper(E entity, PreparedStatement preparedStatement) throws SQLException;

    abstract Optional<E> mapResultSetToEntity(ResultSet resultSet) throws SQLException;
}
