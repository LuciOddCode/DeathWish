package to;

import java.sql.Date;


public class Soul {
    private String sId;
    private String fName;
    private String sName;
    private Date dob;

    public Soul() {
    }

    public Soul(String sId,String fName, String sName, Date dob) {
        this.sId=sId;
        this.fName = fName;
        this.sName = sName;
        this.dob = dob;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Soul{" +
                "fName='" + fName + '\'' +
                ", sName='" + sName + '\'' +
                ", dob=" + dob +
                '}';
    }
}
