package hillbillies.model;

import hillbillies.model.activities.*;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Assert.*;


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
        unit1.setLocation(1,2,3);
        double[] arr=new double[]{1,2,3};
        assertArrayEquals(unit1.getLocation().getArray(),arr,0.005);
    }
    @Test
    public void setLocation1() throws Exception {
        VLocation loc=new VLocation(4,4,4,unit2);
        unit2.setLocation(loc);
        assertFalse(!loc.equals(unit2.getLocation()));
        assertEquals(unit1.getWorld().getWorldMap().get(loc),unit2);
    }

    @Test
    public void setLocation2() throws Exception {
        double[] arr=new double[]{1,2,3};
        assertArrayEquals(unit1.getLocation().getArray(),arr,0.005);
        unit1.setLocation(arr);
    }

    @Test
    public void register() throws Exception {
        unit1.unregister();
        assertFalse(world.getWorldMap().getAllUnits().contains(unit1));
    }

    @Test
    public void getWeight() throws Exception {
        assertEquals(unit1.getWeight(),75);

    }

    @Test
    public void setWeight() throws Exception {
        unit1.setWeight(100);
        assertEquals(unit1.getWeight(),100);

    }

    @Test
    public void getStrength() throws Exception {
        assertEquals(unit1.getStrength(),75);
    }

    @Test
    public void setStrength() throws Exception {
        unit1.setStrength(100);
        assertEquals(unit1.getStrength(),100);
    }

    @Test
    public void getAgility() throws Exception {
        assertEquals(unit1.getAgility(),75);
    }

    @Test
    public void setAgility() throws Exception {
        unit1.setAgility(100);
        assertEquals(unit1.getAgility(),100);

    }

    @Test
    public void getToughness() throws Exception{
        assertEquals(unit1.getToughness(),75);
        }

    @Test
    public void setToughness() throws Exception {
        unit1.setToughness(100);
        assertEquals(unit1.getToughness(),100);
    }

    @Test
    public void isDefaultBehaviorEnabled() throws Exception {
        assertEquals(unit1.isDefaultBehaviorEnabled(),false);
    }

    @Test
    public void setDefaultBehavior() throws Exception {
        unit1.setDefaultBehavior(true);
        assertEquals(unit1.isDefaultBehaviorEnabled(),true);
    }

    @Test
    public void getWorld() throws Exception {
        assertEquals(unit1.getWorld(),world);

    }

    @Test
    public void getMaxPoints() throws Exception {
        assertEquals(unit1.getMaxPoints(),(int) Math.ceil(unit1.getWeight()*(double) unit1.getToughness()/50));
    }


    @Test
    public void getCurrentHitPoints() throws Exception {
        assertEquals(unit1.getCurrentHitPoints(),(int) Math.ceil(unit1.getWeight()*(double) unit1.getToughness()/50));
    }

    @Test
    public void setCurrentHitPoints() throws Exception {
        unit1.setCurrentHitPoints(100);
        assertEquals(unit1.getCurrentHitPoints(),100);



    }

    @Test
    public void getCurrentStaminaPoints() throws Exception {
        assertEquals(unit1.getCurrentStaminaPoints(),(int) Math.ceil(unit1.getWeight()*(double) unit1.getToughness()/50));
    }

    @Test
    public void setCurrentStaminaPoints() throws Exception {
        unit1.setCurrentStaminaPoints(20);
        assertEquals(unit1.getCurrentStaminaPoints(),20);
    }


    @Test
    public void setOrientation() throws Exception {
        unit1.setOrientation((float)1.3);
        assertEquals(unit1.getOrientation(),1.3,0.01);
        unit1.setOrientation((float)25);
        assertEquals(unit1.getOrientation(),Math.PI/2,0.01);

    }

    @Test
    public void setActivity() throws Exception {
        IActivity newAct= new Fall(unit1);
        unit1.setActivity(newAct);
        assertEquals(unit1.getActivity(),newAct);

    }

    @Test
    public void isCarrying() throws Exception {
        assertEquals(unit1.isCarrying(),false);
    }

    @Test
    public void carry() throws Exception {
        Log log=new Log(2,2,2,world);
        unit1.carry(log);
        assertFalse(!unit1.isCarrying());
        assertEquals(unit1.getCarriedObject(),log);
        assertFalse(world.getWorldMap().containsValue(log));
    }

    @Test
    public void drop() throws Exception {

        unit1.drop(unit1.getCarriedObject());
        assertFalse(!unit1.isCarrying());
    }
    @Test
    public void setSprinting() throws Exception {
        unit1.setSprinting(true);
        assertEquals(unit1.isSprinting(),true);
    }

    @Test
    public void activityFinished() throws Exception {
        unit1.activityFinished();
        assertFalse(!(unit1.getActivity() instanceof NoActivity));

    }

    @Test
    public void work() throws Exception {
        unit1.setLocation(1,1,1);
        unit1.work(new int[]{1,1,1});
        assertFalse(!(unit1.getActivity() instanceof Work));
    }

    @Test
    public void attack() throws Exception {
        unit1.attack(unit3);
        assertFalse(!(unit1.getActivity() instanceof Attack));
        assertFalse(!(unit3.getActivity() instanceof Defend));

    }


    @Test
    public void dealDamage() throws Exception {
        unit1.setCurrentHitPoints(100);
        unit1.dealDamage(20);
        assertEquals(unit1.getCurrentHitPoints(),80);
    }

    @Test
    public void moveTo() throws Exception {
        unit1.moveTo(new int[]{9,9,9});
        assert(unit1.getActivity() instanceof Movement);
    }

    @Test
    public void moveToAdjacent() throws Exception {
        unit2.moveToAdjacent(1,0,1);
        assert(unit2.getActivity() instanceof Movement);

    }

    @Test
    public void rest() throws Exception {
        unit2.setActivity(new NoActivity());
        unit2.rest();
        unit1.moveTo(new int[]{9,9,9});
        assert(unit2.getActivity() instanceof Rest);
    }
}