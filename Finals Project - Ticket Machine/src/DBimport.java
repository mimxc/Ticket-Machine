import java.sql.*;

public class DBimport {

  private int seatFirst;
  private int benchmark;
  protected String seatFinal;
  private String ticketFinal;
  private String command;
  private String tablePick;
  private String ticketBus;
  private int departureNumberDB;
  private String ticketInDB;
  private int departureNumberTRint;
  private int seatInDB;
  private boolean duplicateFound;
  protected boolean ticketFound;
  protected boolean bus1Full=false, bus2Full=false, bus3Full=false, bus4Full=false;
  private boolean searching;

  Connection conn;
  Statement statement;
  ResultSet rs;

  public void runConnection() {
    try {
      conn = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12808197", "sql12808197", "cCbesBjc9I");
    }
    catch (Exception e) {
      System.out.println("Connection error");
    }
  }

  public void setTicket(String ticketFinal) {
    this.ticketFinal = ticketFinal;
  }
  public String getTicket() {
    return ticketFinal;
  }

  public void setSeatFirst(int seatFirst) {
    this.seatFirst = seatFirst;
  }
  public int getSeatFirst() {
    return seatFirst;
  }
  
  public void setDeparturedb(int departureNumberDB) {
    this.departureNumberDB = departureNumberDB;
  }
  public int getDeparturedb() {
    return departureNumberDB;
  }

  public void setBusdb(String ticketBus) {
    this.ticketBus = ticketBus;
  }
  public String getBusdb() {
    return ticketBus;
  }



  public void setTime() {
    getDeparturedb();

    // switches between all the table names in the database depending on the user's choice of departure time
    switch (departureNumberDB) {
      case 1:
        tablePick = "six_AM";
        break;

      case 2:
        tablePick = "Nine_AM";
        break;

      case 3:
        tablePick = "one_PM";
        break;

      case 4:
        tablePick = "four_PM";
        break;
    }
  }

  public void checkSeat() {

    duplicateFound = false;

    getBusdb();

    setTime();
    //set the seat's value to number 0, which is filler
    seatFirst = 00;
    benchmark = 00;

    //connect to database
    runConnection();

    //set the command with whichever table to pick from (by time), with only the bus number predetermined, and get the table organised by the seat number
    command = "select * from sql12808197." + tablePick + " where busNumber = '" + ticketBus + "' order by seatNumber";

    try {
      statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
    ResultSet.CONCUR_READ_ONLY);

      //takes the command String that is in SQL syntax and executes it as an SQL code
      rs = statement.executeQuery(command);

      //reads the table in the database as long as there is a data available in the row
      while (rs.next() && !duplicateFound) {

        seatInDB = rs.getInt("seatNumber");

        if (seatInDB==seatFirst) {

          //if the seat number is already taken, add one more value to "move" to the next seat
          seatFirst++;
          benchmark++;

          if (ticketBus.equals("01") && benchmark==41) {
          
          bus1Full = true;
          System.out.println("bus #1 full");
        }
        else if (ticketBus.equals("02") && benchmark==41) {
          
          bus2Full = true;
          System.out.println("bus #2 full");
        }
        else if (ticketBus.equals("03") && benchmark==41) {
          
          bus3Full = true;
          System.out.println("bus #3 full");
        }
        else if (ticketBus.equals("04") && benchmark==41) {
          
          bus4Full = true;
          System.out.println("bus #4 full");
        }
        }
        else if (seatInDB!=seatFirst) {

          //if the seat isn't taken yet, end the reading of database
          duplicateFound = true;
        }

}
          setSeatFirst(seatFirst);
          seatFinal = "0" + String.valueOf(seatFirst);

    }
    catch (SQLException e) {
      duplicateFound = true;
      System.out.println("\nstatement and result error");
    }

  }

  public void ticketIntoDB(String ticketFinal) {

    getBusdb();
    setTime();
    getSeatFirst();

    command = "insert into " + tablePick + " (ticketNumber, busNumber, seatNumber) values ('" + ticketFinal + "', '" + ticketBus + "', " + seatFirst + ")";

    try {
      statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
    ResultSet.CONCUR_READ_ONLY);

      statement.executeUpdate(command);

    }
    catch (SQLException e) {
      System.out.println("\ninserting ticket into db failed");
    }
    finally {
      try {
        statement.close();
      }
      catch (SQLException e) {

      }
    }
  }

  public void setTimeTR(String ticketDeparture) {

    try {
    departureNumberTRint = Integer.parseInt(ticketDeparture);
    }
    catch (Exception e) {

    }

    switch (departureNumberTRint) {
      case 66, 96:
        tablePick = "six_AM";
        break;

      case 69, 99:
        tablePick = "Nine_AM";
        break;

      case 61, 91:
        tablePick = "one_PM";
        break;

      case 64, 94:
        tablePick = "four_PM";
        break;
    } 
  }

  public void ticketIntoDB(String ticketFinal, String ticketBus, String ticketDeparture) {

    ticketFound = false;
    searching = true;
    benchmark = 01;

    runConnection();

    setTimeTR(ticketDeparture);

    command = "select * from sql12808197." + tablePick + " where busNumber = '" + ticketBus + "' order by seatNumber";

    try {
      statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

      rs = statement.executeQuery(command);

      while (rs.next() && searching) {
        ticketInDB = rs.getString("ticketNumber");

        if (ticketInDB.equals(ticketFinal)) {
          ticketFound = true;
          searching = false;
        }
        else {
          benchmark++;
          if (benchmark==41 && !ticketInDB.equals(ticketFinal)) {
            ticketFound = false;
            searching = true;

          }
        }
      }
    }
    catch (SQLException e) {

      e.printStackTrace();
    }

  }

  public void remove(String ticketFinal, String ticketBus, String ticketDeparture) {

    runConnection();

    setTimeTR(ticketDeparture);

    command = "delete from sql12808197." + tablePick + " where ticketNumber ='" + ticketFinal + "'";

    try {
      statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

      statement.executeUpdate(command);

    }
    catch (SQLException e) {

      e.printStackTrace();
    }

  }

}
