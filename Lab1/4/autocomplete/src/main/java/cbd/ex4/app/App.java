package cbd.ex4.app;

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
            File file = new File("src/female-names.txt"); 
            Scanner sc = new Scanner(file);
            
            while (sc.hasNextLine()) { 
                String name = sc.nextLine();
                jedis.zadd("Names", 0, name);
            }
        } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    }
	public void principal(String word) {		
	    Set<String> keys = new TreeSet<>();
	    String cursor = "0";
	    ScanParams sp = new ScanParams();
	    sp.match(word+"*");
	    sp.count(10000);
	    
        ScanResult<Tuple> ret = jedis.zscan("Names", cursor, sp);
        for(Tuple nextElem : ret.getResult()){
            keys.add(nextElem.getElement());
        }
		
        for (String value : keys) 
            System.out.println(value); 
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
