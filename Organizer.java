import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Organizer {
    //accuracy values for inexact

    public Organizer() {

    }
    public void collect(String fileName, String fileOut, int vertices, int timesToRun) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileOut);
        RandomGraph r = new RandomGraph();
        r.create(fileName, vertices);
        Graph graph = new Graph(fileName);
        GraphToolBox compute = new GraphToolBox();
        long[] values = new long[timesToRun];

        writer.write("Vertex Cover\n------------\n");
        /*
         * exactVC
         */
        int correctVCs = 0;
        int[] optimalVC;
        int[] inexactVC;
        for (int i = 0; i < timesToRun; i++) {
            long start = System.currentTimeMillis();
            optimalVC = GraphToolBox.exactVC(graph);
            long elapsedTime = System.currentTimeMillis() - start;
            writer.write("Exact vertex cover found in: " + elapsedTime + " ms\n");
            values[i] = elapsedTime;
            inexactVC = GraphToolBox.inexactVC(graph);

            if (optimalVC.length == inexactVC.length) {
                correctVCs++;
            }
        }
        float value = (float) correctVCs / timesToRun;
        writer.write("\n\n\tAccuracy of Inexact VC: " + value + "\n\n");

        int sum = 0;

        for (long i : values) {
            sum += i;
        }
        float avg = sum / values.length;
        writer.write("\nAverage time for exact vertex cover: " + avg + " ms\n\n");

        /*
         * inexactVC
         */
       /* int[] inexactVC;
        for (int i = 0; i < timesToRun; i++) {

            long start = System.currentTimeMillis();
            inexactVC = compute.inexactVC(graph);
            long elapsedTime = System.currentTimeMillis() - start;
            writer.write("Inexact vertex cover found in: " + elapsedTime + " ms\n");
            values[i] = elapsedTime;

               if(optimalVC.equals(inexactVC)){


            if(realValues.equals(inexactValues)){
                timesCorrect ++;
            }

        }
        float value = (float) timesCorrect / timesToRun;
        writer.write("\n\n\tAccuracy of Inexact VC: " + value + "\n\n");
        sum = 0;
        for (long i : values) {
            sum += i;
        }
        avg = sum / values.length;
        writer.write("\nAverage time for inexact vertex cover: " + avg + " ms\n\n");
*/
        /*
         * optimalIS
         */
        int[] optimalIS;
        int[] inexactIS;
        int correctIS = 0;
        for (int i = 0; i < timesToRun; i++) {
            long start = System.currentTimeMillis();
            optimalIS = compute.optimalIS(graph);
            long elapsedTime = System.currentTimeMillis() - start;
            writer.write("Optimal independent set found in: " + elapsedTime + " ms\n");
            values[i] = elapsedTime;
            inexactIS = GraphToolBox.inexactIS(graph);

            if (optimalIS.length == inexactIS.length) {
                correctIS++;
            }
        }
        value = (float) correctIS / timesToRun;
        writer.write("\n\n\tAccuracy of Inexact IS: " + value + "\n\n");
        sum = 0;
        for (long i : values) {
            sum += i;
        }
        avg = sum / values.length;
        writer.write("\nAverage time for optimal independent set: " + avg + " ms\n\n");
        /*
         * inexactIS
         */
        /*timesCorrect = 0;
        for(int i = 0; i < timesToRun; i++){
            long start = System.currentTimeMillis();
            inexactValues = GraphToolBox.inexactIS(graph);
            long elapsedTime = System.currentTimeMillis() - start;
            writer.write("Inexact independent set found in: " + elapsedTime + " ms\n");
            values[i] = elapsedTime;
            try{
                Arrays.sort(realValues);
                Arrays.sort(inexactValues);

        if(realValues.equals(inexactValues)){
            timesCorrect ++;
        }
            }catch (NullPointerException e) {}
    }
    float v = (float) timesCorrect / timesToRun;
        writer.write("\n\n\tAccuracy of Inexact IS: " + v + "\n\n");

        sum = 0;
        for(long i : values){
            sum += i;
        }
        avg = sum / values.length;
        writer.write("\nAverage time for inexact independent set: " + avg + " ms\n\n");

        writer.close();
    }*/
        writer.close();
    }
}
