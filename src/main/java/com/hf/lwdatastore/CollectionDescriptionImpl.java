/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.lwdatastore;

import java.util.ArrayList;
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
    private CollectionObjectConverter converter;
    
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public void setIndexedAttributes(List<String> indexedAttributes) {
        this.indexedAttributes = indexedAttributes;
    }

    public void setIdAttribute(String idAttribute) {
        this.idAttribute = idAttribute;
    }
    public void addIndexedAttribute(String indexedAttribute) {
        if (this.indexedAttributes == null) {
            this.indexedAttributes = new ArrayList<String>();
        }
        this.indexedAttributes.add(indexedAttribute);
    }
    public void addAttribute(String attribute) {
        if (this.attributes == null) {
            this.attributes = new ArrayList<String>();
        }
        this.attributes.add("attribute");
    }

    @Override
    public CollectionObjectConverter getConverter() {
        return this.converter;
    }

    public void setConverter(CollectionObjectConverter converter) {
        this.converter = converter;
    }
    
}
