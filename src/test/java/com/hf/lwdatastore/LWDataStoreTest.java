/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.lwdatastore;

/**
 *
 * @author pldorrell
 */
public class LWDataStoreTest {

    protected LWDataStoreFactory factory;
    protected DataStore dataStore;

    public DataStoreConfig getConfig() {
        DataStoreConfigImpl dsConfig = new DataStoreConfigImpl();

        dsConfig.setDataDir("/home/pldorrell/.hfw_app/data/test ");

        CollectionDescriptionImpl collectionDescriptionImpl = new CollectionDescriptionImpl();
        collectionDescriptionImpl.setName("testObject");
        collectionDescriptionImpl.setIdAttribute("id");
        collectionDescriptionImpl.addIndexedAttribute("parentNode");
        collectionDescriptionImpl.addAttribute("id");
        collectionDescriptionImpl.addAttribute("parentNode");
        collectionDescriptionImpl.addAttribute("name");
        collectionDescriptionImpl.setConverter(new TestObjectConverter());
        dsConfig.addCollection(collectionDescriptionImpl);

        return dsConfig;
    }

}
