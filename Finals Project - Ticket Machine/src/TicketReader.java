import java.util.*;

public class TicketReader extends TicketGenerator {

  private String ticket;
  private String ticketSeat;
  private String confirmation;

  Scanner scan = new Scanner(System.in);

  HashMap<String, String> busMap;
  HashMap<String, String> destinationMap;
  HashMap<String, String> departureMap;
  
  public void setTicket(String ticket) {
    this.ticket = ticket;
  }
  public String getTicket() {
    return ticket;
  }

  public void read() {

    getTicket();

    char[] array = ticket.toCharArray();

    try {
    ticketBus = String.valueOf(array[5]) + String.valueOf(array[6]);
    ticketDestination = String.valueOf(array[7]) + String.valueOf(array[8]);
    ticketDeparture = String.valueOf(array[9]) + String.valueOf(array[10]);
    ticketSeat = String.valueOf(array[11]) + String.valueOf(array[12]);
    }
    catch (Exception e) {

    }
  }

  public void compareBus() {

    busMap = new HashMap<>();

    busMap.put("01", "Bus #1");
    busMap.put("02", "Bus #2");
    busMap.put("03", "Bus #3");
    busMap.put("04", "Bus #4");

  }

  public void compareDestination() {

    destinationMap = new HashMap<>();

    destinationMap.put("21", "Jalajala");
    destinationMap.put("22", "Malaya");
    destinationMap.put("23", "Niogan");
    destinationMap.put("24", "Quisao");
    destinationMap.put("25", "Pililla");
    destinationMap.put("26", "Baras");
    destinationMap.put("27", "Morong");
    destinationMap.put("28", "Cainta");
    destinationMap.put("29", "Binangonan");

  }

  public void compareDeparture() {

    departureMap = new HashMap<>();

    departureMap.put("66", "06:00 AM");
    departureMap.put("69", "09:00 AM");
    departureMap.put("61", "01:00 PM");
    departureMap.put("64", "04:00 PM");
    departureMap.put("96", "06:00 AM");
    departureMap.put("99", "09:00 AM");
    departureMap.put("91", "01:00 PM");
    departureMap.put("94", "04:00 PM");

  }

  public void readerDisplay() {

    boolean runReaderDisplay = true;

    compareBus();
    compareDestination();
    compareDeparture();

    db.ticketIntoDB(ticket, ticketBus, ticketDeparture);

    if (db.ticketFound) {
      System.out.println("\nYour ticket entails that: ");
      System.out.println("You're riding: " + busMap.get(ticketBus));
      System.out.println("You are bound for: " + destinationMap.get(ticketDestination));
      System.out.println("Your bus will be leaving at: " + departureMap.get(ticketDeparture));
      System.out.println("Your seat number is: " + ticketSeat + "\n");

      while (runReaderDisplay) {
      System.out.println("Would you like to Proceed and Use your Ticket?");
        System.out.println("[1]Yes     [2]No");
        System.out.print("===>");
        confirmation = scan.nextLine().trim();

        if  (confirmation.equals("1")) {
          Loading.quarterSecond();
          System.out.println("\nTicket has been used");

          db.remove(ticket, ticketBus, ticketDeparture);

          System.out.println("\nThank you for your Patronage\n");
          runReaderDisplay = false;

          System.out.print("Going back to the Main Menu");
          Loading.main(null);
        }
        else  if (confirmation.equals("2")) {
          System.out.print("\nGoing back to the Main Menu");
          Loading.main(null);
          runReaderDisplay = false;
        }
        else {
          System.out.println("Please choose one of the Options\n");
        }

      }
    
    }
    else if (!db.ticketFound) {
      System.out.println("\nThe ticket number you've entered has either not been generated or already been used\n");
      System.out.print("Going back to the Main Menu");
      Loading.main(null);
    }
    
  }

  public void removeTR() {
    
    boolean runRemoveTR = true;

    compareBus();
    compareDestination();
    compareDeparture();

    db.ticketIntoDB(ticket, ticketBus, ticketDeparture);

    if (db.ticketFound) {
      while (runRemoveTR) {
        System.out.println("Use ticket?");
        System.out.println("[1]Yes     [2]No");
        confirmation = scan.nextLine().trim();

        if (confirmation.equals("1")) {
          Loading.quarterSecond();

        System.out.println("\nTicket has been used.\n\nYour ticket entails that: ");
        System.out.println("You're riding: " + busMap.get(ticketBus));
        System.out.println("You are bound for: " + destinationMap.get(ticketDestination));
        System.out.println("Your bus will be leaving at: " + departureMap.get(ticketDeparture));
        System.out.println("Your seat number is: " + ticketSeat);

        db.remove(ticket, ticketBus, ticketDeparture);

        System.out.println("\nThank you for your Patronage\n");

        System.out.print("Going back to the Main Menu");
        Loading.main(null);

        runRemoveTR = false;
        }
        else if (confirmation.equals("2")) {
        System.out.print("\nGoing back to the Main Menu");
        Loading.main(null);

        runRemoveTR = false;
        }
        else {
          System.out.println("Please select one of the Options");
        }

      }
    }
    else if (!db.ticketFound) {
      System.out.println("\nThe ticket number you've entered has either not been generated or already been used\n");

      System.out.print("Going back to the Main Menu");
      Loading.main(null);
    }


  }

}
