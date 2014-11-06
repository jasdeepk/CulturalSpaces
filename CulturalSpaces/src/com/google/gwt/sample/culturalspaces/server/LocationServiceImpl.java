package com.google.gwt.sample.culturalspaces.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
import com.google.gwt.sample.culturalspaces.client.LocationObject;
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


	@Override
	public LocationObject[] getLocationObjects() {
		LocationObject[] locations = new LocationObject[355];



		//** PARSER  
		String csvFile = "/Users/jasdeepkahlon/Desktop/CulturalSpaces.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int i = 0;

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] LOCATION = line.split(cvsSplitBy);
				System.out.println(LOCATION);
				System.out.println("Space [name= " + LOCATION[0]
						+", address= " + LOCATION[2] 
								+ ", suburb=" + LOCATION[3]
										+ ", longitude=" + LOCATION[5] 
												+ ", latitude=" + LOCATION[6] + "]" );

				String name = LOCATION[0];
				String address = LOCATION[2];
				String type = LOCATION[0];
				String neighbourhood = LOCATION[3];
				String lon = LOCATION[5];
				String lat = LOCATION[6];

				locations[++i] = new LocationObject(name, address, type, neighbourhood, lon, lat);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");

		//** PARSER 

		return locations;
	}


}