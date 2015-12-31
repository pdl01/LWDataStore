/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.lwdatastore;

import com.hf.lwdatastore.exception.AttributeNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.logging.Logger;

/**
 *
 * @author pldorrell
 */
public class DSCollectionImpl implements DSCollection {
    
    private Set<String> keyIndex;
    private Map<String,Map<String,Set<String>>> indexes;
    private Map<String,Object> data;
    private CollectionDescription collectionDescription;
    private boolean dirty;

    public Map<String, Object> getData() {
        return data;
    }
    
    public DSCollectionImpl(CollectionDescription collectionDescription) {
        this.collectionDescription = collectionDescription;
    }
    
    @Override
    public Set<String> getKeyIndex() {
        return this.keyIndex;
    }
    @Override
    public Map<String,Set<String>> getIndex(String name) {
        return indexes.get(name);
    }
    
    @Override
    public String putObject(CollectionObject object,CollectionObjectConverter converter) {
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
                
        try {
            for (String index: this.collectionDescription.getIndexedAttributes()) {
                String value = converter.getValue(object.getTarget(), index);
                if (this.indexes == null) {
                    this.indexes = new HashMap<String,Map<String,Set<String>>>();
                }
                Map<String,Set<String>> indexObjects = this.indexes.get(index);
                if (indexObjects == null) {
                    indexObjects = new HashMap<String,Set<String>>();
                }
                
                if (indexObjects.containsKey(value)) {
                    Set<String> objectForIndex = indexObjects.get(value);
                    if (objectForIndex == null) {
                        objectForIndex = new TreeSet<String>();
                    }
                    objectForIndex.add(id);
                    indexObjects.put(value, objectForIndex);
                    this.indexes.put(index,indexObjects);
                } else {
                    Set<String> objectForIndex = new TreeSet<String>();
                    objectForIndex.add(id);
                    indexObjects.put(value, objectForIndex);
                    this.indexes.put(index,indexObjects);
                }
                
            }
            
        } catch (AttributeNotFoundException e) {
            //Not able to idenitify the object
        }
        
        if (this.data == null) {
            this.data = new HashMap<String,Object>();
        }
        this.data.put(id, object.getTarget());
        
        return null;
    }

    @Override
    public String[] putObjects(CollectionObjectConverter converter,CollectionObject... objs) {
        this.dirty = true;
        for (CollectionObject obj: objs) {
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
        return null;
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
    
}
