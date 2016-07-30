package readcsv;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.opencsv.CSVReader;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * Esta clase se encarga de Leer el archivo en formato CSV e insertar cada linea
 * en la BD Mysql
 */
public class ImportCsv {

    private static final char DELIMETER = ';';
    private static final String LINE_ENDING = System.lineSeparator();
    private static final String PATH_FILE = "C:/upload.csv";

    private String rutaArchivo;
    private Connection connection;

    /*public static void main(String[] args) {
        readCsv();
        readCsvUsingLoad();
    }*/
    public void readCsv() throws SQLException {

        PreparedStatement pstmt = null;
        try (CSVReader reader = new CSVReader(new FileReader(this.rutaArchivo), DELIMETER);) {
            connection = DBConnection.getConnection();
            //connection.setAutoCommit(false);
            String insertQuery = "Insert into registro_epq (ID_CUENTA, PAIS, "
                    + "DEPARTAMENTO, MUNICIPIO, SECTOR, SUBSECTOR, ID_RUTA, "
                    + "CARRERA, CALLE, NRO_1, PISO, APTO, LOCAL, NRO_2, BARRIO, "
                    + "MANZANA, NRO_3, PISO_2, APTO_2, LOCAL_2, VEREDA, FINCA, "
                    + "CONJUNTO, NRO_4, PROPIETARIO, CEDULA, LECTURA_ANTERIOR, "
                    + "CONSUMO, CONSUMO1, CONSUMO2, CONSUMO3, CONSUMO4, CONSUMO5, "
                    + "CONSUMO6, PROMEDIO_CONSUMO, ID, MES, FECHA_CREACION ) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, "
                    + "null, ?, ?)";
            pstmt = connection.prepareStatement(insertQuery);
            String[] rowData = null;
            int j = 0;
            while ((rowData = reader.readNext()) != null) {
                if (j != 0) {
                    int i = 1;
                    for (String data : rowData) {
                        // Campos del Archivo
                        if (i == 1 || i == 13 || (i > 27 && i < 36)) {
                            if ("".equals(data)) {
                                pstmt.setNull(i, Types.INTEGER);
                            } else {
                                pstmt.setInt(i, Integer.parseInt(data));
                            }
                        } else {
                            pstmt.setString(i, data);
                        }
                        i++;
                    }

                    java.util.Date d = new java.util.Date();
                    int year = d.getYear() + 1900;
                    int mes = d.getMonth() + 1;
                    String mesStr = "";
                    if (mes < 10) {
                        mesStr = "0" + mes;
                    } else {
                        mesStr = String.valueOf(mes);
                    }
                    // Campos adicionales
                    pstmt.setString(36, "" + year + mesStr); // MES
                    pstmt.setDate(37, new Date(d.getTime()));

                    pstmt.execute();
                }
                /*if (j == 10)// insert when the batch size is 10
                {
                    pstmt.executeBatch();
                    j = 0;
                    connection.commit();
                }*/
                j++;
            }
            //connection.commit();
            System.out.println("Data Successfully Uploaded");
        } catch (Exception e) {
            System.err.println("Ocurrio el siguiente error tratando de insertar"
                    + e.getMessage());
            //connection.rollback();
        } /*finally {

            if (pstmt != null) {
                pstmt.close();
            }

            if (connection != null) {
                connection.close();
            }

        }*/

    }

    /*public void readCsvUsingLoad() {
        try (Connection connection = DBConnection.getConnection()) {

            String loadQuery = "LOAD DATA LOCAL INFILE '" + this.rutaArchivo + "' "
                    + "INTO TABLE txn_tbl FIELDS TERMINATED BY '"+ DELIMETER + "' "
                    + " LINES TERMINATED BY '"+ LINE_ENDING + "' "
                    + "(txn_amount, card_number, terminal_id) ";
            System.out.println(loadQuery);
            Statement stmt = connection.createStatement();
            stmt.execute(loadQuery);           
        } catch (Exception e) {
            System.err.println("Ocurrio el siguiente error tratando de insertar"
                    + e.getMessage());
        }
    }*/
    public ImportCsv(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

}
