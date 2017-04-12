package WhyAShark;
import org.jnetpcap.Pcap;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.util.PcapPacketArrayList;

import java.util.ArrayList;


/**
 * Created by Kenneth on 2017-03-30.
 */



public class PcapDST {

    String FileAddress = "";

    private ArrayList<String> DstList = new ArrayList();
    private ArrayList<String> SrcList = new ArrayList();


    public PcapDST(String FileAddress){

        this.FileAddress = FileAddress;
    }

    public void readOfflineFiles() {

        final StringBuilder errbuf = new StringBuilder();

        Pcap pcap = Pcap.openOffline(FileAddress, errbuf);

        if (pcap == null){
            System.err.printf("Error while opening file for parse: "
                    + errbuf.toString());
        }

        PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {

            final Ip4 ip = new Ip4();
            public void nextPacket(PcapPacket packet, String user) {

                if(packet.hasHeader(Ip4.ID)){
                    packet.getHeader(ip);
                    byte[] dIP = new byte[4], sIP = new byte[4];
                    dIP = packet.getHeader(ip).destination();
                    sIP = packet.getHeader(ip).source();
                    String sourceIP = FormatUtils.ip(sIP);
                    String destinationIP = FormatUtils.ip(dIP);
                    DstList.add(destinationIP);
                    SrcList.add(sourceIP);
                    /**
                    System.out.printf("tcp.ip_src=%s%n",sourceIP);
                    System.out.printf("tcp.ip_dest=%s%n",destinationIP);
                     */
                }

            }
        };
        pcap.loop(-1, jpacketHandler, null);





    }

    public ArrayList<String> getDST(){

        return DstList;
    }

    public ArrayList<String> getSRC(){

        return SrcList;

    }

}

