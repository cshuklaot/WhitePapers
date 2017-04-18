/**
 *
 */
package com.emc.modules.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.lang.StringUtils;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfRuntimeException;

/**
 * Handles {@link com.spr.flux.common.generated.dm.RtProcessMsgs spr_rt_process_msgs}.
 */
public class MsgService {

	/**
	 * Initialize the identifier for the current process.
	 *
	 * @param formId
	 *            The form instance identifier.
	 */
	public MsgService(final String formId) {
		this.formIdForDQL = createFormIdForDQL(formId);
	}

	public void storeMessage(final String errormessage) throws DfException, SQLException, ClassNotFoundException {
		final String routineName = "storeMessage";
		try {
			insertMessageInTable(errormessage.length() > 2000 ? errormessage.substring(0, 1999) : errormessage);
		} catch (final DfException ex) {
			LOGGER.logIt(routineName + ex.getStackTraceAsString());
			throw new DfRuntimeException(ex.getMessage(), ex);
		}

	}

	public void insertMessageInTable(final String errormessage)
			throws DfException, SQLException, ClassNotFoundException {
		final String routineName = "insertDataTableRow";
		final String sql = MessageFormat.format(INSERT_INTO_DATA_MSG_FMT.get(), TABLE_NAME, formIdForDQL,
				errormessage, SDF.format(new java.util.Date()));
		System.out.println(sql);
		executeSQL(new SqlAction() {

			@Override
			public void execute(Statement stm) throws SQLException {
				stm.executeUpdate(sql);
				stm.close();
			}
		});
		LOGGER.logIt(routineName + " - sql executed");

	}

	public String getMessage() {
		try {

			final String sql = "select error_message from dbo." + TABLE_NAME + " where form_id = '" + formIdForDQL + "'";
			System.out.println(sql);
			final Mutable<String> message = new Mutable<>(StringUtils.EMPTY);
			executeSQL(new SqlAction() {

				@Override
				public void execute(Statement stm) throws SQLException {
					ResultSet rst = stm.executeQuery(sql);
					if (rst.next())
					{
						message.set(rst.getString("error_message"));
					}
					rst.close();
				}
			});
			LOGGER.logIt("\n\n" + message.get() + "\n\n\n\n\n");
			return message.get();
		} catch (final Exception exc) {
			throw new DfRuntimeException("Failed to get error message: " + exc.getMessage(), exc);
		}
	}

	private void executeSQL(SqlAction action) throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String db_connect_string = geturl();
		Connection conn = DriverManager.getConnection(db_connect_string,
				"corp", "D3m04doc");
		final Statement stm = conn.createStatement();
		action.execute(stm);
		conn.close();

	}

	private String geturl() {
		return "jdbc:sqlserver://10.118.140.18:1433;databaseName=DM_corp_docbase";
	}

	interface SqlAction
	{
		public void execute(final Statement stm) throws SQLException;
	}

	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

	/**
	 * Creates the form ID from a given ID. If the given ID is null or empty, a form ID is created from the calling instance (stacktrace - 2 levels, i.e. assuming this method is called from the
	 * constructor) and a time stamp. In any case single quotes are doubled, so the string can directly be used as a value in a DQL statement.
	 *
	 * @param formId
	 *            the form ID.
	 * @return the form ID for use in a DQL statement.
	 */
	private static String createFormIdForDQL(final String formId) {

		return getEscapedString(formId);

	}

	public static String getEscapedString(final String value) {
		return (value == null) ? "" : value.replaceAll("'", "''");
	}

	/** The Logger for this class. */
	private static final Logger LOGGER = new Logger(MsgService.class);

	private static final String TABLE_NAME = "error_messages";
	/**
	 * The form ID ready to be used in DQL statements. See {@link DQLUtil#getEscapedString(String)}.
	 */
	private final String formIdForDQL;

	private static final ThreadLocal<String> INSERT_INTO_DATA_MSG_FMT = new ThreadLocal<String>() {

		@Override
		protected String initialValue() {
			return "insert into dbo.{0} \n" + "(form_id, error_message,error_date) \n"
					+ "values(''{1}'', ''{2}'', ''{3}'') \n";
		}

	};

}
