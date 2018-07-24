package br.com.curso.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class ReceptorTopico {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			InitialContext context = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory)context.lookup("ConnectionFactory");
			Connection connection = factory.createConnection();
			connection.setClientID("conReceptorTopico");
			connection.start();
			Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			Topic topic = (Topic)context.lookup("MyTopic");
			MessageConsumer consumer = session.createDurableSubscriber(topic, "receptorTopic", "mensagemVerdadeira=true AND meuNome='Renan'", false);
//		MessageConsumer consumer = session.createDurableSubscriber(topic, "receptorTopic");
			consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					TextMessage textMessage = (TextMessage)message;
					try {
						System.out.println(textMessage.getText());
					} catch (JMSException jmsException) {
						
					}				
				}
			});
			new Scanner(System.in).nextLine();
			session.close();
			connection.close();
			context.close();
		} catch(Exception exception) {
			System.out.println(exception);
		}
	}

}
