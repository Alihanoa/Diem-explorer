package com.diemexplorer.explorer;

import com.diem.DiemException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class PerformanceOptimizer extends Thread{

    Connection con;

    @Override
    public void run() {
        while (!interrupted()) {

//            try {
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } catch (DiemException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }


        }



        }

    public void ScanDatabaseToCurrentDate() throws SQLException{
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        LocalDate dateToday = LocalDate.now();
    }
}


