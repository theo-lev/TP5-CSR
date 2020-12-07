package backend;

public class PoissonPilote extends Thread{
    private final int DUREE_VIE = 6;
    private Zone zone;
    private Zone zonePrec;
    private Requin requin;

    public PoissonPilote() {
    }

    @Override
    public void run() {
        int i = 0;
        while (i < DUREE_VIE) {
            this.zone.goOnRequin(this);
            this.requin.goOffRequin(this);
            i++;
        }
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

    void setZonePrec(Zone zone) {
        this.zonePrec = zone;
    }

    Zone getZonePrec() {
        return zonePrec;
    }
}
