
import java.lang.Math;
import java.util.ArrayList;

public class Main {
    // Welcome to Drake's algorithm simulation for
    // Shut the Box

    // Change this to the number of times you want to run the simulation
    public static int runs = 100000;

    // Change this number to the Algorithm type
    // 1 = Random
    // 2 = Biggest Piece
    //3 = Most Pieces
    //4 = 2 Random Average
    //5 = Higher of 2 Random
    //6 = Drake's Algorithm
    //7 = Half Random
    //8 = High to Low
    //9 = Low to High
    //10 = BPDA Average
    //11 = Drake's Improved Algorithm
    public static int type = 11;

    // Change this to 1 to run all Algorithms
    // Or keep at 0 to run just 1 Algorithm
    public static int runAll = 1;

    // If running all Algorithms set this to the number you want to compare them by
    // 1 = wins
    // 2 = highest average score
    // 3 = least average amount of pieces still up
    public static int compare = 1;


    public static int algorithmsAmount = 11;
    public static double[] algorithmsScore = new double[algorithmsAmount];
    public static String[] algorithmsName = new String[algorithmsAmount];
    public static String[] algorithmsLineupName = new String[algorithmsAmount];
    public static String[] lineup = new String[algorithmsAmount];
    public static int[] piecesUp = new int[12];
    public static ArrayList<Integer> options = new ArrayList<Integer>();
    public static double averageScore = 0;
    public static double averagePiecesStillLeft = 0;
    public static boolean playerAlive = true;
    public static int currentDice = 0;
    public static int decision = 0;
    public static int score = 0;
    public static int totalScore = 0;
    public static int piecesStillLeft = 0;
    public static int totalPiecesStillLeft = 0;
    public static int wins = 0;
    public static String algorithmName = "";
    public static int eight = 0;
    public static int nine = 0;
    public static String comparisonName = "";

    public static void main(String[] args) {

        if (runAll == 1){
            type = 0;
            loadingBar();

            for (type = 1; type <= algorithmsAmount; type ++){
                runGame();
                nameAlgorithm();

                if (compare == 1){
                    algorithmsScore[type - 1] = wins;
                    wins = 0;
                } else if (compare == 2){
                    algorithmsScore[type - 1] = averageScore;
                    averageScore = 0;
                    totalScore = 0;
                } else if (compare == 3){
                    algorithmsScore[type - 1] = averagePiecesStillLeft;
                    averagePiecesStillLeft = 0;
                    totalPiecesStillLeft = 0;
                }
                loadingBar();
            }

        } else {
            runGame();
            nameAlgorithm();
        }

        nameComparison();

        if (runAll == 0){
            System.out.println("After running the simulation " +  runs + " times\nWith the algorithm " + algorithmName + "\nThe average score was: " + averageScore + " out of 78\nAlso the average amount of pieces still up was: " + averagePiecesStillLeft + "\nThe total amount of wins were: " + wins);
        } else if (runAll == 1){
            System.out.println("\nAfter running the simulation " +  runs + " times\nComparing the algorithms based off of " + comparisonName + "\nThe results are:\n==========================================\n");

            rankAlgorithms();
            results();
        }
    }

    public static void resetPiecesUp(){
        for (int i = 0; i < 12; i++){
            piecesUp[i] = 1;
        }
    }

    public static int rollDie(){
        return (int) ((Math.random() * 100000) % 6) + 1;
    }

    public static int rollDice(){
        int sum = rollDie();
        sum += rollDie();
        return sum;
    }

