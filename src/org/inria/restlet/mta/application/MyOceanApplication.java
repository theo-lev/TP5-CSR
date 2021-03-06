package org.inria.restlet.mta.application;

import org.inria.restlet.mta.resources.RequinsRessource;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.inria.restlet.mta.resources.RequinRessource;
import org.inria.restlet.mta.resources.ZoneRessource;

public class MyOceanApplication extends Application {

    public MyOceanApplication(Context context)
    {
        super(context);
    }

    @Override
    public Restlet createInboundRoot()
    {
        Router router = new Router(getContext());
        router.attach("/zones/{zone_id}", ZoneRessource.class);
        router.attach("/sharks", RequinsRessource.class);
        router.attach("/shark/{shark_id}", RequinRessource.class);
        router.attach("/tunas", ZoneRessource.class);
        return router;
    }

}
