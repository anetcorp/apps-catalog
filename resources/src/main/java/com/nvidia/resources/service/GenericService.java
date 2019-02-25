package com.nvidia.resources.service;

import java.io.Serializable;
import java.util.List;

/**
 * This interface contains Generic Services related to <T>
 * 
 * @author Satnam Singh
 * @since 1.0
 */
public interface GenericService<T, P extends Serializable> {
	T create(T t);

	T findOne(P id);

	List<T> findAll();

	T update(T t);

	void delete(T t);

	void deleteById(P id);

}
