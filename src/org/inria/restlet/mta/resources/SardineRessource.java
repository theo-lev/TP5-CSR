package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class SardineRessource extends ServerResource {

    private Backend backend_;

    public SardineRessource() {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    @Get("json")
    public Representation getTunas() throws JSONException {
        JSONObject current = new JSONObject();
        int total = this.backend_.getDatabase().getTunas();
        current.put("totalSardines", total);
        return new JsonRepresentation(current);
    }


}
