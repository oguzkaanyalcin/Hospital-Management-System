# Hospital Management System

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue.svg)](https://www.postgresql.org/)

> Bu proje, bir hastane otomasyon sistemini yönetmek için geliştirilmiş bir Java uygulamasıdır. Proje, hastaların randevu almasını, doktorların randevularını yönetmesini ve yöneticilerin hastane yönetimini gerçekleştirmesini sağlar. PostgreSQL veritabanı kullanılarak veriler saklanır ve yönetilir.

## 📋 İçindekiler

- [Proje Özellikleri](#-proje-özellikleri)
- [Kullanılan Teknolojiler](#-kullanılan-teknolojiler)
- [Kurulum ve Çalıştırma](#-kurulum-ve-çalıştırma)
- [Sistem Mimarisi](#-sistem-mimarisi)
- [Veritabanı Şeması](#-veritabanı-şeması)
- [Katkıda Bulunma](#-katkıda-bulunma)
- [Lisans](#-lisans)

## 🚀 Proje Özellikleri

| Özellik | Açıklama |
|---------|----------|
| **📊 Hasta Yönetimi** | Hastalar sisteme kayıt olabilir, randevu alabilir ve mevcut randevularını görüntüleyebilir. |
| **👨‍⚕️ Doktor Yönetimi** | Doktorlar sisteme giriş yapabilir, randevularını görüntüleyebilir ve yönetebilir. |
| **👨‍💼 Yönetici Yönetimi** | Yöneticiler sisteme giriş yaparak hastane yönetimi ile ilgili işlemleri gerçekleştirebilir. |
| **🔒 Veritabanı Bağlantısı** | PostgreSQL veritabanı kullanılarak veriler güvenli bir şekilde saklanır ve yönetilir. |

## 💻 Kullanılan Teknolojiler

| Teknoloji | Açıklama |
|-----------|----------|
| **☕ Java** | Uygulamanın ana programlama dili. |
| **🐘 PostgreSQL** | Veritabanı yönetim sistemi. |
| **🔌 JDBC** | Java Database Connectivity, Java ile veritabanı bağlantısı sağlamak için kullanılır. |
| **🛠️ IntelliJ IDEA** | Projenin geliştirilmesi için kullanılan IDE. |

## 📥 Kurulum ve Çalıştırma

### 1️⃣ Proje Deposu
Projeyi GitHub üzerinden klonlayın:

```bash
git clone https://github.com/oguzkaanyalcin/Hospital-Management-System.git
cd Hospital-Management-System
```
### 2️⃣ Veritabanı Ayarları
src/db/PostgreSQL.java dosyasındaki veritabanı bağlantı bilgilerini kendi veritabanı bilgilerinize göre güncelleyin:
```bash
private static final String URL = "jdbc:postgresql://localhost:5432/hastane_db";
private static final String USER = "postgres";
private static final String PASSWORD = "1234";
```

### 3️⃣ Veritabanı Oluşturma
PostgreSQL veritabanında gerekli tabloları oluşturmak için aşağıdaki SQL komutlarını çalıştırın:
```bash 
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
```
```bash
public class Main {
    public static void main(String[] args) {
        // Uygulama başlatma kodları
    }
}
```
```bash
src/
├── controller/
│   ├── HospitalController.java
│   └── RandevuController.java
├── dao/
│   ├── DoktorDAO.java
│   ├── HastaDAO.java
│   ├── IDoktorDAO.java
│   ├── RandevuDAO.java
│   └── YoneticiDAO.java
├── db/
│   ├── IDatabase.java
│   └── PostgreSQL.java
├── model/
│   ├── Doktor.java
│   ├── Hasta.java
│   ├── Randevu.java
│   └── Yonetici.java
├── view/
│   └── HospitalUI.java
└── Main.java
```
## Geliştiriciler

- [Oğuz Kaan Yalçın](https://github.com/oguzkaanyalcin)
- [Bora Kitirci](https://github.com/bboraki)

