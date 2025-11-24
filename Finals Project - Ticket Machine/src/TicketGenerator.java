import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class TicketGenerator {

  DBimport db = new DBimport();
    
  ArrayList<String> destinationList;
  ArrayList<String> departureList;

  private String ticketRegion = "041";
  private String ticketTerminal = "08";
  protected String ticketBus;
  protected String ticketDestination;
  protected String ticketDeparture;
  private String time;
  private String message;
  private String ticketFinal;
  protected boolean busFull;

  private int destinationNumber;
  private int busNumber;
  private int departureNumber;

  //getting input from user in menu
  public void setDestination(int destinationNumber) {
    this.destinationNumber = destinationNumber;
  }
  public int getDestination() {
    return destinationNumber;
  }
  //getting input from user in menu
  public void setBus(int busNumber) {
    this.busNumber = busNumber;
  }
  public int getBus() {
    return busNumber;
  }
  //getting input from user in menu
  public void setDeparture(int departureNumber) {
    this.departureNumber = departureNumber;
  }
  public int getDeparture() {
    return departureNumber;
  }

  public void destinationPart() {

    destinationList = new ArrayList<>();

    destinationList.add("21");
    destinationList.add("22");
    destinationList.add("23");
    destinationList.add("24");
    destinationList.add("25");
    destinationList.add("26");
    destinationList.add("27");
    destinationList.add("28");
    destinationList.add("29");

    getDestination();
    db.setDeparturedb(departureNumber);

    if (destinationNumber<=5) {
      setBus(1);
      busPart(1);

      switch (destinationNumber) {
        case 1:
        ticketDestination = destinationList.get(0);
        break;

        case 2:
        ticketDestination = destinationList.get(1);
        break;

        case 3:
        ticketDestination = destinationList.get(2);
        break;

        case 4:
        ticketDestination = destinationList.get(3);
        break;

        case 5:
        ticketDestination = destinationList.get(4);
        break;
      }
    }
    else if(destinationNumber>=6) {
      setBus(3);
      busPart(3);

      switch (destinationNumber) {
        case 6:
        ticketDestination = destinationList.get(5);
        break;

        case 7:
        ticketDestination = destinationList.get(6);
        break;

        case 8:
        ticketDestination = destinationList.get(7);
        break;

        case 9:
        ticketDestination = destinationList.get(8);
        break;
      }
    }

  }

  public void busPart(int busNumber) {

    if (busNumber==1) {
      ticketBus = "01";
    }
    else if (busNumber==2) {
      ticketBus = "02";
    }
    else if (busNumber==3) {
      ticketBus = "03";
    }
    else if (busNumber==4) {
      ticketBus = "04";
    }

    db.setBusdb(ticketBus);

  }

  public void departurePart() {

    departureList = new ArrayList<>();

    departureList.add("66");
    departureList.add("69");
    departureList.add("61");
    departureList.add("64");
    departureList.add("96");
    departureList.add("99");
    departureList.add("91");
    departureList.add("94");

    getDeparture();

    switch (departureNumber) {
      case 1:
      if (busNumber==1) {
        ticketDeparture = departureList.get(0);
      }
      else if (busNumber==3) {
        ticketDeparture = departureList.get(4);
      }
      break;

      case 2:
      if (busNumber==1) {
        ticketDeparture = departureList.get(1);
      }
      else if (busNumber==3) {
        ticketDeparture = departureList.get(5);
      }
      break;

      case 3:
      if (busNumber==1) {
        ticketDeparture = departureList.get(2);
      }
      else if (busNumber==3) {
        ticketDeparture = departureList.get(6);
      }
      break;

      case 4:
      if (busNumber==1) {
        ticketDeparture = departureList.get(3);
      }
      else if (busNumber==3) {
        ticketDeparture = departureList.get(7);
      }
      break;
      }

  }

  public void seatPart() {

    db.checkSeat();

    if (db.bus1Full) {
      setBus(02);
      busPart(02);
      db.checkSeat();
    }
    else if (db.bus3Full) {
      setBus(04);
      busPart(04);
      db.checkSeat();
    }

  }

  public void checkTime() {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
    LocalTime now = LocalTime.now();
    LocalTime departure = null;

    switch (departureNumber) {
      case 1:
      departure = LocalTime.of(6, 00);
      break;

      case 2:
      departure = LocalTime.of(9, 00);
      break;

      case 3:
      departure = LocalTime.of(13, 00);
      break;

      case 4:
      departure = LocalTime.of(16, 00);
      break;
    }
      
    try {
      time = departure.format(formatter);

      if (departure.isAfter(now)) {
        message = "See you LATER at " + time;
      }
      else {
        message = "See you TOMORROW at " + time;
      }
    }
    
    catch (Exception e) {}

    }

  public void display() {

    if (destinationNumber>=11 || departureNumber>=6) {
      System.out.println("\nPlease select one of the options available\n");
    }
   else if (db.bus2Full|| db.bus4Full) {
     if (busNumber==02 || busNumber==04) {
     System.out.println("\nAll busses for this scheduled time is full, please pick a different time for your departure\n");
     }
   }
    else {
    //busPart() is running inside destinationPart()
    destinationPart();
    departurePart();
    seatPart();
    checkTime();

    ticketFinal = ticketRegion + ticketTerminal + ticketBus + ticketDestination + ticketDeparture + db.seatFinal;

    db.ticketIntoDB(ticketFinal);

    System.out.println("\nYour ticket number is: " + ticketFinal + "\n" + message);

    System.out.print("Going back to the Main Menu");
    Loading.main(null);
    }
  }
}

