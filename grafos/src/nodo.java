/**
 *
 * @author beto
 */
import java.util.ArrayList; 
public class nodo {
    String nombre;
    int dato;
    double posx;
    double posy;
    ArrayList<nodo> vecinos;
    Boolean explorado;
    
    public nodo(String n, int d){
        this.nombre = n;
        this.dato = d;
        this.posx = 0;
        this.posy = 0;
        this.vecinos = new ArrayList<>();
        this.explorado = false;
    }  
    
     public nodo(String n, int d, double x, double y){
        this.nombre = n;
        this.dato = d;
        this.posx = x;
        this.posy = y;
        this.vecinos = new ArrayList<>();
        this.explorado = false;
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
}
