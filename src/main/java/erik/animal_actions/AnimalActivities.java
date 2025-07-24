package erik.animal_actions;

import erik.animals.Entity;

import java.util.List;

public interface AnimalActivities {

    int eat(Entity entity, List<Entity> animalsOnCurrentField);

}
