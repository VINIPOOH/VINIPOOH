package db.dao;


import db.factory.DBConnector;
import exeptions.DBRuntimeException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<E> implements CrudRepository<E, Integer> {

    private DBConnector connector;

    private final Mapper<E> mapper;
    private final String saveQuery;
    private final String findByIdQuery;
    private final String findAllQuery;
    private final String updateQuery;
    private final String deleteQuery;

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
    public boolean save(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveQuery)) {

            mapper.insertStatementMapper(entity, preparedStatement);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DBRuntimeException();
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

    protected List<E> findAllById(Integer id, String query, Integer offset, Integer limit) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);

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

    public Optional<Integer> saveAndReturnId(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveQuery, Statement.RETURN_GENERATED_KEYS)) {

            mapper.insertStatementMapper(entity, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            return resultSet.next() ? Optional.of(resultSet.getInt(1)) : Optional.empty();
        } catch (SQLException e) {
            throw new DBRuntimeException();
        }
    }

    protected List<E> findAllByStringParam(String parameter, String query, Integer offset, Integer limit) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, parameter);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);
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

    protected Optional<E> findByStringParam(String param, String query) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, param);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? mapper.mapResultSetToEntity(resultSet) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBRuntimeException();
        }
    }

    protected Integer count(String query) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next() ? resultSet.getInt(1) : 0;
        } catch (SQLException e) {
            throw new DBRuntimeException();
        }
    }

    protected Integer countByIntegerParam(String query, Integer parameter) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, parameter);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next() ? resultSet.getInt(1) : 0;
        } catch (SQLException e) {
            throw new DBRuntimeException();
        }
    }

    protected Integer countByStringParam(String query, String parameter) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, parameter);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next() ? resultSet.getInt(1) : 0;
        } catch (SQLException e) {
            throw new DBRuntimeException();
        }
    }
}
