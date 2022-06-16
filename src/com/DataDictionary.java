package com;

import com.model.Property;

import java.util.ArrayList;
import java.util.List;

public class DataDictionary {
    private List<Property> dictionary;

    public DataDictionary() {
        this.dictionary = new ArrayList<>();
    }

    public DataDictionary(List<Property> dictionary) {
        this.dictionary = dictionary;
    }

    public List<Property> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<Property> dictionary) {
        this.dictionary = dictionary;
    }

    public DataDictionary addData(Property property) {
        this.dictionary.add(property);
        return this;
    }

    public void removeData(Property property) {
        this.dictionary.remove(property);
    }

    /**
     * @param propertyCode is the code of the code or name of the property we're looking for
     * @return Property
     */
    public Property searchProperty(String propertyCode){
        return this.dictionary.stream()
                .filter(p -> p.getCode().equals(propertyCode))
                .findAny()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "DataDictionary{" +
                "dictionary=" + dictionary +
                "}";
    }
}