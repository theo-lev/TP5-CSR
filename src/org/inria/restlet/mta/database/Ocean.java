package org.inria.restlet.mta.database;

import org.inria.restlet.mta.backend.Requin;
import org.inria.restlet.mta.backend.Zone;

public interface Ocean {
    int getNbRequinEnVie();

    void addNewShark();

    Requin getSharkById(int sharkId);

    Zone getZoneById(String zoneId);

    int getTunas();
}
