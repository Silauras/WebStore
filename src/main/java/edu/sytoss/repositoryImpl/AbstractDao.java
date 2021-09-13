package edu.sytoss.repositoryImpl;

import edu.sytoss.config.HibernateConfiguration;
import edu.sytoss.model.product.Product;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class AbstractDao<PrimaryKey extends Serializable, T> {
    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.persistentClass = (Class<T>)((ParameterizedType)
                        this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @PersistenceContext
    EntityManager entityManager;

    protected EntityManager getEntityManager(){
        return this.entityManager;
    }

    protected T getByKey(PrimaryKey key){
        return (T) entityManager.find(persistentClass, key);
    }

    protected List<T> getAll(Class<T> persistentClass){
        return (List<T>) getEntityManager()
                .createQuery("from "+ persistentClass.getName())
                .getResultList();
    }
    protected void persist(T entity){
        entityManager.persist(entity);
    }
    protected void update(T entity){
        entityManager.merge(entity);
    }

    protected void delete(T entity){
        entityManager.remove(entity);
    }
}
