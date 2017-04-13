package WhyAShark;

import sun.reflect.generics.tree.Tree;

import java.util.*;

/**
 * Created by lucassilva on 2017-03-30.
 */


public class StatsList {

    TreeMap<String, Integer> list = new TreeMap <>();


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

    public TreeMap getData(){
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

    public Map<String, Integer> getTopEight(){
        List<Map.Entry<String, Integer>> output =
                new LinkedList<Map.Entry<String, Integer>>(list.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(output, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });


        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
            for (Map.Entry<String, Integer> entry : output) {
                sortedMap.put(entry.getKey(), entry.getValue());
                if(sortedMap.size()>=8){
                    return sortedMap;
                }
            }



        return null;
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
