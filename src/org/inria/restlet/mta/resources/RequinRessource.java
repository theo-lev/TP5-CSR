package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.backend.Requin;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class RequinRessource extends ServerResource {

    private Backend backend_;

    public RequinRessource()
    {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    @Get("json")
    public Representation getShark() throws JSONException {
        JSONObject current = new JSONObject();

        String sharkIdString = (String) getRequest().getAttributes().get("sharkId");
        int sharkId = Integer.parseInt(sharkIdString);

        Requin shark = backend_.getDatabase().getSharkById(sharkId);

        current.put("sharkZone", shark.getZone());
        current.put("sharkCycleRestant", shark.getCycleRestant());

        return new JsonRepresentation(current);
    }

}
