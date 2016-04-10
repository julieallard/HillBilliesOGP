package hillbillies.model;

import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class UnitTest {
    private World world;
    private Unit unit1;
    private Unit unit2;
    private Unit unit3;

    @Before
    public void setUp() throws Exception {
        int[][][] cubeWorld=new int[10][10][10];
        TerrainChangeListener terrainChangeListener=new DefaultTerrainChangeListener();
        this.world=new World(cubeWorld,terrainChangeListener);
        this.unit1=new Unit("Billy Bob",3,3,3,75,75,75,75,false,world);
        this.unit2=new Unit("Cletus",3,3,3,75,75,75,75,false,world);
        this.unit3=new Unit("John Favreau",3,3,3,75,75,75,75,false,world);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(unit1.getName(),"Billy Bob");
        assertEquals(unit2.getName(),"Cletus");
    }
    @Test
    public void setName() throws Exception {
        unit3.setName("Chef");
        assertEquals(unit3.getName(),"Chef");
    }
    @Test
    public void getLocation() throws Exception {
        double[] arr=new double[]{3,3,3};
        assertArrayEquals(unit1.getLocation().getArray(),arr,0.005);
    }

    @Test
    public void setLocation() throws Exception {
        unit1.set
    }

    @Test
    public void setLocation1() throws Exception {

    }

    @Test
    public void setLocation2() throws Exception {

    }

    @Test
    public void register() throws Exception {

    }

    @Test
    public void getWeight() throws Exception {

    }

    @Test
    public void setWeight() throws Exception {

    }

    @Test
    public void getStrength() throws Exception {

    }

    @Test
    public void setStrength() throws Exception {

    }

    @Test
    public void getAgility() throws Exception {

    }

    @Test
    public void setAgility() throws Exception {

    }

    @Test
    public void getToughness() throws Exception {

    }

    @Test
    public void setToughness() throws Exception {

    }

    @Test
    public void isDefaultBehaviorEnabled() throws Exception {

    }

    @Test
    public void setDefaultBehavior() throws Exception {

    }

    @Test
    public void startDefaultBehavior() throws Exception {

    }

    @Test
    public void stopDefaultBehavior() throws Exception {

    }

    @Test
    public void getWorld() throws Exception {

    }

    @Test
    public void canHaveAsWorld() throws Exception {

    }

    @Test
    public void setWorld() throws Exception {

    }

    @Test
    public void getMaxPoints() throws Exception {

    }

    @Test
    public void isValidPoints() throws Exception {

    }

    @Test
    public void getCurrentHitPoints() throws Exception {

    }

    @Test
    public void setCurrentHitPoints() throws Exception {

    }

    @Test
    public void getCurrentStaminaPoints() throws Exception {

    }

    @Test
    public void setCurrentStaminaPoints() throws Exception {

    }

    @Test
    public void getOrientation() throws Exception {

    }

    @Test
    public void isValidOrientation() throws Exception {

    }

    @Test
    public void setOrientation() throws Exception {

    }

    @Test
    public void getActivity() throws Exception {

    }

    @Test
    public void isValidActivity() throws Exception {

    }

    @Test
    public void setActivity() throws Exception {

    }

    @Test
    public void isCarrying() throws Exception {

    }

    @Test
    public void setCarrying() throws Exception {

    }

    @Test
    public void getCarriedObject() throws Exception {

    }

    @Test
    public void carry() throws Exception {

    }

    @Test
    public void drop() throws Exception {

    }

    @Test
    public void isSprinting() throws Exception {

    }

    @Test
    public void setSprinting() throws Exception {

    }

    @Test
    public void advanceTime() throws Exception {

    }

    @Test
    public void behaveDefault() throws Exception {

    }

    @Test
    public void activityFinished() throws Exception {

    }

    @Test
    public void work() throws Exception {

    }

    @Test
    public void attack() throws Exception {

    }

    @Test
    public void getFaction() throws Exception {

    }

    @Test
    public void canHaveAsFaction() throws Exception {

    }

    @Test
    public void setFaction() throws Exception {

    }

    @Test
    public void unregister() throws Exception {

    }

    @Test
    public void dealDamage() throws Exception {

    }

    @Test
    public void getXP() throws Exception {

    }

    @Test
    public void addXP() throws Exception {

    }

    @Test
    public void moveTo() throws Exception {

    }

    @Test
    public void moveToAdjacent() throws Exception {

    }

    @Test
    public void rest() throws Exception {

    }

    @Test
    public void setLocation3() throws Exception {

    }

    @Test
    public void setLocation4() throws Exception {

    }

    @Test
    public void setLocation5() throws Exception {

    }

    @Test
    public void getLocation1() throws Exception {

    }

    @Test
    public void register1() throws Exception {

    }

    @Test
    public void unregister1() throws Exception {

    }

    @Test
    public void getWorld1() throws Exception {

    }

    @Test
    public void activityFinished1() throws Exception {

    }

    @Test
    public void getWeight1() throws Exception {

    }

    @Test
    public void advanceTime1() throws Exception {

    }

}