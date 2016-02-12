package com.hf.lwdatastore;

import com.hf.lwdatastore.exception.IndexNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author pldorrell
 */
public interface DSCollection {
    public CollectionDescription getCollectionDescription();
    public String putObject(CollectionObject obj,CollectionObjectConverter converter);
    public String[] putObjects(CollectionObjectConverter converter,CollectionObject...objs);
    public CollectionObject getObject(String key);
    public CollectionObject[] getObjects(String...keys);
    public List<CollectionObject> getObjects();
    public CollectionObject removeObject(String key);
    public CollectionObject[] removeObjects(String...keys);
    public Set<String> getKeyIndex();
    public Map<String,Set<String>> getIndex(String name);
    public boolean isDirty();
    public Map<String,Object> getData();
    public List<CollectionObject> getByIndex(String indexName,String indexValue) throws IndexNotFoundException;
    public List<CollectionObject> getByIndex(Map<String,String> indexes) throws IndexNotFoundException;
}
