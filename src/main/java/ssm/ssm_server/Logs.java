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
import models.Log;
import services.LogService;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("logs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Logs {

	private LogService logService = new LogService();
	private JsonParser parser= new JsonParser();

	@GET
	@Path("{id}")
    public Response getLog(@PathParam("id") String id) {
    	try {
			Log log = logService.getLog(id);
			if(log == null)
				return Response.status(Response.Status.NOT_FOUND).entity(new Gson().toJson("Log not found!")).build();
			
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(log);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@GET
    public Response getAllLogs(@QueryParam("instance") String instanceId) {
    	try {
			List<Log> logs = logService.getAllLogs(instanceId);
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(logs);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@POST
    public Response createLog(String data) {
    	try {
    		JsonObject inputObjectJson = parser.parse(data).getAsJsonObject();
			Log newLog = logService.createLog(inputObjectJson);
			System.out.println(newLog);
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(newLog);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@PUT
	@Path("{id}")
    public Response updateLog(@PathParam("id") String id, String data) {
    	try {
    		JsonObject inputObjectJson = parser.parse(data).getAsJsonObject();
			Log log = logService.updateLog(id, inputObjectJson);
			if(log == null)
				return Response.status(Response.Status.NOT_FOUND).entity(new Gson().toJson("Log not found!")).build();
			
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(log);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@DELETE
	@Path("{id}")
    public Response deleteLog(@PathParam("id") String id) {
    	try {
			String log = logService.deleteLog(id);
			if(log == null)
				return Response.status(Response.Status.NOT_FOUND).entity(new Gson().toJson("Log not found!")).build();
			
			Gson gson = new Gson();
			String json = gson.toJson(log);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }

}
