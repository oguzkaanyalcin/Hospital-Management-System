package model;

import java.util.Date;

public class Randevu {
    private int id;
    private int patientid;
    private int doctorid;
    private Date randevuTarihi; //gün olarak
    private String randevuSaati;
    private Date kayitZamani;
    private String durum;
    private String notlar;
    private String doktorAdi; // Add this field

    public Randevu(int patientid, int doctorid, Date randevuTarihi, String randevuSaati) {
        this.patientid = patientid;
        this.doctorid = doctorid;
        this.randevuTarihi = randevuTarihi;
        this.randevuSaati = randevuSaati;
        this.kayitZamani = new Date();
        this.durum = "Aktif";
    }

    public String getDoktorAdi() {
        return doktorAdi;
    }

    public void setDoktorAdi(String doktorAdi) {
        this.doktorAdi = doktorAdi;
    }

    public Date getRandevuTarihi() {
        return randevuTarihi;
    }

    public void setRandevuTarihi(Date randevuTarihi) {
        this.randevuTarihi = randevuTarihi;
    }

    public String getRandevuSaati() {
        return randevuSaati;
    }

    public void setRandevuSaati(String randevuSaati) {
        this.randevuSaati = randevuSaati;
    }

    public Date getKayitZamani() {
        return kayitZamani;
    }

    public void setKayitZamani(Date kayitZamani) {
        this.kayitZamani = kayitZamani;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public String getNotlar() {
        return notlar;
    }

    public void setNotlar(String notlar) {
        this.notlar = notlar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientid() {
        return patientid;
    }

    public void setPatientid(int patientid) {
        this.patientid = patientid;
    }

    public int getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(int doctorid) {
        this.doctorid = doctorid;
    }

    public int getHastaId() {
        return patientid;
    }

    public int getDoktorId() {
        return doctorid;
    }

    public Date getTarih() {
        return randevuTarihi;
    }

    public String getSaat() {
        return randevuSaati;
    }
}