    public static void createOptions(){
        if (piecesUp[currentDice -1] == 1){
            options.add(currentDice);
        }


        int first = 1;
        for (int second = currentDice; second > first; second --){
            for (first = 1; first < second; first ++){
                if ((first + second) == currentDice){
                    if (piecesUp[first-1] == 1 && piecesUp[second-1] == 1){
                        options.add(first + (second * 100));
                    }
                }
            }
            first = 1;
        }


        first = 1;
        int second = 2;
        for (int third = currentDice; third > second; third --){
            for (first = 1; first < second; first ++){
                for (second = first + 1; second < third; second ++){
                    if ((first + second + third) == currentDice){
                        if (piecesUp[first-1] == 1 && piecesUp[second-1] == 1 && piecesUp[third-1] == 1){
                            options.add(first + (second * 100) + (third * 10000));
                        }
                    }
                }
            }
            first = 1;
            second = 2;
        }


        if (currentDice == 10){
            if (piecesUp[0] == 1 && piecesUp[1] == 1 && piecesUp[2] == 1 && piecesUp[3] == 1){
                options.add(4030201);
            }
        } else if (currentDice == 11){
            if (piecesUp[0] == 1 && piecesUp[1] == 1 && piecesUp[2] == 1 && piecesUp[4] == 1){
                options.add(5030201);
            }
        } else if (currentDice == 12){
            if (piecesUp[0] == 1 && piecesUp[1] == 1 && piecesUp[2] == 1 && piecesUp[5] == 1){
                options.add(6030201);
            } else if (piecesUp[0] == 1 && piecesUp[1] == 1 && piecesUp[3] == 1 && piecesUp[4] == 1){
                options.add(5040201);
            }
        }
    }

    public static void testForGameOver(){
        if (options.size() == 0){
            playerAlive = false;

            int winTest = 0;
            for(int i = 0; i < 12; i++){
                winTest += piecesUp[i];
            }

            if (winTest == 0){
                wins += 1;
            }
        }
    }

    public static void doDecision(){
        int numbers = options.get(decision);

        while (numbers != 0){
            int currentNumber = numbers % 100;
            numbers = numbers / 100;

            if (currentNumber != 0){
                piecesUp[currentNumber - 1] = 0;
            }
        }
    }

    public static void reset(){
        score = 0;
        eight = 0;
        nine = 0;
    }

    public static void score(){
        score = score + currentDice;
    }

    public static void piecesStillLeft(){
        piecesStillLeft = 0;
        for (int i = 0; i < 12; i ++){
            if (piecesUp[i] == 1){
                piecesStillLeft += 1;
            }
        }
    }

    public static void runGame(){
        for(int n = 0; n < runs; n++){
            resetPiecesUp();
            reset();

            playerAlive = true;
            while (playerAlive == true){
                currentDice = rollDice();

                createOptions();
                testForGameOver();
                if (playerAlive == true){
                    decide();
                    doDecision();
                    score();
                } else if (playerAlive == false){
                    piecesStillLeft();
                }

                options.clear();
            }
            totalScore += score;
            totalPiecesStillLeft += piecesStillLeft;
        }

        averageScore = (double) totalScore / runs;
        averagePiecesStillLeft = (double) totalPiecesStillLeft / runs;
    }

    public static void nameComparison(){
        if (compare == 1){
            comparisonName = "wins";
        } else if (compare == 2){
            comparisonName = "highest average score";
        } else if (compare == 3){
            comparisonName = "least average amount of pieces still up";
        }
    }

    public static void results(){

        for (int i = 0; i < algorithmsAmount; i ++){
            if (algorithmsName[i] == algorithmsLineupName[10]){
                lineup[i] = "\t";
            }  else if (algorithmsName[i] == algorithmsLineupName[3] || algorithmsName[i] == algorithmsLineupName[4] || algorithmsName[i] == algorithmsLineupName[5]){
                lineup[i] = "\t\t";
            } else if (algorithmsName[i] == algorithmsLineupName[0]){
                lineup[i] = "\t\t\t\t";
            } else {
                lineup[i] = "\t\t\t";
            }
        }


        for (int i = 0; i < algorithmsAmount; i ++ ){
            System.out.println(i +1 + ")\t" + algorithmsName[i] + lineup[i] + algorithmsScore[i]);
        }
    }

    public static void rankAlgorithms(){

        if (compare == 1 || compare == 2){
            for (int n1 = 0; n1 < algorithmsAmount - 1; n1 ++){
                for (int n2 = n1 + 1; n2 < algorithmsAmount; n2 ++){
                    if (algorithmsScore[n2] > algorithmsScore[n1]){
                        double replaceN = algorithmsScore[n1];
                        String replaceS = algorithmsName[n1];

                        algorithmsScore[n1] = algorithmsScore[n2];
                        algorithmsName[n1] = algorithmsName[n2];

                        algorithmsScore[n2] = replaceN;
                        algorithmsName[n2] = replaceS;
                    }
                }
            }
        } else {
            for (int n1 = 0; n1 < algorithmsAmount - 1; n1 ++){
                for (int n2 = n1 + 1; n2 < algorithmsAmount; n2 ++){
                    if (algorithmsScore[n2] < algorithmsScore[n1]){
                        double replaceN = algorithmsScore[n1];
                        String replaceS = algorithmsName[n1];

                        algorithmsScore[n1] = algorithmsScore[n2];
                        algorithmsName[n1] = algorithmsName[n2];

                        algorithmsScore[n2] = replaceN;
                        algorithmsName[n2] = replaceS;
                    }
                }
            }
        }
    }

