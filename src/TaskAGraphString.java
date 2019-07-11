import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskAGraphString {

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
		List<Edge> edge = new ArrayList<>();
		List<String> vertex = new ArrayList<>();
		for(int i=0;i<words.size();i++) {
			String start=null;
			for(int j=3;j<words.get(i).length()+1;j++) {
				
				String end = words.get(i).substring(j-3,j);
				
				if(start!=null) {
					int index;
					if((index=isNewEdge(edge, start, end))==-1) {
						edge.add(new Edge(start, end, 1));
					}else {
						edge.get(index).addWeight();
					}						
				}
				
				if(isNewVertex(vertex, end)) {
					vertex.add(end);
				}
				start=end;
			}
		}
		
		System.out.println(vertex.size());
		System.out.println(edge.size());
		for(Edge e: edge) {
			e.display();
		}
		
	}
	
	private static int isNewEdge(List<Edge> edge, String start, String end){
		for (int i=0;i<edge.size();i++) {
			String startCurrent = edge.get(i).getStartVertex();
			String endCurrent = edge.get(i).getEndVertex();
			if(startCurrent.equals(start) && endCurrent.equals(end)) {
				return i;
			};
		}
		return -1;
	}
	
	private static boolean isNewVertex(List<String> vert, String word) {
		for(String s: vert) {
			if(s.equals(word)) {
				return false;
			}
		}
		return true;
	}

}

class Edge{
	String startVertex;
	String endVertex;
	int weight;
	public Edge(String startVertex, String endVertex, int weight ) {
		this.startVertex=startVertex;
		this.endVertex=endVertex;
		this.weight=weight;
	}
	
	public String getStartVertex() {
		return startVertex;
	}
	
	public String getEndVertex() {
		return endVertex;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void addWeight() {
		weight++;
	}
	
	public void display() {
		System.out.println(startVertex +" "+ endVertex+ " "+weight);
	}
}
