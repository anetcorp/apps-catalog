package com.nvidia.resources.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nvidia.resources.dao.GenericDao;
import com.nvidia.resources.service.GenericService;

/**
 * Service Class for GENERICE Service
 * 
 * @author SUBODH
 *
 */
@Service
public class GenericServiceImpl<T, P extends Serializable> implements GenericService<T, P> {
	private GenericDao<T, P> genericDao;

	public GenericServiceImpl() {
		// Default Constructor
	}

	public GenericServiceImpl(GenericDao<T, P> genericDao) {
		this.genericDao = genericDao;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T create(T entity) {
		return genericDao.create(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T findOne(P id) {
		return genericDao.findOne(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<T> findAll() {
		return genericDao.findAll();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T update(T entity) {
		return genericDao.update(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(T entity) {
		genericDao.delete(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteById(P id) {
		genericDao.deleteById(id);
	}
}
