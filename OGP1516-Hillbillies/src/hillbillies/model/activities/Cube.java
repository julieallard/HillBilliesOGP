package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.exceptions.UnitIllegalLocation;
@Value
public class Cube {

    public Cube(int[] cube)throws UnitIllegalLocation{
        if (cube.length!=3) throw new UnitIllegalLocation("Astar tried to create an illegal cube");
        this.locArray=cube;
    }
    @Override
    public boolean equals(Object cube1){
        if (cube1==null||!(cube1 instanceof Cube))return false;
        if (cube1==this)return true;
        Cube cube=(Cube) cube1;
        if (cube.locArray.length!=3) throw new UnitIllegalLocation("Astar tried to compare an illegal cube");
        for (int i = 0; i < 3; i++) {
            if (cube.locArray[i]!=this.locArray[i]) return false;
        }
        return true;
    }

    public final int[] locArray;

    public 
}
