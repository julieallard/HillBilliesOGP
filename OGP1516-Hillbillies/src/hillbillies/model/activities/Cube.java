package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.exceptions.IllegalLocation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A class of cubes involving a position.
 * 
 * @version	2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
@Value
public class Cube {

	/**
	 * Initialize this new cube with given location.
	 * 
	 * @param	cube
	 * 			The location for this new cube.
	 * @throws	IllegalLocation
	 * 			The given cube location does not consist of 3 coordinates.
	 */
    public Cube(int[] cube) throws IllegalLocation {
        if (cube.length != 3)
        	throw new IllegalLocation("Astar tried to create an illegal cube.");
        this.locArray = cube;
    }
    
    /**
     * Variable registering the location of this cube.
     */
    public final int[] locArray;
    
    /**
     * Check whether this cube equals the given other cube.
     * 
     * @param	otherCube
     * 			The cube to compare.
     * @return	True if and only if the given cube equals this cube.
     * @throws	IllegalLocation
     * 			The location of the given cube doesn't consist of 3 coordinates.
     */
    @Override
    public boolean equals(Object otherCube) {
        if (otherCube == null || ! (otherCube instanceof Cube))
        	return false;
        if (otherCube == this)
        	return true;
        Cube cube = (Cube) otherCube;
        if (cube.locArray.length != 3)
        	throw new IllegalLocation("Astar tried to compare an illegal cube");
        for (int i = 0; i < 3; i++) {
            if (cube.locArray[i] != this.locArray[i])
            	return false;
        }
        return true;
    }

    /**
     * Return the hash code of this cube's location.
     */
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
        Set<Cube> set = new HashSet<>();
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