import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class ClientTest {
  Client clientOne;
  Client clientTwo;
  Stylist stylistOne;
  Stylist stylistTwo;

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
    stylistOne = new Stylist("Pete", "Wed, Thu, Fri", "919-941-6987");
    stylistOne.save();
    stylistTwo = new Stylist("Sally", "Mon, Wed, Fri", "919-847-8745");
    stylistTwo.save();
    clientOne = new Client("Andrew", "919-941-6987", stylistOne.getId());
    clientOne.save();
    clientTwo = new Client("Sarah", "919-847-8745", 2);
    clientTwo.save();
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteClientsQuery = "DELETE FROM clients *;";
      String deleteStylistsQuery = "DELETE FROM stylists *;";
      con.createQuery(deleteClientsQuery).executeUpdate();
      con.createQuery(deleteStylistsQuery).executeUpdate();
    }
  }

  @Test
  public void constructor_clientInstantiatesCorrectly_true() {
    assertTrue(clientOne instanceof Client);
  }

  @Test
  public void getName_returnsCorrectName_Andrew() {
    assertEquals("Andrew", clientOne.getName());
  }

  @Test
  public void getPhoneNumber_returnsCorrectPhoneNumber_919_941_6987() {
    assertEquals("919-941-6987", clientOne.getPhoneNumber());
  }

  @Test
  public void getStylistId_returnsCorrectStylistId_2() {
    assertEquals(stylistOne.getId(), clientOne.getStylistId());
  }

  @Test
  public void equals_returnsTrueIfFieldsAreSame_true() {
    Client clientEqualsOne = new Client("Andrew", "919-941-6987", 2);
    Client clientEqualsTwo = new Client("Andrew", "919-941-6987", 2);
    assertTrue(clientEqualsOne.equals(clientEqualsTwo));
  }

  @Test
  public void all_returnsAllInstancesOfClient_true() {
    assertTrue(Client.all().get(0).equals(clientOne));
    assertTrue(Client.all().get(1).equals(clientTwo));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    assertTrue(Client.all().get(0).equals(clientOne));
  }

  @Test
  public void save_assignsIdToObject() {
    Client savedClient = Client.all().get(0);
    assertEquals(clientOne.getId(), savedClient.getId());
  }

  @Test
  public void find_returnsClientWithSameId_true() {
    assertEquals(clientTwo, Client.find(clientTwo.getId()));
  }

  @Test
  public void updateName_updatesClientName_true() {
    clientOne.updateName("Pete");
    assertEquals("Pete", Client.find(clientOne.getId()).getName());
  }

  @Test
  public void updatePhoneNumber_updatesClientPhoneNumber_true() {
    clientOne.updatePhoneNumber("730-729-9732");
    assertEquals("730-729-9732", Client.find(clientOne.getId()).getPhoneNumber());
  }

  @Test
  public void updateStylistId_updatesClientStylistId_true() {
    clientOne.updateStylistId(stylistTwo.getId());
    assertEquals(stylistTwo.getId(), Client.find(clientOne.getId()).getStylistId());
  }

  @Test
  public void delete_deletesClient_true() {
    int deletedId = clientOne.getId();
    clientOne.delete();
    assertNull(Client.find(deletedId));
  }

}
