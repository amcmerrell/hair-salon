import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class StylistTest {
  Stylist stylistOne;
  Stylist stylistTwo;

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", "postgres", "panthers");
    stylistOne = new Stylist("Andrew", "919-941-6987");
    stylistOne.save();
    stylistTwo = new Stylist("Sarah", "919-847-8745");
    stylistTwo.save();
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteStylistsQuery = "DELETE FROM stylists *;";
      String deleteClientsQuery = "DELETE FROM clients *;";
      con.createQuery(deleteStylistsQuery).executeUpdate();
      con.createQuery(deleteClientsQuery).executeUpdate();
    }
  }

  @Test
  public void constructor_stylistInstantiatesCorrectly_true() {
    assertTrue(stylistOne instanceof Stylist);
  }

  @Test
  public void getName_returnsCorrectName_Andrew() {
    assertEquals("Andrew", stylistOne.getName());
  }

  @Test
  public void getPhoneNumber_returnsCorrectPhoneNumber_919_941_6987() {
    assertEquals("919-941-6987", stylistOne.getPhoneNumber());
  }

  @Test
  public void equals_returnsTrueIfFieldsAreSame_true() {
    Stylist stylistEqualsOne = new Stylist("Andrew", "919-941-6987");
    Stylist stylistEqualsTwo = new Stylist("Andrew", "919-941-6987");
    assertTrue(stylistEqualsOne.equals(stylistEqualsTwo));
  }

  @Test
  public void all_returnsAllInstancesOfStylist_true() {
    assertTrue(Stylist.all().get(0).equals(stylistOne));
    assertTrue(Stylist.all().get(1).equals(stylistTwo));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    assertTrue(Stylist.all().get(0).equals(stylistOne));
  }

  @Test
  public void save_assignsIdToObject() {
    Stylist savedStylist = Stylist.all().get(0);
    assertEquals(stylistOne.getId(), savedStylist.getId());
  }

  @Test
  public void find_returnsStylistWithSameId_true() {
    assertEquals(stylistTwo, Stylist.find(stylistTwo.getId()));
  }

  @Test
  public void getClients_returnsCorrectClients_true() {
    Client clientOne = new Client("Billy", "857-294-1648", stylistOne.getId());
    clientOne.save();
    Client clientTwo = new Client("Sally", "567-294-3448", stylistTwo.getId());
    clientTwo.save();
    List<Client> testList = stylistOne.getClients();
    assertTrue(!(testList.contains(clientTwo))  && testList.contains(clientOne));
  }

  @Test
  public void updateName_updatesStylistName_true() {
    stylistOne.updateName("Pete");
    assertEquals("Pete", Stylist.find(stylistOne.getId()).getName());
  }

  @Test
  public void updatePhoneNumber_updatesStylistPhoneNumber_true() {
    stylistOne.updatePhoneNumber("730-729-9732");
    assertEquals("730-729-9732", Stylist.find(stylistOne.getId()).getPhoneNumber());
  }

  @Test
  public void delete_deletesStylist_true() {
    int deletedId = stylistOne.getId();
    stylistOne.delete();
    assertNull(Stylist.find(deletedId));
  }

}
