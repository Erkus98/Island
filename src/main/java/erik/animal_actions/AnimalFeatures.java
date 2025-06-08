package erik.animal_actions;

import erik.Field;
import erik.animals.Animal;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public interface AnimalFeatures {

     void move(Future<Map<Field, List<Animal>>> island);


}
