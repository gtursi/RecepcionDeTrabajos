package commons.pref;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;

import recepciondetrabajos.Constants;

import commons.gui.table.ColumnInfo;
import commons.gui.util.FileHelper;
import commons.logging.AppLogger;
import commons.util.SbaStringUtils;

/**
 * Clase que Maneja el almacenamiento y carga de las preferencias visuales del Sistema.
 */
public class PreferencesManager {

	public static PreferencesManager getInstance() {
		if (instance == null) {
			instance = new PreferencesManager();
		}
		return instance;
	}

	public TableInfo getTableInfo(String tableName) {
		return this.defaultPreferences.getTableInfo(tableName);
	}

	public void setColumnInfo(String tableName, ColumnInfo columnInfo) {
		this.defaultPreferences.setColumnInfo(tableName, columnInfo);
		this.persistPreferences();// TODO hace falta persistir cada vez?
	}

	public void setTableInfo(String tableName, String order) {
		defaultPreferences.setTableInfo(tableName, order);
		this.persistPreferences();// TODO hace falta persistir cada vez?
	}

	/**
	 * Crea un manejador de preferencias, mergeando la información en el archivo de preferencias
	 * default. En caso de existir un archivo de preferencias del usuario, actualiza en default con
	 * las propiedades seteadas en el mismo.
	 * 
	 * @param userPreferenceFileName
	 *            Ubicación del archivo de preferencias del usuario
	 */
	private PreferencesManager() {
		this.userPreferenceFileName = SbaStringUtils.concat(FileHelper.OUTPUT_DIR,
				FileHelper.getFileSeparator(), Constants.PREFERENCES_FILE);

		this.loadDefaultPreferences();
		this.loadUserPreferences();
		persistPreferences();
	}

	/**
	 * Carga el archivo default de preferencias.
	 */
	private void loadDefaultPreferences() {
		InputStream inputStream = PreferencesManager.class
				.getResourceAsStream(Constants.PREFERENCES_FILE);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		this.defaultPreferences = PreferencesHelper.readPreferences(inputStreamReader, null);
	}

	/**
	 * Actualiza las preferencias por default con las preferencias del usuario. Al mantener la
	 * estructura del xml de preferencias por default y al tomar solo los atributos de las
	 * preferencias del usuario, se garantiza una la estructura consistente del archivo de
	 * preferencias.
	 */
	private void loadUserPreferences() {
		File newFile = new File(userPreferenceFileName);
		if (newFile.exists()) {
			AppLogger.getLogger().fine(
					"Se encontró el archivo de preferencias visuales del usuario en "
							+ userPreferenceFileName + ".");
			try {
				InputStream inputStream = new FileInputStream(userPreferenceFileName);
				InputStreamReader userInputSreamReader = new InputStreamReader(inputStream);
				this.defaultPreferences = PreferencesHelper.readPreferences(userInputSreamReader,
						this.defaultPreferences);
			} catch (FileNotFoundException e) {
				AppLogger.getLogger().severe(
						"No encontró archivo en ubicado en : " + userPreferenceFileName);
			}
		}
	}

	/**
	 * Persiste las preferencias mantenidas hasta el momento en el árbol de objetos
	 */
	private void persistPreferences() {
		OutputStream output = null;
		try {
			output = new FileOutputStream(this.userPreferenceFileName);
		} catch (FileNotFoundException e) {
			AppLogger.getLogger().log(Level.SEVERE, e.getMessage(), e);
			// nunca debería entrar ya que el mismo sino existe es creado.
		}
		PrintWriter printWriter = new PrintWriter(output);
		this.defaultPreferences.save(printWriter);
	}

	public void print(Preferences tree) {
		tree.save(new PrintWriter(System.out));
	}

	private Preferences defaultPreferences;

	// archivo xml
	private final String userPreferenceFileName;

	// instancia única para manejar cambios en las preferences
	private static PreferencesManager instance;

}
