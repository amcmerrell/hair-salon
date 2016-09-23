import org.sql2o.*;
import java.util.List;

public class Stylist {
  private int id;
  private String name;
  // private String workDays;
  private String phoneNumber;

  public Stylist(String name, String phoneNumber) {
    this.name = name;
    // this.workDays = workDays;
    this.phoneNumber = phoneNumber;
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

  public static List<Stylist> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists";
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists (name, phoneNumber) VALUES (:name, :phoneNumber)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("phoneNumber", this.phoneNumber)
        .executeUpdate()
        .getKey();
    }
  }

  public static Stylist find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
    }
  }

  public List<Client> getClients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE stylistId = :stylistId";
      return con.createQuery(sql)
        .addParameter("stylistId", this.getId())
        .executeAndFetch(Client.class);
    }
  }

  private void update(String columnName, String value) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stylists SET " + columnName + " = :" + columnName + " WHERE id = :id";
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

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stylists WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherStylist) {
    if (!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getId() == newStylist.getId() &&
        this.getName().equals(newStylist.getName()) &&
        this.getPhoneNumber().equals(newStylist.getPhoneNumber());
    }
  }
}
