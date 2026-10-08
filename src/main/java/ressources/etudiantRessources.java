package ressources;

import entities.Etudiant;
import metiers.EtudiantBusiness;

import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;

import java.util.List;
import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.core.Response;

@Path("/etudiants")
public class etudiantRessources {
    EtudiantBusiness etudiantBusiness=new EtudiantBusiness();

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Consumes({MediaType.APPLICATION_JSON})

    public Response addEtudiant(Etudiant etudiant) {
        if (etudiantBusiness.addEtudiant(etudiant)){
            return Response.status(Response.Status.CREATED).entity("created successfully").build();


        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Etudiant> getAll(){
        return this.etudiantBusiness.getAllEtudiants();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})

    public Response getEtudiantById(@QueryParam("id") String id) {
        Etudiant etud=this.etudiantBusiness.getEtudiantByIdentifiant(id);
        if (etud!=null){
            return Response.status(Response.Status.OK).entity(etud).build();


        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteEtudiant(@PathParam("id") String id) {
        if (etudiantBusiness.deleteEtudiant(id)){
            return Response.status(Response.Status.NO_CONTENT).entity(this.etudiantBusiness.getEtudiantByIdentifiant(id)).build();


        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})

    public Response updateEtudiant(@PathParam("id") String id,Etudiant etudiant) {
        if (etudiantBusiness.updateEtudiant(id,etudiant)){
            return Response.status(Response.Status.OK).entity(this.etudiantBusiness.getEtudiantByIdentifiant(id)).build();


        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/option")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})

    public Response getEtudiantByOption(@QueryParam("codeOption") int codeOption) {
        //on fait l'instance pour avoir le code de l'opt avec la methode getopbycode
        OptionBusiness optionBusiness = new OptionBusiness();
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option!=null){ //si l'opt avec le code existe on cherche les etud dans l'opt
            List<Etudiant> list=this.etudiantBusiness.getEtudiantsByOption(option);
            if (list!=null){ //Si la liste existe (y a des etds) alors envoyer reponse 200
                return Response.status(Response.Status.OK).entity(list).build();
            }

        }//failed car pas d'options ou liste vide
        return Response.status(Response.Status.NOT_FOUND).build();
    }



}
