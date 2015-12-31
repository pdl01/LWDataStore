package com.hf.lwdatastore;

import com.hf.lwdatastore.exception.CollectionNotFoundException;
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
    public CollectionObject getObject(String collectionName,String key)  throws CollectionNotFoundException;
    public CollectionObject[] getObjects(String collectionName,String...keys);
    public CollectionObject removeObject(String collectionName,String key);
    public CollectionObject[] removeObjects(String collectionName,String...keys);
    public void synchronize();
    public void shutdown();
}
