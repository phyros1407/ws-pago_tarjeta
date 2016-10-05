package dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import beans.TarjetaBean;
import dao.factory.MySqlDAOFactory;
import dao.interfaces.servicesDAO;

public class servicesMySqlDAO extends MySqlDAOFactory implements servicesDAO {

	@Override
	public double buscarTarjeta(TarjetaBean tarjeta) {
		// TODO Auto-generated method stub

		double saldo = 0;

		try {

			Connection con = MySqlDAOFactory.obtenerConexion();
			Statement stmt = con.createStatement();

			String query = " SELECT saldo " + " FROM T_TARJETA " + " WHERE NUM_TAR = '" + tarjeta.getNum_tar()
					+ "' AND CVC = '" + tarjeta.getCvc() + "' AND ANO_CAD = " + tarjeta.getAno_cad() + " AND MES_CAD = "
					+ tarjeta.getMes_cad() + " ";

			System.out.println("QUERY EJECUTADO ----> " + query);

			ResultSet rs = stmt.executeQuery(query);

			if (!rs.isBeforeFirst()) {
				
				con.close();
				stmt.close();
				rs.close();
				saldo = -1;

			} else {

				if (rs.next()) {

					saldo = rs.getDouble("saldo");
					System.out.println("SALDO EN EL QUERY -----> " + saldo);
				}
				
				con.close();
				stmt.close();
				rs.close();

			}

			
			
		} catch (Exception e) {
			e.getMessage();
		}

		return saldo;
	}

	@Override
	public boolean realizarTransaccion(TarjetaBean tarjeta) {
		// TODO Auto-generated method stub

		boolean flag = false;

		try {

			Connection con = MySqlDAOFactory.obtenerConexion();
			Statement stmt = con.createStatement();

			String query = " UPDATE T_TARJETA " + " SET SALDO = " + tarjeta.getSaldo() + " " + " WHERE NUM_TAR = '"
					+ tarjeta.getNum_tar() + "'";

			System.out.println(query);

			int filas = stmt.executeUpdate(query);

			if (filas == 1) {
				flag = true;
			}

		} catch (Exception e) {
			e.getMessage();
		}
		return flag;
	}

}