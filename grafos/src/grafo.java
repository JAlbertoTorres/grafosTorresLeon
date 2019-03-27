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
    public void reiniciaNodos(){
        int tam = this.getOrden();
        for(int i=0; i<tam; i++){
            this.nodos.get(i).setExp(false);
        }
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
                a.addVecino(b);
                b.addVecino(a);
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
                a.addVecino(b);
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
    
    public int getDeg(nodo n){
        int deg=0;
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
        this.BFS(this.nodos.get(0));
        grafo arbolDFS_I = new grafo();
        arbolDFS_I.nombre= this.nombre +"DFS_I";
        arbolDFS_I.DFS_I(this.nodos.get(0));
        arbolDFS_I.creaDotGraf();
        this.reiniciaNodos();
        grafo arbolDFS_R = new grafo();
        arbolDFS_R.nombre= this.nombre +"DFS_R";
        arbolDFS_R.DFS_R(this.nodos.get(0));
        arbolDFS_R.creaDotGraf();
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
        this.BFS(this.nodos.get(0));
        grafo arbolDFS_I = new grafo();
        arbolDFS_I.nombre= this.nombre +"DFS_I";
        arbolDFS_I.DFS_I(this.nodos.get(0));
        arbolDFS_I.creaDotGraf();
        this.reiniciaNodos();
        grafo arbolDFS_R = new grafo();
        arbolDFS_R.nombre= this.nombre +"DFS_R";
        arbolDFS_R.DFS_R(this.nodos.get(0));
        arbolDFS_R.creaDotGraf();

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
        this.BFS(this.nodos.get(0));
        grafo arbolDFS_I = new grafo();
        arbolDFS_I.nombre= this.nombre +"DFS_I";
        arbolDFS_I.DFS_I(this.nodos.get(0));
        arbolDFS_I.creaDotGraf();
        this.reiniciaNodos();
        grafo arbolDFS_R = new grafo();
        arbolDFS_R.nombre= this.nombre +"DFS_R";
        arbolDFS_R.DFS_R(this.nodos.get(0));
        arbolDFS_R.creaDotGraf();
    }
    public void crearGrafoBarabasi(int n, int g, boolean dirigido, boolean autociclo){
        grafo gB = new grafo();
        int aux;
        double pR;
        int degi, degj;
        Random rand = new Random();
        double x;
        for(int i=0; i<n; i++){
            gB.crearNodo(i);
        }
        float coin;
        int i=0, a, b, k;        
        int j;
        double frac;
        for(i = g; i<n; i++){            
            j = 0;
            degi = gB.getDeg(gB.nodos.get(i));
            while(j<n && degi<g){                
                 if(i==j && !autociclo){
                    if(j<n-1){
                        j+=1;
                    }
                    else{
                        break;
                    }
                }
                degj = gB.getDeg(gB.nodos.get(j));
                frac= (double) degj/g;
                pR= (double)1.0 - frac;
                x= rand.nextDouble();
                //System.out.println("deg= "+ degj+ " pR= "+pR+" x= "+x+ " i= "+i+" j= "+j);               
                if(pR>x){
                    aux=gB.crearArista(gB.nodos.get(i),gB.nodos.get(j), dirigido);
                }
                j++;
                degi = gB.getDeg(gB.nodos.get(i));
            }
        }
        this.nodos = gB.nodos;
        this.aristas = gB.aristas; 
        this.dirigido = dirigido;
        this.nombre = "Barabasi";
        this.BFS(this.nodos.get(0));
        grafo arbolDFS_I = new grafo();
        arbolDFS_I.nombre= this.nombre +"DFS_I";
        arbolDFS_I.DFS_I(this.nodos.get(0));
        arbolDFS_I.creaDotGraf();
        this.reiniciaNodos();
        grafo arbolDFS_R = new grafo();
        arbolDFS_R.nombre= this.nombre +"DFS_R";
        arbolDFS_R.DFS_R(this.nodos.get(0));
        arbolDFS_R.creaDotGraf();
        
    }  
    public void BFS(nodo s){ 
        int i=0;
        grafo arbolBFS = new grafo();
        arbolBFS.nombre = this.nombre + "BFS";
        int tam = this.getOrden();
        int tamC, tamV; //El tamaño de la capa y el tamaño del arreglo de vecinos del nodo u
        Boolean []descubiertos;
        descubiertos = new Boolean[tam];       
        for(i=0; i<tam; i++){
            descubiertos[i]=false;
        }
        descubiertos[s.dato]=true;
        i=0;
        ArrayList<ArrayList<nodo>> capas = new ArrayList<ArrayList<nodo>>();
        ArrayList<nodo> capa = new ArrayList<nodo> ();
        ArrayList<nodo> capa2 = new ArrayList<nodo> ();
        capa.clear();
        capa.add(s);
        capas.add(capa);
        arbolBFS.crearNodo(s.getDat());              
        int k=0;
        while(!(capas.get(k).isEmpty())){            
            capa2 = new ArrayList<nodo> ();
            //System.out.println("tamanio de capa i=" +capas.get(k).size());
            //System.out.println("tamanio de capa2=" +capa2.size());
            tamC= capas.get(k).size();                                   
            for(i=0; i<tamC; i++){
                tamV = capas.get(k).get(i).getVecinos().size();
                for(int j=0; j<tamV; j++){                    
                    if(descubiertos[capas.get(k).get(i).getVecinos().get(j).getDat()]==false){
                        descubiertos[capas.get(k).get(i).getVecinos().get(j).getDat()]=true;
                        capa2.add(capas.get(k).get(i).getVecinos().get(j));
                        arbolBFS.crearNodo(capas.get(k).get(i).getVecinos().get(j).getDat());
                        arbolBFS.crearArista(capas.get(k).get(i), capas.get(k).get(i).getVecinos().get(j), this.dirigido);
                    }
                }                               
            }
            //System.out.println("tamanio de capa2=" +capa2.size());
            capas.add(capa2);
            k+=1;
        }
        arbolBFS.creaDotGraf();              
    }
    public void revisaVecino(nodo s, int n){
        int tam = s.getVecinos().size();
        if(n==tam-1){            
            this.crearArista(s, s.getVecinos().get(n), this.dirigido);            
        }
        else{
            if(s.getVecinos().get(n).getExp()==false){
                this.crearArista(s, s.getVecinos().get(n), this.dirigido);
                this.DFS_R(s.getVecinos().get(n));
            }
            revisaVecino(s,n+1);
            
        }              
    }
    public void DFS_R(nodo s){
        this.crearNodo(s.getDat());
        s.setExp(true);
        this.revisaVecino(s, 0);        
    }
    public void DFS_I(nodo s){       
        this.crearNodo(s.getDat());                
        s.setExp(true);
        int tamV;
        tamV = s.getVecinos().size();        
        for(int i=0; i< tamV; i++){
            if(s.getVecinos().get(i).getExp()==false){
                this.crearArista(s, s.getVecinos().get(i), this.dirigido);
                this.DFS_I(s.getVecinos().get(i));
            }
        }
    }
    
}
