import org.sql2o.*;
import java.util.List;

public class Stylist {
  private int id;
  private String name;
  private String workDays;
  private String phoneNumber;

  public Stylist(String name, String workDays, String phoneNumber) {
    this.name = name;
    this.workDays = workDays;
    this.phoneNumber = phoneNumber;
  }
}
