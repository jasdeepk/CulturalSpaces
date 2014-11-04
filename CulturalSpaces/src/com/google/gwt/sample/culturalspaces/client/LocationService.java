package com.google.gwt.sample.culturalspaces.client;

import com.google.gwt.sample.culturalspaces.server.Location;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("location")
public interface LocationService extends RemoteService {
	

  public void addLocation(String symbol) throws NotLoggedInException;
  public void removeLocation(String symbol) throws NotLoggedInException;
  public String[] getLocation() throws NotLoggedInException;
  public LocationName[] getLocationNames(String[] names);
 
}