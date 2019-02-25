package com.nvidia.resources.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.nvidia.resources.dao.GenericDao;

/**
 * Class who implements GenericDao
 * 
 * @author Satnam Singh
 * @since 1.0
 *
 */
public abstract class GenericDaoJpaImpl<T, P extends Serializable> implements GenericDao<T, P> {

	protected Class<T> clazz;

	@PersistenceContext
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public GenericDaoJpaImpl() {
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Method is used to push the data in database
	 * 
	 * @param Entity
	 */
	public T create(T t) {
		this.entityManager.persist(t);
		return t;
	}

	/**
	 * Method is used to find the data in table by id
	 * 
	 * @param id
	 */
	public T findOne(P id) {
		return this.entityManager.find(clazz, id);
	}

	/**
	 * Method is used to find all the data in table
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return entityManager.createQuery("from " + clazz.getName()).getResultList();
	}

	/**
	 * Method is used to update the data in table
	 * 
	 * @param Entity
	 */
	public T update(T t) {
		return this.entityManager.merge(t);
	}

	/**
	 * Method is used to delete the data in table
	 * 
	 * @param Entity
	 */
	public void delete(T t) {
		this.entityManager.remove(this.entityManager.merge(t));
	}

	/**
	 * Method is used to delete the data in table by id
	 * 
	 * @param id
	 */
	public void deleteById(P id) {
		T entity = findOne(id);
		delete(entity);
	}
}