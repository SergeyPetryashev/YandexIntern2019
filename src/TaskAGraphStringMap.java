import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/*Word S forms n-2 words of length 3:
 * w1 = s1s2s3, w2 = s2s3s4, w3 = s3s4s5… wn-2 = sn-2 sn-1 sn;
 * if for some of the words wi there is no vertex in the graph, then it is created;
 * for each pair of words (wi, wi + 1), an oriented edge of weight 1 is added, 
 * or the weight of an existing edge increases by 1. Thus, we obtain a graph G with v vertices
 * and e oriented edges. Between some vertices there can be several arcs (from a to b and from b to a).
 * For a given set of words, find the number of vertices in the graph and 
 * derive oriented edges between the vertices. 
 */
public class TaskAGraphStringMap {
	static Map<String, Integer> edge = new HashMap<>();
	static Set<String> vertex = new HashSet<>();
	public static void main(String[] args) throws IOException {
        File file = new File("inputA.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int numWords = Integer.parseInt(br.readLine()); 
        String str="";
        while((str=br.readLine())!=null) {
        	graphVertex(str);
        }
        br.close();
		System.out.println(vertex.size());
		System.out.println(edge.size());
		edgeDisplay(edge);
	}
	
	private static void graphVertex(String word) {
		String subWord=null;
		for(int j=3;j<word.length();j++) {
			subWord = word.substring(j-3,j+1);
			
			if(edge.containsKey(subWord)) {
				edge.put(subWord,edge.get(subWord)+1);
			}else {
				edge.put(subWord, 1);
			}
			vertex.add(subWord.substring(0, 3));				
		}
		vertex.add(subWord.substring(1, 4));
	}
	
	private static void edgeDisplay(Map<String, Integer> map) {
		Set<Map.Entry<String,Integer>> set = map.entrySet();
		for(Map.Entry<String,Integer> me : set) {
			System.out.println(me.getKey().substring(0, 3)+" "+me.getKey().substring(1, 4)+" "+me.getValue());
		}
	}
}
