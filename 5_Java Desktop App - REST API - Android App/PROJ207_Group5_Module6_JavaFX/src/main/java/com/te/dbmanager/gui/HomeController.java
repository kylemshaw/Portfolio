//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Kyle Shaw, Snow Tran, and Adolphus Cox
//     Description: Handles logic for home-view.fxml view including main menu
//                  and switch out views according to menu selection.
//----------------------------------------------------------------------------

package com.te.dbmanager.gui;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.te.dbmanager.TravelExpertsApplication;
import com.te.dbmanager.model.Agent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomeController {
    private double  mousePressedX, mousePressedY; //used for click and drag feature
    private Agent currentUser; //user that has logged in
    Node source = null; //used in handleMenuAction event to control formatting

    @FXML
    private BorderPane bpHome;

    @FXML
    private VBox vbMenu;

    @FXML
    private ImageView imgAgent;

    @FXML
    private Label lblAgentName;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnBookings;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSuppliers;

    @FXML
    private Button btnProducts;

    @FXML
    private Button btnReports;

    @FXML
    private Button btnSignout;

    @FXML
    private HBox hbTitle;

    @FXML
    private Circle btnClose;

    @FXML
    private Circle btnMinimize;

    //Getters/Setters

    public void setCurrentUser(Agent agent) {
        currentUser = agent;

        //Customize app for agent (set name and load picture)
        if(currentUser != null){
            lblAgentName.setText(currentUser.getAgtFirstName() + " " + currentUser.getAgtLastName());
            String fileName = "src\\main\\resources\\com\\te\\dbmanager\\agent_images\\" + currentUser.getAgtPhoto();
            File file = new File(fileName);
            Image image = new Image(file.toURI().toString());
            imgAgent.setImage(image);
        }

        //******Snow added, to make agent picture round
        Rectangle clip = new Rectangle(
                imgAgent.getFitWidth(), imgAgent.getFitHeight()
        );
        clip.setArcWidth(75);
        clip.setArcHeight(75);
        imgAgent.setClip(clip);

    }

    public Agent getCurrentUser() {
        return currentUser;
    }


    //Methods

    @FXML
    void handleLogout(ActionEvent event) {
        //event triggered when sign out menu option is clicked (set in fxml)
        try {
            //configure and open login view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent parent = null;
            parent = fxmlLoader.load();
            LoginController loginView = fxmlLoader.<LoginController>getController();

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED); //hide the title bar (we made our own)
            stage.setScene(scene);
            stage.show();

            //close home view
            closeDialog(event);

        } catch (IOException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("Logout failed.");
        }
    }

    @FXML
    void handleMenuAction(ActionEvent event) {
        //event is triggered when a menu button is clicked (set in fxml)

        String menuButtonID = ((Button) event.getSource()).getId(); //ex: btnCustomer
        String fileName = "gui/" + menuButtonID.substring(3).toLowerCase() + "-view.fxml";// ex: gui/customers-view.fxml

        //report special case. TODO: create code similar to above when more reports are added
        if (menuButtonID.equals("btnReports"))
            fileName = "gui/booking-report-view.fxml";
        //***

        System.out.println("You clicked " + menuButtonID); //debugging
        System.out.println("Full String: " + fileName); //debugging

        //load file that corresponds to menu button press in the center pane
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TravelExpertsApplication.class.getResource(fileName));
            Pane pane = fxmlLoader.load();

            if (fileName.equals("gui/dashboard-view.fxml")) {
                DashboardController dashboard = fxmlLoader.<DashboardController>getController();
                dashboard.setAgent(getCurrentUser());
            }
            bpHome.setCenter(pane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        //******** Snow added the following code for highlighting the selected button color,
        //******** because the pseudo "focus" class kept losing focus when click inside table-view
        if (source != null) {
            source.setStyle("-fx-background-color: transparent");
            source = null;
        } else {
            btnDashboard.setStyle("-fx-background-color: transparent;");
        }
        source = (Node) event.getSource();
        source.setStyle("-fx-background-color: #1620A1;");
    }

    @FXML
    void initialize() {
        assert bpHome != null : "fx:id=\"bpHome\" was not injected: check your FXML file 'home-view.fxml'.";
        assert vbMenu != null : "fx:id=\"vbMenu\" was not injected: check your FXML file 'home-view.fxml'.";
        assert imgAgent != null : "fx:id=\"imgAgent\" was not injected: check your FXML file 'home-view.fxml'.";
        assert lblAgentName != null : "fx:id=\"lblAgentName\" was not injected: check your FXML file 'home-view.fxml'.";
        assert btnDashboard != null : "fx:id=\"btnDashboard\" was not injected: check your FXML file 'home-view.fxml'.";
        assert btnBookings != null : "fx:id=\"btnBookings\" was not injected: check your FXML file 'home-view.fxml'.";
        assert btnCustomers != null : "fx:id=\"btnCustomers\" was not injected: check your FXML file 'home-view.fxml'.";
        assert btnPackages != null : "fx:id=\"btnPackages\" was not injected: check your FXML file 'home-view.fxml'.";
        assert btnSuppliers != null : "fx:id=\"btnSuppliers\" was not injected: check your FXML file 'home-view.fxml'.";
        assert btnProducts != null : "fx:id=\"btnProducts\" was not injected: check your FXML file 'home-view.fxml'.";
        assert btnReports != null : "fx:id=\"btnReports\" was not injected: check your FXML file 'home-view.fxml'.";
        assert btnSignout != null : "fx:id=\"btnSignout\" was not injected: check your FXML file 'home-view.fxml'.";
        assert hbTitle != null : "fx:id=\"panTitle\" was not injected: check your FXML file 'home-view.fxml'.";
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'home-view.fxml'.";
        assert btnMinimize != null : "fx:id=\"btnMinimize\" was not injected: check your FXML file 'home-view.fxml'.";

        //******Snow added this line to start the application with home button highlighted
        btnDashboard.setStyle("-fx-background-color: #1620A1;");


        //****** Adolphus - Load dynamic dashboard
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //load dashboard
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(TravelExpertsApplication.class.getResource("gui/dashboard-view.fxml"));
                    Pane pane = fxmlLoader.load();

                    DashboardController dashboard = fxmlLoader.<DashboardController>getController();
                    dashboard.setAgent(getCurrentUser());

                    bpHome.setCenter(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Click and drag window by title pane - start coordinates
        hbTitle.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mousePressedX = mouseEvent.getX();
                mousePressedY = mouseEvent.getY();
            }
        });

        //Click and drag window by title pane - moving the window
        hbTitle.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double crrX = mouseEvent.getScreenX();
                double crrY = mouseEvent.getScreenY();
                Node source = (Node) mouseEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.setX(crrX - mousePressedX);
                stage.setY(crrY - mousePressedY);
            }
        });

        //red close button
        btnClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node source = (Node) mouseEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                System.exit(0);
            }
        });

        //yellow minimize button
        btnMinimize.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node source = (Node) mouseEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.setIconified(true);
            }
        });

    }

    //close main app when signing out
    private void closeDialog(Event event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}

