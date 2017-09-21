package ejb.importaDatos;

import UtilsGeneral.ConfiguracionControl;
import entities.persistence.entities.Unidad;
import exceptions.ImportarExcelException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author Daniel
 */
public class ImportarDatosExcel {

    int contador;
    ConfiguracionControl cc;
    List<Unidad> lista;
    int id;

    public ImportarDatosExcel() {

        contador = 1;
        cc = new ConfiguracionControl();
        lista = new ArrayList();
        id = ConfiguracionControl.traeUltimoId("Unidad");

    }

    public List<Unidad> ImportarUnidadesDeExcel(File file) throws ImportarExcelException {
        try {
            Workbook ArchivoExcel = Workbook.getWorkbook(file);
            for (int hojas = 0; hojas < ArchivoExcel.getNumberOfSheets(); hojas++) {
                Sheet hoja = ArchivoExcel.getSheet(hojas);
                int numColumnas = hoja.getColumns();
                int numFilas = hoja.getRows();
                String dato;
                for (int fila = 1; fila < numFilas; fila++) {
                    Unidad uni = new Unidad();
                    for (int columna = 0; columna < numColumnas; columna++) {
                        dato = hoja.getCell(columna, fila).getContents();
                        uni = cargaUnidad(contador, uni, dato);
                    }
                    lista.add(uni);
                }
            }
        } catch (IOException | IndexOutOfBoundsException | ParseException | BiffException ex) {
            throw new ImportarExcelException(ex.getMessage());
            
        }
        return lista;
    }

    public Unidad cargaUnidad(int elcontador, Unidad uni, String dato) throws ParseException {
        switch (elcontador) {
            case 1:
                uni.setIdUnidad(id);
                id++;
                contador++;
                break;
            case 2:
                uni.setBlock(dato);
                contador++;
                break;
            case 3:
                uni.setTorre(Integer.parseInt(dato));
                contador++;
                break;
            case 4:
                uni.setPuerta(Integer.parseInt(dato));
                contador++;
                break;
            case 5:
                uni.setHabitaciones(Integer.parseInt(dato));
                contador++;
                break;    
            case 6:
                uni.setNombre(dato);
                contador++;
                break;
            case 7:
                uni.setApellido(dato);
                contador++;
                break;
            case 8:
                uni.setTelefono(Integer.valueOf(dato));
                contador++;
                break;
            case 9:
                uni.setMail(dato);
                contador++;
                System.out.println("mail>>>>>>" + dato);
                break;
            case 10:
                if (dato.equals("1")) {
                    uni.setPropietarioInquilino(true);
                } else {
                    uni.setPropietarioInquilino(false);
                }
                contador++;
                break;
            case 11:
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                uni.setFechaIngreso(formatter.parse(dato));
                contador++;
                break;
            case 12:
                if (dato.equals("1")) {
                    uni.setEsEdificio(true);
                } else {
                    uni.setEsEdificio(false);
                }
                contador++;
                break;
            case 13:
                if (dato.equals("1")) {
                    uni.setActivo(true);
                } else {
                    uni.setActivo(false);
                }
                contador = 1;
                break;
        }
        return uni;
    }
}