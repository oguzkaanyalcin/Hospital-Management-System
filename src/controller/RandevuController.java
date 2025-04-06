package controller;

import dao.RandevuDAO;
import model.*;

import java.util.List;
import java.util.Date;

public class RandevuController {
    private final RandevuDAO randevuDAO;

    public RandevuController(RandevuDAO randevuDAO) {
        this.randevuDAO = randevuDAO;
    }

    public boolean randevuAl(Randevu randevu) {
        List<Randevu> mevcutRandevular = randevuDAO.getRandevuByHastaId(randevu.getHastaId());

        if (mevcutRandevular.stream().anyMatch(r -> r.getDurum().equals("AKTIF"))) {
            System.out.println("Bu hasta zaten aktif bir randevuya sahip.");
            return false;
        }

        randevu.setDurum("AKTIF");
        randevu.setKayitZamani(new Date());

        return randevuDAO.insertRandevu(randevu);
    }

    public List<Randevu> getRandevular(int hastaId) {
        return randevuDAO.getRandevuByHastaId(hastaId);
    }

    public boolean randevuIptal(int randevuId, int hastaId) {
        return randevuDAO.deleteRandevu(randevuId);
    }

    public List<Randevu> tumRandevular() {
        return randevuDAO.getAllRandevu();
    }

    // src/controller/RandevuController.java
    public List<Doktor> getDoktorByDepartment(String department) {
        return randevuDAO.getDoktorByDepartment(department);
    }

    // src/controller/RandevuController.java
    public List<String> getDepartments() {
        return randevuDAO.getDepartments();
    }

    public List<Randevu> getRandevularByDoktorId(int doktorId) {
        return randevuDAO.getRandevuByDoktorId(doktorId);
    }
}