package view;

import model.Randevu;
import model.Yonetici;
import util.RandevuUtil;
import controller.HospitalController;
import model.Hasta;
import model.Doktor;
import java.util.List;
import java.util.Scanner;

public class HospitalUI {
    private final Scanner scanner;
    private final HospitalController controller;
    private int hastaId;
    private int doktorId;

    public HospitalUI(HospitalController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void start() {
        while (true) {
            showWelcomeMessage();
            showMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle

            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleRegister();
                    break;
                case 3:
                    handleDoktorLogin();
                    break;
                case 4:
                    handleYoneticiLogin();
                    break;
                case 5:
                    System.out.println("Sistemden çıkılıyor...");
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private void showWelcomeMessage() {
        System.out.println("=====================================");
        System.out.println("  KABO HASTANESİNE HOŞGELDİNİZ");
        System.out.println("=====================================");
        System.out.println("Sağlığınız bizim için önemli!");
        System.out.println("=====================================");
    }

    private void showMainMenu() {
        System.out.println("\n=== Hastane Sistemine Hoşgeldiniz ===");
        System.out.println("1. Giriş Yap");
        System.out.println("2. Kayıt Ol");
        System.out.println("3. Doktor Girişi");
        System.out.println("4. Yönetici Girişi");
        System.out.println("5. Çıkış");
        System.out.print("Seçiminiz: ");
    }

    private void showYoneticiMenu() {
        while (true) {
            System.out.println("\n=== Yönetici İşlemleri ===");
            System.out.println("1. Doktor Ekle");
            System.out.println("2. Tüm Randevuları Görüntüle");
            System.out.println("3. Ana Menüye Dön");
            System.out.print("Seçiminiz: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle

            switch (choice) {
                case 1:
                    handleDoktorEkle();
                    break;
                case 2:
                    handleTumRandevulariGoruntule();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private void handleLogin() {
        System.out.print("TC Kimlik No: ");
        String tcno = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        Hasta hasta = controller.loginHasta(tcno, password);
        if (hasta != null) {
            this.hastaId = hasta.getHastaId();
            System.out.println("Giriş başarılı!");
        } else {
            System.out.println("TC Kimlik No veya şifre hatalı!");
        }
    }

    private void handleYoneticiLogin() {
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        Yonetici yonetici = controller.loginYonetici(password);
        if (yonetici != null) {
            System.out.println("Giriş başarılı!");
            showYoneticiMenu();
        } else {
            System.out.println("Şifre hatalı!");
        }
    }

    private void handleRegister() {
        System.out.println("\n=== Hasta Kayıt ===");

        String tcno;
        while (true) {
            System.out.print("TC Kimlik No: ");
            tcno = scanner.nextLine();

            if (!tcno.matches("\\d{11}")) {
                System.out.println("TC Kimlik No 11 haneli rakamlardan oluşmalıdır!");
                continue;
            }
            break;
        }

        System.out.print("Ad: ");
        String name = scanner.nextLine();
        System.out.print("Soyad: ");
        String surname = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefon: ");
        String phone = scanner.nextLine();

        String birthDate;
        while (true) {
            System.out.print("Doğum Tarihi (YYYY-MM-DD): ");
            birthDate = scanner.nextLine();

            if (!birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println("Hatalı tarih formatı! Örnek: 1990-12-31");
                continue;
            }

            try {
                int year = Integer.parseInt(birthDate.substring(0, 4));
                int month = Integer.parseInt(birthDate.substring(5, 7));
                int day = Integer.parseInt(birthDate.substring(8, 10));

                if (year < 1900 || year > 2024 || month < 1 || month > 12 || day < 1 || day > 31) {
                    System.out.println("Geçersiz tarih! Lütfen gerçek bir tarih girin.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Geçersiz tarih! Lütfen sayısal değerler girin.");
            }
        }

        System.out.print("Adres: ");
        String address = scanner.nextLine();

        Hasta yeniHasta = new Hasta(1, name, surname, password, email, phone);
        yeniHasta.setTcKimlikNo(tcno);
        yeniHasta.setBirthDate(birthDate);
        yeniHasta.setAddress(address);

        if (controller.registerHasta(yeniHasta)) {
            System.out.println("Kayıt başarıyla tamamlandı!");
        } else {
            System.out.println("Kayıt işlemi başarısız!");
        }
    }

    private void handleDoktorLogin() {
        System.out.print("Doktor ID: ");
        int doktorId = scanner.nextInt();
        scanner.nextLine(); // Buffer temizle
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        Doktor doktor = controller.loginDoktor(doktorId, password);
        if (doktor != null) {
            this.doktorId = doktor.getId();
            System.out.println("Giriş başarılı!");
            handleRandevulariGoruntule();
        } else {
            System.out.println("Doktor ID veya şifre hatalı!");
        }
    }

    private void handleRandevulariGoruntule() {
        List<Randevu> randevular = controller.getRandevularByDoktorId(doktorId);
        if (randevular.isEmpty()) {
            System.out.println("Bugün için randevunuz bulunmamaktadır.");
        } else {
            System.out.println("Bugünkü randevularınız:");
            for (Randevu randevu : randevular) {
                System.out.println("Randevu ID: " + randevu.getId() +
                        " | Hasta ID: " + randevu.getHastaId() +
                        " | Tarih: " + randevu.getRandevuTarihi() +
                        " | Saat: " + randevu.getRandevuSaati());
            }
        }
    }

    private void handleRandevuAl() {
        if (hastaId == 0) {
            System.out.println("Önce giriş yapmalısınız!");
            return;
        }

        System.out.print("Doktor ID: ");
        int doktorId = scanner.nextInt();
        scanner.nextLine(); // Buffer temizle

        // RandevuUtil kullanarak randevu oluştur
        Randevu randevu = RandevuUtil.randevuOlustur(hastaId, doktorId);

        // Controller üzerinden randevuyu kaydet
        if (controller.randevuAl(randevu)) {
            System.out.println("Randevu başarıyla oluşturuldu.");
        } else {
            System.out.println("Randevu oluşturulamadı!");
        }
    }

    private void handleDoktorEkle() {
        System.out.print("Ad: ");
        String name = scanner.nextLine();
        System.out.print("Soyad: ");
        String surname = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefon: ");
        String phone = scanner.nextLine();
        System.out.print("Uzmanlık Alanı: ");
        String speciality = scanner.nextLine();

        Doktor yeniDoktor = new Doktor(0, name, surname, password, email, phone, speciality);

        if (controller.addDoktor(yeniDoktor)) {
            System.out.println("Doktor başarıyla eklendi!");
        } else {
            System.out.println("Doktor ekleme işlemi başarısız!");
        }
    }

    private void handleTumRandevulariGoruntule() {
        List<Randevu> randevular = controller.getAllRandevular();
        if (randevular.isEmpty()) {
            System.out.println("Hiç randevu bulunmamaktadır.");
        } else {
            System.out.println("Tüm Randevular:");
            for (Randevu randevu : randevular) {
                System.out.println("Randevu ID: " + randevu.getId() +
                        " | Hasta ID: " + randevu.getHastaId() +
                        " | Doktor ID: " + randevu.getDoktorId() +
                        " | Tarih: " + randevu.getRandevuTarihi() +
                        " | Saat: " + randevu.getRandevuSaati());
            }
        }
    }
}