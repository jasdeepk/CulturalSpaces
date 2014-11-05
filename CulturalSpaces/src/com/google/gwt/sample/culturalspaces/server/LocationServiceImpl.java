package com.google.gwt.sample.culturalspaces.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.sample.culturalspaces.client.LocationService;
import com.google.gwt.sample.culturalspaces.client.LocationName;
import com.google.gwt.sample.culturalspaces.client.NotLoggedInException;
import java.util.Random;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LocationServiceImpl extends RemoteServiceServlet implements
LocationService {

  private static final Logger LOG = Logger.getLogger(LocationServiceImpl.class.getName());
  private static final PersistenceManagerFactory PMF =
      JDOHelper.getPersistenceManagerFactory("transactions-optional");
  
  private static final double MAX_PRICE = 100.0; // $100.00
  private static final double MAX_PRICE_CHANGE = 0.02; // +/- 2%
  
  public void addLocation(String name) throws NotLoggedInException {
    checkLoggedIn();
    PersistenceManager pm = getPersistenceManager();
    try {
      pm.makePersistent(new Location(getUser(), name));
    } finally {
      pm.close();
    }
  }

  
  public void removeLocation(String name) throws NotLoggedInException {
    checkLoggedIn();
    PersistenceManager pm = getPersistenceManager();
    try {
      long deleteCount = 0;
      Query q = pm.newQuery(Location.class, "user == u");
      q.declareParameters("com.google.appengine.api.users.User u");
      List<Location> locations = (List<Location>) q.execute(getUser());
      for (Location location : locations) {
        if (name.equals(location.getName())) {
          deleteCount++;
          pm.deletePersistent(location);
        }
      }
      if (deleteCount != 1) {
        LOG.log(Level.WARNING, "removeStock deleted "+deleteCount+" Stocks");
      }
    } finally {
      pm.close();
    }
  }

  public String[] getLocation() throws NotLoggedInException {
    checkLoggedIn();
    PersistenceManager pm = getPersistenceManager();
    List<String> names = new ArrayList<String>();
    try {
      Query q = pm.newQuery(Location.class, "user == u");
      q.declareParameters("com.google.appengine.api.users.User u");
      q.setOrdering("createDate");
      List<Location> locations = (List<Location>) q.execute(getUser());
      for (Location location : locations) {
        names.add(location.getName());
      }
    } finally {
      pm.close();
    }
    return (String[]) names.toArray(new String[0]);
  }

  private void checkLoggedIn() throws NotLoggedInException {
    if (getUser() == null) {
      throw new NotLoggedInException("Not logged in.");
    }
  }

  private User getUser() {
    UserService userService = UserServiceFactory.getUserService();
    return userService.getCurrentUser();
  }

  private PersistenceManager getPersistenceManager() {
    return PMF.getPersistenceManager();
  }

// CultLocation(String name, String address, String type, String neighbourhood)
// TODO: this might be where the parser parses, where the locations are located.
@Override
public LocationName[] getLocationNames(String[] names) {
    Random rnd = new Random();

    LocationName[] locations = new LocationName[names.length];
    for (int i=0; i<names.length; i++) {
//      double price = rnd.nextDouble() * MAX_PRICE;
//      double change = price * MAX_PRICE_CHANGE * (rnd.nextDouble() * 2f - 1f);
    	String address = "";
    	String type = "";
    	String neighbourhood = "";
      locations[i] = new LocationName(names[i], address, type, neighbourhood);
    }
    return locations;
}


}