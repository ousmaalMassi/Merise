package com;

import com.models.MeriseObject;

import java.util.List;

public class NamesGenerator {

    public static String generateName(List<? extends MeriseObject> nodeList, String title) {
        var ref = new Object() {
            int i = 0;
            String name = title + "_";
        };

        while (nodeList.stream().anyMatch(entityObject -> entityObject.getName().equals(ref.name + ref.i))) {
            ref.i++;
        }
        return ref.name += ref.i;
    }
}
