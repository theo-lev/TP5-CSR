import java.util.ArrayList;
import java.util.Random;

class Requin extends Thread {

    private final int DUREE_VIE = 5;
    private int cycle = DUREE_VIE;
    private Zone zone;
    private int id;

    Requin(int id) {
        this.id = id;
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

            System.out.println("Requin : "+id+" cycle : "+cycle);
            cycle--;
        }
        this.zone.moveOut();
        System.out.println("Requin : "+id+" over");
    }
}
