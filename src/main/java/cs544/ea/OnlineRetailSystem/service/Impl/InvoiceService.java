package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.Invoice;
import cs544.ea.OnlineRetailSystem.domain.Order;
import cs544.ea.OnlineRetailSystem.repository.InvoiceRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private ItemServiceImpl itemService;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice generateInvoice(Invoice invoice) {
        // Logic for generating invoice

        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("Invoice Number: " + invoice.getInvoiceNumber());
            // ... other content
            contentStream.showText("Customer Name:  " + invoice.getCustomerName());
            contentStream.showText("Item:  " + itemService.getAllItems());
            //contentStream.showText("Customer Name:  " + itemService.);




            contentStream.endText();
            contentStream.close();

            // Save the generated PDF to a temporary location or attach it to the invoice entity
            // For simplicity, let's assume we attach the PDF to the invoice entity
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            invoice.setInvoicePdf(outputStream.toByteArray());

            document.close();
        } catch (IOException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
        }

        // Save the invoice to the database
        return invoiceRepository.save(invoice);
    }

    public Invoice findById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId).orElse(null);
    }

//    public void sendInvoiceByEmail(Invoice invoice, String customerEmail) {
//        // Logic for sending invoice via email
//
//        // Example: Sending an email with the invoice PDF attachment
//        try {
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setTo(customerEmail);
//            helper.setSubject("Invoice for " + invoice.getInvoiceNumber());
//            helper.setText("Dear Customer,\n\nPlease find attached the invoice for your recent purchase.");
//
//            // Attach the invoice PDF to the email
//            ByteArrayDataSource dataSource = new ByteArrayDataSource(invoice.getInvoicePdf(), "application/pdf");
//            helper.addAttachment("invoice.pdf", dataSource);
//
//            // Send the email
//            javaMailSender.send(message);
//        } catch (MessagingException e) {
//            // Handle exceptions appropriately
//            e.printStackTrace();
//        }
//    }
}
