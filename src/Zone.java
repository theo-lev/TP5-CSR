import java.util.ArrayList;
import java.util.Random;

public class Zone {

    private int nbSardine;
    private Requin requin;
    private int x;
    private int y;

    private ArrayList<Zone> zonesAround;

    Zone(int x, int y) {
        this.x = x;
        this.y = y;
        this.zonesAround = new ArrayList<>();
    }

    void initNbSardine() {
        if (!this.requinExist()) {
            Random r = new Random();
            this.nbSardine = r.nextInt(10);
        }
    }

    void setRequin(Requin requin) {
        this.requin = requin;
    }

    public String toString() {
        return "Sardine : " + this.nbSardine + ", Requin : " + this.requinExist();
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
        notifyAll();
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

}
