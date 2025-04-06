package dao;

import model.Randevu;
import model.Doktor;

import java.util.List;

public interface IRandevuDAO {
    boolean insertRandevu(Randevu randevu);

    List<Randevu> getAllRandevu();

    List<Randevu> getRandevuByHastaId(int hastaId);

    boolean deleteRandevu(int randevuId);

    List<Doktor> getDoktorByDepartment(String department);

    List<String> getDepartments();
}