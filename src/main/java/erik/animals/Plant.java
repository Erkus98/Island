package erik.animals;

import java.util.List;

public class Plant extends Entity{

    public Plant(){

    }
   public int eat(Entity entity, List<Entity> animalsOnCurrentField){
       System.out.println("Plants do not eat!");
       return 0;
    }

}
