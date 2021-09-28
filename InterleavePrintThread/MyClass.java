public class MyClass {
    public static void main(String args[]) {
        Printer one = new Printer("Team Red",0);
        Printer two = new Printer("Team Blue",1);
        Printer three = new Printer("Team Green",2);
        one.start();
        two.start();
        three.start();
        try{
            one.join();
            two.join();
            three.join();
        }catch(InterruptedException e){
        }
    }
}

class Printer extends Thread{
    String name;
    int myState;
    static int sharedState =0;
    final  int Number=3;
    static Object lock = new Object();
    
    public Printer(String name, int myState){
        this.name=name;
        this.myState= myState;
    }
    
    public void run(){
        synchronized (lock) {
        while(true){
            try{
                while(sharedState!=myState)
                    lock.wait();
                System.out.println(name);
                sharedState = (myState+1)%Number;
                lock.notifyAll();
            }catch (InterruptedException e){}
        }
        }
    }
}