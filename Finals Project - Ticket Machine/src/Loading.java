public class Loading extends Thread {

  public static void main(String[] args) {

    try {
    Thread.sleep(500);

    System.out.print(".");
    Thread.sleep(500);

    System.out.print(".");
    Thread.sleep(500);

    System.out.print(".");
    Thread.sleep(750);
    System.out.println("\n");

  }
  
  catch (InterruptedException e) {
    
  }
  }

  public static void quarterSecond() {

    try {
      Thread.sleep(350);
    }
    catch (InterruptedException e) {

    }
  }

}
