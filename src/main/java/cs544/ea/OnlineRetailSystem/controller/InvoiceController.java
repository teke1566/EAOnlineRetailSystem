package cs544.ea.OnlineRetailSystem.controller;

import cs544.ea.OnlineRetailSystem.domain.Invoice;
import cs544.ea.OnlineRetailSystem.service.Impl.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        Invoice generatedInvoice = invoiceService.generateInvoice(invoice);
        // Return the generated invoice with HTTP status code 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(generatedInvoice);
    }

    @PostMapping("/{invoiceId}/send")
    public ResponseEntity<String> sendInvoice(@PathVariable Long invoiceId, @RequestParam String customerEmail) {
        Invoice invoice = invoiceService.findById(invoiceId);
        if (invoice != null) {
          // invoiceService.sendInvoiceByEmail(invoice, customerEmail);
            return ResponseEntity.ok("Invoice sent successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

