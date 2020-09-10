
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.model.Etapa;
import com.unipampa.padel.model.Inscricao;

public class HttpURLConnectionExample {

	public static void main(String[] args) throws Exception {

		HttpURLConnectionExample obj = new HttpURLConnectionExample();

		System.out.println("Testing 1 - Send Http GET request");
		obj.getDuplas();

//        System.out.println("Testing 2 - Send Http POST request");
//        obj.sendPost();

	}
	/*
	 * private void sendGet() throws Exception {
	 * 
	 * // String url = "https://www.google.com/search?q=mkyong";
	 * 
	 * String url = "http://localhost:8080/api/duplas";
	 * 
	 * HttpURLConnection httpClient = (HttpURLConnection) new
	 * URL(url).openConnection();
	 * 
	 * // optional default is GET httpClient.setRequestMethod("GET");
	 * 
	 * //add request header httpClient.setRequestProperty("Content-Type",
	 * "application/json");
	 * 
	 * httpClient.setDoOutput(true);
	 * 
	 * int responseCode = httpClient.getResponseCode();
	 * System.out.println("\nSending 'GET' request to URL : " + url);
	 * System.out.println("Response Code : " + responseCode);
	 * 
	 * try (BufferedReader in = new BufferedReader( new
	 * InputStreamReader(httpClient.getInputStream()))) {
	 * 
	 * // String line; ArrayList<Dupla> duplas = new ArrayList<>();
	 * 
	 * // while((line = in.readLine()) != null) { // Dupla dupla = new Dupla(); //
	 * dupla = (Dupla) jaxbUnmarshaller.unmarshal(in); // duplas.add(dupla); // }
	 * 
	 * // StringBuilder response = new StringBuilder(); String line; // Gson gson =
	 * new Gson(); while ((line = in.readLine()) != null) { //
	 * response.append(line); Dupla dupla = new Dupla(); dupla = gson.fromJson(line,
	 * Dupla.class); duplas.add(dupla); System.out.println(dupla.getNome()); }
	 * 
	 * //print result // System.out.println(response.toString()); // for (Dupla ds :
	 * duplas) {
	 * 
	 * // System.out.println(ds.getNome()); // //
	 * System.out.println(dupla.getNome());
	 * 
	 * // }
	 * 
	 * 
	 * }
	 * 
	 * }
	 */

	private ArrayList<Inscricao> getDuplas() {

		try {

			String url = "http://localhost:8080/api/inscricoes";

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

//            Gson gson = new Gson();
//            Dupla dupla = gson.fromJson(new String(output.getBytes()), Dupla.class);

//            JSONArray response_json = new JSONArray (output);
//            ObjectMapper mapper = new ObjectMapper();
//            List<Object> listTest = mapper.readValue(String.valueOf(response_json), List.class);

//            for(Object o : listTest) {
//            	Dupla d = (Dupla)o;
//            	System.out.println(d.getNome());
//            }

//			String json = new Gson().toJson(output);
			
//			System.out.println(output);
			
			ArrayList<Inscricao> listaInscritos = new ArrayList<Inscricao>();

			JsonParser jsonParser = new JsonParser();
			JsonArray jsonArray = (JsonArray)jsonParser.parse(output);
			

			
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
			for (int i=0;i<jsonArray.size();i++) {

				JsonObject e = (JsonObject) jsonArray.get(i);
				System.out.println(e);
				Inscricao insc = new Inscricao();
				Dupla dupla = new Dupla();
				Etapa etapa = new Etapa();	
//				Date data = formato.parse(e.get("horainsc").toString());
//				insc.setHorainsc(data);
				dupla.setId(Integer.parseInt(e.get("dupla1").toString()));
				etapa.setId(Integer.parseInt(e.get("etapa1").toString()));
				insc.setEtapa1(etapa);
				
				listaInscritos.add(insc);
				

			}
			
			System.out.println(listaInscritos.size());
			for(Inscricao inscrito : listaInscritos) {
				for(Dupla d : retornaDuplas()) {
					if(inscrito.getDupla1().getId() == d.getId()) {
						inscrito.getDupla1().setNome(d.getNome());
						if(d.isSuplente()) {
							inscrito.getDupla1().setSuplente(true);
						}else {
							inscrito.getDupla1().setSuplente(false);
						}
						inscrito.getDupla1().setPontosRank(d.getPontosRank());
						Categoria cat = new Categoria();
						cat.setId(d.getCategoria().getId());
						inscrito.getDupla1().setCategoria(cat);
						
//						inscrito.setDupla1(d);
						
					}
				}
			}
			
			
			
//			
			for (Inscricao inscritos : listaInscritos) {
				System.out.println("Id: " + inscritos.getDupla1().getId());
				System.out.println("Nome: " + inscritos.getDupla1().getNome());
			}
			return listaInscritos;
			
		} catch (Exception e) {
			
			return null;
			
		}
	}
	
