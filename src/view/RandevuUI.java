package view;

import controller.RandevuController;
import java.util.Scanner;
import util.RandevuUtil;
import model.*;
import java.util.List;

public class RandevuUI {
    private final Scanner scanner;
    private final RandevuController controller;
    private final int hastaId;

    public RandevuUI(RandevuController controller, int hastaId) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
        this.hastaId = hastaId;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== Randevu İşlemleri ===");
            System.out.println("1. Randevu Al");
            System.out.println("2. Randevularımı Görüntüle");
            System.out.println("3. Randevu İptal Et");
            System.out.println("4. Ana Menüye Dön");
            System.out.print("Seçiminiz: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle

            switch (choice) {
                case 1:
                    handleRandevuAl();
                    break;
                case 2:
                    handleRandevularıGoruntule();
                    break;
                case 3:
                    handleRandevuIptal();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }


    private void handleRandevuAl() {
        List<String> departments = controller.getDepartments();
        if (departments.isEmpty()) {
            System.out.println("Bölümler bulunamadı.");
            return;
        }

        System.out.println("Bölümler:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". " + departments.get(i));
        }

        System.out.print("Bölüm seçiniz (numara): ");
        int departmentIndex = scanner.nextInt();
        scanner.nextLine(); // Buffer temizle

        if (departmentIndex < 1 || departmentIndex > departments.size()) {
            System.out.println("Geçersiz bölüm seçimi!");
            return;
        }

        String department = departments.get(departmentIndex - 1);
        List<Doktor> doktorlar = controller.getDoktorByDepartment(department);
        if (doktorlar.isEmpty()) {
            System.out.println("Bu bölümde doktor bulunamadı.");
            return;
        }

        System.out.println("Doktorlar:");
        for (int i = 0; i < doktorlar.size(); i++) {
            Doktor doktor = doktorlar.get(i);
            System.out.println((i + 1) + ". " + doktor.getName() + " " + doktor.getSurname());
        }

        System.out.print("Doktor seçiniz (numara): ");
        int doktorIndex = scanner.nextInt();
        scanner.nextLine(); // Buffer temizle

        if (doktorIndex < 1 || doktorIndex > doktorlar.size()) {
            System.out.println("Geçersiz doktor seçimi!");
            return;
        }

        Doktor selectedDoktor = doktorlar.get(doktorIndex - 1);

        // RandevuUtil kullanarak randevu oluştur
        Randevu randevu = RandevuUtil.randevuOlustur(hastaId, selectedDoktor.getId());

        // Controller üzerinden randevuyu kaydet
        if (controller.randevuAl(randevu)) {
            System.out.println("Randevu başarıyla oluşturuldu.");
        } else {
            System.out.println("Randevu oluşturulamadı!");
        }
    }

    private void handleRandevularıGoruntule() {
        List<Randevu> randevular = controller.getRandevular(hastaId);
        if (randevular.isEmpty()) {
            System.out.println("Hiç randevunuz bulunmamaktadır.");
        } else {
            System.out.println("Randevularınız:");
            for (Randevu randevu : randevular) {
                System.out.println("Randevu ID: " + randevu.getId() +
                        " | Tarih: " + randevu.getRandevuTarihi() +
                        " | Saat: " + randevu.getRandevuSaati() +
                        " | Doktor: " + randevu.getDoktorAdi());
            }
        }
    }

    private void handleRandevuIptal() {
        List<Randevu> randevular = controller.getRandevular(hastaId);
        if (randevular.isEmpty()) {
            System.out.println("İptal edilecek randevu bulunmamaktadır.");
            return;
        }

        System.out.print("İptal edilecek randevu ID: ");
        int randevuId = scanner.nextInt();
        if (controller.randevuIptal(randevuId, hastaId)) {
            System.out.println("Randevu başarıyla iptal edildi.");
        } else {
            System.out.println("Randevu iptal edilemedi!");
        }
    }
}