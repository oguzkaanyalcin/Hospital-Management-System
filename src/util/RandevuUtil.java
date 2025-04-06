package util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import model.Randevu;
import java.text.SimpleDateFormat;
import java.util.*;

public class RandevuUtil {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final List<Date> availableDates = new ArrayList<>();
    private static final List<Integer> availableHours = Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    static {
        // Sonraki 30 günü ekleyelim
        Calendar calendar = Calendar.getInstance();
        for (int i = 1; i <= 30; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            availableDates.add(calendar.getTime());
        }
    }

    public static Randevu randevuOlustur(int hastaId, int doktorId) {
        // Tarih seçimi
        Date randevuTarihi = null;
        while (randevuTarihi == null) {
            System.out.println("Mevcut randevu tarihleri:");
            for (int i = 0; i < availableDates.size(); i++) {
                System.out.println((i + 1) + ". " + dateFormat.format(availableDates.get(i)));
            }

            System.out.print("Randevu tarihi seçiniz (numara): ");
            int tarihIndex = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme

            if (tarihIndex < 1 || tarihIndex > availableDates.size()) {
                System.out.println("Geçersiz seçim!");
            } else {
                randevuTarihi = availableDates.get(tarihIndex - 1);
            }
        }

        // Saat seçimi
        Integer selectedHour = null;
        while (selectedHour == null) {
            System.out.println("Mevcut saatler:");
            for (int i = 0; i < availableHours.size(); i++) {
                System.out.println((i + 1) + ". " + availableHours.get(i) + ":00");
            }

            System.out.print("Saat seçiniz (numara): ");
            int hourIndex = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme

            if (hourIndex < 1 || hourIndex > availableHours.size()) {
                System.out.println("Geçersiz seçim!");
            } else {
                selectedHour = availableHours.get(hourIndex - 1);
            }
        }

        // 15 dakikalık aralık seçimi
        String randevuSaati = selectMinuteInterval(selectedHour);

        // Randevu oluşturma
        Randevu randevu = new Randevu(hastaId, doktorId, randevuTarihi, randevuSaati);
        randevu.setKayitZamani(new Date());
        randevu.setDurum("BEKLIYOR");

        return randevu;
    }

    private static String selectMinuteInterval(int hour) {
        List<String> intervals = Arrays.asList(
                LocalTime.of(hour, 0).format(formatter),
                LocalTime.of(hour, 15).format(formatter),
                LocalTime.of(hour, 30).format(formatter),
                LocalTime.of(hour, 45).format(formatter)
        );

        System.out.println("Mevcut 15 dakikalık aralıklar:");
        for (int i = 0; i < intervals.size(); i++) {
            System.out.println((i + 1) + ". " + intervals.get(i));
        }

        while (true) {
            System.out.print("15 dakikalık aralık seçiniz (numara): ");
            int intervalIndex = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme

            if (intervalIndex < 1 || intervalIndex > intervals.size()) {
                System.out.println("Geçersiz seçim!");
            } else {
                return intervals.get(intervalIndex - 1);
            }
        }
    }
}
