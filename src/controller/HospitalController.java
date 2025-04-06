package controller;

import model.Hasta;
import model.Randevu;
import model.Doktor;
import dao.*;
import db.IDatabase;
import model.Yonetici;
import view.RandevuUI;
import java.util.List;

public class HospitalController {
    private final IDatabase database;
    private final IHastaDAO hastaDAO;
    private final IDoktorDAO doktorDAO;
    private final IYoneticiDAO yoneticiDAO;
    private final RandevuController randevuController;
    private Hasta currentHasta;
    private Doktor currentDoktor;
    private Yonetici currentYonetici;

    public HospitalController(IDatabase database, IHastaDAO hastaDAO, IDoktorDAO doktorDAO, IYoneticiDAO yoneticiDAO, RandevuController randevuController) {
        this.database = database;
        this.hastaDAO = hastaDAO;
        this.doktorDAO = doktorDAO;
        this.yoneticiDAO = yoneticiDAO;
        this.randevuController = randevuController;
    }

    public boolean registerHasta(Hasta hasta) {
        return hastaDAO.registerHasta(hasta);
    }

    public Hasta loginHasta(String tcno, String password) {
        Hasta hasta = hastaDAO.loginHasta(tcno, password);
        if (hasta != null) {
            this.currentHasta = hasta;
            RandevuUI randevuUI = new RandevuUI(randevuController, hasta.getHastaId());
            randevuUI.showMenu();
            return hasta;
        }
        return null;
    }

    public Doktor loginDoktor(int doktorId, String password) {
        Doktor doktor = doktorDAO.loginDoktor(doktorId, password);
        if (doktor != null) {
            this.currentDoktor = doktor;
            return doktor;
        }
        return null;
    }

    public Yonetici loginYonetici(String password) {
        Yonetici yonetici = yoneticiDAO.loginYonetici(password);
        if (yonetici != null) {
            this.currentYonetici = yonetici;
            return yonetici;
        }
        return null;
    }

    public boolean addDoktor(Doktor doktor) {
        return doktorDAO.addDoktor(doktor);
    }

    public List<Randevu> getRandevularByDoktorId(int doktorId) {
        return randevuController.getRandevularByDoktorId(doktorId);
    }

    public List<Randevu> getAllRandevular() {
        return randevuController.tumRandevular();
    }

    public Doktor getCurrentDoktor() {
        return currentDoktor;
    }

    public RandevuController getRandevuController() {
        return randevuController;
    }

    public boolean randevuAl(Randevu randevu) {
        return randevuController.randevuAl(randevu);
    }
}