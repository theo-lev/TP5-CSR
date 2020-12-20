package org.inria.restlet.mta.backend;

import org.inria.restlet.mta.database.api.OceanImpl;

/**
 *
 * Backend for all resources.
 *
 * @author ctedeschi
 * @author msimonin
 *
 */
public class Backend {
    /**
     * Database.
     */
    private OceanImpl database_;

    public Backend() {
        database_ = new OceanImpl();
    }

    public OceanImpl getDatabase() {
        return database_;
    }
}
