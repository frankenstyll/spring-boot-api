package com.dsl.controller;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dsl.model.PostDueDateDebtDTO;
import com.dsl.service.BatchInvoiceServiceImpl;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

@RestController
public class BatchInvoiceController {

	private static final Logger logger = LogManager.getLogger(BatchInvoiceController.class);
	
	private final String fileName = "InvoiceEmailAttach.jrxml";
	
	@Autowired
	BatchInvoiceServiceImpl batchInvoiceServices;
	
	@PostMapping(value = "/report/InvoiceEmailAttach")
	public ResponseEntity<Object>  createInvoiceEmailAttach(@RequestBody PostDueDateDebtDTO postDueDateDebt) throws Exception {
		
		logger.info("[START]generate InvoiceEmailAttach");
		byte[] bytes = null;
		
		try {
			
			if(null != postDueDateDebt) {
				
				Map<String, Object> params  =  new HashMap<String, Object>();
				
				String report = "src/main/resources/report/" + this.fileName;
				params = batchInvoiceServices.getMapParamBatchInvoice(postDueDateDebt);
				
				//bytes = JasperRunManager.runReportToPdf(jasperReport, params, new JREmptyDataSource());
				JasperReport jasperReport = JasperCompileManager.compileReport(report);
				
				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
		        JRPdfExporter exporter = new JRPdfExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
				SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
				if( null != postDueDateDebt.getPassword() ) {
					configuration.setEncrypted(true);
					configuration.set128BitKey(true);
					configuration.setUserPassword(postDueDateDebt.getPassword().toString());
					configuration.setOwnerPassword(postDueDateDebt.getPassword().toString());
				}
				exporter.setConfiguration(configuration);
				exporter.exportReport();
				bytes = byteArray.toByteArray();
				
				logger.info("[END]generate InvoiceEmailAttach");
				return ResponseEntity.status(HttpStatus.OK).body(bytes);
				
			}else  {
				logger.info("PostDueDateDebtDTO Parameter is null" );
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}

		}catch(Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
