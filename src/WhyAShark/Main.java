package WhyAShark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This file/class Main.java is really just an application launcher. All it does is
 *  - create the base window (stage)
 *  - load the first scene
 *
 *  Once the scene is loaded all further actions are handled by event handlers in the controllers.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The primaryStage is the main window. The root is the highest level container in the scene graph. In this
     * case it is a VBox. If you look at InputForm.fxml in SceneBuilder you will see the entire scene graph for
     * this window.
     */
    private Stage primaryStage;
    private VBox root;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        primaryStage.setTitle("Input Sample Data");

        initRootLayout();
    }

    /**
     * initRootLayout is just a helper function. This code could have gone inside start ... but I think it is
     * cleaner to put it here ... this way start() creates the stage and sets the title, and initRootLayout()
     * creates the scene and displays it in the window.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("InputForm.fxml"));
            root = (VBox) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(root, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
