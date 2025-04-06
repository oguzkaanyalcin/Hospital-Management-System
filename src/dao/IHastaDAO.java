package dao;

import model.Hasta;

public interface IHastaDAO {
    boolean registerHasta(Hasta hasta);
    Hasta loginHasta(String tcKimlikNo, String sifre);
}