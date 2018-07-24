package br.com.curso.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class ProdutorMensagem {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();//Classe p/ buscar endereços JNDI passados via arquivo jndi.properties ou construtor
		ConnectionFactory connectionFactory = (ConnectionFactory)context.lookup("ConnectionFactory");//Busca a factory
		Connection connection = connectionFactory.createConnection();//Cria a connection com o ActiveMQ pelo endereço no jndi.properties
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//Gera sessão do consumidor/produtor com MOM (Abstraí transações)
		Destination queue = (Destination)context.lookup("MyQueue");
		MessageProducer producer = session.createProducer(queue);//Cria um produtor de mensagem p/ a fila
		TextMessage message = session.createTextMessage();//Cria uma mensagem a partir da sessão
		message.setText("ENVIANDO MENSAGEM PARA A FILA");
		producer.send(message);//Envia por meio do produtor
		new Scanner(System.in).nextLine();
		session.close();
		connection.close();
		context.close();
	}

}
