import java.util.ArrayList; 
import java.util.Random;
import java.util.*;
import java.io.*;
import java.util.Scanner;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.lang.Math;
import java.util.PriorityQueue;


public class grafo {
    public ArrayList<nodo> nodos;
    public HashMap<String,Arista> aristas;
    public boolean dirigido;
    public String nombre;
    public String tipo;
    public boolean Pesos;
    

    public grafo(){
        nodos = new ArrayList<nodo>();
        aristas = new HashMap<String,Arista>();
        dirigido = false;
        Pesos = false;
    }
    public grafo(grafo g){
        nodos = g.nodos;
        aristas = g.aristas;
        dirigido = g.dirigido;
    }
       
    public void desconectarNodo(nodo s){
        Iterator<String> llave = aristas.keySet().iterator();
        String key;
        while(llave.hasNext()){
           key = llave.next();                
           if( this.aristas.get(key).getA().getName() ==s.getName() ||this.aristas.get(key).getB().getName()==s.getName()){
               this.aristas.remove(key);
           }
        }        
    }
    public void crearNodo(int num){
        String nombre = "A"+num;        
        this.nodos.add(new nodo(nombre, num));        
    }
    public void crearNodo(int num, double x, double y){
        String nombre = "A"+num;        
        this.nodos.add(new nodo(nombre, num, x, y));        
    }  
    public void crearNodo(nodo n){
        this.nodos.add(new nodo(n));
    }
    public void reiniciaNodos(){
        int tam = this.getOrden();
       // System.out.println("Se reiniciaran los "+tam+" nodos");
        for(int i=0; i<tam; i++){
            this.nodos.get(i).setExp(false);
           /* if(!this.nodos.get(i).getExp()){
                System.out.println("Se reinicio correctamente el nodo "+i);
            }*/
        }
    }
    public nodo getNodo(int dato){
        nodo n = null;
        
        for(int i=0; i<this.nodos.size(); i++){
            if(this.nodos.get(i).getDat()==dato)
                n= this.nodos.get(i);
        }
        return n;                
    }
    
    public boolean getDir(){
        return this.dirigido;
    }
    
