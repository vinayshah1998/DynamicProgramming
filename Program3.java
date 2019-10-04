
//Name: Vinay Shah
//EID: vss452


import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Arrays.*;

public class Program3 {

    DamageCalculator calculator;
    PlanetPathScenario planetScenario;

    public Program3() {
        this.calculator = null;
        this.planetScenario = null;
    }

    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3 for Part 2.
     */
    public void initialize(PlanetPathScenario ps) {
        this.planetScenario = ps;
    }

    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3 for Part 1.
     */
    public void initialize(DamageCalculator dc) {
        this.calculator = dc;
    }


    /*
     * This method returns an integer that is the minimum amount of time necessary to travel
     * from the start planet to the end planet in the PlanetPathScenario given the total
     * amount of fuel that Thanos has. If a path is not possible given the amount of fuel, return -1.
     */
     //TODO: Complete this method
     public int computeMinimumTime() {
        int start = planetScenario.getStartPlanet();
        int end = planetScenario.getEndPlanet();
        int totalFuel = planetScenario.getTotalFuel();
        int numPlanets = planetScenario.getNumPlanets();

        int[][] optMatrix = new int[numPlanets][totalFuel + 1];

        for (int j = 0; j < totalFuel + 1; j++){
            for (int i = 0; i < numPlanets; i++){
                int minTime = Integer.MAX_VALUE;
                for (SpaceFlight flight : planetScenario.getFlightsFromPlanet(i)){
                    if (flight.getFuel() <= j){
                        if (optMatrix[flight.getDestination()][j - flight.getFuel()] < Integer.MAX_VALUE){
                            minTime = Math.min(flight.getTime() + optMatrix[flight.getDestination()][j - flight.getFuel()], minTime);
                        }
                    } else {
                        if (i == end){
                            minTime = 0;
                        }
                    }
                }
                optMatrix[i][j] = minTime;
            }
        }

        sort(optMatrix[start]);
        return optMatrix[start][0];
     }


    /*
     * This method returns an integer that is the maximum possible damage that can be dealt
     * given a certain amount of time.
     */
    //TODO: Complete this function
    public int computeDamage() {

        int totalTime = calculator.getTotalTime();
        int numAttacks = calculator.getNumAttacks();

        //creating the dpTable
        int[][] dpTable = new int[numAttacks][totalTime + 1];

        for (int i = 0; i < numAttacks; i++){
            for (int j = 0; j <= totalTime; j++){
                dpTable[i][j] = optimalDamage(i, j, dpTable);
            }
        }
        return dpTable[numAttacks - 1][totalTime];
    }

    private int optimalDamage(int i, int t, int[][] dpTable ){
        int max = 0;
        for (int k = 0; k <= t; k++){
            if (i == 0){ max = calculator.calculateDamage(i, t);}
            else {
                max = Math.max(calculator.calculateDamage(i, k) + dpTable[i - 1][t - k], max);
            }
        }
        return max;
    }

}


