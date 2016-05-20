package hillbillies.modeltest;

import hillbillies.model.CubeObjects.Air;
import hillbillies.model.Log;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by arthurdecloedt on 20/05/16.
 */
public class WorldTest {


    @Test
    public void destroyCube() throws Exception {
        int[][][] types = new int[3][3][3];
        types[1][1][0] = 1;
        types[1][1][1] = 1;
        types[1][1][2] = 2;
        types[2][2][2] = 3;

        World world = new World(types, new DefaultTerrainChangeListener());
        int[] loc = new int[]{1, 1, 0};
        world.destroyCube(loc);
        assertEquals(world.getCubeIDAt(loc), 0);
    }


    @Test
    public void canHaveAsCubeLocation() throws Exception {
        int[][][] types = new int[3][3][3];
        types[1][1][0] = 1;
        types[1][1][1] = 1;
        types[1][1][2] = 2;
        types[2][2][2] = 3;

        World world = new World(types, new DefaultTerrainChangeListener());
        int[] loc = new int[]{1, 1, 0};
        int[] loc2 = new int[]{1, 1, 2};
        int[] loc3 = new int[]{0, 0, 0};
        Log log = new Log(0, 0, 0, world);

        assertFalse(world.canHaveAsCubeLocation(loc, log));
        assertFalse(world.canHaveAsCubeLocation(loc2, log));
        assertFalse(!world.canHaveAsCubeLocation(loc3, log));
    }

    @Test
    public void willBreakFall() throws Exception {
        int[][][] types = new int[3][3][3];
        types[1][1][0] = 1;
        types[1][1][1] = 1;
        types[1][1][2] = 2;
        types[2][2][2] = 3;

        World world = new World(types, new DefaultTerrainChangeListener());
        int[] loc3 = new int[]{0, 0, 0};
        Log log = new Log(0, 0, 0, world);

        assertFalse(world.willBreakFall(loc3));
    }


    @Test
    public void getCubeAt() throws Exception {
            int[][][] types = new int[3][3][3];
            types[1][1][0] = 1;
            types[1][1][1] = 1;
            types[1][1][2] = 2;
            types[2][2][2] = 3;

            World world = new World(types, new DefaultTerrainChangeListener());
            int[] loc3 = new int[]{0, 0, 0};
            assertFalse(!(world.getCubeAt(loc3) instanceof Air));
            assertEquals(world.getCubeIDAt(loc3),0);
    }


    @Test
    public void getSideSize() throws Exception {
        int[][][] worldarr = new int[100][20][10];
        World world = new World(worldarr, new DefaultTerrainChangeListener());
        assertEquals(world.getxSideSize(), 100);
        assertEquals(world.getySideSize(), 20);
        assertEquals(world.getxSideSize(), 10);
    }
}
