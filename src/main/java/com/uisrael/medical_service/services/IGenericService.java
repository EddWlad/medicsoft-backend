package com.uisrael.medical_service.services;

import java.util.List;
public interface IGenericService<T,ID>{
    List<T> findAll() throws Exception;
    T update(T t, ID id) throws Exception;
    T findById(ID id) throws Exception;
    T save(T t) throws Exception;
    public boolean delete (ID id) throws Exception;
}
