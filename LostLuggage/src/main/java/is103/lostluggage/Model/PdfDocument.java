/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author Arthur
 */
public class PdfDocument {
    
    //New pdddocument
    private PDDocument document;
    
    //Application that made the pdf?
    final private String CREATOR = "Lostluggage";
    
    //Leading
    final private double LEADING =  14.5;
    
    //Person that filled in the form?
    private String author = "Arthur & Daron";
    
    //Title = registrationnr?
    private String title = "93849234";
    
    
    //Constructor
    public PdfDocument(){
        
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCREATOR() {
        return CREATOR;
    }
    
    public void setAuthor(String author){
        this.author = author;
    }
    
    public String getAuthor(){
        return author;
    }
        
}