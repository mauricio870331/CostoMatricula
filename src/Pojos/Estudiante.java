/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import Vistas.FormCostoMatricula;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author clopez
 */
public class Estudiante {

    private float promedio;
    private int materias;
    private String txtNombre;
    private String telefono;
    private int edad;
    static SimpleDateFormat sf = new SimpleDateFormat("hh:mm:ss");

    public Estudiante(float promedio, int materias, String txtNombre, String telefono, int edad) {
        this.promedio = promedio;
        this.materias = materias;
        this.txtNombre = txtNombre;
        this.telefono = telefono;
        this.edad = edad;
    }

    public Estudiante() {
    }

    public String guardar() {
        String mensaje = "Error al guardar el Estudiante";
        String ruta = new File("").getAbsolutePath() + "\\src\\Modelo\\";
        File archivo = new File(ruta + "BDEstudiantes.txt");
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
                try (FileWriter escribir = new FileWriter(archivo, true)) {
                    escribir.write(sf.format(new Date()) + "," + getTxtNombre() + "," + getEdad() + "," + getTelefono() + "," + getMaterias() + "," + getPromedio() + "\n");
                    escribir.close();
                }
            } else {
                try (FileWriter escribir = new FileWriter(archivo, true)) {
                    escribir.write(sf.format(new Date()) + "," + getTxtNombre() + "," + getEdad() + "," + getTelefono() + "," + getMaterias() + "," + getPromedio() + "\n");
                    escribir.close();
                }
            }
            mensaje = "Estudiante Guardado";
        } catch (IOException ex) {
            System.out.println("error al crear el archivo " + ex.getMessage());
        }
        return mensaje;
    }

    public void mostrar() {
        String ruta = new File("").getAbsolutePath() + "\\src\\Modelo\\BDEstudiantes.txt";
        File archivo = new File(ruta);
        String usuario = "";
        if (archivo.exists()) {
            DefaultTableModel modelo;
            String Titulos[] = {"#", "Nombre", "Edad", "telefono", "Materias", "Promedio", "Costo Matricula"};
            modelo = new DefaultTableModel(null, Titulos) {
                @Override
                public boolean isCellEditable(int row, int column) { //para evitar que las celdas sean editables
                    return false;
                }
            };
            Object[] columna = new Object[7];
            try {
                FileReader lector = new FileReader(ruta);
                BufferedReader contenido = new BufferedReader(lector);
                while ((usuario = contenido.readLine()) != null) {
                    String datos[] = usuario.split(",");
                    columna[0] = datos[0];
                    columna[1] = datos[1];
                    columna[2] = datos[2];
                    columna[3] = datos[3];
                    columna[4] = datos[4];
                    columna[5] = datos[5];
                    columna[6] = "$ " + calcularCosto(Float.parseFloat(datos[5]), Integer.parseInt(datos[4]));
                    modelo.addRow(columna);
                }
                FormCostoMatricula.tblListUsers.setModel(modelo);
                FormCostoMatricula.tblListUsers.getColumnModel().getColumn(0).setMaxWidth(0);
                FormCostoMatricula.tblListUsers.getColumnModel().getColumn(0).setMinWidth(0);
                FormCostoMatricula.tblListUsers.getColumnModel().getColumn(0).setPreferredWidth(0);
            } catch (IOException ex) {
                System.out.println("error =" + ex.getMessage());
            }
        }
    }

    public float getPromedio() {
        return promedio;
    }

    public void setPromedio(float promedio) {
        this.promedio = promedio;
    }

    public int getMaterias() {
        return materias;
    }

    public void setMaterias(int materias) {
        this.materias = materias;
    }

    public String getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(String txtNombre) {
        this.txtNombre = txtNombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    private float calcularCosto(float pormedio, int materias) {
        int costo = 0;
        int iva = 0;
        if (pormedio >= 4.5) {
            costo = (materias * 5000);
            costo = (int) (costo * 0.3);
        }
        if (pormedio < 4.5) {
            costo = (materias * 5000);
            System.out.println("costo =" + costo);
            iva = (int) (costo * 0.1);
            System.out.println("iva =" + iva);
            costo += iva;
            System.out.println("costo total =" + costo);
        }
        return costo;
    }

}
