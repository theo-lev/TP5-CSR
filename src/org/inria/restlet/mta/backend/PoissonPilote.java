package org.inria.restlet.mta.backend;

public class PoissonPilote extends Thread{
    private final int DUREE_VIE = 6;
    private Zone zone;
    private Requin requin;

    public PoissonPilote() {
    }

    @Override
    public void run() {
        int i = 0;
        while (i < DUREE_VIE) {
            Requin requin = this.zone.waitRequin();
            if (requin != null) {
                boolean b = requin.goOnRequin(this);
                if (b) {
                    this.zone.removePoissonP(this);
                    this.requin.goOffRequin(this);
                    i++;
                } else {
                    this.zone.addPoissonP(this);
                }
            }
        }
        System.out.println("Poisson meurt");
        this.zone.removePoissonP(this);
    }

    Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    void setRequin(Requin requin) {
        this.requin = requin;
    }
}
