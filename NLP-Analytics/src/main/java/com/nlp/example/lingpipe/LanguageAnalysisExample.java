package com.nlp.example.lingpipe;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.Classification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;

public class LanguageAnalysisExample {
	
	
	public static void main(String[] args) {
		LanguageAnalysisExample.basicLanguageAnalysis();
	}
	
	private static void basicLanguageAnalysis() {
        System.out.println("---------------");
        //http://www.rottentomatoes.com/m/forrest_gump/
        String text = "An overly sentimental film with a somewhat "
                + "problematic message, but its sweetness and charm "
                + "are occasionally enough to approximate true depth "
                + "and grace. ";
        
        text = "Svenska är ett östnordiskt språk som talas av cirka "
                + "tio miljoner personer[1], främst i Finland "
                + "och Sverige.";
        text = "¡Buenos días, clase! Good morning, class! Hola, ¿Cómo están hoy? Hello, how are you today? Adiós, ¡hasta luego! Bye, see you soon!";
     
        System.out.println("Text: " + text);
      
        LMClassifier classifier = null;
        try {
            classifier = (LMClassifier) AbstractExternalizable.readObject(new File("langid-leipzig.classifier"));
        } catch (IOException  ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Classification classification  = classifier.classify(text);
        String bestCategory = classification.bestCategory();
        System.out.println("Best Language: " + bestCategory);

        for (String category : classifier.categories()) {
            System.out.println(category);
        }
    }

}
