package org.Backend.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.Backend.Entities.BookingDetails;
import org.Backend.Entities.Bookings;
import org.Backend.Entities.Employee;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

@Component
@Scope("prototype")
public class ReportCreator {

	private ByteArrayOutputStream byteoutputStream;
	private Document document;
	private PdfWriter writer;
	private HeaderFooterPageEvent event;
	private LineSeparator ls = new LineSeparator();
	private Bookings firstToGetGeneralAppointmentDetails;
	private byte[] byteReport;	

	public ReportCreator() throws DocumentException {
		byteoutputStream = new ByteArrayOutputStream();
		document = new Document(PageSize.A4, 36, 36, 90, 36);
		writer = PdfWriter.getInstance(document, byteoutputStream);
		event = new HeaderFooterPageEvent();
		writer.setPageEvent(event);
	}

	public byte[] retrieveAsByte(List<BookingDetails> details, Employee creator, String description)
			throws DocumentException {
		
		dataSetup(details, creator, description);
		byteReport = byteoutputStream.toByteArray();
		
		
		return byteReport;
	}

	public void generateFile(List<BookingDetails> details, Employee creator, String description)
			throws DocumentException, URISyntaxException, IOException {

		dataSetup(details, creator, description);
		OutputStream outStream = new FileOutputStream(
				new File(ReportCreator.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath()
						+ "/" + firstToGetGeneralAppointmentDetails.getId() + "_"
						+ firstToGetGeneralAppointmentDetails.getTitle() + ".pdf");
		byteoutputStream.writeTo(outStream);
	}
	
	public boolean generateFileFromByte(byte[] inputByteReport) throws URISyntaxException, IOException
			 {	
		
		OutputStream outStream = new FileOutputStream(
				new File(ReportCreator.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath()
						+ "/" + firstToGetGeneralAppointmentDetails.getId() + "_"
						+ firstToGetGeneralAppointmentDetails.getTitle() + ".pdf");
		
		ByteArrayOutputStream byteStreamFromByteArray = new ByteArrayOutputStream();
		byteStreamFromByteArray.write(inputByteReport);		
		byteoutputStream.writeTo(outStream);
		
		return true;		
	}

	private void dataSetup(List<BookingDetails> details, Employee creator, String description)
			throws DocumentException {

		document.open();

		setupDocumentMetaInfos(creator);
		firstToGetGeneralAppointmentDetails = details.get(0).getBooking();
		PdfPTable generalAppointmentDetails = new PdfPTable(6);
		document.add(new Paragraph("General information: "));
		generalAppointmentDetails.setSpacingBefore(10f);
		setupGeneralAppointmentDetails(firstToGetGeneralAppointmentDetails, generalAppointmentDetails);
		document.add(generalAppointmentDetails);
		document.add(new Paragraph("Attendees information: "));
		PdfPTable appointmentAttendeesDetailsTable = new PdfPTable(4);
		appointmentAttendeesDetailsTable.setSpacingBefore(10f);
		setupAppointmentAttendeesDetailsTable(appointmentAttendeesDetailsTable);
		appointmentAttendeesDetailsTableFill(appointmentAttendeesDetailsTable, details);
		document.add(appointmentAttendeesDetailsTable);
		document.add(new Chunk(ls));
		setupDescriptionPart(description);

		document.close();
	}

	private void setupDocumentMetaInfos(Employee creator) {
		document.addTitle("Szabo Counseling Appointment Report");
		document.addSubject("Appointment Report");
		document.addAuthor(creator.getName() + " pid:" + creator.getPersonalId());
		document.addCreator(creator.getName() + " pid" + creator.getPersonalId());
	}

	private void setupGeneralAppointmentDetails(Bookings firstToGetGeneralAppointmentDetails,
			PdfPTable generalAppointmentDetails) throws DocumentException {
	//	generalAppointmentDetails.setWidthPercentage(100);
		generalAppointmentDetails.setHorizontalAlignment(Element.ALIGN_LEFT);
		generalAppointmentDetails.setWidths(new int[] { 10, 10, 10, 10, 11, 10 });
		generalAppointmentDetails.getDefaultCell().setBorder(Rectangle.BOX);
		generalAppointmentDetails.addCell("Title");
		generalAppointmentDetails.addCell("Start");
		generalAppointmentDetails.addCell("End");
		generalAppointmentDetails.addCell("Room");
		generalAppointmentDetails.addCell("Notification?");
		generalAppointmentDetails.addCell("Archived?");
		generalAppointmentDetails.addCell(firstToGetGeneralAppointmentDetails.getTitle());
		generalAppointmentDetails.addCell(localDateTimeFormatting(firstToGetGeneralAppointmentDetails.getStart()));
		generalAppointmentDetails.addCell(localDateTimeFormatting(firstToGetGeneralAppointmentDetails.getEnd()));
		generalAppointmentDetails.addCell(firstToGetGeneralAppointmentDetails.getRoom());
		
		if(firstToGetGeneralAppointmentDetails.getNotificationId()!=null) {	
		generalAppointmentDetails.addCell(setupnotificationCellTable(firstToGetGeneralAppointmentDetails));		
		generalAppointmentDetails.addCell("" + firstToGetGeneralAppointmentDetails.isArchived());
		return;
		}
		generalAppointmentDetails.addCell(" None");
		generalAppointmentDetails.addCell("" + firstToGetGeneralAppointmentDetails.isArchived());
	}
	
	private PdfPTable setupnotificationCellTable(Bookings inFirstToGetGeneralAppointmentDetails) {
		PdfPTable notificationCellTable = new PdfPTable(1);
		notificationCellTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		notificationCellTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		notificationCellTable.addCell("Email: " + inFirstToGetGeneralAppointmentDetails.getNotificationId().isEmail());
		notificationCellTable.addCell("SMS: " + inFirstToGetGeneralAppointmentDetails.getNotificationId().isSms());		
		return notificationCellTable;
	}

	private void setupAppointmentAttendeesDetailsTable(PdfPTable appointmentAttendeesDetailsTable)
			throws DocumentException {
		//appointmentAttendeesDetailsTable.setWidthPercentage(50);
		appointmentAttendeesDetailsTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		appointmentAttendeesDetailsTable.setWidths(new int[] { 1, 1, 1, 1 });
		appointmentAttendeesDetailsTable.getDefaultCell().setBorder(Rectangle.BOX);
		appointmentAttendeesDetailsTable.addCell("ID");
		appointmentAttendeesDetailsTable.addCell("Name");
		appointmentAttendeesDetailsTable.addCell("Type");
		appointmentAttendeesDetailsTable.addCell("Response");
	}

	private void appointmentAttendeesDetailsTableFill(PdfPTable appointmentAttendeesDetailsTable,
			List<BookingDetails> details) {

		for (BookingDetails bd : details) {

			if (bd.getEmployee() == null) {
				appointmentAttendeesDetailsTable.addCell(bd.getPatient().getMedicalId().toString());
				appointmentAttendeesDetailsTable.addCell(bd.getPatient().getName());
				appointmentAttendeesDetailsTable.addCell(bd.getPatient().getType().toString());
				appointmentAttendeesDetailsTable.addCell(bd.getResponse().toString());
				continue;
			}

			appointmentAttendeesDetailsTable.addCell(bd.getEmployee().getPersonalId());
			appointmentAttendeesDetailsTable.addCell(bd.getEmployee().getName());
			appointmentAttendeesDetailsTable.addCell(bd.getEmployee().getType().toString());
			appointmentAttendeesDetailsTable.addCell(bd.getResponse().toString());
		}
	}

	private void setupDescriptionPart(String description) throws DocumentException {
		Paragraph descriptionTitleParagraph = new Paragraph("Appointment description: ");
		descriptionTitleParagraph.setSpacingAfter(15f);
		document.add(descriptionTitleParagraph);
		Paragraph descriptionParagraph = new Paragraph(description);
		document.add(descriptionParagraph);
	}

	private String localDateTimeFormatting(LocalDateTime in) {		
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(in);
	}
	
	
}
