package dao;

import model.Yonetici;

public interface IYoneticiDAO {
    Yonetici loginYonetici(String password);
}