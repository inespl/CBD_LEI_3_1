package app;

import java.util.*;
import redis.clients.jedis.Jedis;


public class App {
    private Jedis jedis;
	
	public App() {
		this.jedis = new Jedis("localhost");
	}
    
    public boolean checkIfUserExists(String username){
        Set<String> keys = jedis.keys("*");
		Object[] k = keys.toArray();
        for(Object o : k){           
            if(o.equals(username + ":info"))
                return true;
		}
        return false;
    }

    public void addUser(String username){
        jedis.hset(username + ":info", "username", username);
        jedis.hset(username + ":info", "subs", "[]");
    }

    public void subscribe(String username, String userToSub){
        String subs = jedis.hget(username + ":info", "subs");
        if(subs.equals("[]")){
            jedis.hset(username + ":info", "subs", "["+userToSub+"]");
        }else{
            if(subs.contains(userToSub))
                System.out.println("You are already subscribed to " + userToSub);
            else{
                String[] s = subs.split("]");
                jedis.hset(username + ":info", "subs", s[0] + ";" + userToSub+"]");
            }
        }
    }

    public void sendMessages(String username, String message){
        jedis.lpush(username + ":messages", message);
    }

    public void readMessages(String username){
        // ir às subscriptions com um hget, com o username ver as mensagens mandadas
        String subs = jedis.hget(username + ":info", "subs");
        
        if(subs.equals("[]"))
            System.out.println("No subscriptions");
        else{
            subs = subs.replace("[","").replace("]","");

            String[] ss = subs.split(";");
            for(String sub : ss){
                List<String> messages = jedis.lrange(sub + ":messages", 0, -1);
                if(messages.isEmpty())
                    System.out.println("No messages from: " + sub);    
                else{
                    System.out.println("Messages from: " + sub);
                    for (String msg : messages)
                        System.out.println(msg);
                }
            }
        }        
    }
    
    public void allUsers(){
        Set<String> keys = jedis.keys("*");
		Object[] k = keys.toArray();
        if (k.length == 0){
            System.out.println("No users");
        }else
            for(Object o : k){
                String s = o.toString();
                System.out.println(s.replace(":info",""));
            }
    }

    public void subscriptions(String username){
        String subs = jedis.hget(username + ":info", "subs");
        
        if(subs.equals("[]"))
            System.out.println("You don't subscriptions");
        else{
            subs = subs.replace("[","").replace("]","");
            String[] ss = subs.split(";");
            System.out.println("Your subscriptions:");
            for(String sub : ss)
                System.out.println("\t" + sub);
        }
    }

    public void messagesSent(String username){
        List<String> messages = jedis.lrange(username + ":messages", 0, -1);
        if(messages.isEmpty())
            System.out.println("You haven't send any messages from");    
        else{
            System.out.println("Your messages: ");
            for (String msg : messages)
                System.out.println("\t" + msg);
        }
    }

    public void deleteAll(){
        jedis.flushAll();
    }
    
    
    public static void main( String[] args ){
        App app = new App();

        Scanner in = new Scanner(System.in);            
        Scanner sc = new Scanner(System.in);  
        
        int choice;
        String username;
        // Menu
        do{    
            System.out.println();
            System.out.println("1 - Add User");
            System.out.println("2 - Suscribe to User");
            System.out.println("3 - Send Messages");
            System.out.println("4 - Read Messages");
            System.out.println("5 - See all users");
            System.out.println("6 - Check your info");
            System.out.println("7 - Delete All");
            System.out.println("0 - Exit");

            System.out.print("Action: ");
            choice = in.nextInt();
            System.out.println();
            
            switch(choice){
                case 1:     // ADD USER
                    System.out.print("\tUsername: ");
                    username = in.next();
                    
                    if (app.checkIfUserExists(username)==true){
                        System.out.println("Error! That username already exists!");
                    }else{
                        app.addUser(username);
                    }
                    break;
                
                case 2:     // SUBSCRIBE TO USER
                    System.out.print("\tYour username: ");
                    username = in.next();
                    if (app.checkIfUserExists(username)==false)
                        System.out.println("Error! Your username dosen't exist!");
                    else{
                        System.out.print("\tUsername to subscribe: ");
                        String usernameSubs = sc.next();
                        if(!app.checkIfUserExists(usernameSubs))
                            System.out.println("Error! Username of subscription dosen't exist!");
                        else
                            app.subscribe(username, usernameSubs);
                    }
                    break;

                case 3:     // SEND MESSAGES
                    System.out.print("\tYour username: ");
                    username = in.next();
                    if (app.checkIfUserExists(username)==false)
                        System.out.println("Error! Your username dosen't exist!");
                    else{
                        System.out.print("\tMessage to send: ");
                        String message = sc.nextLine();
                        app.sendMessages(username, message);
                        System.out.println("Message sent!");
                    }
                    break;

                case 4:     // READ MESSAGES
                    System.out.print("\tYour username: ");
                    username = in.next();
                    if (app.checkIfUserExists(username)==false){
                        System.out.println("Error! Your username dosen't exist!");
                        break;
                    }
                    app.readMessages(username);
                    break;
                
                case 5:     // ALL USERS
                    app.allUsers();
                    break;

                case 6:     // CHECK INFO
                    System.out.print("\tYour username: ");
                    username = in.next();
                    
                    if (app.checkIfUserExists(username)==false)
                        System.out.println("Error! Your username dosen't exist!");
                    else{
                        app.subscriptions(username);
                        app.messagesSent(username);
                    }

                    break;

                case 7:     // DELETE ALL
                    System.out.print("\tAre you sure you want to delete everything (yes/no): ");
                    String answer = in.next().toLowerCase();
                    if(answer.equals("yes")){
                        app.deleteAll();
                        System.out.print("Everything is gone");
                    }else
                        System.out.print("Nothing was deleted");


            }
            
        }while(choice != 0);
        System.out.print("Goodbye!");
        in.close(); 
        sc.close();
    }
}
