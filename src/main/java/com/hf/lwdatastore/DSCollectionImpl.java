package com.hf.lwdatastore;

import com.hf.lwdatastore.exception.AttributeNotFoundException;
import com.hf.lwdatastore.exception.IndexNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import org.apache.log4j.Logger;


/**
 *
 * @author pldorrell
 */
public class DSCollectionImpl implements DSCollection {

    private static final Logger log = Logger.getLogger(DSCollectionImpl.class);
    private Set<String> keyIndex;
    private Map<String, Map<String, Set<String>>> indexes;
    private Map<String, Object> data;
    private CollectionDescription collectionDescription;
    private boolean dirty;

    public Map<String, Object> getData() {
        return data;
    }

    public DSCollectionImpl(CollectionDescription collectionDescription) {
        log.debug("New DSCollection:"+collectionDescription.getName());
        this.collectionDescription = collectionDescription;
    }

    @Override
    public Set<String> getKeyIndex() {
        return this.keyIndex;
    }

    @Override
    public Map<String, Set<String>> getIndex(String name) {
        if (indexes != null) {
            return indexes.get(name);    
        } else {
            return null;
        }
        
    }

    @Override
    public String putObject(CollectionObject object, CollectionObjectConverter converter) {
        log.debug("Entering putObject");
        this.dirty = true;
        String id = null;
        try {
            id = converter.getValue(object.getTarget(), this.collectionDescription.getIDAttribute());
        } catch (AttributeNotFoundException e) {
            //Not able to idenitify the object
        }
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        if (this.keyIndex == null) {
            this.keyIndex = new TreeSet<String>();
        }
        this.keyIndex.add(id);
        //set the id on the object we're going to store
        converter.setId(object, id);
        object.setId(id);
        
        try {
            for (String index : this.collectionDescription.getIndexedAttributes()) {
                String value = converter.getValue(object.getTarget(), index);
                if (value == null) {
                    value = "___EMPTY___";
                }
                if (this.indexes == null) {
                    this.indexes = new HashMap<String, Map<String, Set<String>>>();
                }
                Map<String, Set<String>> indexObjects = this.indexes.get(index);
                if (indexObjects == null) {
                    indexObjects = new HashMap<String, Set<String>>();
                }

                if (indexObjects.containsKey(value)) {
                    Set<String> objectForIndex = indexObjects.get(value);
                    if (objectForIndex == null) {
                        objectForIndex = new TreeSet<String>();
                    }
                    objectForIndex.add(id);
                    indexObjects.put(value, objectForIndex);
                    this.indexes.put(index, indexObjects);
                } else {
                    Set<String> objectForIndex = new TreeSet<String>();
                    objectForIndex.add(id);
                    indexObjects.put(value, objectForIndex);
                    this.indexes.put(index, indexObjects);
                }

            }

        } catch (AttributeNotFoundException e) {
            //Not able to idenitify the object
        }

        if (this.data == null) {
            this.data = new HashMap<String, Object>();
        }
        this.data.put(id, object.getTarget());

        return null;
    }

    @Override
    public String[] putObjects(CollectionObjectConverter converter, CollectionObject... objs) {
        this.dirty = true;
        for (CollectionObject obj : objs) {
            this.putObject(obj, converter);
        }
        return null;
    }

    @Override
    public CollectionObject getObject(String key) {
        //check if data is in the data map; and if so return the value
        //check if the primary key is in the primary key set; if not return object not found
        //if if the primary key is in the primary key set and not in the data; need to pull the data from file;

        Object object = this.data.get(key);
        if (object != null) {
            CollectionObject cObject = new CollectionObject();
            cObject.setTarget(object);
            cObject.setId(key);
            return cObject;
        }

        return null;
    }

    @Override
    public CollectionObject[] getObjects(String... keys) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CollectionObject removeObject(String key) {
        this.dirty = true;
        this.keyIndex.remove(key);
        this.data.remove(key);
        this.removeKeyFromIndexes(key);
        return null;
    }
    
    protected void removeKeyFromIndexes(String key) {
       for (String indexName: this.indexes.keySet()) {
           Map<String,Set<String>> indexMap = this.indexes.get(indexName);
           if (indexMap != null) {
               for (String indexValue:indexMap.keySet()) {
                   Set<String> indexedRecords = indexMap.get(indexValue);
                   indexedRecords.remove(key);
                   this.indexes.get(indexName).put(indexValue,indexedRecords);
                   //TODO remove any index values that have no records
               }
           }
       }
    }
    
    @Override
    public CollectionObject[] removeObjects(String... keys) {
        this.dirty = true;
        return null;
    }

    @Override
    public CollectionDescription getCollectionDescription() {
        return this.collectionDescription;
    }

    @Override
    public boolean isDirty() {
        return this.dirty;
    }

    @Override
    public List<CollectionObject> getByIndex(String indexName, String indexValue) throws IndexNotFoundException {
        ArrayList<CollectionObject> objectsToReturn = new ArrayList<CollectionObject>();
        if (this.indexes == null) {
            return objectsToReturn;
        }
        Map<String, Set<String>> indexMap = this.indexes.get(indexName);
        
        if (indexMap == null) {
            throw new IndexNotFoundException();
        }
        Set<String> keysToReturn = indexMap.get(indexValue);
        if (keysToReturn != null) {
            CollectionObject object = null;
            for (String key : keysToReturn) {
                object = this.getObject(key);
                if (object != null) {
                    objectsToReturn.add(object);
                }
            }

        }
        return objectsToReturn;
    }

    @Override
    public List<CollectionObject> getObjects() {
        ArrayList<CollectionObject> cObjects = new ArrayList<CollectionObject>();
        //get all primary keys
        if (this.keyIndex != null) {
            for (String key:this.keyIndex) {
                cObjects.add(this.getObject(key));
            }            
        }
        return cObjects;
    }

    @Override
    public List<CollectionObject> getByIndex(Map<String, String> indexMap) throws IndexNotFoundException {
        Set<CollectionObject> collectionObjects = new TreeSet<CollectionObject>();
        List<CollectionObject> a1=null;
        int indexSet = 0;
        for (String key:indexMap.keySet()){           
           List<CollectionObject> objects = this.getByIndex(key, indexMap.get(key));
           if (indexSet == 0) {
               a1 = new ArrayList<CollectionObject>();
               a1.addAll(objects);
               
           } else {
               //do an intersection
               a1 = this.intersect(a1, objects);
               
           }
           indexSet++;
           //collectionObjects.addAll(objects);
       }
        
       
       //for uniqueness
       collectionObjects.addAll(a1);
       
       ArrayList<CollectionObject> collectionObjectsToReturn = new ArrayList<CollectionObject>();
       collectionObjectsToReturn.addAll(collectionObjects);
       return collectionObjectsToReturn;
    }
    
    private List<CollectionObject> intersect(List<CollectionObject> firstCollection, List<CollectionObject> secondCollection) {
        ArrayList<CollectionObject> a1 = new ArrayList<CollectionObject>();
        for (CollectionObject co_outer: secondCollection) {
            for (CollectionObject co_inner:firstCollection) {
                if (co_outer.getId().equals(co_inner.getId())){
                    a1.add(co_outer);
                }
            }
        }
        return a1;
    }
    
}
