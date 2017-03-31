import java.util.HashMap;

/**
 * Created by lucassilva on 2017-03-30.
 */
public class IpList extends StatsList{

    public IpList(){}

    public HashMap getStats(){
        int total = 0;

        for(int i = 0; i < list.size(); i++){
            String ip = (String)list.keySet().get(i);

            total += (Integer)list.get(ip);
        }

        for(int i = 0; i < list.size(); i++){
            String ip = (String)list.keySet().get(i);
        }
    }

    @Override
    public void insert(String ip){
        if(list.containsKey(ip)){

            Integer x = (Integer)list.get(ip);

            list.replace(ip, x + 1);
            return;
        }  else {
            list.put(ip, 1);
            return;
        }
    }

    public String toString(){
        String output = "---\tIP List\t---\n";

        for(int i = 0; i < list.size(); i++){

            String ip = (String)list.keySet().get(i);

            output += "IP Address:\t" + ip + "\tCount:\t" + list.get(ip) + "\n";
        }
    }
}
