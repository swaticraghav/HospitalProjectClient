package com.web.services.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.core.utils.Constant;

public class GetAll {

	public static void main(String[] args) {

		try {

			URL url = new URL(Constant.GET_ALL_URL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(Constant.GET);
			conn.setRequestProperty(Constant.ACCEPT, Constant.APPLICATION_JSON);

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
}
