package WhyAShark;
import org.jnetpcap.util.PcapPacketArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Created by Kenneth on 2017-03-28.
 */
public class RunPcap extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {

        PcapDST model  = retrieveDSTdb();



        /*

        String FileName = "";

        Scanner scnr = new Scanner(System.in);

        System.out.println("Enter File Address ");
        FileName = scnr.next();


        PcapFile Parse = new PcapFile(FileName);

        PcapPacketArrayList PList = Parse.readOfflineFiles();


        for (int i = 0; i < PList.size(); i++) {
            System.out.println(PList.get(i));
        }
        */


    }

    private static PcapDST retrieveDSTdb(){

        String FileName = "sample.pcap";

        PcapDST DST = new PcapDST(FileName);

        DST.readOfflineFiles();

        for (int i = 0; i < DST.getDST().size(); i++) {
            System.out.println("Destination:");
            System.out.println( DST.getDST().get(i));
        }

        System.out.println("---------------------------------------------------------------------------");


        for (int i = 0; i < DST.getSRC().size(); i++) {
            System.out.println("Source:");
            System.out.println(DST.getSRC().get(i));
        }

        return null;
    }
}
