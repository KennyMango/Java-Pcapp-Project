package WhyAShark;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by lucassilva on 2017-03-30.
 */


public class StatsList {

    HashMap<String, Integer> list = new HashMap <>();


    public StatsList(){

    }

    public HashMap getStats(){
        int total = 0;
        Set<String> keys = list.keySet();
        HashMap<String, Integer> stats = new HashMap<>();


        String[] keyArray = keys.toArray(new String[keys.size()]);


        for(int i = 0; i < list.size(); i++){
            String ip = keyArray[i];

            total += list.get(ip);
        }

        for(int i = 0; i < list.size(); i++){
            String ip = keyArray[i];

            Integer x = list.get(ip)/total;
            stats.put(ip, x);
        }

        return stats;
    }

    public HashMap getData(){
        return list;
    }

    public void insert(String ip){
        if(list.containsKey(ip)){

            Integer x = list.get(ip);

            list.replace(ip, x + 1);
        }  else {
            list.put(ip, 1);
        }
    }

    public HashMap getTopEight(){
        HashMap <String, Integer> output = new HashMap<>();

        while(output.size()<8) {
            String key = "";
            int val = 0;

            for (String i : list.keySet()) {
                if (list.get(i) > val) {
                    if(!output.keySet().contains(key)) {
                        val = list.get(i);
                        key = i;
                    }
                }
            }

            output.put(key, val);
        }

        return output;
    }

    public void removeSniffer(){
        String key =  "";
        int val = 0;

        for (String i: list.keySet()){
            if (list.get(i) > val){
                val = list.get(i);
                key = i;
            }
        }

        list.remove(key);
    }

    @Override
    public String toString(){

        String output = "---\tIP List\t---\n";
        Set<String> keys = list.keySet();

        String[] keyArray = keys.toArray(new String[keys.size()]);

        for(int i = 0; i < list.size(); i++){

            String ip = keyArray[i];

            output += ip + "\tCount:\t" + list.get(ip) + "\n";
        }
        return output;
    }
}
