package com.trading.server;




import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Embedded;
import javax.persistence.Transient;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.files.AppEngineFile;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyOpts;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.util.DAOBase;

import com.trading.server.datamodel.GUser;
import com.trading.server.datamodel.Market;
import com.trading.server.datamodel.Stock;
import com.trading.server.datamodel.TThread;

public class Dao<T> extends DAOBase
{

	static final int BAD_MODIFIERS = Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT;

	static
	{
		// Register all your entity classes here
		
		ObjectifyService.register(GUser.class);
		ObjectifyService.register(TThread.class);
		ObjectifyService.register(Market.class);
		ObjectifyService.register(Stock.class);
		
	}


	
	protected Class<T> clazz;

	/**
	 * We've got to get the associated domain class somehow
	 *
	 * @param clazz
	 */
	protected Dao(Class<T> clazz)
	{
		this.clazz = clazz;
	}

	public Dao(ObjectifyOpts opts) {
		super(opts);
		// TODO Auto-generated constructor stub
	}

	public Key<T> put(T entity)

	{
		return ofy().put(entity);
	}

	public Map<Key<T>, T> putAll(Iterable<T> entities)
	{
		return ofy().put(entities);
	}

	public void delete(T entity)
	{
		ofy().delete(entity);
	}

	public void deleteKey(Key<T> entityKey)
	{
		ofy().delete(entityKey);
	}

	public void deleteAll(Iterable<T> entities)
	{
		ofy().delete(entities);
	}

	public void deleteKeys(Iterable<Key<T>> keys)
	{
		ofy().delete(keys);
	}

	public T get(Long id) 
	{
		return ofy().get(this.clazz, id);
	}
	
	public T getRN(Long id) 
	{
		T o;
		try {
			o = ofy().get(this.clazz, id);
		} catch (NotFoundException e) {
			return null;
		}
		return o;
	}
	
	public T getRN(String id) 
	{
		T o;
		try {
			o = ofy().get(this.clazz, id);
		} catch (NotFoundException e) {
			return null;
		}
		return o;
	}

	public T get(Key<T> key) 
	{
		return ofy().get(key);
	}
	
	public T getRN(Key<T> key) 
	{
		T o;
		try {
			o = ofy().get(key);
		} catch (NotFoundException e) {
			return null;
		}
		
		return o;
	}

	/**
	 * Convenience method to get all objects matching a single property
	 *
	 * @param propName
	 * @param propValue
	 * @return T matching Object
	 */
	public T getByProperty(String propName, Object propValue)
	{
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return q.get();
	}

	public List<T> listByProperty(String propName, Object propValue)
	{
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return asList(q.iterator());
	}
	
	public Query<T> getQByProperty(String propName, Object propValue)
	{
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return q;
	}

	public List<Key<T>> listKeysByProperty(String propName, Object propValue)
	{
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return asKeyList(q.fetchKeys());
	}

//	public T getByExample(T exampleObj)
//	{
//		Query<T> queryByExample = buildQueryByExample(exampleObj);
//		Iterable<T> iterableResults = queryByExample.fetch();
//		Iterator<T> i = iterableResults.iterator();
//		T obj = i.next();
//		if (i.hasNext())
//			throw new RuntimeException("Too many results");
//		return obj;
//	}

//	public List<T> listByExample(T exampleObj)
//	{
//		Query<T> queryByExample = buildQueryByExample(exampleObj);
//		return asList(queryByExample.fetch());
//	}

	private List<T> asList(QueryResultIterator<T> queryResultIterator)
	{
		ArrayList<T> list = new ArrayList<T>();
		while( queryResultIterator.hasNext())
		{
			list.add(queryResultIterator.next());
		}
		return list;
	}

	private List<Key<T>> asKeyList(Iterable<Key<T>> iterableKeys)
	{
		ArrayList<Key<T>> keys = new ArrayList<Key<T>>();
		for (Key<T> key : iterableKeys)
		{
			keys.add(key);
		}
		return keys;
	}

	private Query<T> buildQueryByExample(T exampleObj)
	{
		Query<T> q = ofy().query(clazz);

		// Add all non-null properties to query filter
		for (Field field : clazz.getDeclaredFields())
		{
			// Ignore transient, embedded, array, and collection properties
			if (field.isAnnotationPresent(Transient.class)
				|| (field.isAnnotationPresent(Embedded.class))
				|| (field.getType().isArray())
				|| (Collection.class.isAssignableFrom(field.getType()))
				|| ((field.getModifiers() & BAD_MODIFIERS) != 0))
				continue;

			field.setAccessible(true);

			Object value;
			try
			{
				value = field.get(exampleObj);
			}
			catch (IllegalArgumentException e)
			{
				throw new RuntimeException(e);
			}
			catch (IllegalAccessException e)
			{
				throw new RuntimeException(e);
			}
			if (value != null)
			{
				q.filter(field.getName(), value);
			}
		}

		return q;
	}
	public Query<T> getQuery(){
		return ofy().query(clazz);
	}
	
	public <V> QueryResultIterator<T> getChildren(Key<V> parent, Class<T> clazz, String filt, Object val){
		
		  if(parent == null) throw new IllegalArgumentException();
		  
		  
		  return ofy().query(clazz).ancestor(parent).filter(filt, val).iterator();
		}
	
	public <V> T getChild(Key<V> parent, Class<T> clazz, String filt, Object val){
		
		  if(parent == null) throw new IllegalArgumentException();
		  
		  
		  return ofy().query(clazz).ancestor(parent).filter(filt, val).get();
		}
}
