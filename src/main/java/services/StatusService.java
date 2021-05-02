package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import api.MySQLJDBCUtil;
import models.Status;

public class StatusService {

	Connection connection;
	
	public StatusService() {
		// TODO Auto-generated constructor stub
		try {
			connection = MySQLJDBCUtil.getConnection();
			this.seed();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void seed() {
		List<Status> data = new ArrayList<>();
		
		Status st1 = new Status();
		st1.setStatusId("FREE");
		st1.setStatusName("FREE");
		st1.setStatusDescription("This denotes FREE instance.");
		st1.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		st1.setModifiedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		
		data.add(st1);
		
		Status st2 = new Status();
		st2.setStatusId("INUSE");
		st2.setStatusName("INUSE");
		st2.setStatusDescription("This denotes INUSE instance.");
		st2.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		st2.setModifiedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		
		data.add(st2);
		
		String INSERT_STATUS_SQL = "INSERT INTO status" + "  (id, name, description, createdAt, modifiedAt ) VALUES " +
        " (?, ?, ?, ?, ?);";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STATUS_SQL); 
            connection.setAutoCommit(false);
            for (Iterator < Status > iterator = data.iterator(); iterator.hasNext();) {
                Status status = (Status) iterator.next();
                preparedStatement.setString(1, status.getStatusId());
                preparedStatement.setString(2, status.getStatusName());
                preparedStatement.setString(3, status.getStatusDescription());
                preparedStatement.setTimestamp(4, status.getCreatedAt());
                preparedStatement.setTimestamp(5, status.getModifiedAt());
                preparedStatement.addBatch();
            }
            int[] updateCounts = preparedStatement.executeBatch();
            System.out.println(Arrays.toString(updateCounts));
            connection.commit();
            connection.setAutoCommit(true);
		} catch (Exception e) {
			// TODO: handle exception
			printSQLException(e);
		}
		
	}
	
	public static void printSQLException(Exception e2) {
            if (e2 instanceof SQLException) {
                e2.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e2).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e2).getErrorCode());
                System.err.println("Message: " + e2.getMessage());
                Throwable t = e2.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
    }
	
	public static void printBatchUpdateException(BatchUpdateException b) {

        System.err.println("----BatchUpdateException----");
        System.err.println("SQLState:  " + b.getSQLState());
        System.err.println("Message:  " + b.getMessage());
        System.err.println("Vendor:  " + b.getErrorCode());
        System.err.print("Update counts:  ");
        int[] updateCounts = b.getUpdateCounts();

        for (int i = 0; i < updateCounts.length; i++) {
            System.err.print(updateCounts[i] + "   ");
        }
    }

}
