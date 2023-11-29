package model;

import to.Soul;
import util.CrudUtil;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

public class DeathWishMainModel {


    public Date randomYearGenerator() throws ParseException {
        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(2025, 2100);

        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        String newDeathDate=( gc.get(gc.DAY_OF_MONTH)+ "/" + (gc.get(gc.MONTH) + 1) + "/" +gc.get(gc.YEAR) );

        SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");
        Date date1=formatter1.parse(newDeathDate);

        return date1;

    }
    public int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    public boolean checkAvailability(Soul soul) throws SQLException, ClassNotFoundException {
        boolean isExists=false;
        String sql ="SELECT * FROM souls WHERE fName =?;";
        ResultSet result=CrudUtil.execute(sql,soul.getfName());
        while (result.next()){
            String sName= result.getString(3);
            if (sName.equals(soul.getsName())){
                isExists=true;
            }
        }

        return isExists;
    }

    public String getDeathDate(Soul soul) throws SQLException, ClassNotFoundException {
        String dd=null;
        String sId=null;
        String sql ="SELECT * FROM souls WHERE fName =?;";
        ResultSet result=CrudUtil.execute(sql,soul.getfName());
        while (result.next()){
            sId= result.getString(1);
        }


        String ddSql="SELECT dod FROM deathDate WHERE sId=?;";
        ResultSet ddResult = CrudUtil.execute(ddSql,sId);
        while (ddResult.next()){
            dd= String.valueOf(ddResult.getDate(1));
        }
        return dd;
    }

    public boolean saveSoul(Soul soul) throws SQLException, ClassNotFoundException, ParseException {
        String id="";
        String sql="INSERT INTO souls (sId,fName,sName,dob) VALUES (?,?,?,?);";
        boolean isAdded=CrudUtil.execute(sql,soul.getsId(),soul.getfName(),soul.getsName(),soul.getDob());

        if (isAdded){
            String searchSql="SELECT sId FROM souls ORDER BY sId DESC LIMIT 1";
            ResultSet result=CrudUtil.execute(searchSql);
            while (result.next()){
                id=result.getString(1);
            }

            String ddSql="INSERT INTO deathDate (dod,sId)VALUES(?,?);";
            Date dod=randomYearGenerator();
            isAdded=CrudUtil.execute(ddSql, dod,id);
        }

        return isAdded;
    }

    public String generateNewId() {
        try {
            String sql = "SELECT sId FROM souls ORDER BY sId DESC LIMIT 1";
            ResultSet result = CrudUtil.execute(sql);
            if (result.next()) {
                String id = result.getString(1);

                if (id != null && id.matches("S\\d+")) {
                    int newId = Integer.parseInt(id.substring(1)) + 1;
                    String newIdString;
                    if (newId < 10) {
                        newIdString = "S00" + newId;
                    } else if (newId < 100) {
                        newIdString = "S0" + newId;
                    } else {
                        newIdString = "S" + newId;
                    }

                    // Check if the generated ID already exists in the database
                    if (!idExists(newIdString)) {
                        return newIdString;
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return "S001";
    }

    private boolean idExists(String id) throws SQLException, ClassNotFoundException {
        String checkSql = "SELECT COUNT(*) FROM souls WHERE sId = ?";
        ResultSet resultSet = CrudUtil.execute(checkSql, id);
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }
        return false;
    }

}