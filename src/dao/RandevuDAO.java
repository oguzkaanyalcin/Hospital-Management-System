package dao;

import model.*;
import db.IDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RandevuDAO implements IRandevuDAO {
    private final IDatabase database;

    public RandevuDAO(IDatabase database) {
        this.database = database;
    }

    private Connection getConnection() throws SQLException {
        return database.connect();
    }

    @Override
    public boolean insertRandevu(Randevu randevu) {
        String sql = "INSERT INTO randevular (hasta_id, doktor_id, tarih, randevu_saati, " +
                "kayit_zamani, durum, notlar) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, randevu.getPatientid());
            pstmt.setInt(2, randevu.getDoctorid());
            pstmt.setDate(3, new java.sql.Date(randevu.getRandevuTarihi().getTime()));
            pstmt.setString(4, randevu.getRandevuSaati());
            pstmt.setTimestamp(5, new Timestamp(randevu.getKayitZamani().getTime()));
            pstmt.setString(6, randevu.getDurum());
            pstmt.setString(7, randevu.getNotlar());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Randevu kaydı başarısız: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Randevu> getAllRandevu() {
        List<Randevu> randevular = new ArrayList<>();
        String sql = "SELECT * FROM randevular ORDER BY tarih, randevu_saati";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                randevular.add(createRandevuFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Randevular listelenemedi: " + e.getMessage());
        }
        return randevular;
    }

    @Override
    public List<Randevu> getRandevuByHastaId(int hastaId) {
        List<Randevu> randevular = new ArrayList<>();
        String sql = "SELECT * FROM randevular WHERE hasta_id = ? ORDER BY tarih, randevu_saati";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hastaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    randevular.add(createRandevuFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Hasta randevuları listelenemedi: " + e.getMessage());
        }
        return randevular;
    }

    @Override
    public boolean deleteRandevu(int randevuId) {
        String sql = "DELETE FROM randevular WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, randevuId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Randevu silinemedi: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Doktor> getDoktorByDepartment(String department) {
        List<Doktor> doktorlar = new ArrayList<>();
        String sql = "SELECT * FROM doktorlar WHERE uzmanlik_alan = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, department);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Doktor doktor = new Doktor(
                            rs.getInt("id"),
                            rs.getString("isim"),
                            rs.getString("soyisim"),
                            rs.getString("sifre"),
                            rs.getString("email"),
                            rs.getString("telefon"),
                            rs.getString("uzmanlik_alan")
                    );
                    doktorlar.add(doktor);
                }
            }
        } catch (SQLException e) {
            System.out.println("Doktorlar listelenemedi: " + e.getMessage());
        }
        return doktorlar;
    }

    @Override
    public List<String> getDepartments() {
        List<String> departments = new ArrayList<>();
        String sql = "SELECT DISTINCT uzmanlik_alan FROM doktorlar";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                departments.add(rs.getString("uzmanlik_alan"));
            }
        } catch (SQLException e) {
            System.out.println("Bölümler listelenemedi: " + e.getMessage());
        }
        return departments;
    }


    public List<Randevu> getRandevuByDoktorId(int doktorId) {
        List<Randevu> randevular = new ArrayList<>();
        String sql = "SELECT * FROM randevular WHERE doktor_id = ? ORDER BY tarih, randevu_saati";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, doktorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    randevular.add(createRandevuFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Doktor randevuları listelenemedi: " + e.getMessage());
        }
        return randevular;
    }

    private Randevu createRandevuFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int hastaId = rs.getInt("hasta_id");
        int doktorId = rs.getInt("doktor_id");
        Date tarih = rs.getDate("tarih");
        String saat = rs.getString("randevu_saati");

        Randevu randevu = new Randevu(hastaId, doktorId, tarih, saat);
        randevu.setId(id);
        randevu.setDurum(rs.getString("durum"));
        randevu.setNotlar(rs.getString("notlar"));
        randevu.setKayitZamani(rs.getTimestamp("kayit_zamani"));
        return randevu;
    }
}