    public void setPesos(boolean v){
        this.Pesos=v;
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
                if(!a.getVecinos().contains(b)){
                    a.addVecino(b);
                }
                if(!b.getVecinos().contains(a))
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
                if(!a.getVecinos().contains(b))
                    a.addVecino(b);
                return 1;
            }
        }
        
    }
    public void creaDotGraf(boolean Pesos, boolean NewName){
        Calendar hoy = Calendar.getInstance();
        String path ="/home/beto/Documentos/CIC/DyAdAlgoritmos/Tareas/archivosgdf/";
        if(NewName){
            String nombre="grafo"+this.nombre+this.nodos.size()+hoy.getTime();            
            this.nombre = nombre;
        }
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
                bw.write("nodedef>name VARCHAR");    
                if(Pesos){
                    bw.write(", label VARCHAR\n");
                    for(int i=0; i<this.nodos.size(); i++){
                        bw.write(nodos.get(i).nombre+ ","+nodos.get(i).nombre +"("+nodos.get(i).getPeso()+")\n");
                    }
                }
                else{
                    bw.write("\n");
                    for(int i=0; i<this.nodos.size(); i++){
                        bw.write(nodos.get(i).nombre+"\n");
                    }
                }                
                bw.write("edgedef>node1 VARCHAR,node2 VARCHAR, directed BOOLEAN");
                if(Pesos){
                    bw.write(", weight DOUBLE\n");
                }
                else{
                    bw.write("\n");
                }
                while(llave.hasNext()){
                  key = llave.next();
                  if(Pesos){                
                      bw.write(this.aristas.get(key).getA().getName()+","+this.aristas.get(key).getB().getName() + ", true," +this.aristas.get(key).getPeso() +"\n");
                  }
                  else{                
                    bw.write(this.aristas.get(key).getA().getName()+","+this.aristas.get(key).getB().getName() +", true\n");
                  }

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

                bw.write("nodedef>name VARCHAR");    
                if(Pesos){
                    bw.write(", label VARCHAR\n");
                    for(int i=0; i<this.nodos.size(); i++){
                        bw.write(nodos.get(i).nombre+ ","+nodos.get(i).nombre +"("+nodos.get(i).getPeso()+")\n");
                    }
                }
                else{
                    bw.write("\n");
                    for(int i=0; i<this.nodos.size(); i++){
                        bw.write(nodos.get(i).nombre+"\n");
                    }
                }
                bw.write("edgedef>node1 VARCHAR,node2 VARCHAR");
                if(Pesos){
                    bw.write(", weight DOUBLE\n");
                }
                else{
                    bw.write("\n");
                }
                while(llave.hasNext()){
                    key = llave.next();     
                      if(Pesos){                        
                        bw.write(this.aristas.get(key).getA().getName()+","+this.aristas.get(key).getB().getName() + "," +this.aristas.get(key).getPeso() +"\n");
                    }
                    else{
                      bw.write(this.aristas.get(key).getA().getName()+","+this.aristas.get(key).getB().getName() +"\n");
                    }
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
    public void crearGrafoErdos(int n, int m, boolean dirigido, boolean autociclo, boolean arboles){
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
            while(a==b && !(autociclo)){
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
        this.tipo="Erdos";
        if(arboles){
            this.BFS(this.nodos.get(0));
            grafo arbolDFS_I = new grafo();
            arbolDFS_I.nombre= this.nombre +"DFS_I";
            arbolDFS_I.DFS_I(this.nodos.get(0));
            arbolDFS_I.creaDotGraf(false, true);
            this.reiniciaNodos();
            grafo arbolDFS_R = new grafo();
            arbolDFS_R.nombre= this.nombre +"DFS_R";
            arbolDFS_R.DFS_R(this.nodos.get(0));
            arbolDFS_R.creaDotGraf(false, true);
        }
    }
    
    public void crearGrafoGilbert(int n, float p, boolean dirigido, boolean autociclo, boolean arboles){
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
        this.tipo = "Gilbert";
        if(arboles){
            this.BFS(this.nodos.get(0));
            grafo arbolDFS_I = new grafo();
            arbolDFS_I.nombre= this.nombre +"DFS_I";
            arbolDFS_I.DFS_I(this.nodos.get(0));
            arbolDFS_I.creaDotGraf(false, true);
            this.reiniciaNodos();
            grafo arbolDFS_R = new grafo();
            arbolDFS_R.nombre= this.nombre +"DFS_R";
            arbolDFS_R.DFS_R(this.nodos.get(0));
            arbolDFS_R.creaDotGraf(false, true);
        }

    }
    public double calculaDist(nodo a, nodo b){
        double dist;
        dist = Math.sqrt(Math.pow(a.getPosx()-b.getPosx(), 2)+ Math.pow(a.getPosy()-b.getPosy(), 2));
        return dist;
    }
    public void crearGrafoGeo(int n, double r, boolean dirigido, boolean autociclo, boolean arboles){
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
        this.tipo = "Geografico";
        if(arboles){
            this.BFS(this.nodos.get(0));
            grafo arbolDFS_I = new grafo();
            arbolDFS_I.nombre= this.nombre +"DFS_I";
            arbolDFS_I.DFS_I(this.nodos.get(0));
            arbolDFS_I.creaDotGraf(false, true);
            this.reiniciaNodos();
            grafo arbolDFS_R = new grafo();
            arbolDFS_R.nombre= this.nombre +"DFS_R";
            arbolDFS_R.DFS_R(this.nodos.get(0));
            arbolDFS_R.creaDotGraf(false, true);
        }
    }
    public void crearGrafoBarabasi(int n, int g, boolean dirigido, boolean autociclo, boolean arboles){
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
        for(i = 0; i<n; i++){            
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
        this.tipo = "Barabasi";
        if(arboles){
            this.BFS(this.nodos.get(0));
            grafo arbolDFS_I = new grafo();
            arbolDFS_I.nombre= this.nombre +"DFS_I";
            arbolDFS_I.DFS_I(this.nodos.get(0));
            arbolDFS_I.creaDotGraf(false, true);
            this.reiniciaNodos();
            grafo arbolDFS_R = new grafo();
            arbolDFS_R.nombre= this.nombre +"DFS_R";
            arbolDFS_R.DFS_R(this.nodos.get(0));
            arbolDFS_R.creaDotGraf(false, true);
        }
        
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
        int k=0, n1=0, n2=0;
        while(!(capas.get(k).isEmpty())){            
            capa2 = new ArrayList<nodo> ();
            //System.out.println("tamanio de capa i=" +capas.get(k).size());
            //System.out.println("tamanio de capa2=" +capa2.size());
            tamC= capas.get(k).size();                                   
            for(i=0; i<tamC; i++){
                tamV = capas.get(k).get(i).getVecinos().size();
                n1=capas.get(k).get(i).getDat();
                arbolBFS.crearNodo(n1);
                for(int j=0; j<tamV; j++){                    
                    n2 = capas.get(k).get(i).getVecinos().get(j).getDat();
                    if(descubiertos[n2]==false){
                        descubiertos[n2]=true;
                        capa2.add(capas.get(k).get(i).getVecinos().get(j));                       
                        arbolBFS.crearNodo(n2);
                        arbolBFS.crearArista(arbolBFS.getNodo(n1), arbolBFS.getNodo(n2), this.dirigido);
                    }
                }                               
            }
            //System.out.println("tamanio de capa2=" +capa2.size());
            capas.add(capa2);
            k+=1;
        }
        arbolBFS.creaDotGraf(false, true);              
    }
    public void revisaVecino(nodo s, int n){
        int tam = s.getVecinos().size(), n1=0;        
        if(tam==0){
            n=tam;
        }
        else{
            if(n==tam-1){        
                n1=s.getVecinos().get(n).getDat();
                this.crearNodo(n1);
                this.crearArista(this.getNodo(s.getDat()), this.getNodo(n1) , this.dirigido);            
            }
            else{
                if(s.getVecinos().get(n).getExp()==false){
                    n1=s.getVecinos().get(n).getDat();
                    this.crearNodo(n1);
                    this.crearArista(this.getNodo(s.getDat()), this.getNodo(n1), this.dirigido);
                    this.DFS_R(s.getVecinos().get(n));
                }
                revisaVecino(s,n+1);                               
            }
        }
    }
    public void DFS_R(nodo s){
        this.crearNodo(s.getDat());
        s.setExp(true);
        this.revisaVecino(s, 0);        
    }
    
    public void DFS_I(nodo s){       
        if(this.getNodo(s.getDat())==null){
            this.crearNodo(s.getDat());                
        }        
        s.setExp(true);
        int tamV;
        tamV = s.getVecinos().size();        
        for(int i=0; i< tamV; i++){
            if(s.getVecinos().get(i).getExp()==false){
                this.crearNodo(s.getVecinos().get(i).getDat());                
                this.crearArista(this.getNodo(s.getDat()), this.getNodo(s.getVecinos().get(i).getDat()) , this.dirigido);
                this.DFS_I(s.getVecinos().get(i));
            }
        }
    }
    public void asignaPesos(float min, float max){
        Random rand= new Random();
        float num;
        Iterator<String> llave = aristas.keySet().iterator();
        String key;
        while(llave.hasNext()){
            num = rand.nextFloat()*(max-min);
            num+=min;
            key = llave.next();     
            this.aristas.get(key).setPeso(num);
        }
        this.creaDotGraf(true, false);     
    }
    public void iniciaDijkstra(nodo s, PriorityQueue <nodo> colaN){        
        for (int i=0; i< this.getOrden(); i++){
            if(!(this.nodos.get(i)==s)){
                this.nodos.get(i).setPeso(999999);
            }            
        }
        s.setPeso(0);
        colaN.add(s);              
    }
    public void Dijkstra(nodo s){
        grafo Dijkstra = new grafo();
        Dijkstra.dirigido= true;
        PriorityQueue <nodo> colaN = new PriorityQueue<>();     
        HashMap<Integer,nodo> padres = new HashMap<>();
        Dijkstra.setPesos(true);
        this.iniciaDijkstra(s, colaN);
        this.reiniciaNodos();
        //colaN.add(s);        
        float peso1, peso2, pesoA;
        while(!colaN.isEmpty()){
            nodo actual = colaN.remove();
            Dijkstra.crearNodo(actual.getDat());
            Dijkstra.getNodo(actual.getDat()).setPeso(actual.getPeso());            
            for(int i=0 ; i< actual.getVecinos().size(); i++){                                                               
                pesoA = this.aristas.get(actual.getName()+actual.getVecinos().get(i).getName()).getPeso();
                peso1 = pesoA+actual.getPeso();
                peso2 = actual.getVecinos().get(i).getPeso();
                if(peso1 < peso2 ){
                    padres.put(actual.getVecinos().get(i).getDat(), actual);                    
                    actual.getVecinos().get(i).setPeso(peso1);                    
                    if(!actual.getVecinos().get(i).getExp()){
                        colaN.add(actual.getVecinos().get(i));
                        actual.getVecinos().get(i).setExp(true);
                        //System.out.println("Agregando nodo "+actual.getVecinos().get(i).getDat()+ " a la cola");
                    }
                }
            }            
            Dijkstra.nombre= this.tipo+"Dijkstra";
        }
        Iterator<Integer> llave = padres.keySet().iterator();
        Integer key;
        float peso, pesohijo, pesoPadre;        
        while(llave.hasNext()){           
           key = llave.next();           
           pesohijo =Dijkstra.getNodo(key.intValue()).getPeso();
           pesoPadre = padres.get(key).getPeso();
           peso = pesohijo-pesoPadre;           
           Dijkstra.crearArista(padres.get(key), Dijkstra.getNodo(key),true);           
           Dijkstra.aristas.get(padres.get(key).getName()+Dijkstra.getNodo(key).getName()).setPeso(peso);
        }
        Dijkstra.creaDotGraf(true, true);
    }
    public Set findSet(ArrayList <Set> partes, nodo conj){
        int index=0;
        for(int i=0; i<partes.size(); i++){
            if(partes.get(i).contains(conj)){
                index=i;
            }
        }
        return partes.get(index);
    }
    
    public void kruskalD(){
       grafo arbolMin = new grafo();
       arbolMin.setPesos(true);
       this.reiniciaNodos();
       ArrayList <Set> parts = new ArrayList<>();
       PriorityQueue <Arista> colaA = new PriorityQueue<>();
       Iterator<String> llave = aristas.keySet().iterator();
       String key;
       float pesoT=0; 
       for(int i=0; i<this.getOrden(); i++){
           Set <nodo>vertex=new HashSet<>();
           vertex.add(this.nodos.get(i));
           parts.add(vertex);
       }
       while(llave.hasNext()){          
            key = llave.next();     
            colaA.add(this.aristas.get(key));
       }
       while(!colaA.isEmpty()){
           Arista actual = colaA.remove();
           //Lo primero es encontrar los conjuntos donde estan los nodos A y B de la arista
           Set <nodo> setA = findSet(parts,actual.getA());
           Set <nodo> setB = findSet(parts,actual.getB());
           //System.out.println("Se encontraron los conjuntos A{"+setA+"} y B{"+setB+"}");
           if(setA != setB){
               //Agregamos los dos nodos A y B al arbol de expansion mínima
               if(arbolMin.getNodo(actual.getA().getDat())==null){
                   arbolMin.crearNodo(actual.getA().getDat());
               }
               if(arbolMin.getNodo(actual.getB().getDat())==null){
                   arbolMin.crearNodo(actual.getB().getDat());               
               }               
               arbolMin.crearArista(actual.getA(), actual.getB(), actual.dir);
               arbolMin.aristas.get(actual.getA().getName()+actual.getB().getName()).setPeso(actual.getPeso());
               //Ahora modificamos el conjunto parts con base en A y B               
               parts.remove(setA);               
               parts.remove(setB);               
               setA.addAll(setB);               
               parts.add(setA);
               pesoT+=actual.getPeso();
           }
       }
       System.out.println("El peso del arbol de expansion minima usando el algoritmo de Kruscal es de -> "+pesoT);
       arbolMin.nombre= this.tipo+"kruskalD";
       arbolMin.creaDotGraf(true, true);       
    }
    public int isConectedToManyN(nodo n){
        int k=0;
        boolean conect=false;
        PriorityQueue <nodo> colaN = new PriorityQueue<>();
        colaN.add(n);                
        n.setExp(true);               
        k++;                
        while(!colaN.isEmpty()){
            nodo actual = colaN.remove();                              
            for(int i=0 ; i< actual.getVecinos().size(); i++){                                                                                               
                if(!actual.getVecinos().get(i).getExp()){
                    colaN.add(actual.getVecinos().get(i));
                    actual.getVecinos().get(i).setExp(true);                                        
                    k++;                
                }                
            }
        }                       
        return k;
    }
    public boolean isConected(){
        int k=0;
        boolean conect=false;
        PriorityQueue <nodo> colaN = new PriorityQueue<>();
        colaN.add(this.getNodo(0));                
        this.getNodo(0).setExp(true);               
        k++;                
        while(!colaN.isEmpty()){
            nodo actual = colaN.remove();                              
            for(int i=0 ; i< actual.getVecinos().size(); i++){                                                                                               
                if(!actual.getVecinos().get(i).getExp()){
                    colaN.add(actual.getVecinos().get(i));
                    actual.getVecinos().get(i).setExp(true);                                        
                    k++;                
                }                
            }
        }        
        if(k==this.getOrden()){           
            conect=true;
        }        
        return conect;
    }
    public void borraArista(nodo a, nodo b, boolean d){
        this.aristas.remove(a.getName()+b.getName());        
        if(d){
            a.removeVecino(b);            
        }
        else{                   
            a.removeVecino(b);
            b.removeVecino(a);        
        }        
    }
    public float calculaPeso(){
        float p=0;
        Iterator<String> llave = aristas.keySet().iterator();
        String key;
        while(llave.hasNext()){
           key = llave.next();                           
           p+= this.aristas.get(key).getPeso();
        } 
        return p;
    }
    public grafo copyGrafo(grafo g){
        grafo nuevo = new grafo();
        Iterator<String> llave = g.aristas.keySet().iterator();
        llave = g.aristas.keySet().iterator();
        String key;        
        for(int i =0; i<g.getOrden(); i++){
            nuevo.crearNodo(i);
        }
        while(llave.hasNext()){          
             key = llave.next();     
             nuevo.crearArista(nuevo.getNodo(g.aristas.get(key).getA().getDat()), nuevo.getNodo(g.aristas.get(key).getB().getDat()), g.dirigido);             
             nuevo.aristas.get(key).setPeso(g.aristas.get(key).getPeso());
        }
        
        return nuevo;
    }
    public void kruskalI(){
        grafo arbolMin = new grafo();                
        PriorityQueue <Arista> colaA = new PriorityQueue<>();
        Stack <Arista> colaAInv = new Stack<>();                     
        String key;
        float pesoT;
        Random rand = new Random();
        int b=rand.nextInt(this.getOrden()); 
        int max =this.isConectedToManyN(this.getNodo(b));
        arbolMin= copyGrafo(this);
        arbolMin.reiniciaNodos();                    
        Iterator<String> llave = arbolMin.aristas.keySet().iterator();        
        while(llave.hasNext()){          
             key = llave.next();     
             colaA.add(arbolMin.aristas.get(key));
        }
       
        while(!colaA.isEmpty()){            
            colaAInv.push(colaA.remove());
        }        
        int conected ;
        while(!colaAInv.isEmpty()){
            Arista actual =colaAInv.pop();                                    
            arbolMin.borraArista(actual.getA(), actual.getB(), actual.dir);                                    
            arbolMin.reiniciaNodos();
            conected = arbolMin.isConectedToManyN(arbolMin.getNodo(b));
            if(conected!=max){                
                //Agregamos la arista que acabamos de borrar, pues se desconectaria el grafo                
                arbolMin.crearArista(actual.getA(), actual.getB(), actual.dir);
                arbolMin.aristas.get(actual.getA().getName()+actual.getB().getName()).setPeso(actual.getPeso());
                if(actual.dir){
                    arbolMin.getNodo(actual.getA().getDat()).addVecino(actual.getB());
                }
                else{
                    arbolMin.getNodo(actual.getA().getDat()).addVecino(actual.getB());
                    arbolMin.getNodo(actual.getB().getDat()).addVecino(actual.getA());
                }                                
            }                       
        }
        pesoT=arbolMin.calculaPeso();
        System.out.println("El peso del arbol de expansion minima usando el algoritmo de Kruscal Inverso es de -> "+pesoT);
        arbolMin.nombre= this.tipo+"kruskalInv";
        arbolMin.creaDotGraf(true, true);      
    }
    public void Prim(){        
        grafo arbolMin = new grafo();
        arbolMin.dirigido= this.dirigido;                
        PriorityQueue <Arista> colaA = new PriorityQueue<>();                    
        String  nombreA, nombreB;
        arbolMin.setPesos(true);
        int ind;
        this.reiniciaNodos(); 
        Random rand = new Random();
        //Este metodo requiere seleccionar un nodo aleatorio para iniciar
        int b=rand.nextInt(this.getOrden()); 
        int max =this.isConectedToManyN(this.getNodo(b));        
        
        arbolMin.crearNodo(this.getNodo(b).getDat());        
        while((int)arbolMin.isConectedToManyN(arbolMin.getNodo(b))!=max){                           
            arbolMin.reiniciaNodos();            
            ind=0;
            colaA.clear();
            //debemos buscar en los nodos adyacentes a los nodos que forman parte del arbol 
            //es decir, tomamos todas las aristas salientes del arbol minimo incompleto
            for(int i=0;i<arbolMin.getOrden(); i++){            
                nodo n = arbolMin.nodos.get(i);                               
                for(int j=0; j<this.getNodo(n.getDat()).getVecinos().size(); j++){                                        
                    nombreA= arbolMin.nodos.get(i).getName();
                    nombreB= this.getNodo(n.getDat()).getVecinos().get(j).getName();
                    if(this.aristas.get(nombreA+nombreB)!=null){
                        colaA.add(this.aristas.get(nombreA+nombreB));
                        
                    }
                    else{
                        colaA.add(this.aristas.get(nombreB+nombreA));                        
                    }
                }                
            }
            //Ahora, revisamos las aristas con menor peso, pero solo añadimos una a la vez, para buscar de nuevo
            //en una siguiente iteracion con el arbol actualizado
            //Este codigo esta pensado para grafos no dirigidos, por lo tanto, se comprueban las aristas con (A,B) o (B,A)            
            while(ind==0 && !colaA.isEmpty()){
                Arista actual=colaA.remove();                
                if(arbolMin.getNodo(actual.getB().getDat())==null){
                    arbolMin.crearNodo(actual.getB().getDat());
                    arbolMin.crearArista(arbolMin.getNodo(actual.getA().getDat()),arbolMin.getNodo(actual.getB().getDat()) , this.dirigido);
                    arbolMin.aristas.get(actual.getA().getName()+actual.getB().getName()).setPeso(actual.getPeso());
                    ind++;
                }
                else{
                    if(arbolMin.getNodo(actual.getA().getDat())==null){
                        arbolMin.crearNodo(actual.getA().getDat());
                        arbolMin.crearArista(arbolMin.getNodo(actual.getB().getDat()),arbolMin.getNodo(actual.getA().getDat()) , this.dirigido);
                        arbolMin.aristas.get(actual.getB().getName()+actual.getA().getName()).setPeso(actual.getPeso());
                        ind++;
                    }
                }
            }
        }
        
        arbolMin.nombre= this.tipo+"Prim";
        float pesoT= arbolMin.calculaPeso();
        System.out.println("El peso del arbol de expansion minima usando el algoritmo de Prim es de -> "+pesoT);
        arbolMin.creaDotGraf(true, true);                
    }
}
