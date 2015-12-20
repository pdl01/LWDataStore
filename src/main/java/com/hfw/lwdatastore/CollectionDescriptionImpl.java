/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hfw.lwdatastore;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public class CollectionDescriptionImpl implements CollectionDescription{

    private String name;
    private List<String> attributes;
    private List<String> indexedAttributes;
    private String idAttribute;
    private String classReference;
    
    public CollectionDescriptionImpl() {
        
    }
    public CollectionDescriptionImpl(String name,List<String> attributes,List<String> indexedAttributes,String idAttribute) {
        this.name = name;
        this.attributes = attributes;
        this.idAttribute = idAttribute;
        this.indexedAttributes = indexedAttributes;
    }
    
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getAttributes() {
        return attributes;
    }

    @Override
    public String getIDAttribute() {
        return idAttribute;
    }

    @Override
    public List<String> getIndexedAttributes() {
        return indexedAttributes;
    }
    
}
