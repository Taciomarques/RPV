package com.unipampa.padel.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestUtil {

	public static String retornaDados(String url) {

		try {

			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				System.out.println("Erro " + conn.getResponseCode() + " ao obter dados da URL " + url);
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output = "";
			String line;

			while ((line = br.readLine()) != null) {
				output += line;
			}

			conn.disconnect();

			return output;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
