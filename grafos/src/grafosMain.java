import java.util.Scanner;
public class grafosMain {


    public static void main(String[] args) {      
        //Random rand = new Random();                
        int op =0, numNodos=0;
        boolean d, a;
        while(op==0)
            op=menu();
        while(numNodos==0){
            numNodos=menuNodos();
            }
        d = menuDirigido();
        a = menuAutociclo();
        if(op==1){
            menuErdos(numNodos, d, a);
        }
        if(op==2){
            menuGilbert(numNodos, d, a);
        }
        if(op==3){
            menuGeo(numNodos, d, a);
        }
        if(op==4){
            menuBar(numNodos, d, a);
        }           
    }
    
        public static int menu(){
            Scanner input = new Scanner(System.in);
            System.out.println("\t\tModelos de generacion automatica de grafos");
            System.out.println("Por favor seleccione uno de los siguientes modelos de generacion de grafos por su número");
            System.out.println("1.- Modelo de Erdos y Renyi");
            System.out.println("2.- Modelo de Gilbert");
            System.out.println("3.- Modelo Geografico simple");
            System.out.println("4.- Modelo de Barabasi-Albert");
            try{
                int op = input.nextInt();
                if(op<1 || op>4){
                    System.out.println("Opcion no valida >:(");
                    return 0;
                }
                else{
                    return op;
                }
            } catch(Exception e){
                System.out.println("Opcion no valida >:(");
                return 0;
            }   
        }
        public static int menuNodos(){
            Scanner input = new Scanner(System.in);
            System.out.println("Ingrese el numero de nodos (entre 1 y 1000) para su grafo");
             try{
                int op = input.nextInt();
                if(op<1 || op>1000){
                    System.out.println("Opcion no valida >:(");
                    return 0;
                }
                else{
                    return op;
                }
            } catch(Exception e){
                System.out.println("Opcion no valida >:(");
                return 0;
            }                   
        }
        public static boolean menuDirigido(){
            //Scanner input = new Scanner(System.in);
            int bien =0;
            boolean valor = false;
            while(bien==0){
            System.out.println("Su grafo sera dirigido? (ingrese el numero de opcion)");
            System.out.println("0.- No");
            System.out.println("1.- Si");
             try{
                Scanner input = new Scanner(System.in);
                int op = input.nextInt();
                if(op<0 || op>1){
                    System.out.println("Opcion no valida >:(");

                }
                else{
                    if(op==0){
                        valor= false;
                        bien=1;
                    }
                    else{
                        valor= true;
                        bien=1;
                    }
                }
            } catch(Exception e){
                System.out.println("Opcion no valida >:(");            
            }   
            }
            return valor;
        }
        public static boolean menuAutociclo(){
            int bien =0;
            boolean valor = false;
            while(bien==0){
            System.out.println("Su grafo tendra autociclos? (ingrese el numero de opcion)");
            System.out.println("0.- No");
            System.out.println("1.- Si");
             try{
                Scanner input = new Scanner(System.in);
                int op = input.nextInt();
                if(op<0 || op>1){
                    System.out.println("Opcion no valida >:(");

                }
                else{
                    if(op==0){
                        valor= false;
                        bien=1;
                    }
                    else{
                        valor= true;
                        bien=1;
                    }
                }
            } catch(Exception e){
                System.out.println("Opcion no valida >:(");            
            }   
            }
            return valor;
        }
        public static void menuErdos(int nodos, boolean dir, boolean aut){
            int bien=0;
            int m=0;
            while(bien==0){
                try{
                    Scanner input = new Scanner(System.in);
                    System.out.println("Ingrese el numero de aristas");
                    m = input.nextInt();
                    int max = (nodos*(nodos-1))/2;
                    if(m>max || m<1){
                        System.out.println("Opcion no valida :(");
                    }
                    else{
                        bien=1;                        
                    }
                }catch(Exception e){
                    System.out.println("Opcion no valida :(");
                }          
            }
            grafo g = new grafo();
            g.crearGrafoErdos(nodos, m, dir, aut);
            g.creaDotGraf();
            System.out.println("Se genero el archivo "+g.nombre);
        }
                                    
        public static void menuGilbert(int nodos, boolean dir, boolean aut){
            int bien=0;
            float p=0.0f;
            while(bien==0){
                try{
                    Scanner input = new Scanner(System.in);
                    System.out.println("La probabilidad (entre 0 y 1, usando el punto '.') de crear una arista en los nodos i y j");
                    p =input.nextFloat();
                    if(p<0.0f || p >1.0f){
                        System.out.println("Opcion no valida :(");
                    }
                    else{
                        bien=1;
                    }
                    
                }catch(Exception e){
                    System.out.println("Opcion no valida :(");
                }                
            }
            grafo g = new grafo();
            g.crearGrafoGilbert(nodos, p, dir, aut);
            g.creaDotGraf();
            System.out.println("Se genero el archivo "+g.nombre);
        }
        public static void menuGeo(int nodos, boolean dir, boolean aut){
            int bien=0;
            double r=0;
            while(bien==0){
                try{
                    Scanner input = new Scanner(System.in);
                    System.out.println("Ingrese la distancia maxima para conectar dos nodos, (entre 0 y raiz cuadrada de 2)");
                    r = input.nextDouble();
                    if(r<0.0 || r>(Math.sqrt(2.0))){
                        System.out.println("Opcion no valida :(");
                    }
                    else{
                        bien=1;
                    }
                    
                }catch(Exception e){
                    System.out.println("Opcion no valida :(");
                }
            }
            grafo g = new grafo();
            g.crearGrafoGeo(nodos, r, dir, aut);
            g.creaDotGraf();
            System.out.println("Se genero el archivo "+g.nombre);
        }
        public static void menuBar(int nodos, boolean dir, boolean aut){
            int bien=0;
            int grado=0;
            while(bien==0){
                try{
                    Scanner input = new Scanner(System.in);
                    System.out.println("Ingrese el numero aproximado de aristas por nodo (entre 0 y "+(nodos-1)+")");
                    grado = input.nextInt();
                    if(grado<1 || grado>(nodos-1)){
                        System.out.println("Opcion no valida :(");
                    }
                    else{
                        bien=1;
                    }
                }catch(Exception e){
                    System.out.println("Opcion no valida :(");
                }
            }
            grafo g = new grafo();
            g.crearGrafoBarabasi(nodos, grado, dir, aut);
            g.creaDotGraf();
            System.out.println("Se genero el archivo "+g.nombre);
        }    
}
