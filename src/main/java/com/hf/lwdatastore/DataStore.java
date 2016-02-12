package com.hf.lwdatastore;

import com.hf.lwdatastore.exception.CollectionNotFoundException;
import com.hf.lwdatastore.exception.IndexNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author pldorrell
 */
public interface DataStore {
    public void createCollection(String collectionName,CollectionDescription collectionDescription,Properties properties);
    public DSCollection getCollection(String collectionName);
    
    public String putObject(String collectionName,CollectionObject obj,CollectionObjectConverter converter) throws CollectionNotFoundException;
    public String[] putObjects(String collectionName,CollectionObject...objs);
    public CollectionObject getObject(String collectionName,String key) throws CollectionNotFoundException;
    public CollectionObject[] getObjects(String collectionName,String...keys);
    public CollectionObject removeObject(String collectionName,String key) throws CollectionNotFoundException;
    public CollectionObject[] removeObjects(String collectionName,String...keys);
    public List<CollectionObject> getByIndex(String collectionName,String indexName,String indexValue) throws IndexNotFoundException,CollectionNotFoundException;
    public List<CollectionObject> getByIndex(String collectionName,Map<String,String> indexMap) throws IndexNotFoundException,CollectionNotFoundException;
    public List<CollectionObject> getObjects(String collectionName) throws CollectionNotFoundException;
    public void synchronize();
    public void shutdown();
}
