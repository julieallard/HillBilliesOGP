package hillbillies.model.activities;

import hillbillies.model.Unit;
import hillbillies.model.activities.Cube;

import java.util.*;

public class Astar {

    public Astar(Unit unit) {
        this.unit = unit;
    }

    private final Unit unit;

    protected Cube[] FindPath(Cube startcube, Cube destination) throws RuntimeException {

        //nodes already discovered and evaluated
        Set<Cube> closedSet = Collections.emptySet();
        //nodes already discovered but yet to be evaluated
        Set<Cube> openSet = Collections.emptySet();

        openSet.add(startcube);
        //the node this node can be most efficiently be reached from
        Map<Cube, Cube> cameFrom = new HashMap<Cube, Cube>() {};
        //the cost of getting to that node from the start
        Map<Cube, Double> gScore = new HashMap<Cube, Double>() {};
        //the distance from start to start is 0
        gScore.put(startcube, (double) 0);

        //A cost estimate for reaching the destination from the start through this node
        //partly known (start => node) partly heuristic (node => destination)
        Map<Cube, Double> FScore = new HashMap<Cube, Double>() {};
        FScore.put(startcube, costEstimatefrom(startcube, destination));

        while (! openSet.isEmpty()) {
            Cube current = startcube;
            double fscore = Double.MAX_VALUE;
            //search the discovered neighbour that is probably the closest to the destination
            for (Cube member: openSet) {
                double currentFScore = FScore.getOrDefault(member, Double.MAX_VALUE);
                if (currentFScore <= fscore) {
                    current = member;
                    fscore = currentFScore;
                }
            }
            //if the destination is reached the shortest path is reconstructed
            if (current == destination)
                return reconstructPath(cameFrom, destination);
            openSet.remove(current);
            closedSet.add(current);
            //alle buren worden ontdekt en er worden schattingen gemaakt van de afstand tot de bestemming
            for (Cube buur: getAllNeighbours(current)) {
                if (closedSet.contains(current))
                    continue;
                double tentativeGScore = gScore.get(current) + Cube.getStraightDist(current, buur);
                if (! openSet.contains(buur))
                    openSet.add(buur);
                else if (tentativeGScore >= gScore.get(buur))
                    //a better path has already been found to this node
                    continue;            
                //this is the best path found to this node yet
                cameFrom.put(buur, current);
                gScore.put(buur, tentativeGScore);
                FScore.put(buur, tentativeGScore + costEstimatefrom(buur, destination));
            }
        }
        return new Cube[]{new Cube(new int[]{-1,-1,-1})};
    }

    private double costEstimatefrom(Cube node, Cube destination){
        return Cube.getStraightDist(node, destination);
    }
    
    private Cube[] reconstructPath(Map<Cube, Cube> cameFrom, Cube current){
        List<Cube> path = new ArrayList<Cube>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        Cube[] path2 = new Cube[path.size()];
        return path.toArray(path2);
    }

    private Set<Cube> getAllNeighbours(Cube cube){
        Set<Cube> intermediate = cube.generateNeighbours();
        Set<Cube> set = Collections.EMPTY_SET;
        for (Cube possibleCube: intermediate) {
            if (unit.getWorld().canHaveAsCubeLocation(possibleCube.locArray, unit)) {
                set.add(possibleCube);
            }
        }
        return set;
    }

}