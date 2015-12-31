/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.lwdatastore;

import com.hf.lwdatastore.exception.CollectionNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author pldorrell
 */
public class DataStoreImpl implements DataStore{
    private Map<String,DSCollection> collections;
    
    
    
    @Override
    public void createCollection(String collectionName, CollectionDescription collectionDescription,Properties properties) {
        if (this.collections == null) {
            this.collections = new HashMap<String,DSCollection>();
        }
       DSCollection collection = new DSCollectionImpl(collectionDescription);
       this.collections.put(collectionName, collection);
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
    public CollectionObject removeObject(String collectionName, String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
