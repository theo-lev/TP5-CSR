package org.inria.restlet.mta.database.api;

import org.inria.restlet.mta.backend.PoissonPilote;
import org.inria.restlet.mta.backend.Requin;
import org.inria.restlet.mta.backend.Zone;

import java.util.ArrayList;
import java.util.Random;

public class Ocean {

    private final int N = 3;
    private final int NB_REQUIN = 1;
    private final int NB_POISSONP = 9;
    private Zone[][] ocean = new Zone[N][N];
    private ArrayList<Requin> listRequin = new ArrayList<Requin>(NB_REQUIN);
    private ArrayList<PoissonPilote> listPoissonP = new ArrayList<PoissonPilote>(NB_POISSONP);
    private int idRequin = 0;


    public Ocean() {
//        for (int i = 0; i < NB_REQUIN; i++ ) {
//            this.listRequin.add(new Requin(i));
//        }

        for (int i = 0; i < NB_POISSONP; i++ ) {
            this.listPoissonP.add(new PoissonPilote());
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

//        this.initRequin();
        this.initSardine();
        this.initPoissonP();

        this.display();

        for (PoissonPilote poissonPilote: this.listPoissonP) {
            poissonPilote.start();
        }
//
//        for (Requin requin: this.listRequin) {
//            requin.start();
//        }


//        for (Requin requin: this.listRequin) {
//            try {
//                requin.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

//        for (PoissonPilote poissonPilote: this.listPoissonP) {
//            poissonPilote.stop();
//        }


        this.display();

    }

    private void initRequin() {
        Random r = new Random();
        int i = 0;

        while (i < NB_REQUIN) {
            int x = r.nextInt(N);
            int y = r.nextInt(N);

            if (!this.ocean[x][y].requinExist()) {
                this.ocean[x][y].setRequin(listRequin.get(i));
                listRequin.get(i).setZone(this.ocean[x][y]);
                i++;
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

    private void initPoissonP() {
        Random r = new Random();
        int i = 0;

        while (i < NB_POISSONP) {
            int x = r.nextInt(N);
            int y = r.nextInt(N);

            PoissonPilote p = this.listPoissonP.get(i);
            this.ocean[x][y].addPoissonP(p);
            p.setZone(this.ocean[x][y]);
            i++;
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
        ArrayList<Zone> listZone = new ArrayList<Zone>();
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

    public int getNbRequinEnVie() {
        int i = 0;
        for (Requin requin: listRequin) {
            if (requin.isAlive()) {
                i++;
            }
        }
        return i;
    }

    public void addNewShark() {
        boolean isAdd = false;
        Random r = new Random();

        Requin requin = new Requin(this.idRequin);
        this.listRequin.add(requin);
        this.idRequin++;

        while (!isAdd) {
            int x = r.nextInt(N);
            int y = r.nextInt(N);
            if (!this.ocean[x][y].requinExist()) {
                this.ocean[x][y].setRequin(requin);
                isAdd = true;
            }
        }

        requin.start();
        try {
            requin.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Requin> getListRequin() {
        return listRequin;
    }

    public Requin getSharkById(int sharkId) {
        for (Requin r: this.listRequin) {
            if (r.getId() == sharkId) {
                return r;
            }
        }
        return null;
    }

    public Zone getZoneById(String zoneId) {
        for (Zone[] zone : this.ocean) {
            for (Zone zone1: zone) {
                if (zone1.getId().equals(zoneId)) {
                    return zone1;
                }
            }
        }
        return null;
    }

    public Zone[][] getOcean() {
        return ocean;
    }
}
