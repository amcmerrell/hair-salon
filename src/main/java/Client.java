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
