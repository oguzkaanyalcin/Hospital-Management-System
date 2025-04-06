package model;

public class Doktor extends Person {
    private int id;
    private String speciality;

    public Doktor(int id, String name, String surname, String passw, String email, String phoneNum, String speciality) {
        super(name, surname, passw, email, phoneNum);
        this.id = id;
        this.speciality = speciality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public void displayInfo() {
        System.out.println("Doktor ID: " + id + " | Adı: " + name + " " + surname + " | Uzmanlık: " + speciality);
    }
}
