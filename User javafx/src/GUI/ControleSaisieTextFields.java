/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.control.TextField;

/**
 *
 * @author Firas
 */
public class ControleSaisieTextFields {

    public ControleSaisieTextFields() {
    }
    
    
    

    public boolean isEmpty(String T) {
        return (T.equals(""));
    }

    public boolean checkOnlyInteger(String T) {
        String integerRegex = "^\\d+$";
        return T.matches(integerRegex);
    }

    public boolean checkOnlyString(String T) {
        String AlphaRegex = "^[a-zA-Z].*$";
        return T.matches(AlphaRegex);
    }

    public boolean isEmailAdress(String T) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return T.matches(emailRegex);
    }

}
