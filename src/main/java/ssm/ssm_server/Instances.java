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
import models.Instance;
import services.InstanceService;
import services.LogService;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("instances")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Instances {

	private InstanceService instanceService = new InstanceService();
	private LogService logService = new LogService();

	private JsonParser parser= new JsonParser();

	
	@GET
	@Path("{id}")
    public Response getInstance(@PathParam("id") String id) {
    	try {
			Instance instance = instanceService.getInstance(id);
			if(instance == null)
				return Response.status(Response.Status.NOT_FOUND).entity(new Gson().toJson("Instance not found!")).build();
			
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(instance);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@GET
    public Response getAllInstances(@QueryParam("team") String teamId) {
    	try {
			List<Instance> instances = instanceService.getAllInstances(teamId);
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(instances);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@POST
    public Response createInstance(String data) {
    	try {
    		JsonObject inputObjectJson = parser.parse(data).getAsJsonObject();
			Instance newInstance = instanceService.createInstance(inputObjectJson);
			System.out.println(newInstance);
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(newInstance);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@PUT
	@Path("{id}")
    public Response updateInstance(@PathParam("id") String id, String data) {
    	try {
    		JsonObject inputObjectJson = parser.parse(data).getAsJsonObject();
			Instance instance = instanceService.updateInstance(id, inputObjectJson);
			if(instance == null)
				return Response.status(Response.Status.NOT_FOUND).entity(new Gson().toJson("Instance not found!")).build();
			
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(instance);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@DELETE
	@Path("{id}")
    public Response deleteInstance(@PathParam("id") String id) {
    	try {
    		logService.deleteAllLog(id);
			String instance = instanceService.deleteInstance(id);
			if(instance == null)
				return Response.status(Response.Status.NOT_FOUND).entity(new Gson().toJson("Instance not found!")).build();
			
			Gson gson = new Gson();
			String json = gson.toJson(instance);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }

}
