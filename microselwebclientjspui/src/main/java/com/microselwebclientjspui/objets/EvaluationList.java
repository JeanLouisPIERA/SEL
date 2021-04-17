package com.microselwebclientjspui.objets;

import java.util.ArrayList;
import java.util.List;

public class EvaluationList {

	private List<Evaluation> evaluations;

	public EvaluationList() {
		evaluations = new ArrayList<Evaluation>();

	}

	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

}
