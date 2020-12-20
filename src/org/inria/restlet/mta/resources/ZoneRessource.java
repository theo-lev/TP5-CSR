package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Zone;
import org.inria.restlet.mta.database.api.Ocean;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ZoneRessource extends ServerResource {

    private Zone zone;
    private Ocean ocean;

    public ZoneRessource() {
        super();
        zone = (Zone) getApplication().getContext().getAttributes()
                .get("zone");
    }

    @Get("json")
    public Representation getZone() throws JSONException {
        String zoneId = (String) getRequest().getAttributes().get("zoneId");
        JSONObject current = new JSONObject();

        Zone zone = ocean.getZoneById(zoneId);

        current.put("zoneSharkExist", zone.requinExist());
        current.put("zoneNbSardines", zone.getNbSardine());

        return new JsonRepresentation(current);
    }

    @Get("json")
    public Representation getTunas() throws JSONException {
        JSONObject current = new JSONObject();

        int total = 0;

        for (Zone[] zone: this.ocean.getOcean()) {
            for (Zone zone1: zone) {
                total += zone1.getNbSardine();
            }
        }

        current.put("totalSardines", total);

        return new JsonRepresentation(current);
    }

}
