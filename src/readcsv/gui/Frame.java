/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readcsv.gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import readcsv.ImportCsv;

/**
 *
 * @author Julian
 */
public class Frame extends JFrame implements ActionListener {

    private Container contenedor;
    JLabel labelTitulo;/*declaramos el objeto Label*/
    JTextField rutaArchivo;
    JButton botonExaminar;/*declaramos el objeto Boton*/
    JButton botonProcesar;/*declaramos el objeto Boton*/
  
    JFileChooser fileChooser;


    public Frame()//constructor
    {
        contenedor = getContentPane();
        contenedor.setLayout(null);

        /*Creamos el objeto*/
        fileChooser = new JFileChooser();

        /*Propiedades del Label, lo instanciamos, posicionamos y
			 * activamos los eventos*/
        labelTitulo = new JLabel();
        labelTitulo.setText("Escoger Archivo");
        labelTitulo.setBounds(110, 20, 180, 23);
        
        rutaArchivo = new JTextField();
        rutaArchivo.setText("");
        rutaArchivo.setBounds(110, 40, 220, 23);

        /*Propiedades del boton, lo instanciamos, posicionamos y
			 * activamos los eventos*/
        botonExaminar = new JButton();
        botonExaminar.setText("Examinar");
        botonExaminar.setBounds(100, 130, 90, 23);
        botonExaminar.addActionListener(this);

        botonProcesar = new JButton();
        botonProcesar.setText("Procesar Archivo");
        botonProcesar.setBounds(220, 130, 90, 23);
        botonProcesar.addActionListener(this);
        
        

        /*Agregamos los componentes al Contenedor*/
        contenedor.add(labelTitulo);
        contenedor.add(rutaArchivo);
        contenedor.add(botonExaminar);
        contenedor.add(botonProcesar);
        //Asigna un titulo a la barra de titulo
        setTitle("Seleccione el archivo");
        //tamaño de la ventana
        setSize(400, 200);
        //pone la ventana en el Centro de la pantalla
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == botonExaminar) {
            File archivo = abrirArchivo();
            rutaArchivo.setText(archivo.getAbsolutePath());
        }

        if (evento.getSource() == botonProcesar) {
            if(rutaArchivo != null && !rutaArchivo.getText().equals("")){
                ImportCsv claseImportar = new ImportCsv(rutaArchivo.getText());
                claseImportar.readCsv();
            }
        }
    }

    /**
     * Permite mostrar la ventana que carga archivos en el area de texto
     *
     * @return
     */
    private File abrirArchivo() {

        try {
            /*llamamos el metodo que permite cargar la ventana*/
            fileChooser.showOpenDialog(this);
            /*abrimos el archivo seleccionado*/

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex + "Error al cargar archivo");
        }
        return fileChooser.getSelectedFile();
    }
}
