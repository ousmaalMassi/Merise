package merise;

import merise.models.EntityObject;

import java.util.List;

public class NamesGenerator {


    public String generateName(List<? extends EntityObject> nodeList, String title) {
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
