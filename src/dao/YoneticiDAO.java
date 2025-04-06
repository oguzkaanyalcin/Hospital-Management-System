package dao;

import db.IDatabase;
import model.Yonetici;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YoneticiDAO implements IYoneticiDAO {
    private final IDatabase database;

    public YoneticiDAO(IDatabase database) {
        this.database = database;
    }

    private Connection getConnection() throws SQLException {
        return database.connect();
    }

    @Override
    public Yonetici loginYonetici(String password) {
        String sql = "SELECT * FROM yoneticiler WHERE sifre = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Yonetici(
                        rs.getInt("id"),
                        rs.getString("isim"),
                        rs.getString("soyisim"),
                        rs.getString("sifre")
                );
            }
        } catch (SQLException e) {
            System.out.println("Yönetici giriş işlemi başarısız!");
            e.printStackTrace();
        }
        return null;
    }
}