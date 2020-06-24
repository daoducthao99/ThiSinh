package com.example.thisinh;

public class ThiSinh {
    private String sbd;
    private String hoten;
    private double toan;
    private double ly;
    private double hoa;

    public ThiSinh(String sbd, String hoten, double toan, double ly, double hoa) {
        this.sbd = sbd;
        this.hoten = hoten;
        this.toan = toan;
        this.ly = ly;
        this.hoa = hoa;
    }

    public String getSbd() {
        return sbd;
    }

    public void setSbd(String sbd) {
        this.sbd = sbd;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public double getToan() {
        return toan;
    }

    public void setToan(double toan) {
        this.toan = toan;
    }

    public double getLy() {
        return ly;
    }

    public void setLy(double ly) {
        this.ly = ly;
    }

    public double getHoa() {
        return hoa;
    }

    public void setHoa(double hoa) {
        this.hoa = hoa;
    }

    public String getTen(){
        //return hoten.substring(hoten.lastIndexOf(" ")+1);
        return hoten;
    }

    public double getTongDiem(){
        return toan+ly+hoa;
    }
}
