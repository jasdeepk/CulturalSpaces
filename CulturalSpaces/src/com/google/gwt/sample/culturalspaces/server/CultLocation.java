package com.google.gwt.sample.culturalspaces.server;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class CultLocation {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long id;
  @Persistent
  private User user;
  @Persistent
  private String symbol;
  @Persistent
  private Date createDate;
  @Persistent
  private String type;


@Persistent
  private String address;
  @Persistent
  private String neighbourhood;

  public CultLocation() {
    this.createDate = new Date();
  }

  public CultLocation(User user, String symbol, String address, String type, String neighbourhood) {
    this();
    this.user = user;
    this.symbol = symbol;
    this.address = address;
    this.type = type;
    this.neighbourhood = neighbourhood;
  }

  public Long getId() {
    return this.id;
  }

  public User getUser() {
    return this.user;
  }

  public String getSymbol() {
    return this.symbol;
  }

  public Date getCreateDate() {
    return this.createDate;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }
  
  public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getNeighbourhood() {
	return neighbourhood;
}

public void setNeighbourhood(String neighbourhood) {
	this.neighbourhood = neighbourhood;
}
}