package ressources;

import metiers.OptionBusiness;

import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;

import java.util.List;
import entities.Option;

import javax.ws.rs.core.Response;

import metiers.OptionBusiness;

@Path("/options")
public class OptionRessource {

    OptionBusiness optionBusiness=new OptionBusiness();

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Option> getAll(){
        return this.optionBusiness.getListeOptions();
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})

    public Response getBySemestre(@QueryParam("semestre") int semestre) {
        List<Option> opt=this.optionBusiness.getOptionsBySemestre(semestre);
        if (opt!=null){
            return Response.status(Response.Status.OK).entity(opt).build();


        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Consumes({MediaType.APPLICATION_JSON})

    public Response addOption(Option option) {
        if (optionBusiness.addOption(option)){
            return Response.status(Response.Status.CREATED).entity("created successfully").build();


        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

        @PUT
        @Path("/{code}")
        @Produces({MediaType.APPLICATION_JSON})
        @Consumes({MediaType.APPLICATION_JSON})

        public Response updateOption(Option option,@PathParam("code") int code) {
            if (optionBusiness.updateOption(code,option)){
                return Response.status(Response.Status.OK).entity(this.optionBusiness.getOptionByCode(code)).build();


            }
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    @DELETE
    @Path("/{code}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})

    public Response deleteOption(@PathParam("code") int code) {
        if (optionBusiness.deleteOption(code)){
            return Response.status(Response.Status.NO_CONTENT).entity(this.optionBusiness.getOptionByCode(code)).build();


        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
