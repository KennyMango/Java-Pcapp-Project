package WhyAShark;

/**
 * Created by lucassilva on 2017-04-11.
 */

import java.util.HashMap;

public class NumOfThings {

    private static HashMap<String, Integer> values = new HashMap<>();

    public NumOfThings (){
        values.put("numberOfPackets", 0);
        values.put("numberOfARPpackets", 0);
        values.put("numberOfTcpPackets", 0);
        values.put("numberOfSYN", 0);
        values.put("numberOfSYNACK", 0);
        values.put("numberOfACK", 0);
        values.put("numberOfPSHACK", 0);
        values.put("numberOfFINPSHACK", 0);
        values.put("numberOfFINACK", 0);
        values.put("numberOfRST", 0);
        values.put("numberOfSslTls", 0);
        values.put("numberOfUdpPackets", 0);
        values.put("numberOfDNS", 0);
        values.put("numberOfHTTPpackets", 0);
        values.put("numberOfGETS", 0);
        values.put("numberOfPosts", 0);
        values.put("numberOfImages", 0);
    }

    public HashMap getValues(){
        return values;
    }

    public int getNumberOfPackets(){
        return values.get("numberOfPackets");
    }

    public void addNumberOfPackets(){
        int tmp = values.get("numberOfPackets");
        values.replace("numberOfPackets", tmp+1);
    }

}
