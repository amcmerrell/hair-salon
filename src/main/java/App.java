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
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String clientName = request.queryParams("client-name");
      String clientPhoneNumber = request.queryParams("client-phone");
      int clientStylistId = Integer.parseInt(request.queryParams("stylistId"));
      Client newClient = new Client(clientName, clientPhoneNumber, clientStylistId);
      newClient.save();
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylist_id/clients/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist aStylist = Stylist.find(Integer.parseInt(request.params("id")));
      Client aClient = Client.find(Integer.parseInt(request.params("id")));
      model.put("client", Client.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
