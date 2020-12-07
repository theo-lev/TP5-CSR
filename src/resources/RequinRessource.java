package resources;

import backend.Requin;
import database.api.Ocean;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class RequinRessource extends ServerResource {

    private Requin requin;
    private Ocean ocean;

    public RequinRessource()
    {
        super();
        requin = (Requin) getApplication().getContext().getAttributes()
                .get("requin");
    }

    @Get("json")
    public Representation getSharksAlive() throws JSONException {
        JSONObject current = new JSONObject();
        current.put("sharkAlive", ocean.getNbRequinEnVie());
        return new JsonRepresentation(current);
    }

    @Post("json")
    public void startShark() throws JSONException {
        this.ocean.addNewShark();
    }

    @Get("json")
    public Representation getShark() throws JSONException {
        JSONObject current = new JSONObject();

        String sharkIdString = (String) getRequest().getAttributes().get("sharkId");
        int sharkId = Integer.parseInt(sharkIdString);

        Requin shark = ocean.getSharkById(sharkId);

        current.put("sharkZone", shark.getZone());
        current.put("sharkCycleRestant", shark.getCycleRestant());

        return new JsonRepresentation(current);
    }

}
