package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.backend.Zone;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ZoneRessource extends ServerResource {

    private Backend backend_;

    public ZoneRessource() {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    @Get("json")
    public Representation getZone() throws JSONException {
        String zoneId = (String) getRequest().getAttributes().get("zoneId");
        JSONObject current = new JSONObject();

        Zone zone = backend_.getDatabase().getZoneById(zoneId);

        current.put("zoneSharkExist", zone.requinExist());
        current.put("zoneNbSardines", zone.getNbSardine());

        return new JsonRepresentation(current);
    }
}
