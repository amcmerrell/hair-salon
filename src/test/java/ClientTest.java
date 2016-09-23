import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class ClientTest {
  Client clientOne;
  Client clientTwo;

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", "postgres", "panthers");
    clientOne = new Client("Andrew", "919-941-6987", 2);
    clientOne.save();
    clientTwo = new Client("Sarah", "919-847-8745", 2);
    clientTwo.save();
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteClientsQuery = "DELETE FROM clients *;";
      con.createQuery(deleteClientsQuery).executeUpdate();
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
    assertEquals(2, clientOne.getStylistId());
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
}
