import lombok.Cleanup;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306?serverTimezone=UTC&useServerPrepStmts=true&cachePrepStmts=true";
        String username = "root";
        String password = "root";

        String dropDatabase = "drop database if exists RSMDTestDB;";
        String createDatabase = "create database RSMDTestDB;";
        String useDatabase = "use RSMDTestDB;";
        String createTable0 = "create table table0(t0c0 int, t0c1 int, t0c2 int);";
        String createView0 = "create view view0(v0c0, v0c1, v0c2) as select t0c0, t0c1, t0c2 from table0;";
        String selectView0 = "select * from view0;";
        String selectView0WithAlias = "select v0c0 as alias_v0c0, v0c1 as alias_v0c1, v0c2 as alias_v0c2 from view0;";

        @Cleanup
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement stat = conn.createStatement();
        stat.execute(dropDatabase);
        stat.execute(createDatabase);
        stat.execute(useDatabase);
        stat.execute(createTable0);
        stat.execute(createView0);

        // print DBMS info
        DatabaseMetaData dbMD = conn.getMetaData();
        System.out.println(dbMD.getDatabaseProductVersion());

        // select from view without alias
        ResultSet rs = stat.executeQuery(selectView0);
        ResultSetMetaData md = rs.getMetaData();
        System.out.println("SELECT WITHOUT ALIAS");
        System.out.printf("%-10s\t%-10s\t%-10s\n", "Index", "ColName", "ColLabel");
        for(int ind=1; ind <= md.getColumnCount(); ind++) {
            System.out.printf("%-10d\t%-10s\t%-10s\n", ind, md.getColumnName(ind), md.getColumnLabel(ind));
        }
        rs.close();

        // select from view without alias
        rs = stat.executeQuery(selectView0WithAlias);
        md = rs.getMetaData();
        System.out.println("SELECT WITH ALIAS");
        System.out.printf("%-10s\t%-10s\t%-10s\n", "Index", "ColName", "ColLabel");
        for(int ind=1; ind <= md.getColumnCount(); ind++) {
            System.out.printf("%-10d\t%-10s\t%-10s\n", ind, md.getColumnName(ind), md.getColumnLabel(ind));
        }
        rs.close();

        // drop database
        stat.execute(dropDatabase);
    }
}
