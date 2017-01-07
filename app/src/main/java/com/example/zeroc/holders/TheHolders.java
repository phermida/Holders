package com.example.zeroc.holders;

import android.app.Activity;
import android.os.Bundle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TheHolders extends Activity {

	private static String[] titulos= {"The Holder of the Ambition","The Holder of the End","The Holder of the Present","The Holder of the Rage"};
	private HashMap<String,Historia> historias = new HashMap<>();
	private ArrayList<String> historiasPendientes = new ArrayList<>();
	private boolean modoSurvive = true;
	private List<Puntuacion> puntuaciones = new ArrayList<>();
	private String nombre;
	private MainActivity mainActivity;

	public TheHolders(){
		for (String titulo : titulos)
			historiasPendientes.add(titulo);
		cargarHistorias();
		System.out.println("ESTOU EJECUTANDOME MUY FUERTE");
	}

	public TheHolders(MainActivity mainActivity) {
		for (String titulo : titulos) 
			historiasPendientes.add(titulo);
		cargarHistorias();
		this.mainActivity = mainActivity;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Indica el layout para esta actividad.
		// El archivo de layout se guarda en res/layout/layout_actividad2.xml
		this.setContentView( R.layout.install);
		System.out.println("Iniciando Instalador");
		iniciarJuego();
		finish();
	}

	public void addHistoria(String titulo) {
		Historia aux = new Historia(titulo,mainActivity);
		try {
			aux = readHist(aux);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		historias.put(titulo, aux);
	}
	public void cargarHistorias() {
		for (String string : historiasPendientes) {
			addHistoria(string);
		}
	}
	
	public String start(){
		int random = (int) (Math.random()*historiasPendientes.size());
		Historia hActual = historias.get(historiasPendientes.get(random));
		hActual.ejecutar();
		if(modoSurvive && hActual.getFin().equals("muerto")){
			for (String titulo : titulos) 
				historiasPendientes.add(titulo);
		}
		if(hActual.getFin().equals("vivo")){
			historiasPendientes.remove(hActual.getTitulo());
		}	
		return hActual.getFin();
	}
	
	
	public void guardar(){
		//guardar historiasPendientes
		//guardar modoSurvive
	}
	public void recuperar() {
		//cargar historiasPendientes
		//cargar modoSurvive
	}
	
	public String getNombre(){
		return nombre;
	}
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public void iniciarJuego() {
		//JOptionPane.showMessageDialog(null, "Bienvenido a The Holders");
		System.out.println("DENTRO DE INICIAR JUEGO");
		while(!historiasPendientes.isEmpty()){
			System.out.println("DENTRO DE BUCLE ANTES DE START");
			start();
			//JOptionPane.showMessageDialog(null, "Tienes "+(titulos.length-historiasPendientes.size())+" Holders de "+titulos.length+", nunca deben ser reunidos.");
		}
		puntuaciones.add(new Puntuacion(nombre, titulos.length-historiasPendientes.size()));
	}
	
	public void ordenarPorFecha(){
		 Collections.sort(puntuaciones, new Comparator<Puntuacion>(){
			 public int compare(Puntuacion object1, Puntuacion object2) {
			        return object1.getFecha().compareTo(object2.getFecha());
			    }
		 });
	}
	
	public void ordenarPorNombre(){
		
		 Collections.sort(puntuaciones, new Comparator<Puntuacion>(){
			 public int compare(Puntuacion object1, Puntuacion object2) {
			        return object1.getNombre().compareTo(object2.getNombre());
			    }
		 });
	}
	
	private void setSurvive(boolean survival) {
		modoSurvive= survival;
	}

	private Historia readHist(Historia h) throws IOException, ClassNotFoundException {

		//Creamos un fujo de entrada a disco, pas�ndole el nombre del archivo en disco o un objeto de la clase File.
		FileInputStream fileIn= this.openFileInput(h.getTitulo()+".holder");

		//El fujo de entrada ObjectInputStream es el que procesa los datos y se ha de vincular a un objeto fileIn de la clase FileInputStream.
		ObjectInputStream entrada= new ObjectInputStream(fileIn);

		//El método readObject lee los objetos del flujo de entrada, en el mismo orden en el que ha sido escritos.
		Object aux = entrada.readObject();

		//Finalmente, se cierra los flujos
		entrada.close();
		if( aux instanceof Historia){
			return (Historia)aux;

		}else
			return null;

		// System.out.println("LEIDO");

	}


}