	private ArrayList<Dupla> retornaDuplas() {

		try {

			String url = "http://localhost:8080/api/duplas";

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

//            Gson gson = new Gson();
//            Dupla dupla = gson.fromJson(new String(output.getBytes()), Dupla.class);

//            JSONArray response_json = new JSONArray (output);
//            ObjectMapper mapper = new ObjectMapper();
//            List<Object> listTest = mapper.readValue(String.valueOf(response_json), List.class);

//            for(Object o : listTest) {
//            	Dupla d = (Dupla)o;
//            	System.out.println(d.getNome());
//            }

//			String json = new Gson().toJson(output);

			ArrayList<Dupla> listaDupla = new ArrayList<Dupla>();

			JsonParser jsonParser = new JsonParser();
			JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

			int i = 0;
			for (i = 0; i < jsonArray.size(); i++) {

				JsonObject e = (JsonObject) jsonArray.get(i);
//				System.out.println(e.get("nome"));
				Dupla dupla = new Dupla();
				dupla.setId(Integer.parseInt(e.get("id").toString()));
				dupla.setNome(e.get("nome").toString());
				Categoria cat = new Categoria();
//				JsonObject c = (JsonObject) e.get("categoria");
				cat.setId(Integer.parseInt(e.get("categoria").toString()));
//				cat.setId(Integer.parseInt(c.get("id").toString()));
//				cat.setNome(c.get("nome").toString());
				dupla.setCategoria(cat);
				dupla.setImpedimento(e.get("impedimento").toString());
				if(e.get("suplente").toString().equalsIgnoreCase("true")) {
					dupla.setSuplente(true);
				}else {
					dupla.setSuplente(false);
				}
				dupla.setPontosRank(Integer.parseInt(e.get("pontosRank").toString()));
				listaDupla.add(dupla);

			}

//			System.out.println(listaDupla.size());
//			
//			for (Dupla d : listaDupla) {
//				System.out.println("Id: " + d.getId());
//				System.out.println("Nome: " + d.getNome());
//			}

			return listaDupla;

		} catch (Exception e) {

			return null;

		}
	}

	private void sendPost() throws Exception {

		// url is missing?
		// String url = "https://selfsolve.apple.com/wcResults.do";
//        String url = "https://httpbin.org/post";
		String url = "http://localhost:8080/api/dupla";

		HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

		// add reuqest header
		httpClient.setRequestMethod("POST");
//        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
//        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

//        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
		String urlParameters = "nome=tacio/test&cpf=00898367077&numCel=997021192&email=taciomarques@gmail.com";

		// Send post request
		httpClient.setDoOutput(true);
		try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
			wr.writeBytes(urlParameters);
			wr.flush();
		}

		int responseCode = httpClient.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		try (BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()))) {

			String line;
			StringBuilder response = new StringBuilder();

			while ((line = in.readLine()) != null) {
				response.append(line);
			}

			// print result
			System.out.println(response.toString());

		}

	}

}
