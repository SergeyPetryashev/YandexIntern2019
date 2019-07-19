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

	public static void main(String[] args) throws IOException {
        File file = new File("inputA.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int numWords = Integer.parseInt(br.readLine()); 
        List<String> listWords = new ArrayList<>(numWords);
        String str="";
        while((str=br.readLine())!=null) {
        	listWords.add(str);
        }
        br.close();
        graphVertex(listWords);
	}
	
	private static void graphVertex(List<String> words) {
		Map<String, Integer> edge = new HashMap<>();
		Set<String> vertex = new HashSet<>();
		for(int i=0;i<words.size();i++) {
			String start=null, end=null;
			for(int j=3;j<words.get(i).length();j++) {
				start = words.get(i).substring(j-3,j);
				end = words.get(i).substring(j-2,j+1);
				
				if(edge.containsKey(start+end)) {
					edge.put(start+end,edge.get(start+end)+1);
				}else {
					edge.put(start+end, 1);
				}				
				vertex.add(start);				
			}
			vertex.add(end);
		}
		
		System.out.println(vertex.size());
		System.out.println(edge.size());
		edgeDisplay(edge);

	}
	
	private static void edgeDisplay(Map<String, Integer> map) {
		Set<Map.Entry<String,Integer>> set = map.entrySet();
		for(Map.Entry<String,Integer> me : set) {
			System.out.println(me.getKey().substring(0, 3)+" "+me.getKey().substring(3, 6)+" "+me.getValue());
		}
	}
}
