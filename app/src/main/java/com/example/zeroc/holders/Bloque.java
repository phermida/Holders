package com.example.zeroc.holders;

import java.util.ArrayList;


public class Bloque implements  java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String tipo;
	private String texto;
	private ArrayList<String> opciones = new ArrayList<>();
	private ArrayList<String> opcionFin = new ArrayList<>();
	private ArrayList<Bloque> opcionSiguiente = new ArrayList<>();
	private Bloque siguiente;
	private String fin;
	
	
	public Bloque(String tipo) {
		this.tipo = tipo;
	}
	
	public void ejecutar(){
	
		switch (tipo) {
		case "decision":			
			int i = JOptionPane.showOptionDialog(null, texto, null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones.toArray(), null);
			siguiente = opcionSiguiente.get(i);
			fin = opcionFin.get(i);
			break;
		case "simple":
			JOptionPane.showMessageDialog(null, texto);
			siguiente = opcionSiguiente.get(0);
			fin = opcionFin.get(0);
			break;
		case "random":
			JOptionPane.showMessageDialog(null, texto);
			int random = (int) (Math.random() * opciones.size());
			siguiente = opcionSiguiente.get(random);
			fin = opcionFin.get(random);
			break;
		default:
			System.err.println("tipo de bloque erroneo");
			break;
		}
	}


	public void addOpcion(String opcion,Bloque siguienteBloque, String fin) {
		this.opciones.add(opcion);
		this.opcionFin.add(fin);
		this.opcionSiguiente.add(siguienteBloque);
	}
	
	public Bloque getSiguiente() {
		return siguiente;
	}

	public String getFin() {
		return fin;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
}
