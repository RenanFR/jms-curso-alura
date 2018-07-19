package br.com.curso.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReceptorMensagem {
		
	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext initialContext = new InitialContext();//Classe p/ buscar endereços JNDI passados via arquivo jndi.properties ou construtor
		ConnectionFactory factory = (ConnectionFactory)initialContext.lookup("ConnectionFactory");//Busca a factory
		Connection connection = factory.createConnection();//Cria a connection com o ActiveMQ pelo endereço no jndi.properties
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//Gera sessão do consumidor/produtor com MOM (Abstraí transações)
		Destination queue = (Destination)initialContext.lookup("MyQueue");//Busca um destino/origem (Fila/Tópico) a partir da qual iremos interagir com as mensagens
		MessageConsumer consumer = session.createConsumer(queue);//Cria consumidor p/ a fila com base na sessão
		Message message = consumer.receive();//Recebe uma única mensagem
		System.out.println("MENSAGEM RECEBIDA: " + message);
		new Scanner(System.in).nextLine();
		session.close();
		connection.close();
		initialContext.close();
		
	}

}
