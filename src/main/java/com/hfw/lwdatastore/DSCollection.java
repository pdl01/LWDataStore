package com.hfw.lwdatastore;

import java.util.Set;

/**
 *
 * @author pldorrell
 */
public interface DSCollection {
    public CollectionDescription getCollectionDescription();
    public String putObject(Object obj);
    public String[] putObjects(Object...objs);
    public Object getObject(String key);
    public Object[] getObjects(String...keys);
    public Object removeObject(String key);
    public Object[] removeObjects(String...keys);
    public Set<String> getKeyIndex();
    public Set<IndexObject> getIndex(String name);    
    
}
