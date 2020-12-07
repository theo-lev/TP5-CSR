package backend;

import java.util.ArrayList;
import java.util.Random;

public class Zone {

    private int nbSardine;
    private Requin requin;
    private ArrayList<PoissonPilote> listPoissonP;
    private int x;
    private int y;
    private String id;

    private ArrayList<Zone> zonesAround;

    public Zone(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = x + y +"";
        this.listPoissonP = new ArrayList<>();
        this.zonesAround = new ArrayList<>();
    }

    public void initNbSardine() {
        if (!this.requinExist()) {
            Random r = new Random();
            this.nbSardine = r.nextInt(20);
        }
    }

    public void setRequin(Requin requin) {
        this.requin = requin;
    }

    public void addPoissonP(PoissonPilote poissonPilote) {
        this.listPoissonP.add(poissonPilote);
    }

    public String toString() {
        return "Sardine : " + this.nbSardine + ", backend.Requin : " + this.requinExist() + ", PoissonP : " + this.listPoissonP.size();
    }

    public boolean requinExist() {
        return this.requin != null;
    }

    synchronized void moveIn(Requin requin) {
        if (this.requinExist()) {
            try {
                System.out.println("backend.Requin " + Thread.currentThread().getName() + " wait in zone " + this.getX() + " " +this.getY());
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.requin = requin;
        this.requin.setZone(this);
    }

    synchronized void moveOut() {
        this.requin = null;
        System.out.println("notifyAll moveOut");
        notifyAll();
    }

    synchronized void goOnRequin(PoissonPilote poissonPilote) {
        if (!this.requinExist() || (this.requinExist() && !this.requin.isNotFull())) {
            try {
                System.out.println(Thread.currentThread().getName() + " wait in zone " + this.getX() + " " +this.getY());
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(poissonPilote.getName() + " monte");
        this.requin.addPoissonP(poissonPilote);
        this.removePoissonP(poissonPilote);
        poissonPilote.setZonePrec(poissonPilote.getZone());
        poissonPilote.setZone(null);
        poissonPilote.setRequin(this.requin);

    }

    synchronized void eatSardine() {
        if (nbSardine >= 2) {
            this.nbSardine = this.nbSardine-2;
        } else {
            this.nbSardine = 0;
        }
        System.out.println("notifyAll eatSardines");
        notifyAll();
        try {
            Random r = new Random();
            Thread.sleep(r.nextInt(1000)+1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setZonesAround(ArrayList<Zone> zonesAround) {
        this.zonesAround = zonesAround;
    }

    ArrayList<Zone> getZonesAround() {
        return zonesAround;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void removePoissonP(PoissonPilote p) {
        this.listPoissonP.remove(p);
    }

    public String getId() {
        return id;
    }

    public int getNbSardine() {
        return nbSardine;
    }
}
