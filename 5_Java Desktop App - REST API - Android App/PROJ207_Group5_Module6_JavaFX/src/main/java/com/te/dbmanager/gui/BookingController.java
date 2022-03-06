package com.te.dbmanager.gui;

import com.te.dbmanager.data.BookingDB;
import com.te.dbmanager.model.Booking;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Sample Skeleton for 'bookings-view.fxml' Controller Class
 Author: Xiaoyan Deng
 Date: 2021/10/12
 Course: PROJ-207
 Workshop 6
 */
public class BookingController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tableBookings"
    private TableView<Booking> tableBookings; // Value injected by FXMLLoader

    @FXML // fx:id="colBookingId"
    private TableColumn<Booking, String> colBookingId; // Value injected by FXMLLoader

    @FXML // fx:id="colBookingDate"
    private TableColumn<Booking, String> colBookingDate; // Value injected by FXMLLoader

    @FXML // fx:id="colBookingNo"
    private TableColumn<Booking, String> colBookingNo; // Value injected by FXMLLoader

    @FXML // fx:id="colTravelerCount"
    private TableColumn<Booking, String> colTravelerCount; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerId"
    private TableColumn<Booking, String> colCustomerId; // Value injected by FXMLLoader

    @FXML // fx:id="colTripTypeId"
    private TableColumn<Booking, String> colTripTypeId; // Value injected by FXMLLoader

    @FXML // fx:id="colPackageId"
    private TableColumn<Booking, String> colPackageId; // Value injected by FXMLLoader

    @FXML // fx:id="colFeeId"
    private TableColumn<Booking, String> colFeeId; // Value injected by FXMLLoader

    @FXML // fx:id="colAgentId"
    private TableColumn<Booking, String> colAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdd"
    private Button btnAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btnEdit"
    private Button btnEdit; // Value injected by FXMLLoader

    @FXML // fx:id="btnDelete"
    private Button btnDelete; // Value injected by FXMLLoader

    private ObservableList<Booking> data;  //data shown in the table view
    private BookingDB bookingDB = new BookingDB();


    /**
     * Open dialog of booking detail event when Add button is clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickAddBooking(MouseEvent event) throws IOException, ParseException {
        onOpenDialog(Integer.MAX_VALUE);
    }

    /**
     * Open dialog of booking detail when Edit button is clicked
      * @param event
     */
    @FXML
    void onClickEditBooking(MouseEvent event) {
        try {
            onOpenDialog(tableBookings.getSelectionModel().getSelectedIndex());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * handle event when Delete button is clicked
     * @param event
     */
    @FXML
    void onClickDeleteBooking(MouseEvent event) {
        Booking b = data.get(tableBookings.getSelectionModel().getSelectedIndex());

        //alert to warn and confirm delete operation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this booking?");
        alert.setTitle("Delete Booking");
        alert.setHeaderText("Delete booking will delete all corresponding booking details");
        Optional<ButtonType> result = alert.showAndWait();
        //if click OK, delete selected booking. Otherwise, do nothing
        if(result.isPresent() && result.get()==ButtonType.OK){
            bookingDB.deleteBookingById(Integer.parseInt(b.getBookingId()));
            bookingDB.deleteBookingDetailsByBookingId(Integer.parseInt(b.getBookingId()));
            data.remove(b);
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tableBookings != null : "fx:id=\"tableBookings\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert colBookingId != null : "fx:id=\"colBookingId\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert colBookingDate != null : "fx:id=\"colBookingDate\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert colBookingNo != null : "fx:id=\"colBookingNo\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert colTravelerCount != null : "fx:id=\"colTravelerCount\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert colCustomerId != null : "fx:id=\"colCustomerId\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert colTripTypeId != null : "fx:id=\"colTripTypeId\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert colPackageId != null : "fx:id=\"colPackageId\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert colFeeId != null : "fx:id=\"colFeeId\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert colAgentId != null : "fx:id=\"colAgentId\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'bookings-view.fxml'.";

        getBookingDataShown();                            // get and show all booking data
        getBookingMetadataShown();                        // get and show all booking metadata

        btnEdit.disableProperty().set(true);              //disable Edit button
        btnDelete.disableProperty().set(true);            //disable Delete button

        /**
         * handle event when the item in the table view is clicked
         */
        tableBookings.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (tableBookings.getSelectionModel().getSelectedItem() != null) {
                    btnEdit.disableProperty().set(false);     //enable Edit button
                    btnDelete.disableProperty().set(false);   //enable Delete button
                }
            }
        });

        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    onOpenDialog(Integer.MAX_VALUE);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    onOpenDialog(tableBookings.getSelectionModel().getSelectedIndex());
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Booking b = data.get(tableBookings.getSelectionModel().getSelectedIndex());

                //alert to warn and confirm delete operation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this booking?");
                alert.setTitle("Delete Booking");
                alert.setHeaderText("Delete booking will delete all corresponding booking details");
                Optional<ButtonType> result = alert.showAndWait();
                //if click OK, delete selected booking. Otherwise, do nothing
                if(result.isPresent() && result.get()==ButtonType.OK){
                    bookingDB.deleteBookingById(Integer.parseInt(b.getBookingId()));
                    bookingDB.deleteBookingDetailsByBookingId(Integer.parseInt(b.getBookingId()));
                    data.remove(b);
                }
            }
        });

    }

    /**
     * open a dialog of booking detail
     * @param selectedIndex the selected index of bookings in the table view
     * @throws IOException
     */
    private void onOpenDialog(int selectedIndex) throws IOException, ParseException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("booking-detail-view.fxml"));
        Parent parent = fxmlLoader.load();
        BookingDetailController bookingDetailController = fxmlLoader.<BookingDetailController>getController();
        bookingDetailController.setMainObservableList(data);
        bookingDetailController.setMainSelectedIndex(selectedIndex);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();
        btnEdit.disableProperty().set(true);
        btnDelete.disableProperty().set(true);
        tableBookings.getSelectionModel().select(null);
        getBookingDataShown();
    }

    /**
     * get metadata of bookings and show as column name of the table view
     */
    private void getBookingMetadataShown() {
        colBookingId.setText("Id");
        colBookingDate.setText("Date");
        colBookingNo.setText("Number");
        colTravelerCount.setText("Travellers");
        colCustomerId.setText("Customer");
        colTripTypeId.setText("Trip Type");
        colPackageId.setText("Package");
        colFeeId.setText("Fee");
        colAgentId.setText("Agent");

        /*ResultSetMetaData rsmd = bookingDB.getBookingMetadata();
        try {
            colBookingId.setText(rsmd.getColumnLabel(1));
            colBookingDate.setText(rsmd.getColumnLabel(2));
            colBookingNo.setText(rsmd.getColumnLabel(3));
            colTravelerCount.setText(rsmd.getColumnLabel(4));
            colCustomerId.setText(rsmd.getColumnLabel(5));
            colTripTypeId.setText(rsmd.getColumnLabel(6));
            colPackageId.setText(rsmd.getColumnLabel(7));
            colFeeId.setText(rsmd.getColumnLabel(8));
            colAgentId.setText(rsmd.getColumnLabel(9));
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * get all booking data and shown in the table view
     */
    private void getBookingDataShown() {
        data = bookingDB.getAllBookings(); // get data
        colBookingId.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingId"));
        colBookingDate.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingDate"));
        colBookingNo.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingNo"));
        colTravelerCount.setCellValueFactory(new PropertyValueFactory<Booking, String>("travelerCount"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<Booking, String>("customerId"));
        colTripTypeId.setCellValueFactory(new PropertyValueFactory<Booking, String>("tripTypeId"));
        colPackageId.setCellValueFactory(new PropertyValueFactory<Booking, String>("packageId"));
        colFeeId.setCellValueFactory(new PropertyValueFactory<Booking, String>("feeId"));
        colAgentId.setCellValueFactory(new PropertyValueFactory<Booking, String>("agentId"));
        tableBookings.setItems(data); // show data in the table
    }

}
