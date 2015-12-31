/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.lwdatastore;

import java.util.List;

/**
 *
 * @author pldorrell
 */
public interface DataStoreConfig {
    public String getDataDir();
    public void setDataDir(String dataDir);
    
    public List<CollectionDescription> getCollections();
    public void addCollection(CollectionDescription _coll);
    public CollectionDescription getCollection(String name);
    public void removeCollection(String name);
    
}
