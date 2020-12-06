import java.util.ArrayList;
import java.util.Random;

class Requin extends Thread {

    private final int DUREE_VIE = 5;
    private final int P = 2;
    private int cycle = DUREE_VIE;
    private Zone zone;
    private int id;
    private ArrayList<PoissonPilote> listPoissonP;

    Requin(int id) {
        this.id = id;
        this.listPoissonP = new ArrayList<>();
    }

    void setZone(Zone zone) {
        this.zone = zone;
    }

    @Override
    public void run() {
        while (cycle > 0) {
            ArrayList<Zone> zonesAround = this.zone.getZonesAround();

            Random random = new Random();
            int i = random.nextInt(zonesAround.size());

            Zone newZone = zonesAround.get(i);
            System.out.println("Requin : "+id+" moveout");

            this.zone.moveOut();

            this.zone = newZone;

            System.out.println("Requin : "+id+" movein");

            this.zone.moveIn(this);
            cycle--;
        }
        this.zone.moveOut();

        System.out.println("Requin : "+id+" over");
    }

    ArrayList<PoissonPilote> getListPoissonP() {
        return listPoissonP;
    }

    boolean isNotFull() {
        return (P - listPoissonP.size()) > 0;
    }

    synchronized void addPoissonP(PoissonPilote p) {
        if (this.isNotFull()) {
            this.listPoissonP.add(p);
        }
    }

    private synchronized void removePoissonP(PoissonPilote p) {
        this.listPoissonP.remove(p);
    }
}
