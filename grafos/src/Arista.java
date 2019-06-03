/**
 *
 * @author beto
 */
class Arista implements Comparable<Arista>{
    nodo A;
    nodo B;
    float peso;
    boolean dir;
    
    public Arista(nodo a, nodo b){
          this.A = a;
          this.B = b;
          this.peso = 1.0f;
          this.dir = false;
    }
    public Arista(nodo a, nodo b, boolean d){
          this.A = a;
          this.B = b;
          this.peso = 1.0f;
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
    public void setA(nodo a){
        this.A=a;
    }
    public void setB(nodo b){
        this.B=b;
    }
    public void setPeso(float f){
        this.peso=f;
    }
    
    public float getPeso(){
        return this.peso;
    }
    @Override
    public int compareTo(Arista a) {
        if(this.getPeso() > a.getPeso()){
            return 1;
        }
        else if(this.getPeso()< a.getPeso()){
            return -1;
        }
        else{
            return 0;
        }        
    }    
}
