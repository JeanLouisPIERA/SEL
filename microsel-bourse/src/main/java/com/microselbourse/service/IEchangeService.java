package com.microselbourse.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microselbourse.beans.UserBean;
import com.microselbourse.criteria.EchangeCriteria;
import com.microselbourse.entities.Echange;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface IEchangeService {

	Echange createEchange(long id) throws EntityAlreadyExistsException, EntityNotFoundException;

	Echange readEchange(Long id) throws EntityNotFoundException;

	Page<Echange> searchAllEchangesByCriteria(EchangeCriteria echangeCriteria, Pageable pageable);

	Echange refuserEchangeEmetteur(@Valid Long id, Echange echangeToRefuse, UserBean emetteur, UserBean recepteur) throws EntityNotFoundException, DeniedAccessException,
			UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException;

	Echange validerEchangeEmetteur(@Valid Long id, Echange echangeToValidate, UserBean emetteur, UserBean recepteur) throws EntityNotFoundException, DeniedAccessException,
			UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException;

	Echange refuserEchangeRecepteur(@Valid Long id, Echange echangeToRefuse, UserBean emetteur, UserBean recepteur) throws EntityNotFoundException, DeniedAccessException,
			UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException;

	Echange validerEchangeRecepteur(@Valid Long id, Echange echangeToValidate, UserBean emetteur, UserBean recepteur) throws EntityNotFoundException, DeniedAccessException,
			UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException;

	Echange annulerEchange(@Valid Long id, String intervenantId)
			throws UnsupportedEncodingException, MessagingException, DeniedAccessException, EntityNotFoundException;


	Echange confirmerEchange(@Valid Long id, String intervenantId)
			throws UnsupportedEncodingException, MessagingException, DeniedAccessException, EntityNotFoundException;

	Echange validerEchange(@Valid Long id, String intervenantId) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, EntityAlreadyExistsException, MessagingException;

	Echange refuserEchange(@Valid Long id, String intervenantId) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, EntityAlreadyExistsException, MessagingException;

	

	/**
	 * Si la réalisation de l’échange n’est pas établie par au moins 1 avis (c’est-à-dire que le statut de l’échange est toujours en CONFIRME 
	 * et que l’avis des 2 adhérents n’est pas renseigné), le système : 
	 * • Passe le statut de l’échange en SUPPRIME 
	 * • Passe le statut de l’avis des 2 adhérents en ANOMALIE mais ne bloque pas leur accès à la bourse d’échange 
	 * • Aucun enregistrement de la transaction en unités de compte au débit ou au crédit de l’émetteur et du récepteur 
	 * Pour éviter cette situation à cause d’un « oubli», le système envoie un mail de rappel 48 heures avant la date d’échéance.
	 * @return
	 */
	List<Echange> searchAndUpdateEchangesASupprimer();
	
	/**
	  * Si seul le récepteur ou seul l’émetteur a renseigné un avis VALIDE sur l’échange, le système : 
	 * • Considère que l’échange est réputé « validé » et  passe son statut en FORCEVALID 
	 * • Enregistre la transaction en unités de compte au débit ou au crédit de l’émetteur et du récepteur qui a validé l’échange. 
	 * La contrepartie est le compte interne COUNTERPART 
	 * • Bloque l’accès à l’espace personnel de l’autre adhérent, passe son avis en ANOMALIE (= silencieux) et lui envoie un mail 
	 * Lorsque le système bloque l’accès d’un adhérent à son espace personnel, il passe toutes les PROPOSITIONS et toutes
	 * les REPONSES de cet adhérent dans la bourse d’échanges en statut BLOQUE
	 * @return
	 * @throws EntityAlreadyExistsException 
	 * @throws EntityNotFoundException 
	 */
	List<Echange> searchAndUpdateEchangesAForceValiderRecepteur() throws EntityNotFoundException, EntityAlreadyExistsException;

	/**
	  * Si seul le récepteur ou seul l’émetteur a renseigné un avis VALIDE sur l’échange, le système : 
	 * • Considère que l’échange est réputé « validé » et  passe son statut en FORCEVALID 
	 * • Enregistre la transaction en unités de compte au débit ou au crédit de l’émetteur et du récepteur qui a validé l’échange. 
	 * La contrepartie est le compte interne COUNTERPART 
	 * • Bloque l’accès à l’espace personnel de l’autre adhérent, passe son avis en ANOMALIE (= silencieux) et lui envoie un mail 
	 * Lorsque le système bloque l’accès d’un adhérent à son espace personnel, il passe toutes les PROPOSITIONS et toutes
	 * les REPONSES de cet adhérent dans la bourse d’échanges en statut BLOQUE
	 * @return
	 * @throws EntityAlreadyExistsException 
	 * @throws EntityNotFoundException 
	 */
	List<Echange> searchAndUpdateEchangesAForceValiderEmetteur() throws EntityNotFoundException, EntityAlreadyExistsException;

	List<Echange> searchAndUpdateEchangesAForceRefuserRecepteur() throws EntityNotFoundException;

	/**
	 * Si seul le récepteur ou seul l’émetteur a renseigné un avis REFUSE sur l’échange, le système : 
	 * • Considère que l’échange est réputé « refusé » et passe son statut en FORCEREFUS 
	 * • N’enregistre aucune transaction en unités de compte au débit ou au crédit de l’émetteur et du récepteur qui a refusé l’échange. 
	 * • Bloque l’accès à l’espace personnel de l’autre adhérent, passe son avis en ANOMALIE (= silencieux) et lui envoie un mail
	 * @return
	 * @throws EntityNotFoundException 
	 */
	List<Echange> searchAndUpdateEchangesAForceRefuserEmetteur() throws EntityNotFoundException;



	
}
