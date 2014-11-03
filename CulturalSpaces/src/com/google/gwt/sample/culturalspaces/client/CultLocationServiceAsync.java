package com.google.gwt.sample.culturalspaces.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CultLocationServiceAsync {
  public void addCultLocation(String symbol, AsyncCallback<Void> async);
  public void removeCultLocation(String symbol, AsyncCallback<Void> async);
  public void getCultLocations(AsyncCallback<String[]> async);
}