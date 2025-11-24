import java.util.Scanner;

public class TicketMachine {

    Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws Exception {

    TicketMachine tm = new TicketMachine();

    boolean MainRun = true;

   while (MainRun) {

   System.out.println("Welcome to the Ticket Machine\n");
   System.out.println("[1]Get a Ticket");
   System.out.println("[2]Check Details of Bought Ticket");
   System.out.println("[3]Use a Ticket");
   System.out.println("[4] Exit Ticket Machine");
   System.out.print("===> ");
   String menuChoice = tm.scan.nextLine().trim();

       if (menuChoice.equals("1")) { 
            Loading.quarterSecond();
            tm.generator();
       }
       else if (menuChoice.equals("2")) {
            Loading.quarterSecond();
           tm.reader();
       }
       else if (menuChoice.equals("3")) {
            Loading.quarterSecond();
            tm.use();
       }
       else if (menuChoice.equals("4")) {
           System.out.print("Exiting Program");
           Loading.main(args);
           MainRun = false;
       }
       else {
           System.out.println("\ninvalid input\n");
           Loading.quarterSecond();
       }

   }

    }

    public void generator() {

        TicketGenerator tg = new TicketGenerator();

        String destinationChoice = null;
        String departureChoice = null;

        int destChoiceInt = 0;
        int depChoiceInt = 0;

        boolean generatorRun = true;
        while (generatorRun) {

            System.out.println("Please select your destination:");
            System.out.println("[1] Jalajala     [6] Baras");
            System.out.println("[2] Malaya       [7] Morong");
            System.out.println("[3] Niogan       [8] Cainta");
            System.out.println("[4] Quisao       [9] Binangonan");
            System.out.println("[5] Pililla      [10] Go back");
            System.out.print("===> ");
            destinationChoice = scan.nextLine().trim();

            Loading.quarterSecond();

            System.out.println("These are the scheduled bus departures:");
            System.out.println("[1] 06:00 AM     [3] 01:00 PM");
            System.out.println("[2] 09:00 AM     [4] 04:00 PM");
            System.out.println("[5] Go back");
            System.out.print("===> ");
            departureChoice = scan.nextLine().trim();

            if (destinationChoice.equals("10") || departureChoice.equals("5")) {
                System.out.print("Going back to the Main Menu");
                Loading.main(null);
                generatorRun = false;
            }
            else {
                try {
                    //convert String input of user to int
                    destChoiceInt = Integer.parseInt(destinationChoice);
                    depChoiceInt = Integer.parseInt(departureChoice);
            
                    //take input of user to TicketGenerator
                    tg.setDestination(destChoiceInt);
                    tg.setDeparture(depChoiceInt);
                    //run all necessary methods
                    tg.display();

                    generatorRun = false;

                }
                catch (Exception e) {
                    Loading.quarterSecond();
                    System.out.println("\nInvalid Input. Please select one of the options available\n");
                    Loading.quarterSecond();

                }
            }

        }
    }

    public void reader() {

        TicketReader tr = new TicketReader();

        String ticketInput;

        boolean readerRun = true;
        System.out.println(" ");

        while (readerRun) {

            System.out.println("Please enter your Ticket Number");
            System.out.print("===> ");
            ticketInput = scan.nextLine().trim();

            if (ticketInput.isEmpty()) {
                Loading.quarterSecond();
                System.out.print("Space was left blank, ");
            }
            else {
                tr.setTicket(ticketInput);
                tr.read();
                tr.readerDisplay();
            }

            readerRun = false;
        }
    }

    public void use() {

        TicketReader tr = new TicketReader();;

        String ticketInput;

        boolean useRun = true;
        while (useRun) {

            System.out.println("Please enter your Ticket Number");
            System.out.print("===> ");
            ticketInput = scan.nextLine().trim();

            if (ticketInput.isEmpty()) {
                Loading.quarterSecond();
                System.out.print("Space was left blank, ");
            }
            else {
                tr.setTicket(ticketInput);
                tr.read();
                tr.removeTR();

            useRun = false;
            }
        }
    }
 }


