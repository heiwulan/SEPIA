package de.uni.freiburg.iig.telematik.sepia.serialize.test;

import java.io.IOException;

import de.invation.code.toval.parser.ParserException;
import de.invation.code.toval.validate.ParameterException;
import de.uni.freiburg.iig.telematik.sepia.graphic.AbstractGraphicalPN;
import de.uni.freiburg.iig.telematik.sepia.parser.pnml.PNMLParser;
import de.uni.freiburg.iig.telematik.sepia.serialize.PNSerialization;
import de.uni.freiburg.iig.telematik.sepia.serialize.formats.PNSerializationFormat;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws ParameterException 
	 * @throws ParserException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {
		PNMLParser parser = new PNMLParser();
		AbstractGraphicalPN net = parser.parse("/Users/stocker/Desktop/ptnet.pnml");
		System.out.println(net.getPetriNet().getNetType());
//		System.out.println(net.getPetriNet());
		PNSerialization.serialize(net, PNSerializationFormat.PNML, "/Users/stocker/Desktop/ptnetout.pnml");
		
//		AbstractGraphicalPN net = parser.parse("/Users/stocker/Desktop/cpn.pnml");
//		System.out.println(net.getPetriNet().getNetType());
//		System.out.println(net.getPetriNet());
//		PNSerialization.serialize(net.getPetriNet(), PNSerializationFormat.PNML, "/Users/stocker/Desktop/cpnout.pnml");
		
//		AbstractGraphicalPN net = parser.parse("/Users/stocker/Desktop/ifnet.pnml");
//		System.out.println(net.getPetriNet().getNetType());
//		System.out.println(net.getPetriNet());
//		PNSerialization.serialize(net, PNSerializationFormat.PNML, "/Users/stocker/Desktop/ifnetout.pnml");
	}

}
