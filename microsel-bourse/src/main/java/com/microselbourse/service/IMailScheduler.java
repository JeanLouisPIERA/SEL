package com.microselbourse.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

public interface IMailScheduler {

	public void sendMailsEchangesASupprimerToEmetteurList() throws MessagingException, UnsupportedEncodingException;

	public void sendMailsEchangesASupprimerToRecepteurList() throws MessagingException, UnsupportedEncodingException;

	public void sendMailsEchangesAForceValiderList() throws MessagingException, UnsupportedEncodingException;

	public void sendMailsEchangesAForceRefuserList() throws MessagingException, UnsupportedEncodingException;

}
