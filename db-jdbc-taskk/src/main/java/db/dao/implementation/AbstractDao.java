package db.dao.implementation;


import db.dao.CrudRepository;
import db.factory.DBConnector;
import db.maper.Mapper;
import exeptions.DBRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<E> implements CrudRepository<E, Integer> {

    private final Mapper<E> mapper;
    private final String saveQuery;
    private final String findByIdQuery;
    private final String findAllQuery;
    private final String updateQuery;
    private final String deleteQuery;
    private DBConnector connector;

    protected AbstractDao(DBConnector connector, Mapper<E> mapper, String saveQuery, String findByIdQuery, String findAllQuery,
                          String updateQuery, String deleteQuery) {
        this.connector = connector;
        this.mapper = mapper;
        this.saveQuery = saveQuery;
        this.findByIdQuery = findByIdQuery;
        this.findAllQuery = findAllQuery;
        this.updateQuery = updateQuery;
        this.deleteQuery = deleteQuery;
    }

    @Override
    public boolean save(E entity) throws SQLException {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveQuery)) {

            mapper.insertStatementMapper(entity, preparedStatement);
            return preparedStatement.executeUpdate() != 0;
        }
    }

    @Override
    public Optional<E> findById(Integer id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByIdQuery)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? mapper.mapResultSetToEntity(resultSet) : Optional.empty();
        } catch (SQLException e) {
            throw new DBRuntimeException();
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DBRuntimeException();
        }
    }

    @Override
    public List<E> findAll(Integer offset, Integer limit) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery)) {

            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, limit);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<E> result = new ArrayList<>();

            while (resultSet.next()) {
                mapper.mapResultSetToEntity(resultSet).ifPresent(result::add);
            }
            return result;
        } catch (SQLException e) {
            throw new DBRuntimeException();
        }
    }

    @Override
    public boolean update(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            mapper.updateStatementMapper(entity, preparedStatement);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DBRuntimeException();
        }
    }


}
