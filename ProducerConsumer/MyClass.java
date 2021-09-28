import java.util.*;
public class MyClass {
    public static void main(String args[]) {
        Buffer buffer= new Buffer(5);
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
        producer.start();
        consumer.start();
        try{
            producer.join();
            consumer.join();
        }catch(InterruptedException e){
          
        }
    }
}

class Buffer{
    List<Integer> store;
    int capacity;
    
    public Buffer(int capacity){
       this.capacity=capacity; 
       this.store = new ArrayList();
    }
    
    public synchronized void addValue(int value){
        try{
            if(store.size()==capacity)
                wait();
            store.add(value);
            System.out.println("Produced : "+value);
            notify();
        }catch(InterruptedException e){
        }
    }
    
    public synchronized void removeValue(){
        try{
            if(store.isEmpty())
                wait();
            int value = store.get(0);
            store.remove(0);
            System.out.println("Consumed : "+value);
            notify();
        }catch(InterruptedException e){
        }
    }
    
}

class Producer extends Thread{
    private Buffer buffer;
    public Producer(Buffer buffer){
        this.buffer=buffer;
    }
    
    public void run(){
        int val=1;
        while(true){
            buffer.addValue(val++);
        }
    }
}

class Consumer extends Thread{
    private Buffer buffer;
    public Consumer(Buffer buffer){
        this.buffer=buffer;
    }
    
    public void run(){
        while(true){
            buffer.removeValue();
        }
    }
}