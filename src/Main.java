public class Main {
    public static void main(String[] args) {
        MyGraphics g = new MyGraphics();
        MyLogic l = new MyLogic();
        g.setIntel(l);
        for(int i =0; i<2; i++)
            System.out.println("started");
    }
}
