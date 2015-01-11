package com.betalife.sushibuffet.util;

import java.util.List;

//import java.io.UnsupportedEncodingException;
//import java.util.List;
//
//import jpos.JposException;
//import jpos.POSPrinter;
//import jpos.POSPrinterConst;
//import jpos.POSPrinterControl113;
public class EpsonPrinter implements Printer {

	@Override
	public void print(List<String> lines, int times) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void print(byte[] img, int times) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLogo(String logo) {
		// TODO Auto-generated method stub

	}
	//
	// POSPrinterControl113 ptr = (POSPrinterControl113) new POSPrinter();
	// private String logo;
	//
	// public void print(List<String> lines, int times) throws Exception {
	// if (lines == null || lines.isEmpty()) {
	// return;
	// }
	// init();
	// logo();
	// printing(lines, times);
	// closing();
	// }
	//
	// public void print(byte[] img, int times) throws Exception {
	// if (img == null || img.length == 0) {
	// return;
	// }
	// init();
	// // logo();
	// printing(img, times);
	// closing();
	// }
	//
	// public static void main(String[] args) throws Exception {
	// Html2ImageBytes util = new Html2ImageBytes();
	// util.loadHtml("<b>今天是周六!</b> Please goto <a title=\"Goto Google\" href=\"http://www.baidu.com\">Google</a>.");
	// byte[] bytes = util.getBytes();
	// new EpsonPrinter().print(bytes, 1);
	// }
	//
	// /**
	// * Outline The processing code required in order to enable or to disable
	// use
	// * of service is written here.
	// *
	// * @exception JposException
	// * This exception is fired toward the failure of the method
	// * which JavaPOS defines.
	// */
	// /**
	// * When the window was closed
	// *
	// * @throws JposException
	// */
	// protected void init() throws JposException {
	// /** When the window open */
	// // JavaPOS's code for Step1
	// // Open the device.
	// // Use the name of the device that connected with your computer.
	// ptr.open("POSPrinter");
	//
	// // Get the exclusive control right for the opened device.
	// // Then the device is disable from other application.
	// ptr.claim(1000);
	//
	// // Enable the device.
	// ptr.setDeviceEnabled(true);
	// // JavaPOS's code for Step1--END
	// }
	//
	// //
	// ***********************Button*************************************************
	// /**
	// * Outline The code for using the most standard method "PrintNormal" to
	// * print is described.
	// *
	// * @throws JposException
	// * @throws UnsupportedEncodingException
	// */
	// void printing(List<String> lines, int times) throws JposException,
	// UnsupportedEncodingException {
	// for (int i = 0; i < times; i++) {
	// printing(lines);
	// }
	// }
	//
	// void printing(List<String> lines) throws JposException,
	// UnsupportedEncodingException {
	// // printNormal(int station, String data)
	// // A string is sent by using the method "printNormal", and it is
	// // printed.
	// // "\n" is the standard code for starting a new line.
	// // When the end of the line have no "\n",printing by
	// // using the method "printNormal" doesn't start, may be.
	// for (String line : lines) {
	// ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, line);
	// }
	// // cut paper
	// ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|fP");
	// }
	//
	// void printing(byte[] img) throws JposException {
	// for (int i = 0; i < times; i++) {
	// printing(img);
	// }
	// }
	//
	// void printing(byte[] img) throws JposException {
	// ptr.printMemoryBitmap(POSPrinterConst.PTR_S_RECEIPT, img,
	// POSPrinterConst.PTR_BMT_BMP,
	// ptr.getRecLineWidth(), POSPrinterConst.PTR_BM_CENTER);
	// // cut paper
	// ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|fP");
	// }
	//
	// //
	// ***********************Method*************************************************
	// /**
	// * Outline The code to finish a service.
	// *
	// * @throws JposException
	// */
	// void closing() throws JposException {
	// // JavaPOS's code for Step1
	// // Cancel the device.
	// ptr.setDeviceEnabled(false);
	//
	// // Release the device exclusive control right.
	// ptr.release();
	//
	// // Finish using the device.
	// ptr.close();
	// // JavaPOS's code for Step1--END
	// }
	//
	// void logo() throws JposException {
	//
	// // Output by the high quality mode
	// ptr.setRecLetterQuality(true);
	// // Register a bitmap
	// if (ptr.getCapRecBitmap() == true) {
	// // Register a bitmap
	// ptr.setBitmap(1, POSPrinterConst.PTR_S_RECEIPT, logo,
	// POSPrinterConst.PTR_BM_ASIS,
	// POSPrinterConst.PTR_BM_CENTER);
	// }
	// // print logo
	// // ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|1B");
	//
	// }
	//
	// public String getLogo() {
	// return logo;
	// }
	//
	// public void setLogo(String logo) {
	// this.logo = logo;
	// }
	//
}
