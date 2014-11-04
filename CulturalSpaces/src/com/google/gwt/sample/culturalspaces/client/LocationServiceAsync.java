package com.google.gwt.sample.culturalspaces.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LocationServiceAsync {
  public void addLocation(String symbol, AsyncCallback<Void> async);
  public void removeLocation(String symbol, AsyncCallback<Void> async);
  public void getLocation(AsyncCallback<String[]> async);
  void getLocationNames(String[] names, AsyncCallback<LocationName[]> callback);
  
}