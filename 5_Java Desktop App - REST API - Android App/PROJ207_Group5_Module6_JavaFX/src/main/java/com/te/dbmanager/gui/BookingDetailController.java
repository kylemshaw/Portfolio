package com.te.dbmanager.gui;

import com.te.dbmanager.data.AgentDB;
import com.te.dbmanager.data.BookingDB;
import com.te.dbmanager.data.CustomerDB;
import com.te.dbmanager.model.*;
import com.te.dbmanager.model.Package;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Sample Skeleton for 'booking-detail-view.fxml' Controller Class
 Author: Xiaoyan Deng
 Date: 2021/10/12
 Course: PROJ-207
 Workshop 6
 */
public class BookingDetailController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private HBox hbTitle;

    @FXML
    private TextField tfTitle;

    @FXML
    private Circle btnMinimize;

    @FXML
    private Circle btnClose;

    @FXML // fx:id="lbBookingId"
    private Label lbBookingId; // Value injected by FXMLLoader

    @FXML // fx:id="lbBookingDate"
    private Label lbBookingDate; // Value injected by FXMLLoader

    @FXML // fx:id="lbBookingNo"
    private Label lbBookingNo; // Value injected by FXMLLoader

    @FXML // fx:id="lbTravelerCount"
    private Label lbTravelerCount; // Value injected by FXMLLoader

    @FXML // fx:id="lbCustomerId"
    private Label lbCustomerId; // Value injected by FXMLLoader

    @FXML // fx:id="lbTripTypeId"
    private Label lbTripTypeId; // Value injected by FXMLLoader

    @FXML // fx:id="lbPackageId"
    private Label lbPackageId; // Value injected by FXMLLoader

    @FXML // fx:id="lbFeeId"
    private Label lbFeeId; // Value injected by FXMLLoader

    @FXML // fx:id="lbAgentId"
    private Label lbAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="tfBookingId"
    private Label tfBookingId; // Value injected by FXMLLoader

    @FXML // fx:id="dpBookingDate"
    private DatePicker dpBookingDate; // Value injected by FXMLLoader

    @FXML // fx:id="tfBookingNo"
    private TextField tfBookingNo; // Value injected by FXMLLoader

    @FXML // fx:id="tfTravelerCount"
    private TextField tfTravelerCount; // Value injected by FXMLLoader

    @FXML // fx:id="cbCustomerId"
    private ComboBox<Customer> cbCustomerId; // Value injected by FXMLLoader

    @FXML // fx:id="cbTripTypeId"
    private ComboBox<TripType> cbTripTypeId; // Value injected by FXMLLoader

    @FXML // fx:id="cbPackageId"
    private ComboBox<Package> cbPackageId; // Value injected by FXMLLoader

    @FXML // fx:id="cbFeeId"
    private ComboBox<Fee> cbFeeId; // Value injected by FXMLLoader

    @FXML // fx:id="cbAgentId"
    private ComboBox<Agent> cbAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="btnSave"
    private Button btnSave; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="lbMessage"
    private Label lbMessage; // Value injected by FXMLLoader

    @FXML // fx:id="lbTitle"
    private Label lbTitle; // Value injected by FXMLLoader

    private ObservableList<Booking> mainData;
    private int selectedIndex;
    private ObservableList<Customer> customers;
    private ObservableList<TripType> tripTypes;
    private ObservableList<Package> packages;
    private ObservableList<Fee> fees;
    private ObservableList<Agent> agents;
    private BookingDB bookingDB = new BookingDB();
    private Booking booking = null;
    private String msg;
    private double  mousePressedX, mousePressedY;


    @FXML
    void onClickCancel(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickBookingSave(MouseEvent event) {
        if(validation()){
            if(booking.getBookingId()=="add"){
                mainData.add(bookingDB.getBookingById(bookingDB.insertBooking(booking)));
            }else{
                bookingDB.updateBooking(booking);
                mainData.set(selectedIndex,booking);
            }
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }else{
            lbMessage.setText(msg);
        }
    }

    private boolean validation() {
        boolean valid = true;
        msg = "";
        //bookingId check
        String bookingId="add";
        if(tfBookingId.getText()!=""){
            bookingId = tfBookingId.getText();
        }
        //bookingDate check
        String bookingDate = null;
        if(dpBookingDate.getValue()!=null){
            LocalDate localDate = dpBookingDate.getValue();
            bookingDate = localDate.toString()+" 00:00:00";
        }else{
            valid = false;
            msg=msg+"Booking Date shall be chosen\n";
        }
        //bookingNo
        String bookingNo="";
        if(tfBookingNo.getText()==""){
            valid = false;
            msg=msg+"Booking No shall be entered\n";
        }else{
            bookingNo = tfBookingNo.getText();
        }
        //travelerCount check
        String travelerCount = null;
        if(tfTravelerCount.getText()!=""){
            if(tfTravelerCount.getText().matches("^[0-9]*\\.?[0-9]+")){
                travelerCount = tfTravelerCount.getText();
            }else {
                valid = false;
                msg = msg + "Invalid travelerCount\n";
            }
        }else{
            valid = false;
            msg=msg+"Traveler count shall be entered\n";
        }

        //customerId
        String customerId = null;
        if(cbCustomerId.getValue()!=null){
            customerId = String.valueOf(((Customer)cbCustomerId.getValue()).getCustomerId());
        }else{
            valid = false;
            msg=msg+"Customer Id shall be chosen\n";
        }

        //tripTypeId
        String tripTypeId = null;
        if(cbTripTypeId.getValue()!=null){
            tripTypeId = ((TripType)cbTripTypeId.getValue()).getTripTypeId();
        }

        //packageId
        String packageId = null;
        if(cbPackageId.getValue()!=null){
            packageId = String.valueOf(((Package)cbPackageId.getValue()).getPackageId());
        }else{
            valid = false;
            msg=msg+"Package Id shall be chosen\n";
        }
        //feeId
        String feeId = null;
        if(cbFeeId.getValue()!=null){
            feeId = String.valueOf(((Fee)cbFeeId.getValue()).getFeeId());
        }
        //agentId
        String agentId = null;
        if(cbAgentId.getValue()!=null){
            agentId = String.valueOf(((Agent)cbAgentId.getValue()).getAgentId());
        }

        if(valid==true){
            booking = new Booking(bookingId,bookingDate,bookingNo,travelerCount,customerId,
                    tripTypeId,packageId,feeId,agentId);
        }
        return valid;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert hbTitle != null : "fx:id=\"hbTitle\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert btnMinimize != null : "fx:id=\"btnMinimize\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert lbBookingId != null : "fx:id=\"lbBookingId\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert lbBookingDate != null : "fx:id=\"lbBookingDate\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert lbBookingNo != null : "fx:id=\"lbBookingNo\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert lbTravelerCount != null : "fx:id=\"lbTravelerCount\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert lbCustomerId != null : "fx:id=\"lbCustomerId\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert lbTripTypeId != null : "fx:id=\"lbTripTypeId\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert lbPackageId != null : "fx:id=\"lbPackageId\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert lbFeeId != null : "fx:id=\"lbFeeId\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert lbAgentId != null : "fx:id=\"lbAgentId\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert tfBookingId != null : "fx:id=\"tfBookingId\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert dpBookingDate != null : "fx:id=\"dpBookingDate\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert tfBookingNo != null : "fx:id=\"tfBookingNo\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert tfTravelerCount != null : "fx:id=\"tfTravelerCount\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert cbCustomerId != null : "fx:id=\"cbCustomerId\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert cbTripTypeId != null : "fx:id=\"cbTripTypeId\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert cbPackageId != null : "fx:id=\"cbPackageId\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert cbFeeId != null : "fx:id=\"cbFeeId\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert cbAgentId != null : "fx:id=\"cbAgentId\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert lbMessage != null : "fx:id=\"lbMessage\" was not injected: check your FXML file 'booking-detail-view.fxml'.";
        assert lbTitle != null : "fx:id=\"lbTitle\" was not injected: check your FXML file 'booking-detail-view.fxml'.";

        //getBookingMetadataShown(); // get and show all booking metadata
        getCustomersShown(); // get and shown all customerId in combo box
        getTripTypeIdShown(); // get and shown all TripTypeId in combo box
        getPackageIdShown(); // get and shown all PackageId in combo box
        getFeeIdShown();
        getAgentIdShown();

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

    //---------get and show metadata of bookings-------------------------
    /*private void getBookingMetadataShown() {
        ResultSetMetaData rsmd = bookingDB.getBookingMetadata();
        try {
            lbBookingId.setText(rsmd.getColumnLabel(1));
            lbBookingDate.setText(rsmd.getColumnLabel(2));
            lbBookingNo.setText(rsmd.getColumnLabel(3));
            lbTravelerCount.setText(rsmd.getColumnLabel(4));
            lbCustomerId.setText(rsmd.getColumnLabel(5));
            lbTripTypeId.setText(rsmd.getColumnLabel(6));
            lbPackageId.setText(rsmd.getColumnLabel(7));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    //-----------get and show all customers in combo box-----------------------
    private void getCustomersShown() {
        customers = bookingDB.getAllCustomers();
        cbCustomerId.setItems(customers);
    }

    //-----------get and show all tripTypes in combo box-----------------------
    private void getTripTypeIdShown() {
        packages = bookingDB.getAllPackages();
        cbPackageId.setItems(packages);
    }

    //-----------get and show all packages in combo box-----------------------
    private void getPackageIdShown() {
        tripTypes = bookingDB.getAllTripTypes();
        cbTripTypeId.setItems(tripTypes);
    }

    //-----------get and show all fees in combo box-----------------------
    private void getFeeIdShown() {
        fees = bookingDB.getAllFees();
        cbFeeId.setItems(fees);
    }

    //-----------get and show all agents in combo box-----------------------
    private void getAgentIdShown() {
        try {
            agents = AgentDB.getAllAgents();
        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("Database error when retrieving agent list from database.");
        }
        cbAgentId.setItems(agents);
    }

    //--------pass data from booking page to booking detail dialog----------------
    public void setMainObservableList(ObservableList<Booking> data) {
        mainData = data;
    }

    //-----------get and show the selected booking if selected-----------------------
    //-----------if add button is clicked, disable delete button--------------------
    public void setMainSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        if(selectedIndex<Integer.MAX_VALUE){
            this.tfTitle.appendText(" - Edit Booking");
            // show selected Booking detail
            Booking b = mainData.get(selectedIndex);
            tfBookingId.setText("");
            dpBookingDate.setValue(null);
            tfBookingNo.setText("");
            tfTravelerCount.setText("");
            if(b.getBookingId()!=null)
                tfBookingId.setText(b.getBookingId());
            if(b.getBookingDate()!=null){
                int year = Integer.parseInt(b.getBookingDate().substring(0,4));
                int month = Integer.parseInt(b.getBookingDate().substring(5,7));
                int day = Integer.parseInt(b.getBookingDate().substring(8,10));
                LocalDate localDate = LocalDate.of(year,month,day);
                dpBookingDate.setValue(localDate);
            }
            tfBookingNo.setText(b.getBookingNo());
            tfTravelerCount.setText(b.getTravelerCount());
            //set value for customerId
            int customerId = Integer.parseInt(b.getCustomerId());
            cbCustomerId.setValue(bookingDB.getCustomerById(customerId));
            //set value for tripTypeId
            if(b.getTripTypeId()!=null){
                String tripTypeId = b.getTripTypeId();
                cbTripTypeId.setValue(bookingDB.getTripTypeById(tripTypeId));
            }
            //set value for packageId
            int packageId = Integer.parseInt(b.getPackageId());
            cbPackageId.setValue(bookingDB.getPackageById(packageId));
            //set value for feeId
            if(b.getFeeId()!=null){
                String feeId = b.getFeeId();
                cbFeeId.setValue(bookingDB.getFeeById(feeId));
            }else{
                cbFeeId.setValue(bookingDB.getFeeById("BK"));
            }
            //set value for agentId
            try {
                int agentId = Integer.parseInt(b.getAgentId());
                cbAgentId.setValue(AgentDB.getAgent(agentId));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            this.tfTitle.appendText(" - Add Booking");
            cbFeeId.setValue(bookingDB.getFeeById("BK"));
        }
    }
}
