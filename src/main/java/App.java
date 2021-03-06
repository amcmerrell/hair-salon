import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("clients", Client.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String stylistName = request.queryParams("stylist-name");
      String stylistWorkDays = request.queryParams("work-days");
      String stylistPhoneNumber = request.queryParams("stylist-phone");
      Stylist newStylist = new Stylist(stylistName, stylistWorkDays, stylistPhoneNumber);
      newStylist.save();
      response.redirect("/stylists/" + newStylist.getId());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylist_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist aStylist = Stylist.find(Integer.parseInt(request.params("stylist_id")));
      model.put("stylist", aStylist);
      model.put("clients", aStylist.getClients());
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylist_id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist deletedStylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
      deletedStylist.delete();
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylist_id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist updatedStylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
      int stylistId = updatedStylist.getId();
      String stylistName = request.queryParams("stylist-name");
      String stylistWorkDays = request.queryParams("work-days");
      String stylistPhoneNumber = request.queryParams("stylist-phone");
      updatedStylist.updateName(stylistName);
      updatedStylist.updateWorkDays(stylistWorkDays);
      updatedStylist.updatePhoneNumber(stylistPhoneNumber);
      response.redirect("/stylists/" + stylistId);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylist_id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist updatedStylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
      model.put("stylist", updatedStylist);
      model.put("template", "templates/stylist-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylist_id/clients/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String clientName = request.queryParams("client-name");
      String clientPhoneNumber = request.queryParams("client-phone");
      int clientStylistId = Integer.parseInt(request.queryParams("stylistId"));
      Client newClient = new Client(clientName, clientPhoneNumber, clientStylistId);
      newClient.save();
      int clientId = newClient.getId();
      response.redirect("/stylists/" + clientStylistId + "/clients/" + clientId);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylist_id/clients/:client_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist aStylist = Stylist.find(Integer.parseInt(request.params("stylist_id")));
      Client aClient = Client.find(Integer.parseInt(request.params("client_id")));
      model.put("stylist", aStylist);
      model.put("client", aClient);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylist_id/clients/:client_id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client deletedClient = Client.find(Integer.parseInt(request.params(":client_id")));
      deletedClient.delete();
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylist_id/clients/:client_id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client updatedClient = Client.find(Integer.parseInt(request.params(":client_id")));
      String clientName = request.queryParams("client-name");
      String clientPhoneNumber = request.queryParams("client-phone");
      int clientStylistId = Integer.parseInt(request.queryParams("stylistId"));
      updatedClient.updateName(clientName);
      updatedClient.updatePhoneNumber(clientPhoneNumber);
      updatedClient.updateStylistId(clientStylistId);
      response.redirect("/stylists/" + clientStylistId + "/clients/" + updatedClient.getId());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylist_id/clients/:client_id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist aStylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
      Client updatedClient = Client.find(Integer.parseInt(request.params(":client_id")));
      model.put("client", updatedClient);
      model.put("stylists", Stylist.all());
      model.put("template", "templates/client-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
