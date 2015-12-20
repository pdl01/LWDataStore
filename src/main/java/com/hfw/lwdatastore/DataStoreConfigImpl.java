/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hfw.lwdatastore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pldorrell
 */
public class DataStoreConfigImpl implements DataStoreConfig {
    private Map<String,CollectionDescription> collections;
    private String dataDir = null;
    
    @Override
    public List<CollectionDescription> getCollections() {
        if (this.collections == null) {
            return null;
        }
        ArrayList al = new ArrayList<CollectionDescription>();
        for (CollectionDescription c: this.collections.values()) {
            al.add(c);
        }
        return al;
    }

    @Override
    public void addCollection(CollectionDescription _coll) {
        if (this.collections == null) {
            this.collections = new HashMap<String,CollectionDescription>();
        }
        this.collections.put(_coll.getName(), _coll);
    }

    
    @Override
    public void removeCollection(String name) {
        this.collections.remove(name);
    }

    @Override
    public String getDataDir() {
        return dataDir;
    }

    @Override
    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    @Override
    public CollectionDescription getCollection(String name) {
        return this.collections.get(name);
    }
    
}
