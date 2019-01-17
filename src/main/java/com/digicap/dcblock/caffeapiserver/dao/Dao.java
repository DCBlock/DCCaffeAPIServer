package com.digicap.dcblock.caffeapiserver.dao;

import java.util.List;

public interface Dao<T> {
    
    int save(T t);

    List<T> getAll();

    int update(T t, String[] params);

    int delete(T t);

//    Optional<T> get(long id);
}
