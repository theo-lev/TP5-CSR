package backend;

import java.util.ArrayList;
import java.util.Random;

public class Requin extends Thread {

    private final int DUREE_VIE = 5;
    private final int P = 2;
    private int cycle = DUREE_VIE;
    private Zone zone;
    private int id;
    private ArrayList<PoissonPilote> listPoissonP;

    public Requin(int id) {
        this.id = id;
        this.listPoissonP = new ArrayList<>();
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @Override
    synchronized public void run() {
        while (cycle > 0) {
            ArrayList<Zone> zonesAround = this.zone.getZonesAround();

            Random random = new Random();
            int i = random.nextInt(zonesAround.size());

            Zone newZone = zonesAround.get(i);
            System.out.println("backend.Requin : "+id+" moveout");

            this.zone.moveOut();

            this.zone = newZone;

            System.out.println("backend.Requin : "+id+" movein");

            this.zone.moveIn(this);
            System.out.println("backend.Requin : "+id+" in zone "+ this.zone.getX() + " " + this.zone.getY());
            notifyAll();
            this.zone.eatSardine();
            cycle--;
        }
//        this.zone.moveOut();

        System.out.println("backend.Requin : "+id+" over");
    }

    ArrayList<PoissonPilote> getListPoissonP() {
        return listPoissonP;
    }

    boolean isNotFull() {
        return listPoissonP.size() < 2;
    }

    void addPoissonP(PoissonPilote p) {
        if (this.isNotFull()) {
            this.listPoissonP.add(p);
        }
    }

    private void removePoissonP(PoissonPilote p) {
        this.listPoissonP.remove(p);
    }

    synchronized void goOffRequin(PoissonPilote poissonPilote) {
        if (this.zone == null || (this.zone.equals(poissonPilote.getZonePrec()))) {
            try {
                System.out.println(Thread.currentThread().getName() + " wait in on requin");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(poissonPilote.getName() + " descend");
        poissonPilote.setZone(this.zone);
        this.zone.addPoissonP(poissonPilote);
        this.removePoissonP(poissonPilote);
    }

    public Zone getZone() {
        return zone;
    }

    public int getCycleRestant() {
        return this.DUREE_VIE - cycle;
    }
}
