package com.web.services.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.core.utils.bean.Constant;
import com.core.utils.bean.Patient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PatientControllerServlet")
public class PatientControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");

			// if the command is missing, then default to listing patients
			if (theCommand == null) {
				theCommand = "LIST";
			}

			// route to the appropriate method
			switch (theCommand) {

			case "LIST":
				listPatients(request, response);
				break;

			case "ADD":
				addPatients(request, response);
				break;

			case "LOAD":
				loadPatient(request, response);
				break;

			case "UPDATE":
				updatePatient(request, response);
				break;

			case "DELETE":
				deletePatient(request, response);
				break;

			default:
				listPatients(request, response);
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	private void updatePatient(HttpServletRequest request, HttpServletResponse response) {

		try {

			URL url = new URL(Constant.REST_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(Constant.PUT);
			conn.setRequestProperty(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON);

			Patient patient = new Patient();
			patient.setName(request.getParameter(Constant.NAME));
			patient.setId(Long.valueOf(request.getParameter(Constant.PATIENT_ID)));
			patient.setEmail(request.getParameter(Constant.EMAIL));

			OutputStream os = conn.getOutputStream();
			os.write(new Gson().toJson(patient).toString().getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			// send back to main page (the patient list)
			listPatients(request, response);

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

		}

	}

	private void deletePatient(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			URL url = new URL(Constant.REST_URL + "/" + request.getParameter(Constant.PATIENT_ID));

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(Constant.DELETE);
			conn.setRequestProperty(Constant.ACCEPT, Constant.APPLICATION_JSON);

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			// send them back to "list patients" page
			listPatients(request, response);
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	private void loadPatient(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student id from form data
		String thePatientId = request.getParameter(Constant.PATIENT_ID);

		// get student from database (db util)

		try {

			URL url = new URL(Constant.REST_URL + "/" + thePatientId);

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

				// place patient in the request attribute
				request.setAttribute("THE_PATIENT", patient);

				// send to jsp page: update-student-form.jsp
				RequestDispatcher dispatcher = request.getRequestDispatcher("/update-patient-form.jsp");
				dispatcher.forward(request, response);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	private void addPatients(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			URL url = new URL(Constant.REST_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(Constant.POST);
			conn.setRequestProperty(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON);

			// read patient info from form data
			String name = request.getParameter(Constant.NAME);
			String email = request.getParameter(Constant.EMAIL);

			Patient patient = new Patient();
			patient.setName(name);
			patient.setEmail(email);

			OutputStream os = conn.getOutputStream();
			os.write(new Gson().toJson(patient).toString().getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		// send back to main page (the patient list)
		listPatients(request, response);

	}

	private void listPatients(HttpServletRequest request, HttpServletResponse response) throws Exception {

		URL url = new URL(Constant.REST_URL);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(Constant.GET);
		conn.setRequestProperty(Constant.ACCEPT, Constant.APPLICATION_JSON);

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		while ((output = br.readLine()) != null) {

			List<Patient> patients = new ArrayList<>();

			Gson gson = new Gson();
			JsonElement je = gson.fromJson(output, JsonElement.class);
			JsonArray ja = je.getAsJsonArray();
			for (int i = 0; i < ja.size(); i++) {
				Patient patient = gson.fromJson(ja.get(i), Patient.class);
				patients.add(patient);
			}

			// add patients to the request
			request.setAttribute("PATIENT_LIST", patients);

			// send to JSP page (view)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-patients.jsp");
			dispatcher.forward(request, response);

		}

		conn.disconnect();
	}

}
