package com.example.call;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.twiml.Gather;
import com.twilio.twiml.Play;
import com.twilio.twiml.Say;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;

@RestController
public class controller {

	@RequestMapping(value="/menu", produces=MediaType.APPLICATION_XML_VALUE)
	public String playMenu(){
		VoiceResponse response = new VoiceResponse.Builder()

				.gather(new Gather.Builder()
	                    .action("/menu/show")
	                    .finishOnKey("#")
	                    .numDigits(1)
	    				.play(new Play.Builder("https://s3-us-west-2.amazonaws.com/resumeapp/Welcome.mp3")
	    	                    .loop(3)
	    	                    .build())
	    				.timeout(10)
	                    .build())
	            
	            .build();

	    try {
	    	
	        return response.toXml();
	    } catch (TwiMLException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@RequestMapping(value="/menu/show", method={RequestMethod.GET, RequestMethod.POST}, produces=MediaType.APPLICATION_XML_VALUE)
	public String showMenu(@RequestParam(name="Digits", required=true) String digit, HttpServletResponse httpResponse) throws IOException{

        VoiceResponse response = null;
        switch (digit) {
            case "1":
                response = new VoiceResponse.Builder()
        	            .play(new Play.Builder("https://s3-us-west-2.amazonaws.com/resumeapp/Summary_background.mp3")
        	                    .build())
        	            .build();
                
                break;
            case "2":
                response = new VoiceResponse.Builder()
        	            .play(new Play.Builder("https://s3-us-west-2.amazonaws.com/resumeapp/Gogo_projects.mp3")
        	                    .build())
        	            .build();
                break;
            case "3":
                response = new VoiceResponse.Builder()
        	            .play(new Play.Builder("https://s3-us-west-2.amazonaws.com/resumeapp/Old_projects.mp3")
        	                    .build())
        	            .build();
                break;
             
            case "7":
            	response = new VoiceResponse.Builder().say(new Say.Builder("Thank you and have a good day").build()).build();
            	break;
            default:
            	httpResponse.sendRedirect("/menu");
            	 return null;
        }
        
	    try {
	        return response.toXml();
	    } catch (TwiMLException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	
}
