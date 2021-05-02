package ssm.ssm_server;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.User;
import services.UserService;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Users {

	private UserService usersService = new UserService();
	private JsonParser parser= new JsonParser();

	@GET
	@Path("{id}")
    public Response getUser(@PathParam("id") String id) {
    	try {
			User user = usersService.getUser(id);
			if(user == null)
				return Response.status(Response.Status.NOT_FOUND).entity(new Gson().toJson("User not found!")).build();
			
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(user);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@GET
    public Response getAllUsers(@QueryParam("email") String email) {
    	try {
			List<User> users = usersService.getAllUsers(email);
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(users);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@POST
    public Response createUser(String data) {
    	try {
    		JsonObject inputObjectJson = parser.parse(data).getAsJsonObject();
			User newUser = usersService.createUser(inputObjectJson);
			System.out.println(newUser);
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(newUser);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@PUT
	@Path("{id}")
    public Response updateUser(@PathParam("id") String id, String data) {
    	try {
    		JsonObject inputObjectJson = parser.parse(data).getAsJsonObject();
			User user = usersService.updateUser(id, inputObjectJson);
			if(user == null)
				return Response.status(Response.Status.NOT_FOUND).entity(new Gson().toJson("User not found!")).build();
			
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(user);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@DELETE
	@Path("{id}")
    public Response deleteUser(@PathParam("id") String id) {
    	try {
			String user = usersService.deleteUser(id);
			if(user == null)
				return Response.status(Response.Status.NOT_FOUND).entity(new Gson().toJson("User not found!")).build();
			
			Gson gson = new Gson();
			String json = gson.toJson(user);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
}
