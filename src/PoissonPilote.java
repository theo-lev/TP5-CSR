class PoissonPilote extends Thread{
    private final int DUREE_VIE = 6;
    private Zone zone;
    private Requin requin;

    PoissonPilote() {
    }

    @Override
    public void run() {
//        int i = 0;
//        while (i < DUREE_VIE) {
//            this.zone.goOnRequin(this);
//            i++;
//            this.requin.goOffRequin(this);
//        }
//        this.zone.removePoissonP(this);
    }

    Zone getZone() {
        return zone;
    }

    void setZone(Zone zone) {
        this.zone = zone;
    }

    void setRequin(Requin requin) {
        this.requin = requin;
    }
}
