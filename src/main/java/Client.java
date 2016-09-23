import org.sql2o.*;
import java.util.List;

public class Client {
  private int id;
  private String name;
  private String phoneNumber;
  private int stylistId;

  public Client(String name, String phoneNumber, int stylistId) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.stylistId = stylistId;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public int getStylistId() {
    return stylistId;
  }

  public static List<Client> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients";
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients (name, phoneNumber, stylistId) VALUES (:name, :phoneNumber, :stylistId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("phoneNumber", this.phoneNumber)
        .addParameter("stylistId", this.stylistId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
    }
  }

  private void update(String columnName, String value) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET " + columnName + " = :" + columnName + " WHERE id = :id";
      con.createQuery(sql)
        .addParameter(columnName, value)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void updateName(String name) {
    update("name", name);
  }

  public void updatePhoneNumber(String phoneNumber) {
    update("phoneNumber", phoneNumber);
  }

  public void updateStylistId(int stylistId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET stylistId = :stylistId WHERE id = :id";
      con.createQuery(sql)
        .addParameter("stylistId", stylistId)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherClient) {
    if (!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getName().equals(newClient.getName()) &&
        this.getPhoneNumber().equals(newClient.getPhoneNumber()) &&
        this.getStylistId() == newClient.getStylistId();
    }
  }
}
