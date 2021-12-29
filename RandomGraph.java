import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomGraph {
    public RandomGraph(){

    }

    public String create(String fileName,int vertices) throws FileNotFoundException {
        Random random = new Random();
        PrintWriter writer = new PrintWriter(fileName);
        writer.write("numVert " + vertices);
        writer.write("\nvertex numNeighbors neighbor1 neighbor 2 ...\n");

        //create big graph list
        ArrayList<Integer>[] graph = new ArrayList[vertices];
        ArrayList<Integer> values = new ArrayList<>();
        for(int i = 0; i < vertices; i++){
            graph[i] = new ArrayList<>();
            values.add(i);
        }
        //add random number of edges to each vertex
        for(int i = 0; i < graph.length; i++){
            Collections.shuffle(values);
            int edges = random.nextInt(graph.length);
            for(int j = 0; j < edges - graph[i].size(); j++){
                if(!graph[i].contains(values.get(j))) {
                    graph[i].add(values.get(j));
                }
                //add each side of edge
                if(!graph[values.get(j)].contains(i)) {
                    graph[values.get(j)].add(i);
                }
            }
        }
        //write graph to file
        for(int i = 0; i < graph.length; i++){
            writer.write(i + " " + graph[i].size() + " ");
            for(int j = 0; j < graph[i].size(); j++){
                if(graph[i].size() == 0) {
                    writer.write("\n");
                }else if(j == graph[i].size() - 1){
                    writer.write(graph[i].get(j) + "\n");
                }else{
                    writer.write(graph[i].get(j) + " ");
                }
            }
            if(graph[i].size() == 0){
                writer.write("\n");
            }
        }
        writer.close();
        return fileName;
    }
}
