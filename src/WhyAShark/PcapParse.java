package WhyAShark;

import org.jnetpcap.Pcap;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.application.WebImage;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by Kenneth on 2017-04-10.
 */
public class PcapParse {

    private static Pcap pcap;
    private static String FileAddress = "";

    private static final Ethernet ethernet = new Ethernet();
    private static final Http http = new Http();
    private static final Tcp tcp = new Tcp();
    private static final Udp udp = new Udp();
    private static final Ip4 ip = new Ip4();
    private static final WebImage webimage = new WebImage();
    private static StatsList DSTlist = new StatsList();
    private static StatsList SRClist = new StatsList();

    private static NumOfThings data = new NumOfThings();

    private static HashMap<String, String> ipAddressesVisited = new HashMap<>();
    private static TreeSet<Integer> clientPortsUsed = new TreeSet<>();
    private static TreeSet<Integer> serversPortsUsed = new TreeSet<>();
    private static HashMap<String, Integer> imageTypes = new HashMap<>();

    private static String macAddress = "";

    private static PrintWriter writer;


    public PcapParse(String File){

        this.FileAddress = File;
    }

    public void readOfflineFiles() {

        try
        {
            setMacAddress();

            writer = new PrintWriter("Report.txt", "UTF-8");

            StringBuilder errbuf = new StringBuilder();

            pcap = Pcap.openOffline(FileAddress, errbuf);

            if (pcap == null)
            {
                System.err.println(errbuf);

                return;
            }
            PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>()
            {

                public void nextPacket(PcapPacket packet, String user)
                {
                    data.update("numberOfPackets");

                    if (packet.hasHeader(ethernet))
                    {
                        processEthernetheader();

                        if (packet.hasHeader(ip))
                        {
                            processIPheader();

                            createIPlist(packet);

                            if (packet.hasHeader(tcp))
                            {
                                processTCPheader();
                            }
                            else if (packet.hasHeader(udp))
                            {
                                processUDPheader();
                            }

                            if (packet.hasHeader(http))
                            {
                                processHTTPheader();

                            }

                            if (packet.hasHeader(webimage))
                            {
                                processImage();
                            }
                        }
                    }
                }
            };

            pcap.loop(Pcap.LOOP_INFINITE, jpacketHandler, " *");


            printTrafficStatistics();
            printTCPflagsStatistics();
            printImageTypes();
            printPortsUsed("Servers' ", serversPortsUsed);
            printPortsUsed("Client's ", clientPortsUsed);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            pcap.close();
            writer.close();
        }

    }

//    public static void main(String[] args)
//    {
//        try
//        {
//            setMacAddress();
//
//            writer = new PrintWriter("Report.txt", "UTF-8");
//
//            String pcapName = "sample.pcap";
//
//            StringBuilder errbuf = new StringBuilder();
//
//            pcap = Pcap.openOffline(pcapName, errbuf);
//
//            if (pcap == null)
//            {
//                System.err.println(errbuf);
//
//                return;
//            }
//            PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>()
//            {
//
//                public void nextPacket(PcapPacket packet, String user)
//                {
//                    data.update("numberOfPackets");
//
//                    if (packet.hasHeader(ethernet))
//                    {
//                        processEthernetheader();
//
//                        if (packet.hasHeader(ip))
//                        {
//                            processIPheader();
//
//                            createIPlist(packet);
//
//                            if (packet.hasHeader(tcp))
//                            {
//                                processTCPheader();
//                            }
//                            else if (packet.hasHeader(udp))
//                            {
//                                processUDPheader();
//                            }
//
//                            if (packet.hasHeader(http))
//                            {
//                                processHTTPheader();
//
//                            }
//
//                            if (packet.hasHeader(webimage))
//                            {
//                                processImage();
//                            }
//                        }
//                    }
//                }
//            };
//
//            pcap.loop(Pcap.LOOP_INFINITE, jpacketHandler, " *");
//
//            printTrafficStatistics();
//            printTCPflagsStatistics();
//            printImageTypes();
//            printPortsUsed("Servers' ", serversPortsUsed);
//            printPortsUsed("Client's ", clientPortsUsed);
//
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            pcap.close();
//            writer.close();
//        }
//
//    }



