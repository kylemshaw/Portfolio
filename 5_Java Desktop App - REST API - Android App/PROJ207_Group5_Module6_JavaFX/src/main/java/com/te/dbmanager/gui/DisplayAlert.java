//------------------------------------
// PROJ 207 Threaded Project #3
// Group: 5
// Class: OOSD May 21
// Author (s): Adolphus Cox
// Description: Class that contains static methods for constructing message box alerts
// with fewer lines of code
//------------------------------------

package com.te.dbmanager.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DisplayAlert {
    public static Alert showConfirmation(String msg) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText(msg);
        a.getButtonTypes().clear();
        a.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        a.showAndWait();

        return a;
    }

    public static void showWarning(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setContentText(msg);
        a.showAndWait();
    }
}
