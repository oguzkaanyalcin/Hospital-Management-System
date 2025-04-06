package dao;

import model.Doktor;
import model.Randevu;
import java.util.List;

public interface IDoktorDAO {
    Doktor loginDoktor(int doktorId, String password);
    List<Randevu> getRandevularByDoktorId(int doktorId);
    boolean addDoktor(Doktor doktor);
}