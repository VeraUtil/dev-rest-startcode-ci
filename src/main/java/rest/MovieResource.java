package rest;

import MovieDTO.MovieDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Movie;
import utils.EMF_Creator;
import facades.MovieFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("movie")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final MovieFacade facade =  MovieFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    // Burde det egentlig bruge vores GSON og returnere det eller hva? 
    @Path("id/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MovieDTO getMovieByID(@PathParam("id") int id) {
        return facade.getMovieByID(id);
    }
    
    @Path("all/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MovieDTO> getAllMovies() {
        return facade.getAllMovies();
    }

    @Path("name/{title}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMoviesByTitle(@PathParam("title") String title) {
        List<MovieDTO> movies = facade.getMovieByTitle(title);
        String jsonString = new Gson().toJson(movies);
        return jsonString;
    }
    
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = facade.getMovieCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }
         
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Movie create(Movie entity) {
        return facade.addMovie(entity);
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    /*
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = facade.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }*/
}
