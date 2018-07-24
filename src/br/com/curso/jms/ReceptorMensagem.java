package br.com.curso.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class ReceptorMensagem {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory)context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		Destination queue = (Destination)context.lookup("MyQueue");//Busca um destino/origem (Fila/Tópico) a partir da qual iremos interagir com as mensagens
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer consumer = session.createConsumer(queue);//Cria consumidor p/ a fila com base na sessão
//		Message message = consumer.receive();//Recebe uma única mensagem
		consumer.setMessageListener(new MessageListener() {//Configura um Listener persistente que trata as mensagens no momento em que chegam
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage)message;//A mensagem pode ser traduzida para um tipo mais específico de acordo com seu conteúdo
				try {
					System.out.println(textMessage.getText());
					//Convertendo a mensagem p/ seu tipo específico ganhamos acesso a operações específicas p/ manipular o conteúdo
				} catch (JMSException e) {
					
				} 
			}
		});
//		System.out.println("MENSAGEM RECEBIDA: " + message);
		new Scanner(System.in).nextLine();
		session.close();
		connection.close();
		context.close();		
	}

}
