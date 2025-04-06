// src/Main.java
import controller.HospitalController;
import controller.RandevuController;
import dao.*;
import db.PostgreSQL;
import model.Yonetici;
import view.HospitalUI;

public class Main {
    public static void main(String[] args) {
        PostgreSQL database = new PostgreSQL();

        if (database.getConnection() == null) {
            System.out.println("Veritabanı bağlantısı kurulamadı. Program sonlandırılıyor.");
            System.exit(1);
        }

        HastaDAO hastaDAO = new HastaDAO(database);
        RandevuDAO randevuDAO = new RandevuDAO(database);
        DoktorDAO doktorDAO = new DoktorDAO(database);
        YoneticiDAO yoneticiDAO = new YoneticiDAO(database);

        RandevuController randevuController = new RandevuController(randevuDAO);
        HospitalController controller = new HospitalController(database, hastaDAO, doktorDAO, yoneticiDAO, randevuController);
        HospitalUI ui = new HospitalUI(controller);
        ui.start();

        database.disconnect();
    }
}