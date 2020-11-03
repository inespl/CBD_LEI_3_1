package cbd.app;

import java.util.*;

import redis.clients.jedis.Jedis;

public class HashImpl {
	private Jedis jedis;

	public static String USERS = "users"; // Key map for users' name

	public HashImpl(){
		this.jedis = new Jedis("localhost");
	}

	public void saveUser(String username){
		jedis.hset(USERS, "name", username);
	}

	public List<String> getUser(){
		return jedis.hvals(USERS);
	}
 
	public Map<String, String> getAllKeys() {
		return jedis.hgetAll(USERS);
	}

	public static void main(String[] args) {
		SimplePost board = new SimplePost();
		// set some users
		String[] users = { "Ana", "Pedro", "Maria", "Luis" };
		for (String user: users)
			board.saveUser(user);
		
		System.out.println("ALL KEYS:");
		for(String user : board.getAllKeys()){
			System.out.println(user);	
		}

		System.out.println("\nUSERS:");
		for(String user : board.getUser()){
			System.out.println(user);	
		}

	} 
}




