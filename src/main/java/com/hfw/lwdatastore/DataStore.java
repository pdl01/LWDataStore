package com.hfw.lwdatastore;

import java.util.Properties;

/**
 *
 * @author pldorrell
 */
public interface DataStore {
    public void createCollection(String collectionName,CollectionDescription collectionDescription,Properties properties);
    public DSCollection getCollection(String collectionName);
    public String putObject(String collectionName,Object obj);
    public String[] putObjects(String collectionName,Object...objs);
    public Object getObject(String collectionName,String key);
    public Object[] getObjects(String collectionName,String...keys);
    public Object removeObject(String collectionName,String key);
    public Object[] removeObjects(String collectionName,String...keys);
    public void synchronize();
}
