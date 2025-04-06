package dao;

import db.IDatabase;
import model.Doktor;
import model.Randevu;

import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoktorDAO implements IDoktorDAO {
    private final IDatabase database;

    public DoktorDAO(IDatabase database) {
        this.database = database;
    }

    private Connection getConnection() throws SQLException {
        return database.connect();
    }

    public boolean addDoktor(Doktor doktor) {
        String sql = "INSERT INTO doktorlar (isim, soyisim, sifre, email, telefon, uzmanlik_alan) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, doktor.getName());
            pstmt.setString(2, doktor.getSurname());
            pstmt.setString(3, doktor.getPassw());
            pstmt.setString(4, doktor.getEmail());
            pstmt.setString(5, doktor.getPhoneNum());
            pstmt.setString(6, doktor.getSpeciality());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Doktor ekleme işlemi başarısız!");
            e.printStackTrace();
            return false;
        }
    }


    public List<Randevu> getRandevularByDoktorId(int doktorId) {
        List<Randevu> randevular = new ArrayList<>();
        String sql = "SELECT * FROM randevular WHERE doktor_id = ? ORDER BY tarih, randevu_saati";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, doktorId);

            try(ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    randevular.add(createRandevuFromResultSet(rs));
                }
            }

         } catch (SQLException e) {
            System.out.println("Hasta randevuları listelenemedi: " + e.getMessage());
        }
        return randevular;
    }

    public Doktor loginDoktor(int doktorId, String password) {
        String sql = "SELECT * FROM doktorlar WHERE id = ? AND sifre = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, doktorId);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Doktor(
                        rs.getInt("id"),
                        rs.getString("isim"),
                        rs.getString("soyisim"),
                        rs.getString("sifre"),
                        rs.getString("email"),
                        rs.getString("telefon"),
                        rs.getString("uzmanlik_alan")
                );
            }
        } catch (SQLException e) {
            System.out.println("Doktor giriş işlemi başarısız!");
            e.printStackTrace();
        }
        return null;
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