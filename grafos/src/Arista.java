/**
 *
 * @author beto
 */
public class Arista {
    nodo A;
    nodo B;
    int peso;
    boolean dir;
    
    public Arista(nodo a, nodo b){
          this.A = a;
          this.B = b;
          this.peso = 0;
          this.dir = false;
    }
    public Arista(nodo a, nodo b, boolean d){
          this.A = a;
          this.B = b;
          this.peso = 0;
          this.dir = d;
    }
    public void print(){
        System.out.println("("+A.getName()+","+B.getName()+")");
    }
    public nodo getA(){
        return this.A;
    }
    public nodo getB(){
        return this.B;
    }
}
