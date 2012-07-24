package commons.util;

import java.io.File;
import java.io.IOException;

public abstract class SystemUtils {

	/*
	 * TODO: Por ahora solo sirve para Windows.
	 */

	public static void exec(String command, final String... args) throws IOException,
			InterruptedException {
		String[] cmdArgs = new String[5 + args.length];
		int cmdArgIndex = 0;
		// cmd.exe => Inicia una nueva instancia del int�rprete de comandos de Windows.
		cmdArgs[cmdArgIndex++] = WIN_CMD;
		// /C => Ejecuta el comando especificado en cadena y luego finaliza
		cmdArgs[cmdArgIndex++] = "/c";
		// Start => Inicia una ventana aparte para ejecutar un programa o un comando especificado.
		cmdArgs[cmdArgIndex++] = "start";
		// "t�tulo" => Texto que se mostrar� en la barra de t�tulo de la ventana.
		// IMPORTANTE: Se debe poner un t�tulo vac�o, porque sino podr�a confundir el siguiente
		// par�metro (comando/nombre de archivo) con el t�tulo (en caso que deba hacerse "escape"
		// por contener espacios en blanco.)
		cmdArgs[cmdArgIndex++] = "\"\"";
		cmdArgs[cmdArgIndex++] = command;
		for (String arg : args) {
			cmdArgs[cmdArgIndex++] = arg;
		}
		Runtime.getRuntime().exec(cmdArgs).waitFor();
	}

	public static void showFile(String fileName) throws IOException, InterruptedException {
		fileName = SbaStringUtils.concat("\"", new File(fileName).getCanonicalPath(), "\"");
		String[] args = { WIN_CMD, "/c", "start", "\"\"", fileName };
		Runtime.getRuntime().exec(args).waitFor();
	}

	/**
	 * En Windows 95/98 el int�rprete de comandos es "COMMAND.COM". En los Windows posteriores es
	 * "cmd.exe"
	 */
	private static final String WIN_CMD = System.getProperty("os.name").toUpperCase()
			.startsWith("WINDOWS 9") ? "COMMAND.COM" : "cmd.exe";
}
