package ssm.ssm_server;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.Team;
import services.TeamService;

@Path("teams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Teams {
	
	private TeamService teamService = new TeamService();
	private JsonParser parser= new JsonParser();

	@GET
	@Path("{id}")
    public Response getTeam(@PathParam("id") String id) {
    	try {
			Team team = teamService.getTeam(id);
			if(team == null)
				return Response.status(Response.Status.NOT_FOUND).entity(new Gson().toJson("Team not found!")).build();
			
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(team);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@GET
    public Response getAllTeams() {
    	try {
			List<Team> teams = teamService.getAllTeams();
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(teams);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@POST
    public Response createTeam(String data) {
    	try {
    		JsonObject inputObjectJson = parser.parse(data).getAsJsonObject();
			Team newTeam = teamService.createTeam(inputObjectJson);
			System.out.println(newTeam);
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(newTeam);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@PUT
	@Path("{id}")
    public Response updateTeam(@PathParam("id") String id, String data) {
    	try {
    		JsonObject inputObjectJson = parser.parse(data).getAsJsonObject();
			Team team = teamService.updateTeam(id, inputObjectJson);
			if(team == null)
				return Response.status(Response.Status.NOT_FOUND).entity(new Gson().toJson("Team not found!")).build();
			
			GsonBuilder builder = new GsonBuilder().serializeNulls();
			Gson gson = builder.create();
			String json = gson.toJson(team);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }
	
	@DELETE
	@Path("{id}")
    public Response deleteTeam(@PathParam("id") String id) {
    	try {
			String team = teamService.deleteTeam(id);
			if(team == null)
				return Response.status(Response.Status.NOT_FOUND).entity(new Gson().toJson("Team not found!")).build();
			
			Gson gson = new Gson();
			String json = gson.toJson(team);
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Gson().toJson("Something went wrong. Try again!")).build();
		}
    }

}
