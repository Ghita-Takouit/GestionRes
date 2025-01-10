package dao;

import java.util.List;

public interface IDAO<T>{
    public boolean create(T obj);
    public boolean update(T obj);
    public boolean delete(int id);
    public T findById(int id);
    public List<T> findAll();

}
