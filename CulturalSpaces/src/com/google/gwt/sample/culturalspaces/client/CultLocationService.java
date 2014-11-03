package com.google.gwt.sample.culturalspaces.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("stock")
public interface CultLocationService extends RemoteService {
  public void addCultLocation(String symbol) throws NotLoggedInException;
  public void removeCultLocation(String symbol) throws NotLoggedInException;
  public String[] getCultLocations() throws NotLoggedInException;
}