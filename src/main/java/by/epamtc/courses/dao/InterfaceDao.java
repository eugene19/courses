package by.epamtc.courses.dao;

import java.util.List;

public interface InterfaceDao<T> {
    List<T> getAll() throws DaoException;

    T getById(int id) throws DaoException;

    void update(T ob) throws DaoException;

    int insert(T ob) throws DaoException;

    void delete(int id) throws DaoException;
}
