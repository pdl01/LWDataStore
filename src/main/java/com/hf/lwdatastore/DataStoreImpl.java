
package com.hf.lwdatastore;

import com.hf.lwdatastore.exception.CollectionNotFoundException;
import com.hf.lwdatastore.exception.IndexNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author pldorrell
 */
public class DataStoreImpl implements DataStore{
    private static final Logger log = Logger.getLogger(DataStoreImpl.class);
    private Map<String,DSCollection> collections;
    
    private static DataStoreImpl instance  = null;
    protected DataStoreImpl () {
        
    }
    
    public static DataStoreImpl getInstance() {
        if (instance == null) {
            instance = new DataStoreImpl();
        }
        return instance;
    } 
    
    @Override
    public void createCollection(String collectionName, CollectionDescription collectionDescription,Properties properties) {
        log.debug("createCollection:"+collectionName);
        if (this.collections == null) {
            this.collections = new HashMap<String,DSCollection>();
        }
       DSCollection collection = new DSCollectionImpl(collectionDescription);
       this.collections.put(collectionName, collection);
        log.debug("leaving createCollection:"+collectionName);
    }

    @Override
    public String putObject(String collectionName, CollectionObject obj,CollectionObjectConverter converter) throws CollectionNotFoundException{
        if (this.collections.containsKey(collectionName)) {
            this.collections.get(collectionName).putObject(obj,converter);                
        } else {
            throw new CollectionNotFoundException();
        }

        return "";
    }
    
    
    @Override
    public String[] putObjects(String collectionName, CollectionObject... objs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CollectionObject getObject(String collectionName, String key) throws CollectionNotFoundException {
        if (this.collections.containsKey(collectionName)) {
            CollectionObject cObject = this.collections.get(collectionName).getObject(key);                
            return cObject;
        } else {
            throw new CollectionNotFoundException();
        }

    }

    @Override
    public CollectionObject[] getObjects(String collectionName, String... keys) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CollectionObject removeObject(String collectionName, String key)  throws CollectionNotFoundException {
        if (this.collections.containsKey(collectionName)) {
            CollectionObject cObject = this.collections.get(collectionName).removeObject(key);                
            return cObject;
        } else {
            throw new CollectionNotFoundException();
        }
    }

    @Override
    public CollectionObject[] removeObjects(String collectionName, String... keys) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void synchronize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void shutdown() {
        this.synchronize();
    }
    @Override
    public DSCollection getCollection(String collectionName) {
        return this.collections.get(collectionName);
    }

    @Override
    public List<CollectionObject> getByIndex(String collectionName, String indexName, String indexValue) throws IndexNotFoundException,CollectionNotFoundException {
        if (this.collections.containsKey(collectionName)) {
            List<CollectionObject> cObjects = this.collections.get(collectionName).getByIndex(indexName, indexValue);                
            return cObjects;
        } else {
            throw new CollectionNotFoundException();
        }

    }

    @Override
    public List<CollectionObject> getObjects(String collectionName) throws CollectionNotFoundException {
        if (this.collections.containsKey(collectionName)) {
            List<CollectionObject> cObjects = this.collections.get(collectionName).getObjects();                
            return cObjects;
        } else {
            throw new CollectionNotFoundException();
        }    
    }

    @Override
    public List<CollectionObject> getByIndex(String collectionName, Map<String, String> indexMap) throws IndexNotFoundException,CollectionNotFoundException {
        if (this.collections.containsKey(collectionName)) {
            List<CollectionObject> cObjects = this.collections.get(collectionName).getByIndex(indexMap);
            return cObjects;
        } else {
            throw new CollectionNotFoundException();
        }    

    }
    
}
