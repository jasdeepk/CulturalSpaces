package com.google.gwt.sample.culturalspaces.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.sample.culturalspaces.client.NotLoggedInException;
import com.google.gwt.sample.culturalspaces.client.LocationName;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Anchor;

public class CulturalSpaces implements EntryPoint {

private VerticalPanel mainPanel = new VerticalPanel(); 
private FlexTable locationsFlexTable = new FlexTable();  
private HorizontalPanel addPanel = new HorizontalPanel();     
private Label lastUpdatedLabel = new Label();

private ArrayList<String> names = new ArrayList<String>();
private LocationServiceAsync locationSvc = GWT.create(LocationService.class);

private LoginInfo loginInfo = null;
private VerticalPanel loginPanel = new VerticalPanel();
private Label loginLabel = new Label(
    "Please sign in to your Google Account to access the StockWatcher application.");
private Anchor signInLink = new Anchor("Sign In");
private Anchor signOutLink = new Anchor("Sign Out");
private final LocationServiceAsync locationService = GWT.create(LocationService.class);


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

private void updateTable(LocationName[] names) {
	for (LocationName name : names)
		updateTable(name);

	// Display timestamp showing last refresh.
	lastUpdatedLabel.setText("Last update : "
			+ DateTimeFormat.getMediumDateTimeFormat().format(new Date()));
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

}
