package tp_mutantes;

import java.util.Random;


public class mutantes {

    public static void main(String[] args) {
        
        //Declaración array para almacenar elementos(filas) con seis letras
        String secuenciaAdn[];
        secuenciaAdn = new String[6];
        
        //Import clase random
        Random rnd = new Random();
        
        //Definimos los caracteres necesarios
        String setOfCharacters = "ATCG";
        int randomInt;
        char randomChar;
        String filaString = "";
        
        //Bucle for para agregar los caracteres aleatorios al array
        for (int i = 0; i < secuenciaAdn.length; i++) {
            for (int j = 0; j < secuenciaAdn.length; j++) {
                //Obtenemos un índice random del String
                randomInt = rnd.nextInt(setOfCharacters.length());
                //Obtener el caracter aleatorio a partir del índice random
                randomChar = setOfCharacters.charAt(randomInt);
                //Generar string del array que sería una fila de nuestra matriz a crear.
                filaString += randomChar;
            }
            
            //Agregamos el string formado al array
            secuenciaAdn[i] = filaString;
            //Reinicio de la filaString
            filaString = "";
        }
        
        //Mostramos array con la secuencia adn formada
        System.out.println("Array de secuencia ADN: ");
        mostrarArray(secuenciaAdn);
        
        //Resultado final
        if(isMutant(secuenciaAdn)){
            System.out.println("LA PERSONA ES MUTANTE");
        } else {
            System.out.println("LA PERSONA NO ES MUTANTE");
        }
    } 
    
    
    public static void mostrarArray(String[] array){
        for (int i = 0; i < array.length; i++) {
            System.out.print(" [" + array[i] + "] ");
        }
        System.out.println("");
        System.out.println("");
    }
    
    public static void mostrarMatriz(char[][] matrizADN){
        for (int i = 0; i < matrizADN.length; i++) {
            for (int j = 0; j < matrizADN.length; j++) {
                System.out.print(" [" +matrizADN[i][j]+"] ");
            }
            System.out.println("");
        }
    }
    
    //Obtener transpuesta de una matriz
    public static char[][] matrizTranspuesta(char[][] matrizOriginal) {
        char[][] matrizTrans = new char[6][6];

        for (int i = 0; i < matrizOriginal.length; i++) {
            for (int j = 0; j < matrizOriginal.length; j++) {
                matrizTrans[i][j] = matrizOriginal[j][i];
            }
        }

        return matrizTrans;
    }   
    
    public static boolean isMutant(String[] secuenciaADN){
        char[][] matrizADN = new char[6][6];
        
        //Relleno de matriz
        for (int i = 0; i < secuenciaADN.length; i++) {
            for (int j = 0; j < secuenciaADN.length; j++) {
                //Extraer letra de cada elemento del array secuenciaADN
                char letra = secuenciaADN[i].charAt(j);
                matrizADN [i][j] = letra;
            }
        }
        
        //Mostrar matriz creada
        System.out.println("Matriz creada: ");
        mostrarMatriz(matrizADN);
        System.out.println("");
        
        //Crear la matriz transpuesta nos permite valorar la existencia
        //de una secuencia de 4 letras iguales en alguna COLUMNA de la matriz original.
        char [] [] matrizTrans = matrizTranspuesta(matrizADN);
        
        //Mostrar matriz transpuesta
        System.out.println("Matriz transpuesta");
        mostrarMatriz(matrizTrans);
        System.out.println("");
        
       
        boolean result;
        int cantidadSecuenciasRepetidas = 0;
        
        //OBTENER DIAGONALES
        String [] arrDiagonales = obtenerDiagonales(matrizADN);
        System.out.println("Array de diagonales: ");
        mostrarArray(arrDiagonales);
       

        //Recorrer matrices para contabilizar las secuencias de letras iguales
        //Por cada fila se invoca a la función consecutivos que determina 
        //si hay una secuencia de 4 letras iguales en dicha fila
        for (int i = 0; i < matrizADN.length; i++) {
            //Nota: Al pasarle la fila de la matriz transpuesta lo que se hace realmente es
            //valorar si existe una secuencia de 4 letras iguales en alguna COLUMNA de la matriz original
            System.out.println("Fila " + i + " de la matriz original: " + consecutivos(matrizADN[i]));
            System.out.println("Fila " + i + " de la transpuesta: " + consecutivos(matrizTrans[i]));
            
            if (consecutivos(matrizADN[i]) || consecutivos(matrizTrans[i])) {
                cantidadSecuenciasRepetidas++;
            }
        }
        
        //Recorremos las diagonales, primero validamos si la diagonal tiene 4 letras como mínimo
        for (int i = 0; i < arrDiagonales.length; i++) {

            if (arrDiagonales[i].length() >= 4) {
                char [] diagonalConCuatro_o_masLetras = arrDiagonales[i].toCharArray();
                
                System.out.println("Diagonal: " + i + ": " + consecutivos(diagonalConCuatro_o_masLetras));
                
                if(consecutivos(diagonalConCuatro_o_masLetras)){
                    cantidadSecuenciasRepetidas++;
                }
            }
        }
        
        System.out.println("");
        //Resultado final: Si se encontró mas de una secuencia de letras iguales la persona es mutante.
        result = cantidadSecuenciasRepetidas > 1;
        return result;
    }
    
    
    //Recibe por parámetro un subarray que equivale a cada una de las filas 
    //de la matriz original, a su diagonal y transpuesta
    public static boolean consecutivos(char[]filaMatriz){
        char valor = ' ';
        int cantidad = 1;
        
        for (char caracter: filaMatriz){
            if (cantidad == 4){
                break;
            } else if (caracter == valor){
                cantidad++;
            } else {
                valor = caracter;
                cantidad = 1;
            }
        }
        
        
        return (cantidad == 4);
    }
    
    //Creamos un ArrayList con todas las diagonales de la matriz
    public static String[] obtenerDiagonales (char[][]matrizADN){
        
        //La cantidad de diagonales siempre será el doble de la dimensión de
        //la matriz menos 1.
        String[] arrDiagonales = new String[(matrizADN.length*2)-1];
        
        String aux = "";
        
        int dim = matrizADN.length;
        
        //Bucle para obtener las diagonales inferiores a la media inclusive
        for (int i = dim-1; i >= 0; i--) {
            int row=i, col=0; //Valores iniciales
            
            while (row<dim && col<dim) {                
                aux+=String.valueOf(matrizADN[row][col]);
                row++;
                col++;
            }

            //Agregamos la diagonal formada en el array
            arrDiagonales[i] = aux;
            //Reiniciamos variable auxiliar
            aux="";
        }
        
        //El comienzo será la diagonal superior a la media
        int inicioMitadSuperior = dim;     
        //Segundo bucle para las diagonales superiores a la media excluída
        for (int i = 0; i < dim-1; i++) {
            int row=0, col=i+1;
            
            while (row<dim && col<dim) {                
                aux+=String.valueOf(matrizADN[row][col]);
                row++;
                col++;      
            }
            
            arrDiagonales[inicioMitadSuperior] = aux;
            inicioMitadSuperior++;
            aux="";
        }
        
        return arrDiagonales;
    }
    
}
