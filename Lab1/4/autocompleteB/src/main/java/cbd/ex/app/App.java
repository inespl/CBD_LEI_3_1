package cbd.ex.app;

import redis.clients.jedis.*;
import java.io.*;
import java.util.*;


public class App {
    private Jedis jedis;
    
    public App(){
        this.jedis = new Jedis("localhost");
    }

    public void getAllNames(){
        try{
            File file = new File("src/nomes-registados-2020.csv"); 
            Scanner sc = new Scanner(file);
            
            while (sc.hasNextLine()) { 
                String line = sc.nextLine();                
                String[] info = line.split(";");
                int score = Integer.parseInt(info[2]);
                jedis.zadd("Names", score, info[0].toLowerCase()); 
            } 
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
	public void principal(String word) {		
	    
        Map<Double, List<String>> map = new TreeMap<>(new ScoreComp());
        List<String> list;
	    String cursor = "0";
	    ScanParams sp = new ScanParams();
	    sp.match(word+"*");
	    sp.count(10000);
	    
        ScanResult<Tuple> ret = jedis.zscan("Names", cursor, sp);
        for(Tuple nextElem : ret.getResult()){
            list = new ArrayList<>();

            String[] elems = nextElem.toString().replace("[","").replace("]","").split(",");
            double score = Double.parseDouble(elems[1]);

            if(map.containsKey(score)){
                list = map.get(score);
                list.add(elems[0]);
                map.put(score, list);    
            }else{
                list.add(elems[0]);
                map.put(score, list);
            }
        }
        
        for (Map.Entry<Double,List<String>> entry : map.entrySet()){
            for(String name : entry.getValue())
                System.out.printf("%-13s \t (%.0f)\n", name, entry.getKey());        
        }  
            
		//System.out.println(map);
    }

    public static void main(String[] args){
        App app = new App();

        Scanner sc = new Scanner(System.in);
		System.out.print("Search for ('Enter' for quit):");
		String word = sc.next();
 
        app.getAllNames();
        app.principal(word);
    }

	
}

class ScoreComp implements Comparator<Double>{
 
    @Override
    public int compare(Double one, Double two) {
        if(one < two){
            return 1;
        } else {
            return -1;
        }
    }
}
