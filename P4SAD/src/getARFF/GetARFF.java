package getARFF;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class GetARFF {
	
	public GetARFF(){
		
	}
	
	public void generarArffCSV(String inputPath, String outputPath, String name) throws IOException {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "\",\"";
		String texto = "";
		
		int aux = 0;
		try {
			br = new BufferedReader(new FileReader(inputPath));
			while ((line = br.readLine()) != null) {
				if (!line.equals("")) {

					line = line.substring(1, line.length());
					line = remove1(line);
					String[] data = line.split(cvsSplitBy);
					if (aux == 0) {
						texto += "@RELATION tweets \n";
						texto += "\n";
						// texto += "@ATTRIBUTE " + data[0] + " STRING \n";
						texto += "@ATTRIBUTE clase {negative,positive} \n";
						// texto += "@ATTRIBUTE " + data[2] + " STRING \n";
						// texto += "@ATTRIBUTE " + data[3] + " STRING \n";
						texto += "@ATTRIBUTE " + data[4].replace("\"", "") + " STRING \n";
						texto += "\n";
						texto += "@DATA \n";
						aux += 1;
					} else {
						if (data.length > 3) {
							if(data[1].equals("positive")||data[1].equals("negative")){
								texto += data[1] + ",'" +data[4].replace("'", " ") +"'\n";
							}
							else{
								texto += "?" + ",'" +data[4].replace("'", " ") +"'\n";
							}
//							if (!data[1].equals("irrelevant")) {
//								if (!data[1].equals("UNKNOWN") && !data[1].equals("neutral")) {	
//									texto += data[1] + ",'" + data[4].replace("'", " ") + "'\n";
//								} else {
//									texto += "?" + ",'" + data[4].replace("'", " ") + "'\n";
//								}
//							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Revisar path del fichero de datos: " + inputPath);
		} catch (IOException e) {
			System.out.println("ERROR: Revisar contenido del fichero de datos: " + inputPath);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("Error al cerrar el archivo.");
				}
			}
		}
		if (name == null) {
			name = "prueba";
		}
		String posiblePath = outputPath + "/" + name + ".arff";
		File f = new File(posiblePath);
		int n = 0;
		while (f.exists()) {
		    n += 1;
		    posiblePath = outputPath + "/" + name + "(" + n + ")" + ".arff";
		    f = new File(posiblePath);
		}
		BufferedWriter fr = new BufferedWriter(new FileWriter(posiblePath));
		fr.write(texto);
		fr.close();
	}
	
	private String remove1(String texto) throws UnsupportedEncodingException {
	    String original = "������������������������������������������������������������";
	    // Cadena de caracteres ASCII que reemplazar�n los originales.
	    String ascii = "AAAAAAACEEEEIIIIDNOOOOOOUUUUYBaaaaaaaceeeeiiiionoooooouuuuyy";
	    String output = texto;
	    for (int i=0; i<original.length(); i++) {
	    // Reemplazamos los caracteres especiales.

	        output = output.replace(original.charAt(i), ascii.charAt(i));

	    }//for i
	    
	    output = utf8aAscil(output);
	    
	 return output;   
	}
	
	private static String utf8aAscil( final String inString ) {
	    if (null == inString ) return null;
	    byte[] byteArr = inString.getBytes();
	    for ( int i=0; i < byteArr.length; i++ ) {
	        byte ch= byteArr[i]; 
	        // remove any characters outside the valid UTF-8 range as well as all control characters
	        // except tabs and new lines
	        if ( !( (ch > 31 && ch < 253 ) || ch == '\t' || ch == '\n' || ch == '\r') ) {
	            byteArr[i]=' ';
	        }
	    }
	    return new String( byteArr );
	}
	
	public void generarARFFtxt(String pArchivo,String pSalida,String pName) throws FileNotFoundException, UnsupportedEncodingException, IOException {
		//leer
		File archivo = new File(pArchivo);
		FileReader fr = new FileReader(archivo);
		BufferedReader br = new BufferedReader(fr);
		Scanner entrada = new Scanner(archivo);
		//write
		String rute=pSalida+"/"+pName+".arff";
		File archi = new File(rute);
		BufferedWriter bw = new BufferedWriter(new FileWriter(archi));
		PrintWriter pw = new PrintWriter(bw);
		
		int contador = 0;		
		String linea = "", inicio="", resto="";
		int count=0;
		while (entrada.hasNext()) {
			linea = entrada.nextLine();
			if (contador == 0) {
				pw.println("@RELATION SMS");
				pw.println();
				pw.println("@ATTRIBUTE clase {ham, spam}");
				pw.println("@ATTRIBUTE Text STRING");
				pw.println();
				pw.println("@DATA");
				contador++;
			} else {
				if (linea.length() > 4) {
					inicio = linea.substring(0, 4);
					inicio = inicio.replace("\t", "");
					if(inicio.equals("ham")||inicio.equals("spam")){
						resto = linea.substring(4, linea.length());
						resto = resto.replace(":", "");
						resto = resto.replace("\t","");
						resto = resto.replace("-)", "");
						resto = resto.replace(")", "");
						resto = resto.replace(".", "");
						resto = resto.replace("(", "");
						resto = resto.replace("/", "");
						resto = resto.replace("''", "");
						resto = resto.replace("-", "");
						pw.println(inicio + ",'" + resto + "'");
						//pw.println(" ");
						pw.flush();
					}
					else{
						resto = linea;
						resto = resto.replace(":", "");
						resto = resto.replace("-)", "");
						resto = resto.replace(")", "");
						resto = resto.replace(".", "");
						resto = resto.replace("(", "");
						resto = resto.replace("/", "");
						resto = resto.replace("''", "");
						resto = resto.replace("-", "");
						pw.print("?,'"+resto + "'");
						pw.println(" ");
						pw.flush();
					}
				}
			}				
		}		
		pw.close();
		entrada.close();
		br.close();
		System.out.println("Terminado!!");
	}
	
	public Instances createDataset(String directoryPath, String pClass,Instances pData) throws Exception {
		 
		System.out.println("directorio: "+directoryPath);  
		Instances data=pData;
	    File dir = new File(directoryPath);
	    String[] files = dir.list();
	    for (int i = 0; i < files.length; i++) {
	      if (files[i].endsWith(".txt")) {
	    	  
	    	  try {
	    		  
	    		  double[] newInst = new double[2];
	    		  newInst[0] = (double)data.attribute(0).indexOfValue(pClass);
	    		  File txt = new File(directoryPath + "/" + files[i]);
	    		  InputStreamReader is;
	    		  is = new InputStreamReader(new FileInputStream(txt));
	    		  StringBuffer txtStr = new StringBuffer();
	    		  int c;
	    		  while ((c = is.read()) != -1) {
	    			  txtStr.append((char)c);
	    		  }
	    		  is.close();
	    		  String tx = new String(txtStr);
	    		  String tx1 = this.remove1(tx);
	    		  String tx2 = GetARFF.utf8aAscil(tx1);
	    		  tx2 = tx2.replaceAll("\\\'","'");
	    		  tx2 = tx2.replaceAll("\n", "");
	    		  tx2 = tx2.replaceAll("\\\"","");
	    		  tx2 = tx2.replaceAll("\'", "");
	    		  newInst[1] = (double)data.attribute(1).addStringValue(tx2);
	      //newInst[1] = (double)data.attribute(1).addStringValue(txtStr.toString());
	    		  data.add(new DenseInstance(1.0, newInst));
	    		 
	    	  } catch (Exception e) {
	    		  System.err.println("failed to convert file: " + directoryPath + "/" + files[i]);
	    }
	      }
	    }
	    return data;
	}

}