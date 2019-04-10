/**
 *
 * @author beto
 */
import java.util.ArrayList; 
class nodo implements Comparable<nodo>{
    String nombre;
    int dato;
    float peso;
    double posx;
    double posy;
    ArrayList<nodo> vecinos;   
    Boolean explorado;
    
    public nodo(String n, int d){
        this.nombre = n;
        this.dato = d;
        this.peso=1;
        this.posx = 0;
        this.posy = 0;
        this.vecinos = new ArrayList<>(0);
        this.explorado = false;
    }  
    
     public nodo(String n, int d, double x, double y){
        this.nombre = n;
        this.dato = d;
        this.posx = x;
        this.posy = y;
        this.peso=1;
        this.vecinos = new ArrayList<>(0);
        this.explorado = false;
    } 
    
    public void setPeso(float peso){
        this.peso= peso;
    }
    public void setPosx(double x){
        this.posx = x;
    }
    
    public void setPosy(double y){
        this.posy = y;
    }
    
    public void setExp(Boolean v){
        this.explorado=v;
    }
    public Boolean getExp(){
        return this.explorado;
    }
    
    public String getName(){
        return this.nombre;
    }
    
    public int getDat(){
        return this.dato;
    }
    
    public float getPeso(){
        return this.peso;
    }
    
    public double getPosx(){
        return this.posx;
    }
    
    public double getPosy(){
        return this.posy;
    }
    
    public void print(){
        System.out.println(nombre + ","+ dato);
    }
    
    public void addVecino(nodo vecino){
        this.vecinos.add(vecino);
    }
    
    public ArrayList<nodo> getVecinos(){
        return this.vecinos;
    }     

    @Override
    public int compareTo(nodo n) {
        if(this.getDat() > n.getDat()){
            return 1;
        }
        else if(this.getDat()< n.getDat()){
            return -1;
        }
        else{
            return 0;
        }        
    }
}
