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
	// TODO Create table for stock data.  
	// TODO Assemble Add Stock panel.  
	// TODO Assemble Main panel.  
	// TODO Associate the Main panel with the HTML host page.  
	// TODO Move cursor focus to the input box.

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