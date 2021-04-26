package com.microselbourse.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

public interface IMailScheduler {

	

	/**
	 * Si la réalisation de l’échange n’est pas établie par au moins 1 avis (c’est-à-dire que le statut de l’échange est toujours en CONFIRME 
	 * et que l’avis des 2 adhérents n’est pas renseigné), le système : 
	 * • Passe le statut de l’échange en SUPPRIME 
	 * • Passe le statut de l’avis des 2 adhérents en ANOMALIE mais ne bloque pas leur accès à la bourse d’échange 
	 * • Aucun enregistrement de la transaction en unités de compte au débit ou au crédit de l’émetteur et du récepteur 
	 * Pour éviter cette situation à cause d’un « oubli», le système envoie un mail de rappel 48 heures avant la date d’échéance.
	 * @return
	 */
	void sendMailsEchangesASupprimer() throws MessagingException, UnsupportedEncodingException;
	
	
	public void sendMailsEchangesAForceValiderList() throws MessagingException, UnsupportedEncodingException;

	public void sendMailsEchangesAForceRefuserList() throws MessagingException, UnsupportedEncodingException;

}
