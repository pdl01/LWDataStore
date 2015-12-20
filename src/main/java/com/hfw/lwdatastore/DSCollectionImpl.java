/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hfw.lwdatastore;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author pldorrell
 */
public class DSCollectionImpl implements DSCollection {
    
    private Set keyIndex;
    private Map<String,Set<IndexObject>> indexes;
    private CollectionDescription collectionDescription;
    
    public DSCollectionImpl(CollectionDescription collectionDescription) {
        this.collectionDescription = collectionDescription;
    }
    @Override
    public String putObject(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] putObjects(Object... objs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] getObjects(String... keys) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object removeObject(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] removeObjects(String... keys) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CollectionDescription getCollectionDescription() {
        return this.collectionDescription;
    }
    
}
