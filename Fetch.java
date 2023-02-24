import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;
public class Fetch{
    private static int balance;
    public Fetch(int balance){
        this.balance = balance;
    }

    public static void main(String[] args) {
        Fetch fe = new Fetch(5000);
        ArrayList<String> list = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<String> times = new ArrayList<>();
        try{

            Scanner scanner = new Scanner(new File("transactions.csv"));
            scanner.nextLine();
            int i = 0;
            while(scanner.hasNextLine()){
                list.add(scanner.nextLine());
                times.add(list.get(i).split(",")[2]);
                if(!map.containsKey(list.get(i).split(",")[0]))
                    map.put(list.get(i).split(",")[0], Integer.parseInt(list.get(i).split(",")[1]));
                else{
                    int add = Integer.parseInt(list.get(i).split(",")[1]);
                    map.replace(list.get(i).split(",")[0], map.get(list.get(i).split(",")[0]) + Integer.parseInt(list.get(i).split(",")[1]));
                }
                i ++;
            }

        }
        catch(FileNotFoundException f){
        }


        String ANSWER = calcBalance(list, times, map, balance);
        System.out.println(ANSWER);
    }

    public static String calcBalance(ArrayList<String> list, ArrayList<String> times,  HashMap<String, Integer> map, int balance){
        
        String [] time = times.toArray(new String[times.size()]);
        Arrays.sort(time);
        int i = 0;
        
        while(i < time.length && balance != 0){
            for(int j = 0; j < list.size(); j++){
                
                if(list.get(j).split(",")[2].equals(time[i])){
                    int payerBalance =map.get(list.get(j).split(",")[0]);
                    int toSub = Integer.parseInt(list.get(j).split(",")[1]);
                    if(payerBalance > 0){
                        if(balance - toSub > 0){
                            map.replace(list.get(j).split(",")[0], payerBalance-toSub);
                            balance -= toSub;
                        }
                        else{
                            map.replace(list.get(j).split(",")[0], map.get(list.get(j).split(",")[0]) - balance);
                            balance = 0;
                        }
                    }
                }
            }   
            i++;
        }

        String str = "";
        for(String s: map.keySet()){
            str += (s+ ": " + map.get(s) + "\n");
        }
        return str.trim();
    }
}