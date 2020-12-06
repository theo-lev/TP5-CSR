import java.util.ArrayList;
import java.util.Random;

class Ocean {

    private final int N = 3;
    private final int NB_REQUIN = 2;
    private Zone[][] ocean = new Zone[N][N];
    private ArrayList<Requin> listRequin = new ArrayList<>(NB_REQUIN);


    Ocean() {
        for (int i = 0; i < NB_REQUIN; i++ ) {
            listRequin.add(new Requin(i));
        }

        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                this.ocean[x][y] = new Zone(x, y);
            }
        }

        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                this.ocean[x][y].setZonesAround(this.getZoneAround(this.ocean[x][y]));
            }
        }

        this.initRequin();
        this.initSardine();

        this.display();

        for (Requin requin: this.listRequin) {
            requin.start();
        }

        for (Requin requin: this.listRequin) {
            try {
                requin.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.display();

    }

    private void initRequin() {
        Random r = new Random();

        ArrayList<Requin> listTemp = new ArrayList<>(listRequin);
        while (listTemp.size() > 0) {
            int randomRequin = r.nextInt(listTemp.size());
            int x = r.nextInt(N);
            int y = r.nextInt(N);

            if (!this.ocean[x][y].requinExist()) {
                this.ocean[x][y].setRequin(listRequin.get(randomRequin));
                listRequin.get(randomRequin).setZone(this.ocean[x][y]);
                listTemp.remove(randomRequin);
            }
        }
    }

    private void initSardine() {
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                this.ocean[x][y].initNbSardine();
            }
        }
    }

    private void display() {
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                System.out.print(" | " + this.ocean[x][y].toString() + " | ");
            }
            System.out.println();
        }
    }

    private ArrayList<Zone> getZoneAround(Zone zone) {
        ArrayList<Zone> listZone = new ArrayList<>();
        int x = zone.getX();
        int y = zone.getY();

        //UP
        if (x == 0) {
            listZone.add(this.ocean[N-1][y]);
        } else {
            listZone.add(this.ocean[x-1][y]);
        }
        //RIGHT
        if (y == N-1) {
            listZone.add(this.ocean[x][0]);
        } else {
            listZone.add(this.ocean[x][y+1]);
        }
        //DOWN
        if (x == N-1) {
            listZone.add(this.ocean[0][y]);
        } else {
            listZone.add(this.ocean[x+1][y]);
        }
        //LEFT
        if (y == 0) {
            listZone.add(this.ocean[x][N-1]);
        } else {
            listZone.add(this.ocean[x][y-1]);
        }

        return listZone;
    }

}