    static void setMacAddress()
    {
        try
        {
            InetAddress ip2 = InetAddress.getLocalHost();

            NetworkInterface network = NetworkInterface.getByInetAddress(ip2);

            byte[] mac = network.getHardwareAddress();

            if (mac != null)
            {
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < mac.length; i++)
                {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                macAddress = sb.toString().replaceAll("-", ":");
            }
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
    }

    public String getMacAddress(){
        return macAddress;
    }



    /**
     * Processes the ethernet header of this packet
     */
    private static void processEthernetheader()
    {
        String temp = FormatUtils.hexdump(ethernet.getHeader());

        if (temp.substring(45, 50).equals("08 06"))
        {
            data.update("numberOfARPpackets");
        }

    }

    /**
     * Processes the IP header of this packet
     */
    private static void processIPheader()
    {

        String sourceMac = FormatUtils.mac(ethernet.source());

        String destinationIP = FormatUtils.ip(ip.destination());

        getDestinationAddress(sourceMac, destinationIP);
    }

    private static void createIPlist(PcapPacket packet){
        packet.getHeader(ip);
        byte[] dIP = new byte[4], sIP = new byte[4];
        dIP = packet.getHeader(ip).destination();
        sIP = packet.getHeader(ip).source();
        String sourceIP = FormatUtils.ip(sIP);
        String destinationIP = FormatUtils.ip(dIP);
        DSTlist.insert(destinationIP);
        SRClist.insert(sourceIP);

        DSTlist.removeSniffer();
        SRClist.removeSniffer();
        

    }



    /**
     * Processes the TCP header of this packet
     */
    private static void processTCPheader()
    {
        data.update("numberOfTcpPackets");

        int sport = tcp.source();

        int dport = tcp.destination();

        addPorts(sport, dport);

        processTCPflags();

        processPorts(sport, dport);
    }

    /**
     * Processes the flags of this packet's TCP header
     * TCP Flags include: [SYN], [SYN ACK], [ACK], [PSH ACK]
     * [FIN PSH ACK], [FIN ACK], [RST]
     */
    private static void processTCPflags()
    {
        if (tcp.flags_SYN() && (!tcp.flags_ACK()))
        {
            data.update("numberOfSYN");
        }
        else if (tcp.flags_SYN() && tcp.flags_ACK())
        {
            data.update("numberOfSYNACK");
        }
        else if (tcp.flags_ACK() && (!tcp.flags_SYN()) && (!tcp.flags_PSH()) && (!tcp.flags_FIN()) && (!tcp.flags_RST()))
        {
            data.update("numberOfACK");
        }
        else if (tcp.flags_PSH() && (tcp.flags_ACK() && (!tcp.flags_FIN())))
        {
            data.update("numberOfPSHACK");
        }
        else if (tcp.flags_FIN() && tcp.flags_ACK() && (!tcp.flags_PSH()))
        {
            data.update("numberOfFINACK");
        }
        else if (tcp.flags_PSH() && (tcp.flags_ACK() && (tcp.flags_FIN())))
        {
            data.update("numberOfFINPSHACK");
        }
        else if (tcp.flags_RST())
        {
            data.update("numberOfRST");
        }
    }

    /**
     * Processes the ports of a packet using transport layer protocols (TCP, UDP)
     *
     */
    private static void processPorts(int sport, int dport)
    {
        if (sport == 53 || dport == 53)
        {
            data.update("numberOfDNS");
        }
        else if (sport == 443 || dport == 443)
        {
            data.update("numberOfSslTls");
        }
    }


    private static void addPorts(int sport, int dport)
    {
        String sourceMac = FormatUtils.mac(ethernet.source());

        String destinationMac = FormatUtils.mac(ethernet.destination());

        if (sourceMac.equals(macAddress))
        {
            clientPortsUsed.add(sport);

            serversPortsUsed.add(dport);
        }
        else if (destinationMac.equals(macAddress))
        {
            clientPortsUsed.add(dport);

            serversPortsUsed.add(sport);
        }
    }

    /**
     * Processes the UDP header of this packet
     */
    private static void processUDPheader()
    {
        data.update("numberOfUdpPackets");

        int sport = udp.source();

        int dport = udp.destination();

        addPorts(sport, dport);

        processPorts(sport, dport);
    }

    /**
     * Processes the HTTP header of this packet
     */
    private static void processHTTPheader()
    {
        data.update("numberOfHTTPpackets");

        if (http.header().contains("GET"))
        {
            data.update("numberOfGETS");
        }
        else if (http.header().contains("POST"))
        {
            data.update("numberOfPosts");
        }
    }

    /**
     * Processes images transferred over HTTP
     * Images transferred over SSL/TLS are not processed
     */
    private static void processImage()
    {
        data.update("numberOfImages");

        String imageType = http.contentTypeEnum().toString();

        Integer count = imageTypes.get(imageType);

        if (count == null)
        {
            imageTypes.put(imageType, 1);
        }
        else
        {
            imageTypes.put(imageType, count + 1);
        }
    }

    private static StatsList getDST(){
        return DSTlist;
    }

    private static StatsList getSRC(){
        return SRClist;
    }

    public static HashMap<String, String> getIpAddressesVisited() {
        return ipAddressesVisited;
    }

    public static TreeSet<Integer> getClientPortsUsed() {
        return clientPortsUsed;
    }

    public static TreeSet<Integer> getServersPortsUsed() {
        return serversPortsUsed;
    }

    public static HashMap<String, Integer> getImageTypes() {
        return imageTypes;
    }



    /**
     * Prints the distributions among the different image types that
     * have been downloaded in the machine
     */
    private static void printImageTypes()
    {
        writer.printf("%s %d %s \n", "Found ", data.getNum("numberOfImages"), " images (images transferred over SSL/TLS not included):");

        for (Map.Entry entry : imageTypes.entrySet())
        {
            writer.printf("%-4s %s %d \n", entry.getKey(), " ", entry.getValue());
        }
    }

    /**
     * Adds the IP destination address to the Map of IP addresses visited

     */
    private static void getDestinationAddress(String sourceMac, String destinationIP)
    {
        try
        {
            if (sourceMac.equals(macAddress))
            {
                ipAddressesVisited.put(destinationIP, "");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Prints the ports that have been used
     */
    private static void printPortsUsed(String machine, TreeSet<Integer> portsUsed)
    {
        writer.println();

        writer.println(machine + " ports used:");

        int i = 0;

        for (int port : portsUsed)
        {
            i++;

            writer.printf("%d  ", port);

            if (i % 18 == 0)
            {
                writer.println();
            }
        }
        writer.println();
    }


    private static void printTrafficStatistics()
    {
        writer.printf("Report for " + FileAddress + "\n\n");
        writer.printf("%-46s %s %8d \n", "Total number of packets in pcap", ": ", data.getNum("numberOfPackets"));
        writer.printf("%-45s  %s %8d \n", "ARP packets", ": ", data.getNum("numberOfARPpackets"));

        writer.printf("%-45s  %s %8d \n", "TCP packets", ": ", data.getNum("numberOfTcpPackets"));
        writer.printf("%-45s  %s %8d \n", "SSL/TLS packets", ": ", data.getNum("numberOfSslTls"));

        writer.printf("%-45s  %s %8d \n", "UDP packets", ": ", data.getNum("numberOfUdpPackets"));
        writer.printf("%-45s  %s %8d \n", "DNS packets", ": ", data.getNum("numberOfDNS"));
        writer.printf("%-45s  %s %8d \n", "HTTP packets", ": ", data.getNum("numberOfHTTPpackets"));
        writer.printf("%-45s  %s %8d \n", "Number of  GET requests", ": ", data.getNum("numberOfGETS"));
        writer.printf("%-45s  %s %8d \n", "Number of POST requests", ": ", data.getNum("numberOfPosts"));
    }



    private static void printTCPflagsStatistics()
    {
        writer.println();
        writer.println("TCP Flags distribution: ");
        writer.printf("%-12s %s %8d %5.2f %s \n", "SYN", ": ", data.getNum("numberOfSYN"), data.calculateFlagStats("numberOfSYN"), "%");
        writer.printf("%-12s %s %8d %5.2f %s \n", "SYN ACK", ": ", data.getNum("numberOfSYNACK"), data.calculateFlagStats("numberOfSYNACK"), "%");
        writer.printf("%-12s %s %8d %5.2f %s \n", "ACK", ": ", data.getNum("numberOfACK"), data.calculateFlagStats("numberOfACK"), "%");
        writer.printf("%-12s %s %8d %5.2f %s \n", "PSH ACK", ": ", data.getNum("numberOfPSHACK"), data.calculateFlagStats("numberOfPSHACK"), "%");
        writer.printf("%-12s %s %8d %5.2f %s \n", "FIN PSH ACK", ": ", data.getNum("numberOfFINPSHACK"), data.calculateFlagStats("numberOfFINPSHACK"), "%");
        writer.printf("%-12s %s %8d %5.2f %s \n", "FIN ACK", ": ", data.getNum("numberOfFINACK"), data.calculateFlagStats("numberOfFINACK"), "%");
        writer.printf("%-12s %s %8d %5.2f %s \n", "RST", ": ", data.getNum("numberOfRST"), data.calculateFlagStats("numberOfRST"), "%");
        writer.println();
    }


}
