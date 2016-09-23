import org.sql2o.*;
import java.util.Arrays;

public class client {
  private int id;
  private String name;
  private String phoneNumber;
  private int stylistId;

  public client(String name, String phoneNumber, int stylistId) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.stylistId = stylistId;
  }
}
