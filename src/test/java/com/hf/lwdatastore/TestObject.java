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
public class TestObject {

    private String id;
    private String parentNode;
    private String name;
    public TestObject() {
        
        
    }
    
    public TestObject (String id,String parentNode,String name) {
        this.id = id;
        this.parentNode = parentNode;
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentNode() {
        return parentNode;
    }

    public void setParentNode(String parentNode) {
        this.parentNode = parentNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