    public static void loadingBar(){
        for (int i = 1; i <= algorithmsAmount; i ++){
            if (i <= type){
                System.out.print("■");
            } else {
                System.out.print("□");
            }
        }
        System.out.print("\n");
    }

    public static void nameAlgorithm(){
        if (type == 1){
            algorithmName = "Random";
        } else if (type == 2){
            algorithmName = "Biggest Piece";
        } else if (type == 3){
            algorithmName = "Most Pieces";
        } else if (type == 4){
            algorithmName = "2 Random Average";
        } else if (type == 5){
            algorithmName = "Higher of 2 Random";
        } else if (type == 6){
            algorithmName = "Drake's Algorithm";
        } else if (type == 7){
            algorithmName = "Half Random";
        } else if (type == 8){
            algorithmName = "High to Low";
        } else if (type == 9){
            algorithmName = "Low to High";
        } else if (type == 10){
            algorithmName = "BPDA Average";
        } else if (type == 11){
            algorithmName = "Drake's Improved Algorithm";
        }
        algorithmsName[type - 1] = (String) algorithmName;
        algorithmsLineupName[type - 1] = (String) algorithmName;
    }

    public static void decide(){
        if (type == 1){
            decision = (int) (Math.random() * 10000) % options.size();
        } else if (type == 2){
            decision = 0;

        } else if (type == 3){
            decision = options.size() - 1;

        } else if (type == 4){
            int rand1 = (int) (Math.random() * 10000) % options.size();
            int rand2 = (int) (Math.random() * 10000) % options.size();
            decision = (int) (rand1 + rand2) / 2;

        } else if (type == 5){
            int rand1 = (int) (Math.random() * 10000) % options.size();
            int rand2 = (int) (Math.random() * 10000) % options.size();
            if (rand1 < rand2){
                decision = rand1;
            } else {
                decision = rand2;
            }

        } else if (type == 6){
            if (options.get(0) <= 12){
                decision = 0;
            } else {
                int max = 0;
                for (int i = options.size(); i > 0; i --){
                    if (options.get(i-1) > 1200){
                        max = i - 1;
                    }
                }
                if (max != 0 && max > 0){
                    decision = max - 1;
                } else {
                    decision = 0;
                }
            }

        } else if (type == 7){
            if (options.get(0) <= 12){
                decision = 0;
            } else {
                decision = (int) (Math.random() * 10000) % options.size();
            }

        } else if (type == 8){
            if (eight >= options.size()){
                eight = 0;
            }
            decision = eight;
            eight ++;

        } else if (type == 9){
            if (nine == 0 || nine >= options.size()){
                nine = options.size() - 1;
            } else {
                nine --;
            }
            decision = nine;

        } else if (type == 10){
            int BP = 0;
            int DA;
            if (options.get(0) <= 12){
                DA = 0;
            } else {
                int max = 0;
                for (int i = options.size(); i > 0; i --){
                    if (options.get(i-1) > 1200){
                        max = i - 1;
                    }
                }
                if (max != 0 && max > 0){
                    DA = max - 1;
                } else {
                    DA = 0;
                }
            }
            int Avg =(BP + DA) / 2;
            Avg -= 1;
            if (Avg < 0){
                Avg = 0;
            }
            decision = Avg;

        } else if (type == 11){
            if (options.get(0) <= 12){
                decision = 0;
            } else if (options.get(0) >= 800 && options.get(0) < 10000){
                decision = 0;
            } else {
                int max = 0;
                for (int i = options.size(); i > 0; i --){
                    if (options.get(i-1) > 1200){
                        max = i - 1;
                    }
                }
                if (max != 0 && max > 0){
                    decision = max - 1;
                } else {
                    decision = 0;
                }
            }
        }
    }
}
