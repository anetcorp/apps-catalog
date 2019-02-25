package com.nvidia.resources.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for GENERIC DAO
 * 
 * @author Satnam Singh
 * @since 1.0
 *
 */
public interface GenericDao<T, P extends Serializable> {
	T create(T t);

	T findOne(P id);

	List<T> findAll();

	T update(T t);

	void delete(T t);

	void deleteById(P id);
}