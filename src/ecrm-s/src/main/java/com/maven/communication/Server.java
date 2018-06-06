package com.maven.communication;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class Server {
	
	public static void main(String[] args) {
		try {
			IoAcceptor acceptor = new NioSocketAcceptor();  
	        acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );  
	        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));  
	        acceptor.setHandler(  new TimeServerHandler() );  
	          
	        acceptor.getSessionConfig().setReadBufferSize( 2048 );  
	        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );  
	          
	        acceptor.bind(new InetSocketAddress(1008));  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
