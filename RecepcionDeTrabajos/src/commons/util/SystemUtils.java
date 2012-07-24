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
		// cmd.exe => Inicia una nueva instancia del intérprete de comandos de Windows.
		cmdArgs[cmdArgIndex++] = WIN_CMD;
		// /C => Ejecuta el comando especificado en cadena y luego finaliza
		cmdArgs[cmdArgIndex++] = "/c";
		// Start => Inicia una ventana aparte para ejecutar un programa o un comando especificado.
		cmdArgs[cmdArgIndex++] = "start";
		// "título" => Texto que se mostrará en la barra de título de la ventana.
		// IMPORTANTE: Se debe poner un título vacío, porque sino podría confundir el siguiente
		// parámetro (comando/nombre de archivo) con el título (en caso que deba hacerse "escape"
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
	 * En Windows 95/98 el intérprete de comandos es "COMMAND.COM". En los Windows posteriores es
	 * "cmd.exe"
	 */
	private static final String WIN_CMD = System.getProperty("os.name").toUpperCase()
			.startsWith("WINDOWS 9") ? "COMMAND.COM" : "cmd.exe";
}
