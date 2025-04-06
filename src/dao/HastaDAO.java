package dao;

import db.IDatabase;
import model.Hasta;
import java.sql.*;

public class HastaDAO implements IHastaDAO {
    private final IDatabase database;

    public HastaDAO(IDatabase database) {
        this.database = database;
    }

    private Connection getConnection() throws SQLException {
        return database.connect();
    }

    @Override
    public boolean registerHasta(Hasta hasta) {
        String sql = "INSERT INTO hastalar (tc_kimlik_no, isim, soyisim, sifre, " +
                "dogum_tarihi, telefon, email, adres) " +
                "VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, hasta.getTcKimlikNo());
            pstmt.setString(2, hasta.getName());
            pstmt.setString(3, hasta.getSurname());
            pstmt.setString(4, hasta.getPassw());
            pstmt.setString(5, hasta.getBirthDate());
            pstmt.setString(6, hasta.getPhoneNum());
            pstmt.setString(7, hasta.getEmail());
            pstmt.setString(8, hasta.getAddress());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Hasta kayıt işlemi başarısız!");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Hasta loginHasta(String tcKimlikNo, String sifre) {
        String sql = "SELECT * FROM hastalar WHERE tc_kimlik_no = ? AND sifre = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, tcKimlikNo);
            pstmt.setString(2, sifre);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Hasta(
                        rs.getInt("id"),
                        rs.getString("isim"),
                        rs.getString("soyisim"),
                        rs.getString("sifre"),
                        rs.getString("email"),
                        rs.getString("telefon")
                );
            }
        } catch (SQLException e) {
            System.out.println("Hasta giriş işlemi başarısız!");
            e.printStackTrace();
        }
        return null;
    }
}