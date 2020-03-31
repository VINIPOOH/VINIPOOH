package db.maper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface Mapper<E> {
    void insertStatementMapper(E entity, PreparedStatement preparedStatement) throws SQLException;

    void updateStatementMapper(E entity, PreparedStatement preparedStatement) throws SQLException;

    Optional<E> mapResultSetToEntity(ResultSet resultSet) throws SQLException;
}
