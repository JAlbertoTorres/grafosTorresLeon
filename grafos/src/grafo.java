import java.util.ArrayList; 
import java.util.Random;
import java.io.*;
import java.util.Scanner;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.lang.Math;

public class grafo {
    public ArrayList<nodo> nodos;
    public HashMap<String,Arista> aristas;
    public boolean dirigido;
    public String nombre;
    
    public grafo(){
        nodos = new ArrayList<nodo>();
        aristas = new HashMap<String,Arista>();
        dirigido = false;
    }
    public grafo(grafo g){
        nodos = g.nodos;
        aristas = g.aristas;
        dirigido = g.dirigido;
    }
    
    public void crearNodo(int num){
        String nombre = "A"+num;        
        this.nodos.add(new nodo(nombre, num));        
    }
     public void crearNodo(int num, double x, double y){
        String nombre = "A"+num;        
        this.nodos.add(new nodo(nombre, num, x, y));        
    }
     
    public int crearArista(nodo a, nodo b, boolean d){
        Arista ar1= new Arista(a,b);
        //Arista ar2= new Arista(b,a);
        String ar1Name= a.getName()+b.getName();
        String ar2Name= b.getName()+a.getName();
        
               
        if(!d){
            if(this.aristas.get(ar1Name)!=null||this.aristas.get(ar2Name)!=null){
                //System.out.println("Esa arista en el grafo no dirigido ya existe");
                return 0;
            }
            else{
                this.aristas.put(ar1Name,new Arista(a,b));        
                return 1;
            }
        }
        else{
            if(this.aristas.get(ar1Name)!=null){
                  //  System.out.println("Esa arista en el grafo dirigido ya existe");
                    return 0;
            }
            else{
                this.aristas.put(ar1Name, new Arista(a,b));        
                return 1;
            }
        }
        
    }
    public void creaDotGraf(){
        Calendar hoy = Calendar.getInstance();
        String path ="/home/beto/Documentos/CIC/DyAdAlgoritmos/Tareas/archivosgdf/";
        String nombre="grafo"+this.nombre+this.nodos.size()+hoy.getTime();
        this.nombre=nombre+".gdf";
        File archivo;
        Scanner sc;
        FileWriter fw;
        BufferedWriter bw;
        Iterator<String> llave = aristas.keySet().iterator();
        String key;
        if(this.dirigido){
            try{
                archivo=new File(path+nombre+".gdf");
                fw=new FileWriter(archivo);
                bw=new BufferedWriter(fw);
                bw.write("nodedef>name VARCHAR\n");    
                for(int i=0; i<this.nodos.size(); i++){
                    bw.write(nodos.get(i).nombre+"\n");
                }
                bw.write("edgedef>node1 VARCHAR,node2 VARCHAR\n");
                while(llave.hasNext()){
                  key = llave.next();     
                  bw.write(this.aristas.get(key).getA().getName()+","+this.aristas.get(key).getB().getName() +"\n");

                }
                                
                bw.flush();
                //this.dibujaGrafo(nombre);
            }

                 catch (IOException io)  
                {
                    // insert code to run when exception occurs
                }
            
        }
        else{
             try{
                archivo=new File(path+nombre+".gdf");
                fw=new FileWriter(archivo);
                bw=new BufferedWriter(fw);

                bw.write("nodedef>name VARCHAR\n");    
                for(int i=0; i<this.nodos.size(); i++){
                    bw.write(nodos.get(i).nombre+"\n");
                }
                bw.write("edgedef>node1 VARCHAR,node2 VARCHAR\n");
                while(llave.hasNext()){
                  key = llave.next();     
                  bw.write(this.aristas.get(key).getA().getName()+","+this.aristas.get(key).getB().getName() +"\n");

                }
                bw.flush(); 
                //this.dibujaGrafo(nombre);
             }

                 catch (IOException io)  
                {
                    // insert code to run when exception occurs
                }
        }
        
    }
    public void dibujaGrafo(String name){
        String dot = name+".dot";
        String png = name+".png";
        new GraphvizJava( dot,png );
    }
    
    public double getDeg(nodo n){
        double deg=0;
        Iterator<String> llave = aristas.keySet().iterator();
        String key;
        while(llave.hasNext()){
            key = llave.next();                     
            if(aristas.get(key).getA()== n ||aristas.get(key).getB()==n){
                deg++;
            }
        }
        return deg;
    }
    public int getOrden(){
        return this.nodos.size();
    }
    public void printNodos(){
        int n= nodos.size(), i=0;
        System.out.println("Nodos");
        for(i=0; i<n; i++){
            nodos.get(i).print();
        }
    }
    public void printAristas(){
        int n = aristas.size(), i=0;        
        System.out.println("Aristas");        
        Iterator<String> llave = aristas.keySet().iterator();
        while(llave.hasNext()){            
           aristas.get(llave.next()).print();
        }        
        
    }
    public void crearGrafoErdos(int n, int m, boolean dirigido, boolean autociclo){
        grafo gE = new grafo();
        Random rand = new Random();
        int i, a,b;
    
        float coin, k;
        for(i=0; i<n; i++){
            gE.crearNodo(i);
        }
        i=0;
        
        while(i<m){            
            a=rand.nextInt(n); b=rand.nextInt(n);
            if(a==b && !(autociclo)){
                b=rand.nextInt(n);                                
            }
            k = rand.nextInt(10);
            k+=1;            
            coin =(float) 1/k;            
            if(coin > 0.5){
                i+=gE.crearArista(gE.nodos.get(a),gE.nodos.get(b), dirigido);
                //i++;                
            }                   
        }
        //}
        this.nodos = gE.nodos;
        this.aristas = gE.aristas;
        this.dirigido = dirigido;
        this.nombre = "Erdos";
    }
    
