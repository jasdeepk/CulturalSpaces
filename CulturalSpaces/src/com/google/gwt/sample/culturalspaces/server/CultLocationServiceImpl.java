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
import com.google.gwt.sample.culturalspaces.client.CultLocationService;
import com.google.gwt.sample.culturalspaces.client.NotLoggedInException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CultLocationServiceImpl extends RemoteServiceServlet implements
CultLocationService {

  private static final Logger LOG = Logger.getLogger(CultLocationServiceImpl.class.getName());
  private static final PersistenceManagerFactory PMF =
      JDOHelper.getPersistenceManagerFactory("transactions-optional");

  public void addCultLocation(String symbol) throws NotLoggedInException {
    checkLoggedIn();
    PersistenceManager pm = getPersistenceManager();
    try {
      pm.makePersistent(new CultLocation(getUser(), symbol, symbol, symbol, symbol));
    } finally {
      pm.close();
    }
  }

  public void removeCultLocation(String symbol) throws NotLoggedInException {
    checkLoggedIn();
    PersistenceManager pm = getPersistenceManager();
    try {
      long deleteCount = 0;
      Query q = pm.newQuery(CultLocation.class, "user == u");
      q.declareParameters("com.google.appengine.api.users.User u");
      List<CultLocation> CultLocations = (List<CultLocation>) q.execute(getUser());
      for (CultLocation stock : CultLocations) {
        if (symbol.equals(stock.getSymbol())) {
          deleteCount++;
          pm.deletePersistent(stock);
        }
      }
      if (deleteCount != 1) {
        LOG.log(Level.WARNING, "removeStock deleted "+deleteCount+" Stocks");
      }
    } finally {
      pm.close();
    }
  }

  public String[] getCultLocations() throws NotLoggedInException {
    checkLoggedIn();
    PersistenceManager pm = getPersistenceManager();
    List<String> symbols = new ArrayList<String>();
    try {
      Query q = pm.newQuery(CultLocation.class, "user == u");
      q.declareParameters("com.google.appengine.api.users.User u");
      q.setOrdering("createDate");
      List<CultLocation> CultLocations = (List<CultLocation>) q.execute(getUser());
      for (CultLocation stock : CultLocations) {
        symbols.add(stock.getSymbol());
      }
    } finally {
      pm.close();
    }
    return (String[]) symbols.toArray(new String[0]);
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


}