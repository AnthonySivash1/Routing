package com.anthony.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
public class SimpleRouteBuilder extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		//splitter splits it based on token 
		//from("file:C:/inputFolder").split().tokenize("\n").to("jms:queue:javainuse");
		
		
		
		//contebt based router
//		from("file:C:/inputFolder").split().tokenize("\n").to("direct:test");
//        
//		//Content Based routing- Route the message based on the token it contains.
//		        from("direct:test"). 
//		        choice().
//		        when(body().contains("javainuse1"))
//		        .to("jms:queue:javainuse1").
//		        when(body().contains("javainuse2"))
//		        .to("jms:queue:javainuse2")
//		        .when(body().contains("javainuse3"))
//		        .to("jms:queue:javainuse3").
//		        otherwise().
//		        to("jms:queue:otherwise");
		       //3. Recepient list
		//from("file:C:/inputFolder").split().tokenize("\n").to("direct:test");
        
		//Recipient List- Dynamically set the recipients of the exchange 
		         //by creating the queue name at runtime
//		        from("direct:test")
//		        .process(new Processor() {
//		            public void process(Exchange exchange) throws Exception {
//		               String recipient = exchange.getIn().getBody().toString();
//		               String recipientQueue="jms:queue:"+recipient;
//		               exchange.getIn().setHeader("queue", recipientQueue);
//		      }
//		      }).recipientList(header("queue"));
		       
		//
		//Wire taping
from("file:C:/inputFolder").split().tokenize("\n").to("direct:test1");
        
        from("direct:test1")
        //Wire Tap:Suppose get some error so send seperate copies of the message to 
        //DeadLetter queue and also to direct:test2 
        .wireTap("jms:queue:DeadLetterQueue")
        .to("direct:test2");
        
        from("direct:test2")
        .process(new Processor() {
            public void process(Exchange arg0) throws Exception {
              System.out.println(arg0.getIn().getBody().toString());
            }
      });
	}
	

}
