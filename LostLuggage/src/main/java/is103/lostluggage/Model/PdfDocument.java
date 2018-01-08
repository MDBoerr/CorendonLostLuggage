/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    //The PDF Document that is going to be filled and exported
    private final PDDocument document = new PDDocument();

    //Document information for the pdf document
    private final PDDocumentInformation docInfo = document.getDocumentInformation();

    //The first page in the pdf document
    private final PDPage page1 = new PDPage();

    //The stream used to write to the pages
    private PDPageContentStream contentStream;

    //List of strings that are passed through during initialization
    private ArrayList<String> formText = new ArrayList();

    private String formType = "";

    final private String[] lostFormLayout = {"Registration Number: ", "Employee: ", "Date: ", "Time: ", "Airport: ",
        "Name: ", "Address: ", "Place of residence: ", "Postalcode: ", "Country: ", "Phone: ", "E-mail: ",
        "Labelnumber: ", "Flight: ", "Type: ", "Brand: ", "Color: ", "Second color: ", "Dimensions: ", "Weight: ", "Characteristics"
    };

    final private String[] foundFormLayout = {"Registration Number: ", "Employee: ", "Date: ", "Time: ", "Airport: ",
        "Labelnumber: ", "Flight: ", "Type: ", "Brand: ", "Color: ", "Second color: ", "Dimensions: ", "Weight: ", "Characteristics: ", "Location: ",
        "Name: ", "Address: ", "Place of residence: ", "Postalcode: ", "Country: ", "Phone: ", "E-mail: "
    };

    final private String[] retrievedLayout = {"Registration Number: ", "Date: ", "Passenger name: ", "Employee: ", "Deliverer: "};

    private String filename = "";

    //Object constructor
    public PdfDocument(String type, ArrayList<String> formText, String filename) throws IOException {

        //Set object properties
        this.filename = filename;
        this.formText = formText;
        this.formType = type;

        if (type.equals("Found")) {
            docInfo.setTitle("Found Luggage: " + formText.get(0));
        } else if (type.equals("Lost")) {
            docInfo.setTitle("Lost Luggage: " + formText.get(0));
        } else {
            docInfo.setTitle("Retrieved: " + formText.get(0));
        }

        //Adds the text to the pdf
        this.addAllTheText();

    }

    //adds a new line to the document
    public void addAllTheText() throws IOException {

        //Initialize a contentstream to write to the first page of the document
        this.contentStream = new PDPageContentStream(document, page1);

        //Begin the text writing
        this.contentStream.beginText();

        //Set the font
        this.contentStream.setFont(PDType1Font.HELVETICA, 12);

        //set the leading of the page
        this.contentStream.setLeading(14.5f);

        //new lines at this offset
        // 25 from left (higher number means more to right, 750 from bottom(higher number means more to top)
        this.contentStream.newLineAtOffset(25, 750);

        //Show the text on the page
        for (int x = 0; x < formText.size(); x++) {

            if (this.formType.equals("Lost")) {
                contentStream.showText(lostFormLayout[x]);
            } else if (this.formType.equals("Found")) {
                contentStream.showText(foundFormLayout[x]);
            } else {
                contentStream.showText(retrievedLayout[x]);
            }

            contentStream.showText(formText.get(x));

            contentStream.newLine();
        }

        //End the text
        contentStream.endText();

        //End the stream
        contentStream.close();

        //Add the first page to the pdf document
        this.document.addPage(this.page1);

        //USe the save method to save the pdf document to the users computer
        this.savePdf(this.filename);
    }

    //Method to save the pdf to the location given
    public void savePdf(String filename) {
        try {
            this.document.save(filename + ".pdf");
            this.document.close();
        } catch (IOException ex) {
            Logger.getLogger(PdfDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}