package model;

public class Hasta extends Person {
    private int hastaId;
    private String tcKimlikNo;
    private String birthDate;
    private String address;

    public Hasta(int hastaId, String name, String surname, String passw, String email, String phoneNum) {
        super(name, surname, passw, email, phoneNum);
        this.hastaId = hastaId;
    }

    public int getHastaId() {
        return hastaId;
    }

    public void setHastaId(int hastaId) {
        this.hastaId = hastaId;
    }

    public String getTcKimlikNo() {
        return tcKimlikNo;
    }

    public void setTcKimlikNo(String tcKimlikNo) {
        this.tcKimlikNo = tcKimlikNo;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return hastaId;
    }

    @Override
    public void displayInfo() {
        System.out.println("Hasta ID: " + hastaId + " | Adı: " + getName() + " " + getSurname());
    }
}