    public void crearGrafoGilbert(int n, float p, boolean dirigido, boolean autociclo){
           grafo gG = new grafo();
           Random rand = new Random();
           int k, aux;
           float prob;
           for(int i=0; i<n; i++){
                gG.crearNodo(i);
            }                 
           for(int i=0; i<n; i++){
               for(int j=0; j<n; j++){
                    k = rand.nextInt(10);
                    k+=1.0f;
                    prob = (float)1/k;
                    //System.out.println("k= "+k+" prob= "+prob);
                    if(prob>p){
                        if(i==j && !autociclo){
                            if(j<n-1){
                                j+=1;
                            }
                            else{
                                break;
                            }
                        }
                    aux=gG.crearArista(gG.nodos.get(i),gG.nodos.get(j), dirigido);
                   }
               }
           }
        this.nodos = gG.nodos;
        this.aristas = gG.aristas; 
        this.dirigido = dirigido;
        this.nombre = "Gilbert";

    }
    public double calculaDist(nodo a, nodo b){
        double dist;
        dist = Math.sqrt(Math.pow(a.getPosx()-b.getPosx(), 2)+ Math.pow(a.getPosy()-b.getPosy(), 2));
        return dist;
    }
    public void crearGrafoGeo(int n, double r, boolean dirigido, boolean autociclo){
        grafo gGeo = new grafo();
        Random rand = new Random();
        int aux;
        double x,y;
        double dist;
        for(int i=0; i<n; i++){
            x= rand.nextDouble(); y=rand.nextDouble();
            gGeo.crearNodo(i, x, y);
        }
        for(int i=0; i<n; i++){
              for(int j=0; j<n; j++){
                   dist = gGeo.calculaDist(gGeo.nodos.get(i),gGeo.nodos.get(j)); 
                   //System.out.println("k= "+k+" prob= "+prob);
                   if(dist<r){
                       if(i==j && !autociclo){
                           if(j<n-1){
                               j+=1;
                           }
                           else{
                               break;
                           }
                       }
                   aux=gGeo.crearArista(gGeo.nodos.get(i),gGeo.nodos.get(j), dirigido);

                  }
              }
          }
        this.nodos = gGeo.nodos;
        this.aristas = gGeo.aristas; 
        this.dirigido = dirigido;
        this.nombre = "Geografico";
    }
    public void crearGrafoBarabasi(int n, int g, boolean dirigido, boolean autociclo){
        grafo gB = new grafo();
        int aux;
        double pR, deg;
        Random rand = new Random();
        double x;
        for(int i=0; i<n; i++){
            gB.crearNodo(i);
        }
        float coin;
        int i=0, a, b, k;
        while(i<g){            
           a=rand.nextInt(n); b=rand.nextInt(n);
           if(a==b && !(autociclo)){
               b=rand.nextInt(n);                                
           }
           k = rand.nextInt(10);
           k+=1;            
           coin =(float) 1/k;  
           //System.out.println("Tratando de conectar los nodos a= "+a+" b="+b );
           if(coin > 0.5){
               i+=gB.crearArista(gB.nodos.get(a),gB.nodos.get(b), dirigido);              
           }                   
        }
        /*for(int i= 0; i<g; i++){
            for(int j=0; j<g; j++){
                if(i==j && !autociclo){
                    if(j<g-1){
                        j+=1;
                    }
                    else{
                        break;
                    }
                    
                }
                aux=gB.crearArista(gB.nodos.get(i),gB.nodos.get(j), dirigido);
            }
        }*/
        
        int j;
        for(i = g; i<n; i++){            
            j = 0;
            deg = gB.getDeg(gB.nodos.get(i));
            while(j<n && deg<g){                
                 if(i==j && !autociclo){
                    if(j<n-1){
                        j+=1;
                    }
                    else{
                        break;
                    }
                }
                deg = gB.getDeg(gB.nodos.get(i));
                pR= (double)1.0 - (deg/g);
                x= rand.nextDouble();
                //System.out.println("deg= "+ deg+ " pR= "+pR+" x= "+x+ " i= "+i+" j= "+j);               
                if(pR>x){
                    aux=gB.crearArista(gB.nodos.get(i),gB.nodos.get(j), dirigido);
                }
                j++;
            }
        }
        this.nodos = gB.nodos;
        this.aristas = gB.aristas; 
        this.dirigido = dirigido;
        this.nombre = "Barabasi";
           
    }    
}
