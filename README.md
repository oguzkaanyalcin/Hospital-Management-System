Bu proje, bir hastane otomasyon sistemini yönetmek için geliştirilmiş bir Java uygulamasıdır. Proje, hastaların randevu almasını, doktorların randevularını yönetmesini ve yöneticilerin hastane yönetimini gerçekleştirmesini sağlar. PostgreSQL veritabanı kullanılarak veriler saklanır ve yönetilir.

<hr></hr>
Proje Özellikleri
Özellik
Açıklama
Hasta Yönetimi
Hastalar sisteme kayıt olabilir, randevu alabilir ve mevcut randevularını görüntüleyebilir.
Doktor Yönetimi
Doktorlar sisteme giriş yapabilir, randevularını görüntüleyebilir ve yönetebilir.
Yönetici Yönetimi
Yöneticiler sisteme giriş yaparak hastane yönetimi ile ilgili işlemleri gerçekleştirebilir.
Veritabanı Bağlantısı
PostgreSQL veritabanı kullanılarak veriler güvenli bir şekilde saklanır ve yönetilir.
<hr></hr>
Kullanılan Teknolojiler
Teknoloji
Açıklama
Java
Uygulamanın ana programlama dili.
PostgreSQL
Veritabanı yönetim sistemi.
JDBC
Java Database Connectivity, Java ile veritabanı bağlantısı sağlamak için kullanılır.
IntelliJ IDEA
Projenin geliştirilmesi için kullanılan IDE.
<hr></hr>
Kurulum ve Çalıştırma
Proje Deposu: Projeyi GitHub üzerinden klonlayın.


git clone https://github.com/oguzkaanyalcin/Hospital-Management-System.git
cd Hospital-Management-System
Veritabanı Ayarları: src/db/PostgreSQL.java dosyasındaki veritabanı bağlantı bilgilerini kendi veritabanı bilgilerinize göre güncelleyin.


private static final String URL = "jdbc:postgresql://localhost:5432/hastane_db";
private static final String USER = "postgres";
private static final String PASSWORD = "1234";
Veritabanı Oluşturma: PostgreSQL veritabanında gerekli tabloları oluşturmak için aşağıdaki SQL komutlarını çalıştırın.


CREATE TABLE yoneticiler (
    id SERIAL PRIMARY KEY,
    isim VARCHAR(50),
    soyisim VARCHAR(50),
    sifre VARCHAR(50)
);

CREATE TABLE hastalar (
    id SERIAL PRIMARY KEY,
    isim VARCHAR(50),
    soyisim VARCHAR(50),
    tc_no VARCHAR(11),
    sifre VARCHAR(50)
);

CREATE TABLE doktorlar (
    id SERIAL PRIMARY KEY,
    isim VARCHAR(50),
    soyisim VARCHAR(50),
    brans VARCHAR(50),
    sifre VARCHAR(50)
);

CREATE TABLE randevular (
    id SERIAL PRIMARY KEY,
    hasta_id INT REFERENCES hastalar(id),
    doktor_id INT REFERENCES doktorlar(id),
    tarih TIMESTAMP,
    durum VARCHAR(20)
);
Uygulamayı Çalıştırma: IntelliJ IDEA veya başka bir IDE kullanarak Main sınıfını çalıştırın.


public class Main {
    public static void main(String[] args) {
        // Uygulama başlatma kodları
    }
}
<hr></hr>
Katkıda Bulunma
Projeye katkıda bulunmak için GitHub üzerinden bir pull request oluşturabilirsiniz. Her türlü geri bildirim ve katkı memnuniyetle karşılanır.

<hr></hr>
Lisans
Bu proje MIT Lisansı ile lisanslanmıştır. Daha fazla bilgi için LICENSE dosyasına bakabilirsiniz.
