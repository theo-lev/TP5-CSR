import java.util.ArrayList;
import java.util.Random;

public class Zone {

    private int nbSardine;
    private Requin requin;
    private ArrayList<PoissonPilote> listPoissonP;
    private int x;
    private int y;

    private ArrayList<Zone> zonesAround;

    Zone(int x, int y) {
        this.x = x;
        this.y = y;
        this.listPoissonP = new ArrayList<>();
        this.zonesAround = new ArrayList<>();
    }

    void initNbSardine() {
        if (!this.requinExist()) {
            Random r = new Random();
            this.nbSardine = r.nextInt(20);
        }
    }

    void setRequin(Requin requin) {
        this.requin = requin;
    }

    void addPoissonP(PoissonPilote poissonPilote) {
        this.listPoissonP.add(poissonPilote);
    }

    public String toString() {
        return "Sardine : " + this.nbSardine + ", Requin : " + this.requinExist() + ", PoissonP : " + this.listPoissonP.size();
    }

    boolean requinExist() {
        return this.requin != null;
    }

    synchronized void moveIn(Requin requin) {
        if (this.requinExist()) {
            try {
                System.out.println("Requin " + Thread.currentThread().getName() + " wait in zone " + this.getX() + " " +this.getY());
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.requin = requin;
        this.requin.setZone(this);
        this.eatSardine();
        try {
            Random r = new Random();
            Thread.sleep(r.nextInt(1000)+1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void moveOut() {
        this.requin = null;
        notifyAll();
    }

    private void eatSardine() {
        if (nbSardine >= 2) {
            this.nbSardine = this.nbSardine-2;
        } else {
            this.nbSardine = 0;
        }
    }

    void setZonesAround(ArrayList<Zone> zonesAround) {
        this.zonesAround = zonesAround;
    }

    ArrayList<Zone> getZonesAround() {
        return zonesAround;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void removePoissonP(PoissonPilote p) {
        this.listPoissonP.remove(p);
    }
}
