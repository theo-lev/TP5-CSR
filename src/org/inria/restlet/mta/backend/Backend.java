package org.inria.restlet.mta.backend;

import org.inria.restlet.mta.database.api.Ocean;

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
    private Ocean database_;

    public Backend() {
        database_ = new Ocean();
    }

    public Ocean getDatabase() {
        return database_;
    }
}
