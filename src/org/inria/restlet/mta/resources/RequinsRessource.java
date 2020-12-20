package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class RequinsRessource extends ServerResource {

    private Backend backend_;

    public RequinsRessource()
    {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    @Get("json")
    public Representation getSharksAlive() throws JSONException {
        JSONObject current = new JSONObject();
        if (this.backend_.getDatabase() != null) {
            System.out.println("NONON");
        } else {
            System.out.println("null");
        }
        current.put("sharksAlive", backend_.getDatabase().getNbRequinEnVie());
        return new JsonRepresentation(current);
    }

    @Post("json")
    public void startShark() throws JSONException {
        this.backend_.getDatabase().addNewShark();
    }
}
