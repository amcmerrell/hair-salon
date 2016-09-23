import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class ClientTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", "postgres", "panthers");
    Client testClient = new Client("Andrew", "919-941-6987", 2);
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
    Client testClient = new Client("Andrew", "919-941-6987", 2);
    assertTrue(testClient instanceof Client);
  }

  @Test
  public void getName_returnsCorrectName_Andrew() {
    Client testClient = new Client("Andrew", "919-941-6987", 2);
    assertEquals("Andrew", testClient.getName());
  }

  @Test
  public void getPhoneNumber_returnsCorrectPhoneNumber_919_941_6987() {
    Client testClient = new Client("Andrew", "919-941-6987", 2);
    assertEquals("919-941-6987", testClient.getPhoneNumber());
  }

  @Test
  public void getStylistId_returnsCorrectStylistId_2() {
    Client testClient = new Client("Andrew", "919-941-6987", 2);
    assertEquals(2, testClient.getStylistId());
  }

  @Test
  public void equals_returnsTrueIfFieldsAreSame_true() {
    Client clientEqualsOne = new Client("Andrew", "919-941-6987", 2);
    Client clientEqualsTwo = new Client("Andrew", "919-941-6987", 2);
    assertTrue(clientEqualsOne.equals(clientEqualsTwo));
  }

  @Test
  public void all_returnsAllInstancesOfClient_true() {
    Client clientOne = new Client("Andrew", "919-941-6987", 2);
    clientOne.save();
    Client clientTwo = new Client("Sarah", "919-847-8745", 2);
    clientTwo.save();
    assertTrue(Client.all().get(0).equals(clientOne));
    assertTrue(Client.all().get(1).equals(clientTwo));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Client testClient = new Client("Andrew", "919-941-6987", 2);
    testClient.save();
    assertTrue(Client.all().get(0).equals(testClient));
  }

}
