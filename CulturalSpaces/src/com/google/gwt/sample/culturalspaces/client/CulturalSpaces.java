package com.google.gwt.sample.culturalspaces.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CulturalSpaces implements EntryPoint {

private VerticalPanel mainPanel = new VerticalPanel(); 
private FlexTable stocksFlexTable = new FlexTable();  
private HorizontalPanel addPanel = new HorizontalPanel();  
private TextBox newSymbolTextBox = new TextBox();  
private Button addStockButton = new Button("Add");  
private Label lastUpdatedLabel = new Label();

private ArrayList<String> names = new ArrayList<String>();
private LocationServiceAsync locationSvc = GWT.create(LocationService.class);

/**  * Entry point method.  */  
public void onModuleLoad() {
	// Check login status using login service.
    LoginServiceAsync loginService = GWT.create(LoginService.class);
    loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
      public void onFailure(Throwable error) {
    	  handleError(error);
      }

      public void onSuccess(LoginInfo result) {
        loginInfo = result;
        if(loginInfo.isLoggedIn()) {
	loadCulturalSpaces();
        } else {
            loadLogin();
          }
        }
      });
    }

private void loadLogin() {
  // Assemble login panel.
  signInLink.setHref(loginInfo.getLoginUrl());
  loginPanel.add(loginLabel);
  loginPanel.add(signInLink);
  RootPanel.get("locationList").add(loginPanel);
    
}

private void loadCulturalSpaces() {
	// Set up sign out hyperlink.
    signOutLink.setHref(loginInfo.getLogoutUrl());
	
	// Create table for stock data.  
	locationsFlexTable.setText(0, 0, "Location Name"); 
	locationsFlexTable.setText(0, 1, "Address");  
	   
	// Assemble Main panel.
	mainPanel.add(signOutLink);
    mainPanel.add(locationsFlexTable);
    mainPanel.add(addPanel);
    mainPanel.add(lastUpdatedLabel);
    
	// TODO Associate the Main panel with the HTML host page.
    RootPanel.get("locationList").add(mainPanel);


}

private void handleError(Throwable error) {
    Window.alert(error.getMessage());
    if (error instanceof NotLoggedInException) {
      Window.Location.replace(loginInfo.getLogoutUrl());
    }
  }

private void refreshWatchList() {
    // Initialize the service proxy.
    if (locationSvc == null) {
      locationSvc = GWT.create(LocationService.class);
    }

    // Set up the callback object.
    AsyncCallback<LocationName[]> callback = new AsyncCallback<LocationName[]>() {
      public void onFailure(Throwable caught) {
        // TODO: Do something with errors.
      }

      public void onSuccess(LocationName[] result) {
        updateTable(result);
      }
    };

    // Make the call to the stock price service.
    locationSvc.getLocationNames(names.toArray(new String[0]), callback);

}