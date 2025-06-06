package Erik.animal_actions;

import Erik.Field;
import Erik.animals.Animal;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public interface Movement {

     void move(Future<Map<Field, List<Animal>>> island);


}
