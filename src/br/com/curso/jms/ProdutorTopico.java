package br.com.curso.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class ProdutorTopico {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			InitialContext context = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory)context.lookup("ConnectionFactory");
			Connection connection = factory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);		
			Topic topic = (Topic)context.lookup("MyTopic");//Obtendo o tópico via jndi
			MessageProducer producer = session.createProducer(topic);
			TextMessage message = session.createTextMessage("MENSAGEM DE TEXTO P/ O TÓPICO");
			message.setBooleanProperty("mensagemVerdadeira", true);
			message.setStringProperty("meuNome", "Renan");
			producer.send(message);
			new Scanner(System.in).nextLine();
			session.close();
			connection.close();
			context.close();		
		} catch (Exception exception) {
			System.out.println(exception);
		}
	}

}
