package WhyAShark;
import org.jnetpcap.Pcap;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.util.PcapPacketArrayList;


/**
 * Created by Kenneth on 2017-03-28.
 */
public class PcapFile {



    String FileAddress = "";



    public PcapFile(String FileAddress){

        this.FileAddress = FileAddress;
    }

    public PcapPacketArrayList readOfflineFiles() {

        final StringBuilder errbuf = new StringBuilder();

        Pcap pcap = Pcap.openOffline(FileAddress, errbuf);

        if (pcap == null){
            System.err.printf("Error while opening file for parse: "
                    + errbuf.toString());
        }

        PcapPacketHandler<PcapPacketArrayList> jpacketHandler = new PcapPacketHandler<PcapPacketArrayList>(){

            public void nextPacket(PcapPacket packet, PcapPacketArrayList PacketsList) {

                PacketsList.add(packet);
            }

            };


        try {
            PcapPacketArrayList packets = new PcapPacketArrayList();
            pcap.loop(-1,jpacketHandler,packets);

            return packets;
        } finally {
            pcap.close();
        }




    }


}
