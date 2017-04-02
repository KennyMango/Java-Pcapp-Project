package WhyAShark;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by lucassilva on 2017-03-30.
 */

public class IpList extends StatsList{



    public IpList(){

    }

    public HashMap getStats(){
        int total = 0;
        Set<String> keys = list.keySet();
        HashMap<String, Double> stats = new HashMap<>();


        String[] keyArray = keys.toArray(new String[keys.size()]);


        for(int i = 0; i < list.size(); i++){
            String ip = keyArray[i];

            total += list.get(ip);
        }

        for(int i = 0; i < list.size(); i++){
            String ip = keyArray[i];

            double x = list.get(ip)/total;
            stats.put(ip, x);
        }

        return stats;
    }

    @Override
    public void insert(String ip){
        if(list.containsKey(ip)){

            Integer x = list.get(ip);

            list.replace(ip, x + 1);
        }  else {
            list.put(ip, 1);
        }
    }

    public String toString(){

        String output = "---\tIP List\t---\n";
        Set<String> keys = list.keySet();

        String[] keyArray = keys.toArray(new String[keys.size()]);

        for(int i = 0; i < list.size(); i++){

            String ip = keyArray[i];

            output += "IP Address:\t" + ip + "\tCount:\t" + list.get(ip) + "\n";
        }
        return null;
    }
}
