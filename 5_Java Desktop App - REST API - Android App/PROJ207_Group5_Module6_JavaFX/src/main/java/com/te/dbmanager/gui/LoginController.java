//------------------------------------
// PROJ 207 Threaded Project #3
// Group: 5
// Class: OOSD May 21
// Author (s): Adolphus Cox
// Description: Controller class for Log In dialog (login-view.fxml)
//
//------------------------------------

package com.te.dbmanager.gui;

import com.te.dbmanager.data.AgentDB;
import com.te.dbmanager.model.Agent;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtUsername"
    private TextField txtUsername; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassword"
    private TextField txtPassword; // Value injected by FXMLLoader

    @FXML // fx:id="btnEnter"
    private Button btnEnter; // Value injected by FXMLLoader

    @FXML // fx:id="btnExit"
    private Button btnExit; // Value injected by FXMLLoader

    @FXML // fx:id="apDialog"
    private BorderPane bpDialog;

    @FXML
    private Label lblTravelExperts;

    @FXML
    private HBox hbTitle;

    @FXML
    private Circle btnClose;

    @FXML
    private Circle btnMinimize;

    private boolean isAuthenticated = false;
    private Agent authenticatedUser;
    private double  mousePressedX, mousePressedY;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'login-view.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'login-view.fxml'.";
        assert btnEnter != null : "fx:id=\"btnEnter\" was not injected: check your FXML file 'login-view.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'login-view.fxml'.";
        assert lblTravelExperts != null : "fx:id=\"lblTravelExperts\" was not injected: check your FXML file 'login-view.fxml'.";

        btnEnter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                authenticateUsernameAndPassword(mouseEvent);
            }
        });

        // Keypress Event handler
        bpDialog.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    authenticateUsernameAndPassword(event);
                }

                if (event.getCode() == KeyCode.ESCAPE) {
                    closeDialog(event);
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

        btnClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node source = (Node) mouseEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                System.exit(0);
            }
        });

        btnMinimize.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node source = (Node) mouseEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.setIconified(true);
            }
        });

    }

    private void authenticateUsernameAndPassword(Event event) {
        boolean validationPassed = true;

        // Check for blank entries in both username and password fields

        if (Objects.equals(txtUsername.getText(), "")) {
            DisplayAlert.showWarning("The " + getLabel(txtUsername) + " field has not been filled.");
            txtUsername.requestFocus();
            validationPassed = false;
        }

        if (Objects.equals(txtPassword.getText(), "") && validationPassed) {
            DisplayAlert.showWarning("The " + getLabel(txtPassword) + " field has not been filled.");
            txtPassword.requestFocus();
            validationPassed = false;
        }

        // if validation passes, get all agents and loop through each to check for correct
        // username and password

        if (validationPassed) {
            ObservableList<Agent> agents = null;
            try {
                agents = AgentDB.getAllAgents();
            } catch (SQLException e) {
                e.printStackTrace();
                DisplayAlert.showWarning("A database error has occurred.");
            }

            for (Agent agent : agents) {
                if (Objects.equals(agent.getAgtUsername(), txtUsername.getText()) &&
                        Objects.equals(agent.getAgtPassword(), txtPassword.getText())) {
                    isAuthenticated = true;
                    authenticatedUser = agent;
                }
            }

            if (isAuthenticated) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home-view.fxml"));
                try {
                    Parent parent = fxmlLoader.load();
                    HomeController homeView = fxmlLoader.<HomeController>getController();
                    homeView.setCurrentUser(authenticatedUser);

                    Scene scene = new Scene(parent);

                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UNDECORATED); //hide the title bar (we made our own)
                    stage.setScene(scene);
                    stage.show();

                    closeDialog(event);

                } catch (IOException e) {
                    e.printStackTrace();
                    DisplayAlert.showWarning("Error loading application window.");
                }
            } else {
                DisplayAlert.showWarning("The username or password is incorrect. Please try again.");
                txtPassword.requestFocus();
            }
        }
    }

    private void closeDialog(Event event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private String getLabel(TextField textField) {
        if (textField == txtUsername) {
            return "'Username'";
        } else {
            return "'Password'";
        }
    }
}

