package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.exceptions.UnitIllegalLocation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Value
public class Cube {

    public Cube(int[] cube) throws UnitIllegalLocation{ 
        if (cube.length != 3)
        	throw new UnitIllegalLocation("Astar tried to create an illegal cube");
        this.locArray = cube;
    }
    
    @Override
    public boolean equals(Object cube1) {
        if (cube1 == null || ! (cube1 instanceof Cube))
        	return false;
        if (cube1 == this)
        	return true;
        Cube cube = (Cube) cube1;
        if (cube.locArray.length != 3)
        	throw new UnitIllegalLocation("Astar tried to compare an illegal cube");
        for (int i = 0; i < 3; i++) {
            if (cube.locArray[i] != this.locArray[i])
            	return false;
        }
        return true;
    }

    public final int[] locArray;

    @Override
    public int hashCode() {
        return Arrays.hashCode(locArray);
    }
    
    //Heuristiek voor Astar hier gebruiken we Manhattan
    protected static double getStraightDist(Cube cube1, Cube cube2){
        double dx = cube1.locArray[0] - cube2.locArray[0];
        double dy = cube1.locArray[1] - cube2.locArray[1];
        double dz = cube1.locArray[2] - cube2.locArray[2];
        return dx + dy + dz;
    }

    protected Set<Cube> generateNeighbours() {
        Set<Cube> set = Collections.EMPTY_SET;
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                for (int z = -1; z< 2; z++) {
                    if(x == 0 && y == 0 && z == 0)
                        continue;               
                    int[] temparr = new int[]{this.locArray[0] + x, this.locArray[1] + y, this.locArray[2] + z};
                    set.add(new Cube(temparr));
                }
            }
        }
        return set;
    }
    
}
