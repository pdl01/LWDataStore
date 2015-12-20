/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hfw.lwdatastore;

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
    public String putObject(String collectionName, Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] putObjects(String collectionName, Object... objs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(String collectionName, String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] getObjects(String collectionName, String... keys) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object removeObject(String collectionName, String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] removeObjects(String collectionName, String... keys) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void synchronize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DSCollection getCollection(String collectionName) {
        return this.collections.get(collectionName);
    }
    
}
