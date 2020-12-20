package org.inria.restlet.mta.backend;

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
        this.listPoissonP = new ArrayList<PoissonPilote>();
    }

    public void setZone(Zone zone) {
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
            newZone.moveIn(this);
            this.zone = newZone;

            System.out.println("Requin : "+id+" in zone "+ this.zone.getX() + " " + this.zone.getY());
            notifierPoisson();

            this.zone.eatSardine();
            cycle--;
        }
        notifierPoisson();
        this.zone.moveOut();

        System.out.println("Requin : "+id+" over");
    }

    synchronized private void notifierPoisson() {
        notifyAll();
    }

    ArrayList<PoissonPilote> getListPoissonP() {
        return listPoissonP;
    }

    private synchronized boolean isNotFull() {
        return listPoissonP.size() < P;
    }

    private void addPoissonP(PoissonPilote p) {
        if (this.isNotFull()) {
            this.listPoissonP.add(p);
        }
    }

    private void removePoissonP(PoissonPilote p) {
        this.listPoissonP.remove(p);
    }

    synchronized void goOffRequin(PoissonPilote poissonPilote) {
        if (this.zone == null || this.zone == poissonPilote.getZone()) {
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

    synchronized boolean goOnRequin(PoissonPilote poissonPilote) {
        if (this.isNotFull()) {
            System.out.println(poissonPilote.getName() + " monte sur requin " + this.getId());
            this.addPoissonP(poissonPilote);
            poissonPilote.setRequin(this);
            return true;
        } else {
            return false;
        }
    }

    public Zone getZone() {
        return zone;
    }

    public int getCycleRestant() {
        return this.DUREE_VIE - cycle;
    }
}
