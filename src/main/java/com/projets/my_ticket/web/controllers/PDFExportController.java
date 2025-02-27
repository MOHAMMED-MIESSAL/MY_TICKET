package com.projets.my_ticket.web.controllers;

import com.projets.my_ticket.domain.Reservation;
import com.projets.my_ticket.service.ReservationService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class PDFExportController {

    private final ReservationService reservationService;

    @GetMapping("/export-reservation")
    public ResponseEntity<byte[]> generateReservationPDF(@RequestParam UUID reservationId) {
        try {
            // Charger le fichier .jrxml depuis le bon chemin
            InputStream reportStream = new ClassPathResource("report/mytemplate.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Récupérer la réservation par ID
            Optional<Reservation> reservation = reservationService.findById(reservationId);

            if (reservation.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // ✅ Transformer en liste pour JasperReports
            List<Reservation> reservationList = List.of(reservation.get());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reservationList);

            // Ajouter des paramètres (si nécessaire)
            Map<String, Object> parameters = new HashMap<>();

            // Remplir le rapport avec les données
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Exporter en PDF
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            // Retourner le PDF en réponse HTTP
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reservation.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
