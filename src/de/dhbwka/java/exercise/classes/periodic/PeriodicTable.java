package de.dhbwka.java.exercise.classes.periodic;

/**
 * @author DHBW lecturer
 * @version 1.0
 * 
 * Part of lectures on 'Programming in Java'. Baden-Wuerttemberg
 * Cooperative State University.
 * 
 * (C) 2016 by J. Sidler, T. Schlachter, C. Schmitt, W. Suess
 */
public class PeriodicTable {
	
	private Element[] elements = new Element[119];
	
	public PeriodicTable() {
	}
	
	public void addElement(Element e) {
		if (! hasElement(e.getOrdinal()))
			elements[e.getOrdinal()] = e;
	}
	
	public boolean hasElement(int o) {
		return elements[o] != null;
	}

	public Element getElement(int o) {
		return elements[o];		
	}
	
	public Element[] getMetals() {
		int countMetals = 0;
		for (Element e : elements)
			if (e != null && e instanceof Metal)
				countMetals++;
		Element[] result = new Element[countMetals];
		int pos = 0;
		for (Element e : elements)
			if (e != null && e instanceof Metal)
				result[pos++] = e;		
		return result;
	}

	public static void main(String[] args) {	
		PeriodicTable pt = new PeriodicTable();
		pt.addElement(new Element("Wasserstoff", "H", 1, 'K', Element.GAS, Element.MAIN));
		pt.addElement(new Element("Helium", "He", 2, 'K', Element.GAS, Element.MAIN));
		pt.addElement(new Metal("Natrium", "Na", 11, 'M', Element.SOLID, Element.MAIN,false,21E6));
		pt.addElement(new Metal("Eisen", "Fe", 26, 'N', Element.SOLID, Element.SIDE,false,10.02E6));
		pt.addElement(new Metal("Germanium", "Ge", 32, 'N', Element.SOLID, Element.SIDE,true,1.45));
		pt.addElement(new Element("Brom", "Br", 35, 'N', Element.LIQUID, Element.MAIN));
		pt.addElement(new Metal("Tellur", "Te", 52, 'O', Element.SOLID, Element.MAIN,true,0.005));
		pt.addElement(new Metal("Gold", "Au", 79, 'P', Element.SOLID, Element.SIDE,false,44E6));

		System.out.println("Elemente:");
		for(Element e : pt.elements)
			if (e != null)
				System.out.println(e);
		
		System.out.println("\nMetalle:");
		for(Element e : pt.getMetals())
			System.out.println(e);
		
		System.out.println("\nGold:");
		System.out.println(pt.getElement(79));
		
	}
	
}
