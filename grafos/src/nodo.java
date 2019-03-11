/**
 *
 * @author beto
 */
public class nodo {
    String nombre;
    int dato;
    double posx;
    double posy;
    
    public nodo(String n, int d){
        this.nombre = n;
        this.dato = d;
        this.posx = 0;
        this.posy = 0;
    }  
    
     public nodo(String n, int d, double x, double y){
        this.nombre = n;
        this.dato = d;
        this.posx = x;
        this.posy = y;
    } 
    
    public void setPosx(double x){
        this.posx = x;
    }
    
    public void setPosy(double y){
        this.posy = y;
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
    
}
