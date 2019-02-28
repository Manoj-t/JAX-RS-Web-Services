package com.manoj.restws.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import com.manoj.restws.model.Patient;
import com.manoj.restws.services.PatientService;
import com.manoj.restws.services.Exception.PatientBusinessException;

@Service
public class PatientServiceImpl implements PatientService {

	Map<Integer, Patient> patients = new HashMap<Integer, Patient>();
	int currentId = 123;

	public PatientServiceImpl() {
		init();
	}

	public void init() {
		Patient patient = new Patient();
		patient.setId(currentId);
		patient.setName("John");
		patients.put(patient.getId(), patient);
	}
	
	
	public List<Patient> getPatients() {
		Collection<Patient> results = patients.values();
		List<Patient> response = new ArrayList<Patient>(results);
		return response;
	}

	@Override
	public Patient getPatient(int id) {
		if(patients.get(id) == null)
		{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
		return patients.get(id);
	}

	@Override
	public Response createPatient(Patient patient) {
		patient.setId(++currentId);
		patients.put(patient.getId(), patient);
		return Response.ok(patient).build();
	}

	@Override
	public Response updatePatient(Patient patient) {

		Response response;

		Patient currentPatient = patients.get(patient.getId());

		if (currentPatient != null) {
			patients.put(patient.getId(), patient);
			response = Response.ok().build();
		} else {
			throw new PatientBusinessException();
		}
		return response;
	}

	@Override
	public Response deletePatient(int id) {
		Response response;
		Patient patient = patients.get(id);
		if (patient != null) {
			patients.remove(id);
			response = Response.ok().build();
		} else {
			response = Response.notModified().build();
		}
		return response;
	}

}
