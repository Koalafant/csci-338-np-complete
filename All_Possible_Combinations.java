import java.util.ArrayList;

public class All_Possible_Combinations
//Code from:
//https://www.baeldung.com/java-combinations-algorithm
//modified to produce integer sets that I needed.
    //returns every set of combinations size r up to n.
{

    public ArrayList<int[]> generate(int n, int r) {
        ArrayList<int[]> combinations = new ArrayList<>();
        int[] combination = new int[r];

        for(int i = 0; i < r; i++){
            combination[i] = i;
        }

        try {
            while (combination[r - 1] < n) {
                try {
                    combinations.add(combination.clone());
                }catch (OutOfMemoryError e){
                    return combinations;
                }
                int t = r - 1;
                while (t != 0 && combination[t] == n - r + t) {
                    t--;
                }
                combination[t]++;
                for (int i = t + 1; i < r; i++) {
                    combination[i] = combination[i - 1] + 1;
                }
            }
            return combinations;
        }catch (IndexOutOfBoundsException e) {
            return combinations;
        }
    }
}