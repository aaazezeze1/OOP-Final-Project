package g3_hbsystem;

public class Patient extends Person{

    private String patientAddress;
    private String patientPhoneNum;
    private String email;

    public Patient(int patientId, String patientName, String patientAddress, String patientPhoneNum, String email) {
        super(patientId, patientName);
        this.patientAddress = patientAddress;
        this.patientPhoneNum = patientPhoneNum;
        this.email = email;
    }

    public String getPatientAddress()
    {
        return  patientAddress;
    }

    public void setPatientAddress()
    {
        this.patientAddress = patientAddress;
    }

    public String getPatientPhoneNum()
    {
        return patientPhoneNum;
    }

    public void setPatientPhoneNum()
    {
        this.patientPhoneNum = patientPhoneNum;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail()
    {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return  super.toString() + ", Address: " + patientAddress + ", Phone Number: " + patientPhoneNum + " , Email: " + email;
    }

}
