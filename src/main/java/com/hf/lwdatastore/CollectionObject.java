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
public class CollectionObject implements Comparable<CollectionObject> {
    private String id;
    private Object target;
    
    public CollectionObject() {
        
    }
    public CollectionObject(Object _target) {
        this.target = _target;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public int compareTo(CollectionObject o) {
        return this.id.compareTo(o.getId());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
