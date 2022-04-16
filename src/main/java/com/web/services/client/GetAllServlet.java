package com.web.services.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.core.utils.Constant;
import com.core.utils.bean.Patient;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class GetAllServlet {

	public static void main(String[] args) {

		try {

			URL url = new URL(Constant.GET_ALL_URL + "/123");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(Constant.GET);
			conn.setRequestProperty(Constant.ACCEPT, Constant.APPLICATION_JSON);

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				Gson gson = new Gson();
				JsonElement je = gson.fromJson(output, JsonElement.class);
				Patient patient = gson.fromJson(je, Patient.class);

				System.out.println(patient.getEmail());
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
}
