import java.util.*;

public class ArrowEnergy {
    
    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        
        //creates my list to store my information of the velo, ke, and weight
        ArrayList<Double> veloList = new ArrayList<Double>();
        ArrayList<Double> kineticEnergyList = new ArrayList<Double>();
        ArrayList<Double> arrowWeightList = new ArrayList<Double>();
        ArrayList<Double> momentumList = new ArrayList<Double>();
        
        //gether information from the user about their bow
        System.out.println("Enter your bows IBO(IBO is the arrow speed according to the IBO specification in ft/s): ");
        double ibo = scn.nextDouble();
        
        System.out.println("Enter your draw length: ");
        double drawL = scn.nextDouble();
        
        System.out.println("Enter your draw weight: ");
        double drawW = scn.nextDouble();
        
        //execute my code 
        velocityToKE(veloList, kineticEnergyList, arrowWeightList, ibo, drawL, drawW, 200);
        int highI = greedy(kineticEnergyList, 0, 0, 0);
        output(veloList, kineticEnergyList, arrowWeightList, highI);
        
        momentum(momentumList, veloList, arrowWeightList);
        
        int i = 0;
        double maxP = 0;
        int high = 0;
        
        while(i < momentumList.size()){
        
            double n = momentumList.get(i);
        
            if(maxP < n){                  //I store i here so i can later pull information from each list
                maxP = n;
                high = i;
            }
            
            i++;
        }
        
        System.out.println("Max momentum: " + maxP);
        System.out.println("Momentum from highest KE: " + momentumList.get(highI));
        System.out.println("Momentum index: " + high);
        System.out.println("KE index: " + highI);
        System.out.println("Max momentum arrow weight: " + arrowWeightList.get(high));
        System.out.println("Max KE arrow weight: " + arrowWeightList.get(highI));
        System.out.println("KE of max momentum arrow: " + kineticEnergyList.get(high));
        int mid = (high + highI)/2;
        System.out.println("KE of mid arrow: " + kineticEnergyList.get(mid));
        System.out.println("Mid KE arrow weight: " + arrowWeightList.get(mid));
        System.out.println("Momentum from mid: " + momentumList.get(mid));
        System.out.println("velocity from mid: " + String.format("%.2f", veloList.get(mid)));
    }
    
    
    
    //here we actually use a equation to caluate the bows shot velo based on the info we gathered above
    //we then use the velo and the arrow weight to claulate the kinetic energy produced
    public static void velocityToKE(ArrayList<Double> veloList, ArrayList<Double> kineticEnergyList, ArrayList<Double> arrowWeightList, double ibo, double drawL, double drawW, double arrowWeight){
        
        double velo;
        double ke;
        
        if(arrowWeight <= 900){
            arrowWeightList.add(arrowWeight);
            velo = ibo + (drawL - 30) * 10 - 5/3 - (arrowWeight - 5 * drawW)/3;   //equation for velo
            veloList.add(velo);
            ke = arrowWeight * (velo * velo)/450800;                                // equaiton for kinetic energy
            kineticEnergyList.add(ke);
            velocityToKE(veloList, kineticEnergyList, arrowWeightList, ibo, drawL, drawW, arrowWeight + 1);
        }
    }
    
    //we make a greedy choice based on highest ke at each point thorughout the list
    public static int greedy(ArrayList<Double> kineticEnergyList, int i, double maxKE, int highI){
        
        while(i < kineticEnergyList.size()){
        
            double n = kineticEnergyList.get(i);
        
            if(maxKE < n){                  //I store i here so i can later pull information from each list
                maxKE = n;
                highI = i;
            }
            
            i++;
        }
        return highI;               //pass back i with the highest ke to pull for later information
    }
    
    public static void momentum(ArrayList<Double> momentumList, ArrayList<Double> veloList, ArrayList<Double> arrowWeightList){
        
        double velo;
        double weight;
        double momentum;
        
        for(int i = 0; i < veloList.size(); i++){
            velo = veloList.get(i);
            weight = arrowWeightList.get(i);
            momentum = velo * weight / 225400;
            
            momentumList.add(momentum);
        }
    }
    
    //this just simply outputs everything the user wants to know
    public static void output(ArrayList<Double> veloList, ArrayList<Double> kineticEnergyList, ArrayList<Double> arrowWeightList, int highI){
        
        double velo = veloList.get(highI);
        double arrowWeight = arrowWeightList.get(highI);
        double kE = kineticEnergyList.get(highI);
        
        System.out.println("The max kinetic energy that can be produced is: " + String.format("%.2f", kE));
        System.out.println("With an arrow weight of: " + arrowWeight);
        System.out.println("While shooting at a velocity of: " + String.format("%.2f", velo) + " feet per second.");
    }
    
}
