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
public interface CollectionDescription {
    public String getName();
    public List<String> getAttributes();
    public String getIDAttribute();
    public List<String> getIndexedAttributes();
    public CollectionObjectConverter getConverter();
}
