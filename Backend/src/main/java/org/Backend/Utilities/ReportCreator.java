package org.Backend.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.List;

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
import com.itextpdf.text.pdf.PdfPCell;
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

	private void dataSetup(List<BookingDetails> details, Employee creator, String description)
			throws DocumentException {

		document.open();

		setupDocumentMetaInfos(creator);
		firstToGetGeneralAppointmentDetails = details.get(0).getBooking();
		PdfPTable generalAppointmentDetails = new PdfPTable(6);
		setupGeneralAppointmentDetails(firstToGetGeneralAppointmentDetails, generalAppointmentDetails);
		document.add(generalAppointmentDetails);
		PdfPTable appointmentAttendeesDetailsTable = new PdfPTable(4);
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
		generalAppointmentDetails.setWidthPercentage(50);
		generalAppointmentDetails.setHorizontalAlignment(Element.ALIGN_LEFT);
		generalAppointmentDetails.setWidths(new int[] { 1, 1, 1, 1, 1, 1 });
		generalAppointmentDetails.getDefaultCell().setBorder(Rectangle.BOX);
		generalAppointmentDetails.addCell("Title");
		generalAppointmentDetails.addCell("Start");
		generalAppointmentDetails.addCell("End");
		generalAppointmentDetails.addCell("Room");
		generalAppointmentDetails.addCell("Notification?");
		generalAppointmentDetails.addCell("Archived?");
		generalAppointmentDetails.addCell(firstToGetGeneralAppointmentDetails.getTitle());
		generalAppointmentDetails.addCell(firstToGetGeneralAppointmentDetails.getStart().toString());
		generalAppointmentDetails.addCell(firstToGetGeneralAppointmentDetails.getEnd().toString());
		generalAppointmentDetails.addCell(firstToGetGeneralAppointmentDetails.getRoom());
		generalAppointmentDetails.addCell("@: " + firstToGetGeneralAppointmentDetails.getNotificationId().isEmail()
				+ " sms: " + firstToGetGeneralAppointmentDetails.getNotificationId().isSms());
		generalAppointmentDetails.addCell("" + firstToGetGeneralAppointmentDetails.isArchived());
	}

	private void setupAppointmentAttendeesDetailsTable(PdfPTable appointmentAttendeesDetailsTable)
			throws DocumentException {
		appointmentAttendeesDetailsTable.setWidthPercentage(50);
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
		Paragraph descriptionTitleParagraph = new Paragraph();
		descriptionTitleParagraph.add(new Chunk("Appointment description: "));
		document.add(descriptionTitleParagraph);
		Paragraph descriptionParagraph = new Paragraph(description);
		document.add(descriptionParagraph);
	}

}
